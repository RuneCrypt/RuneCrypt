package net.runecrypt.codec.codec317.network.codec.login;

import net.runecrypt.codec.messages.LoginResponse;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginEncoder extends OneToOneEncoder {

    @Override
    protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
        if (!(msg instanceof LoginResponse))
            return msg;

        LoginResponse loginResponse = (LoginResponse) msg;

        ChannelBuffer channelBuffer = ChannelBuffers.buffer(3);
        channelBuffer.writeByte(loginResponse.returnCode);

        if (loginResponse.returnCode == 2) {
            channelBuffer.writeByte(loginResponse.rights.intValue);
            channelBuffer.writeByte(0);
        }

        return channelBuffer;
    }
}
