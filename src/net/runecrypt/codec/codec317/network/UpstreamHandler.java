/*
    Copyright (C) 2013, RuneCrypt Development Team.

    RuneCrypt is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    RuneCrypt is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with RuneCrypt.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.runecrypt.codec.codec317.network;

import net.runecrypt.codec.Codec;
import net.runecrypt.codec.messages.HandshakeType;
import net.runecrypt.codec.session.Session;
import net.runecrypt.codec.session.impl.LoginSession;
import org.jboss.netty.channel.*;

/**
 * An {@link SimpleChannelUpstreamHandler} that is used to handle downstream
 * messages from Netty.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <4:47:20 PM - Mar 4, 2013>
 */
public class UpstreamHandler extends SimpleChannelUpstreamHandler {

    private final Codec codec;

    /**
     * Constructs a new UpstreamHandler instance.
     *
     * @param codec The codec for the handler.
     */
    public UpstreamHandler(Codec codec) {
        this.codec = codec;
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        if (ctx.getAttachment() == null) {
            HandshakeType handshakeType = (HandshakeType)e.getMessage();
            switch (handshakeType.getType()) {
                case HANDSHAKE_LOGIN:
                    ctx.setAttachment(new LoginSession(ctx, codec));
                    break;
            }
        } else
            ((Session) ctx.getAttachment()).message(e.getMessage());
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        e.getCause().printStackTrace();
        if (e.getChannel().isConnected())
            e.getChannel().close();
    }

    /*
     * (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelUpstreamHandler#channelDisconnected(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        Channel channel = ctx.getChannel();
        if (channel.isConnected()) channel.close();

        Session session = (Session)ctx.getAttachment();
        if (session != null) session.disconnected();
    }
}
