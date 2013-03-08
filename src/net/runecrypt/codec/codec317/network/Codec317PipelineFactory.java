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
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.codec317.network.codec.handshake.HandshakeDecoder;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

/**
 * An {@link ChannelPipelineFactory} that is used to handle the codec.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <4:24:02 PM - Mar 4, 2013>
 */
public class Codec317PipelineFactory implements ChannelPipelineFactory {

    private final CodecManifest codecManifest;
    private final Codec codec;

    /**
     * Constructs a new {@code CodecPipelineFactory} instance.
     *
     * @param codec The codec of the server.
     * @param codecManifest The manifest for the codec.
     */
    public Codec317PipelineFactory(Codec codec, CodecManifest codecManifest) {
        this.codec = codec;
        this.codecManifest = codecManifest;
    }

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.ChannelPipelineFactory#getPipeline()
     */
    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new HandshakeDecoder(codecManifest));
        pipeline.addLast("upHandler", new UpstreamHandler(codec));
        return pipeline;
    }
}
