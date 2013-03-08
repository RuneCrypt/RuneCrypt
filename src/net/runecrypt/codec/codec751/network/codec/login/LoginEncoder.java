package net.runecrypt.codec.codec751.network.codec.login;

import net.runecrypt.codec.messages.LoginResponse;
import net.runecrypt.game.World;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof LoginResponse))
            return msg;

        LoginResponse loginResponse = (LoginResponse) msg;

        ChannelBuffer channelBuffer = ChannelBuffers.dynamicBuffer();
        channelBuffer.writeByte(loginResponse.returnCode);

        if (loginResponse.returnCode == 2) {
            ChannelBuffer block = loginResponse.buffer;
            channelBuffer.writeByte(block.writerIndex());
            channelBuffer.writeBytes(block);
        }

        return channelBuffer;
    }
}
