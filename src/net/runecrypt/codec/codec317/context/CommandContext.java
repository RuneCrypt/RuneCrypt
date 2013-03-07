package net.runecrypt.codec.codec317.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandContext implements PacketContext {

    private String command;
    private String[] args;

    public CommandContext(String command, String[] args) {
        this.command = command;
        this.args = args;
    }
}
