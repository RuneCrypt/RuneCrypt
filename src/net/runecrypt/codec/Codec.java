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
package net.runecrypt.codec;

import net.runecrypt.Server;
import net.runecrypt.codec.codec317.Codec317;
import net.runecrypt.codec.codec317.network.CodecPipelineFactory;
import org.jboss.netty.channel.ChannelPipelineFactory;

/**
 * Represents a single protocol codec that the server can handle, for example we
 * will be able to make this framework any revision we want, but we can only run
 * one at a time (meaning we can not run a 317 and 484 at the same time).
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <3:58:19 PM - Mar 4, 2013>
 */
public abstract class Codec {

    private Server server;

    /**
     * Constructs a new {@code Codec} instance.
     *
     * @param server The server instance.
     */
    public Codec(Server server) {
        this.server = server;
    }

    /**
     * Sets the packet lengths for the protocol.
     */
    public abstract void setPacketLengths();

    /**
     * Sets the current outgoing packets we're handling.
     */
    public abstract void setOutgoingPackets();

    /**
     * Sets the incomming packets we're able to send.
     */
    public abstract void setIncommingPackets();

    /**
     * Gets the packet lengths for the packet decoder.
     *
     * @return The packet length.
     */
    public abstract int[] getPacketLengths();

    /**
     * Gets the reivision codec based on the integer value.
     *
     * @param server   The server for the codec.
     * @param revision The revision we're getting.
     * @return The codec (only if it exists).
     */
    public static Codec forRevision(Server server, int revision) {
        switch (revision) {
            case 317:
                return new Codec317(server);
        }
        throw new IllegalStateException("No such codec for revision: " + revision);
    }

    /**
     * Gets the Pipeline Factory based on the revision.
     *
     * @param revision      The revision we're getting.
     * @param codec         The codec of the revision.
     * @param codecManifest The manifest for the codec.
     * @return The pipeline factory for the codec.
     */
    public static ChannelPipelineFactory pipelineFactoryForRevision(int revision, Codec codec, CodecManifest codecManifest) {
        switch (revision) {
            case 317:
                return new CodecPipelineFactory(codec, codecManifest);
        }
        throw new IllegalStateException("No such pipeline for revision: " + revision);
    }

    /**
     * Gets the server instance.
     *
     * @return the server
     */
    public Server getServer() {
        return server;
    }


}
