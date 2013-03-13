package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldListUpdateContext implements PacketContext {

    /**
     * Skips the world list update.
     */
    private final boolean skipUpdate;

    /**
     * Updates the world list fully.
     */
    public final boolean fullUpdate;

    /**
     * Creates a new WorldListUpdate which does'nt skip the update and does a full worldlist update.
     */
    public WorldListUpdateContext() {
        this(false, true);
    }

    /**
     * Creates a new WorldListUpdate which doesnt skip the update.
     */
    public WorldListUpdateContext(boolean fullUpdate) {
        this(false, fullUpdate);
    }

    /**
     * Creates a new WorldListUpdate context.
     *
     * @param skipUpdate Skips the update.
     * @param fullUpdate Updates the worldlist fully.
     */
    public WorldListUpdateContext(boolean skipUpdate, boolean fullUpdate) {
        this.skipUpdate = skipUpdate;
        this.fullUpdate = fullUpdate;
    }

    /**
     * is there a full update required.
     *
     * @return the fullUpdate.
     */
    public boolean isFullUpdate() {
        return fullUpdate;
    }

    /**
     * Skip the world list update.
     *
     * @return the skipUpdate
     */
    public boolean skipUpdate() {
        return skipUpdate;
    }
}
