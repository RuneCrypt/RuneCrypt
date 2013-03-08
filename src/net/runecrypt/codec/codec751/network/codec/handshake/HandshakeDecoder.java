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
package net.runecrypt.codec.codec751.network.codec.handshake;

import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.codec751.network.codec.ondemand.UpdateDecoder;
import net.runecrypt.codec.codec751.network.codec.ondemand.UpdateEncoder;
import net.runecrypt.codec.codec751.network.codec.ondemand.XorEncoder;
import net.runecrypt.codec.messages.HandshakeType;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

/**
 * An {@link FrameDecoder} that is used to handle the handshake procedure.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <4:47:39 PM - Mar 4, 2013>
 */
public class HandshakeDecoder extends FrameDecoder {

    @SuppressWarnings("unused")
    private final CodecManifest codecManifest;

    /**
     * Constructs a {@code HandshakeDecoder} instance.
     */
    public HandshakeDecoder(CodecManifest codecManifest) {
        super(true);
        this.codecManifest = codecManifest;
    }

    /* (non-Javadoc)
     * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        HandshakeType handshakeType = new HandshakeType(0xFF & buffer.readByte());
        switch (handshakeType.getType()) {
            case HANDSHAKE_ONDEMAND:
                channel.getPipeline().addFirst("encoder", new UpdateEncoder());
                channel.getPipeline().addFirst("xor-encoder", new XorEncoder());
                channel.getPipeline().addBefore("upHandler", "updateDecoder", new UpdateDecoder());
                break;
            default:
                break;
        }
        channel.getPipeline().remove(HandshakeDecoder.class);
        return buffer.readable() ? new Object[]{handshakeType, buffer.readBytes(buffer.readableBytes())} : handshakeType;
    }
}
