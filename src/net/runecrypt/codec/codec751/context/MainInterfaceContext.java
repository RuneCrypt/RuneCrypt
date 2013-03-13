package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainInterfaceContext implements PacketContext {
	
	public int interfaceId;

	/**
	 * Constructs a new {@code MainInterface} instance.
	 * 
	 * @param interfaceId The interface ID.
	 */
	public MainInterfaceContext(int interfaceId) {
		this.interfaceId = interfaceId;
	}
}
