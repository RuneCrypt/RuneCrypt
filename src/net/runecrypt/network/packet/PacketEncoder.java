package net.runecrypt.network.packet;

import net.runecrypt.network.Packet;

/**
 * @author Sean
 *
 * @param <T> The GamePacketContext type.
 */
public interface PacketEncoder<T extends PacketContext> {

	/**
	 * Encodes a certain packet.
	 * @param context The GamePacketContext for this packet.
	 * @return The encoded packet.
	 */
	public Packet encode(T context);
}
