package net.runecrypt.network.packet;

import net.runecrypt.game.model.player.Player;

/**
 * @author Sean
 */
public interface PacketHandler<T extends PacketContext> {

    /**
     * Handles a certain packet for a player.
     *
     * @param player  The player to handle the packet for.
     * @param context The packet context.
     */
    public void handle(Player player, T context);
}
