package net.runecrypt.codec.codec751.handlers;

import net.runecrypt.codec.codec751.context.WorldListContext;
import net.runecrypt.codec.codec751.context.WorldListUpdateContext;
import net.runecrypt.codec.codec751.encoders.WorldList;
import net.runecrypt.game.model.player.Player;
import net.runecrypt.network.packet.PacketHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldListHandler implements PacketHandler<WorldListContext> {

    @Override
    public void handle(Player player, WorldListContext context) {
        boolean fullUpdate = context.getUpdateType() == 0;
        player.encode(WorldList.class, new WorldListUpdateContext(fullUpdate));
    }
}
