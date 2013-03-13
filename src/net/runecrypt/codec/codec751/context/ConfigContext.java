package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 11:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigContext implements PacketContext {

    public int id, value;
    public boolean csConfig;

    /**
     * Constructs a new {@code Config} instance.
     *
     * @param id       The ID of the config.
     * @param value    The value of the config.
     * @param csConfig A flag to indicate if it is a CS config.
     */
    public ConfigContext(int id, int value, boolean csConfig) {
        this.id = id;
        this.value = value;
        this.csConfig = csConfig;
    }
}
