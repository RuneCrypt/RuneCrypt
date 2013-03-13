package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameInterfaceContext implements PacketContext {
	
	public int windowId, interfaceId;
	public boolean walkable;
	public int windowLocation;

	private int[][] DEFAULT_WINDOWS = new int[][] {
		new int[] { 137, 752 },
	};
	
	/**
	 * Constructs a new {@code GameInterfaceContext} instance.
	 * 
	 * @param windowId The window id of the interface.
	 * @param interfaceId The interface ID of the interface.
	 * @param windowLocation The location of the window.
	 * @param walkable Indiciates if the interface is walkable.
	 */
	public GameInterfaceContext(int windowId, int interfaceId, int windowLocation, boolean walkable) {
		this.windowId = windowId;
		for (int i = 0; i < DEFAULT_WINDOWS.length; i++) {
			if (interfaceId == DEFAULT_WINDOWS[i][0])
				windowId = DEFAULT_WINDOWS[i][1];
		}
		this.interfaceId = interfaceId;
		this.windowLocation = windowLocation;
		this.walkable = walkable;
	}
}
