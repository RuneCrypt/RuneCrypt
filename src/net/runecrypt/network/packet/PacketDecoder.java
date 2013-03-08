package net.runecrypt.network.packet;

import net.runecrypt.network.PacketReader;

/**
 * @param <T>
 * @author Sean
 */
public interface PacketDecoder<T extends PacketContext> {

    /**
     * Decodes a certain packet sent from the client.
     *
     * @param packet The packet to decode.
     * @return The packet to decode.
     */
    public T decode(PacketReader packet);
}
