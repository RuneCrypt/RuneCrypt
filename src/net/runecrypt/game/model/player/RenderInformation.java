package net.runecrypt.game.model.player;

import net.runecrypt.game.World;
import net.runecrypt.game.model.Position;
import net.runecrypt.network.FrameBuffer;

/**
 * Holds the player's rendering information.
 * 
 * @author Jolt environment v2 development team
 * @author Emperor (converted to Java + NPC information).
 *
 */
public class RenderInformation {

	/**
	 * The player.
	 */
	private final Player player;
	
	/**
	 * Holds the players' hash locations.
	 */
	public final int[] hashLocations = new int[2048];
	
	/**
	 * The amount of local players.
	 */
	public int localsCount = 0;
	
	/**
	 * The amount of global players.
	 */
	public int globalsCount = 0;
	
	/**
	 * The local player indexes.
	 */
	public final short[] locals = new short[2048];
	
	/**
	 * The global player indexes.
	 */
	public final short[] globals = new short[2048];
	
	/**
	 * The local players.
	 */
	public final boolean[] isLocal = new boolean[2048];
	
	/**
	 * The skipped player indexes.
	 */
	public final byte[] skips = new byte[2048];
    
    /**
     * The player's last location.
     */
    private Position lastLocation;
    
    /**
     * If the player has just logged in.
     */
    private boolean onFirstCycle;

    /**
     * The amount of added players in the current update cycle.
     */
	private int added;
    
	/**
	 * Constructs a new {@code RenderInformation} {@code Object}.
	 * @param player The player.
	 */
	public RenderInformation(Player player) {
		this.player = player;
		this.onFirstCycle = true;
	}
	
	/**
	 * Updates the player's map region packet with player information.
	 * @param packet The packet.
	 */
	public void enterWorld(FrameBuffer packet) {
        int myindex = player.getIndex();
        locals[localsCount++] = (short) myindex;
        isLocal[myindex] = true;
        hashLocations[myindex] = 0;
        packet.writeBits(30, player.getPosition().get30BitsHash());
        for (short index = 1; index < 2048; index++) {
            if (index == myindex) {
                continue;
            }
            globals[globalsCount++] = index;
        	Player p = World.getInstance().players[index];
        	if (p == null) {
        		packet.writeBits(18, 0);
        		continue;
        	}
        	packet.writeBits(18, p.getPosition().get18BitsHash());
        }
	}
	
	/**
	 * Updates the player rendering information.
	 */
	public void updateInformation() {
		localsCount = 0;
		globalsCount = 0;
		added = 0;
        onFirstCycle = false;
        lastLocation = player.getPosition();
        for (short i = 1; i < 2048; i++) {
        	skips[i] >>= 1;
        	if (isLocal[i]) {
        		locals[localsCount++] = i;
        	} else {
        		globals[globalsCount++] = i;
        	}
        	Player p = World.getInstance().players[i];
        	if (p != null && p.channel.isConnected()) {
                hashLocations[i] = p.getPosition().get18BitsHash();
        	}
        }
	}

	/**
	 * @return the lastLocation
	 */
	public Position getLastLocation() {
		return lastLocation;
	}

	/**
	 * @return the onFirstCycle
	 */
	public boolean isOnFirstCycle() {
		return onFirstCycle;
	}

	/**
	 * Gets the amount of currently added players in this cycle. 
	 * @return The amount, incremented.
	 */
	public int getAddedIncr() {
		return added++;
	}

}