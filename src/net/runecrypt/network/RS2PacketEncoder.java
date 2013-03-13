package net.runecrypt.network;

import net.burtleburtle.bob.rand.IsaacRandom;
import net.runecrypt.util.BufferUtils;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 11:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class RS2PacketEncoder extends OneToOneEncoder {

    @SuppressWarnings("unused")
    private final IsaacRandom encodingRandom;
    private int currentProtocol;

    /**
     * Constructs a new {@code RS2PacketEncoder} instance.
     *
     * @param encodingRandom  The encoding random instance.
     * @param currentProtocol The current protocol of the server.
     */
    public RS2PacketEncoder(IsaacRandom encodingRandom, int currentProtocol) {
        this.encodingRandom = encodingRandom;
        this.currentProtocol = currentProtocol;
    }

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object message) throws Exception {
        Frame frame = (Frame) message;

        int opcode = frame.getOpcode();

        byte[] buffer = frame.getFrameBuffer();
        if (frame.getWritePos() < buffer.length || frame.getWritePos() == 0) {
            int headerLength = 0;

            if (opcode >= 0) {
                headerLength++;
                if (frame.getLength() < 0 && frame.getLength() != -3) {
                    headerLength += -(frame.getLength());
                }

                if (currentProtocol == 751) {
                    if (opcode >= 128) {
                        headerLength++;
                    }
                }
            }

            byte[] newBuffer = new byte[headerLength + frame.getWritePos()];

            if (opcode >= 0) {
                int pos = 0;
                if (currentProtocol == 751) {
                    if (opcode >= 128) {
                        BufferUtils.writeByte(128, pos++, newBuffer);
                    }
                }

                BufferUtils.writeByte(opcode, pos++, newBuffer);
                if (frame.getLength() < 0) {
                    switch (frame.getLength()) {
                        case Frame.BYTE:
                            BufferUtils.writeByte(frame.getWritePos(), pos, newBuffer);
                            break;

                        case Frame.SHORT:
                            BufferUtils.writeShort(frame.getWritePos(), pos, newBuffer);
                            break;
                    }
                }
            }

            System.arraycopy(buffer, 0, newBuffer, headerLength, frame.getWritePos());
            return ChannelBuffers.wrappedBuffer(newBuffer);
        } else
            return message;
    }
}
