package net.runecrypt.codec.session.impl;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.runecrypt.Server;
import net.runecrypt.codec.Codec;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.codec751.network.codec.ondemand.XorEncoder;
import net.runecrypt.codec.messages.UpdateEncryptionMessage;
import net.runecrypt.codec.messages.UpdateStatusMessage;
import net.runecrypt.codec.messages.UpdateVersionMessage;
import net.runecrypt.codec.session.Session;
import net.runecrypt.ondemand.FileRequest;
import net.runecrypt.ondemand.FileResponse;
import net.runecrypt.ondemand.UpdateService;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.openrs.cache.Cache;

/**
 * @author Graham
 * @author Sean
 */
public final class UpdateSession extends Session {
	
	private static final Logger logger = Logger.getLogger(UpdateSession.class.getName());
	private final UpdateService service;
	private final Deque<FileRequest> fileQueue = new ArrayDeque<FileRequest>();
	private boolean idle = true;
	private boolean handshakeComplete = false;
	private CodecManifest manifest;
	private Server server;

	/**
	 * Creates a new UpdateSession.
	 * @param context The channel handler context.
	 */
	public UpdateSession(ChannelHandlerContext context, Codec codec, CodecManifest manifest) {
		super(context);
		this.server = codec.getServer();
		this.service = server.updateService;
		this.manifest = manifest;
	}

	@Override
	public void disconnected() {
		fileQueue.clear();
	}

	@Override
	public void message(Object message) {
		if (handshakeComplete) {
			if(message instanceof FileRequest) {
				FileRequest request = (FileRequest) message;
				synchronized (fileQueue) {
					if (request.isPriority()) {
						fileQueue.addFirst(request);
					} else {
						fileQueue.addLast(request);
					}
					if (idle) {
						service.addPendingSession(this);
						idle = false;
					}
				}
			} else if (message instanceof UpdateEncryptionMessage) {
				UpdateEncryptionMessage encryption = (UpdateEncryptionMessage) message;
				XorEncoder encoder = channel.getPipeline().get(XorEncoder.class);
				encoder.setKey(encryption.getKey());
			}
		} else {
			UpdateVersionMessage validationMessage = (UpdateVersionMessage) message;
			int status;
			if (validationMessage.getVersion() == manifest.requiredProtocol()) {
				status = UpdateStatusMessage.STATUS_OK;
			} else {
				status = UpdateStatusMessage.STATUS_OUT_OF_DATE;
			}
			ChannelFuture future = channel.write(new UpdateStatusMessage(status));
			if (status == UpdateStatusMessage.STATUS_OK) {
				handshakeComplete = true;
			} else {
				future.addListener(ChannelFutureListener.CLOSE);
			}
		}
	}
	
	/**
	 * Processes the file queue.
	 */
	public void processFileQueue() {
		try {
			FileRequest request;
			synchronized (fileQueue) {
				request = fileQueue.pop();
				if (fileQueue.isEmpty()) {
					idle = true;
				} else {
					service.addPendingSession(this);
					idle = false;
				}
			}
			if (request != null) {
				int type = request.getType();
				int file = request.getFile();

				Cache cache = server.cache;
				ChannelBuffer buffer;

				if (type == 255 && file == 255) {
					buffer = ChannelBuffers.wrappedBuffer(server.checksumTable);
				} else {
					buffer = ChannelBuffers.wrappedBuffer(cache.getStore().read(type, file));
					if (type != 255)
						buffer = buffer.slice(0, buffer.readableBytes() - 2);
				}
				channel.write(new FileResponse(request.isPriority(), type, file, buffer));
			}
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Failed to service file request, closing channel...", ex);
			channel.close();
		}
	}
}
