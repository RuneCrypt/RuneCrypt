package net.runecrypt.codec.codec751.handlers;

import net.runecrypt.codec.codec751.encoders.KeepAlive;
import net.runecrypt.game.model.player.Player;
import net.runecrypt.network.packet.PacketContext;
import net.runecrypt.network.packet.PacketHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeepAliveHandler implements PacketHandler<PacketContext> {

    @Override
    public void handle(Player player, PacketContext context) {
        player.encode(KeepAlive.class, null);
    }
}
