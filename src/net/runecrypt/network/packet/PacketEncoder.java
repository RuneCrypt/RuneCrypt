package net.runecrypt.network.packet;

import net.runecrypt.network.Frame;

/**
 * @param <T> The GamePacketContext type.
 * @author Sean
 */
public interface PacketEncoder<T extends PacketContext> {

    /**
     * Encodes a certain packet.
     *
     * @param context The GamePacketContext for this packet.
     * @return The encoded packet.
     */
    public Frame encode(T context);
}
