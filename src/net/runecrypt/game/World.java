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

    public static enum LoginType {WORLD, LOBBY}
    private static World instance = new World();
    public Player[] players = new Player[0x9c4];
    private Player[] lobbyPlayers = new Player[0x9c4];

    /**
     * Regsiters a player into the game.
     *
     * @param player    The player who we're registering.
     * @param loginType The type of login we're performing.
     */
    public boolean register(Player player, LoginType loginType) {
        Player[] loginPlayers = loginType == LoginType.WORLD ? players : lobbyPlayers;
        for (int i = 1; i < loginPlayers.length; i++) {
            if (loginType.equals(LoginType.WORLD) && lobbyPlayers[player.getIndex()] != null) {
                unregister(player, LoginType.LOBBY);
            }
            if (loginPlayers[i] == null) {
                loginPlayers[i] = player;
                player.setIndex(i);
                return true;
            }
        }
        System.err.println("Player can not join because the server is full!");
        return false;
    }

    /**
     * Unregisters a player into the game.
     *
     * @param player    The player who we're unregistering.
     * @param loginType The type of login we're performing.
     */
    public void unregister(Player player, LoginType loginType) {
        Player[] loginPlayers = loginType == LoginType.WORLD ? players : lobbyPlayers;
        if (player.getIndex() <= -1)
            return;

        loginPlayers[player.getIndex()] = null;
    }

    /**
     * Gets a player based on the username.
     *
     * @param username The username we're checking for.
     * @return The {@code Player} or {@code Null} if the player doesn't exsist.
     */
    public Player getPlayer(String username) {
        for (int i = 1; i < players.length; i++) {
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
