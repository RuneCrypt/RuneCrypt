package net.runecrypt.codec.codec751.network.codec.ondemand;

import net.runecrypt.codec.messages.UpdateEncryptionMessage;
import net.runecrypt.codec.messages.UpdateVersionMessage;
import net.runecrypt.ondemand.FileRequest;
import net.runecrypt.util.BufferUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateDecoder extends FrameDecoder implements ChannelHandler {

    /**
     * @author Graham
     */
    private enum State {
        READ_VERSION, READ_REQUEST
    }

    private State state = State.READ_VERSION;

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 6)
            return null;
        if (state == State.READ_VERSION) {
            state = State.READ_REQUEST;
            int length = buffer.readUnsignedByte();
            if (buffer.readableBytes() >= length) {
                int version = buffer.readInt();
                int subVersion = buffer.readInt();
                String key = BufferUtils.readString(buffer);
                return new UpdateVersionMessage(version, subVersion, key);
            }
        } else {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0 || opcode == 1) {
                int type = buffer.readUnsignedByte();
                int file = buffer.readInt();
                return new FileRequest(opcode == 1, type, file);
            } else if (opcode == 4) {
                int key = buffer.readUnsignedByte();
                buffer.readerIndex(buffer.readerIndex() + 2);
                return new UpdateEncryptionMessage(key);
            } else {
                buffer.readerIndex(buffer.readerIndex() + 5);
            }
        }
        return null;
    }

}
