package net.runecrypt.codec.codec751.network.codec.ondemand;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

public final class XorEncoder extends OneToOneEncoder {

    private int key = 0;

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof ChannelBuffer))
            return msg;

        ChannelBuffer buffer = (ChannelBuffer) msg;
        ChannelBuffer xorBuffer = ChannelBuffers.buffer(buffer.readableBytes());
        while (buffer.readable()) {
            xorBuffer.writeByte(buffer.readUnsignedByte() ^ key);
        }

        return xorBuffer;
    }

    public void setKey(int key) {
        this.key = key;
    }

}

