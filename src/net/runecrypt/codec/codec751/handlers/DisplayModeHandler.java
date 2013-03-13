package net.runecrypt.codec.codec751.handlers;

import net.runecrypt.codec.codec751.context.DisplayModeContext;
import net.runecrypt.game.model.player.Player;
import net.runecrypt.network.packet.PacketHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:38PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayModeHandler implements PacketHandler<DisplayModeContext> {

	@Override
	public void handle(Player player, DisplayModeContext context) {
		player.displayMode = context.displayMode;
		if (player.onLogin) {
			player.performLogin();
			player.onLogin = false;
		}
	}
}
