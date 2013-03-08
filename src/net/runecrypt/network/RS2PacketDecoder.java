package net.runecrypt.network;

import net.burtleburtle.bob.rand.IsaacRandom;
import net.runecrypt.codec.Codec;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 11:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class RS2PacketDecoder extends ReplayingDecoder<RS2PacketDecoder.RS2PacketDecoderState> {

    public enum RS2PacketDecoderState {OPCODE_READ, OPCODE_SIZE, FINALIZE}

    private final IsaacRandom decodingRandom;
    private int opcode = -1, size = -1;
    private final Codec codec;

    /**
     * Constructs a new {@code RS2PacketDecoder} instance.
     *
     * @param decodingRandom The decoding random instance.
     */
    public RS2PacketDecoder(IsaacRandom decodingRandom, Codec codec) {
        super(RS2PacketDecoderState.OPCODE_READ);
        this.decodingRandom = decodingRandom;
        this.codec = codec;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer in, RS2PacketDecoderState state) throws Exception {
        if (in.readableBytes() >= 1) {
            switch (state) {
                case OPCODE_READ:
                    if (in.readableBytes() >= 1) {
                        opcode = in.readByte() - decodingRandom.nextInt() & 0xFF;
                        checkpoint(RS2PacketDecoderState.OPCODE_SIZE);
                    }
                    break;
                case OPCODE_SIZE:
                    if (in.readableBytes() >= 1) {
                        size = codec.getPacketLengths()[opcode];
                        if (size == -1) {
                            size = in.readByte() & 0xFF;
                        }
                        checkpoint(RS2PacketDecoderState.FINALIZE);
                    }
                    break;
                case FINALIZE:
                    if (in.readableBytes() >= size) {
                        ChannelBuffer payload = dynamicBuffer();
                        payload.writeBytes(in.readBytes(size));
                        checkpoint(RS2PacketDecoderState.OPCODE_READ);
                        return new Packet(opcode, Packet.PacketType.FIXED, payload);
                    }
                    break;
            }
        }
        return null;
    }
}
