package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldListContext implements PacketContext {

    /**
     * The world list update type.
     */
    private final int updateType;

    /**
     * Creates a new WorldListContext.
     * @param updateType The update type.
     */
    public WorldListContext(int updateType) {
        this.updateType = updateType;
    }

    /**
     * Gets the update type.
     * @return the updateType.
     */
    public int getUpdateType() {
        return updateType;
    }
}
