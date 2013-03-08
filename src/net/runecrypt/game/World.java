package net.runecrypt.game;

import net.runecrypt.game.model.player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public final class World {

    private static World instance = new World();
    private Player[] players = new Player[0x9c4];

    /**
     * Regsiters a player into the game world.
     *
     * @param player The player who we're registering.
     */
    public boolean register(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                player.setIndex(i);
                return true;
            }
        }
        System.err.println("Player can not join because the server is full!");
        return false;
    }

    /**
     * Gets a player based on the username.
     *
     * @param username The username we're checking for.
     * @return The {@code Player} or {@code Null} if the player doesn't exsist.
     */
    public Player getPlayer(String username) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == null)
                continue;

            if (players[i].playerDef.getUsername().equalsIgnoreCase(username))
                return players[i];
        }
        return null;
    }

    /**
     * Gets the singleton instance for the world.
     *
     * @return The singleton instance.
     */
    public static World getInstance() {
        return instance;
    }
}
