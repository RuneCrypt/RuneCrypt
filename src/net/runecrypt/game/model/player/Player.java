package net.runecrypt.game.model.player;

import net.runecrypt.game.model.Entity;
import org.jboss.netty.channel.Channel;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:31 AM
 * To change this template use File | Settings | File Templates.
 */
public final class Player extends Entity {

    public PlayerDef playerDef;
    public Channel channel;

    /**
     * Constructs a new {@code Player} instance.
     * @param playerDef The definition of the player.
     * @param channel The channel that represents a player's connection.
     */
    public Player(PlayerDef playerDef, Channel channel) {
        this.playerDef = playerDef;
        this.channel = channel;
    }

    /**
     * Sends the login configurations.
     */
    public void sendLoginConfigs() {

        System.out.println("Player [username=" + playerDef.getUsername() + ", password=" + playerDef.getPassword() + ", index=" + getIndex() + "] has entered game world.");
    }
}
