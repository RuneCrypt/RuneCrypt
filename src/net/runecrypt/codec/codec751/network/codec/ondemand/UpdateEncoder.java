package net.runecrypt.codec.codec751.network.codec.ondemand;

import java.io.IOException;

import net.runecrypt.codec.messages.UpdateStatusMessage;
import net.runecrypt.ondemand.FileResponse;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public final class UpdateEncoder extends OneToOneEncoder implements ChannelHandler {

	/**
	 * Data sent for the 27 integers.
	 */
	public static final int[] UPDATE_DATA = { 1582, 78700, 44880, 39771,
		358716, 44375, 0, 16497, 9734, 408717, 894828, 276612, 426615,
		538603, 776400, 24019, 17682, 1244, 20331, 1775, 119, 967757,
		2188046, 4930, 3578 
	};

	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws IOException {
		if (msg instanceof FileResponse) {
			FileResponse response = (FileResponse) msg;

			ChannelBuffer container = response.getContainer();
			int type = response.getType();
			int file = response.getFile();

			ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
			buffer.writeByte(type);
			buffer.writeInt(file);

			int compression = container.readUnsignedByte();
			if (!response.isPriority())
				compression |= 0x80;

			buffer.writeByte(compression);

			int bytes = container.readableBytes();
			if (bytes > 506)
				bytes = 506;

			buffer.writeBytes(container.readBytes(bytes));

			while ((bytes = container.readableBytes()) != 0) {
				bytes = container.readableBytes();
				if (bytes == 0)
					break;
				else if (bytes > 511)
					bytes = 511;

				buffer.writeByte(0xFF);
				buffer.writeBytes(container.readBytes(bytes));
			}
			return buffer;
			
		} else if (msg instanceof UpdateStatusMessage) {
			UpdateStatusMessage status = (UpdateStatusMessage) msg;
			ChannelBuffer buffer = ChannelBuffers.dynamicBuffer();
			buffer.writeByte(status.getStatus());
			for(int i = 0; i < UPDATE_DATA.length; i++) {
				buffer.writeInt(UPDATE_DATA[i]);
			}
			return buffer;
		}
		return msg;
	}
}
