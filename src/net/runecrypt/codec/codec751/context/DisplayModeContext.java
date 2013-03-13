package net.runecrypt.codec.codec751.context;

import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:25PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayModeContext implements PacketContext {
	
	public static enum DisplayModes {
		
		FIXED_SCREEN(1, 548);
		
		public int id, paneId;
		
		private DisplayModes(int id, int paneId) {
			this.id = id;
			this.paneId = paneId;
		}
		
		public static DisplayModes forId(int id) {
			switch (id) {
			case 1:
				return FIXED_SCREEN;
			}
			throw new IllegalStateException("No such display mode for id: " + id);
		}
	}
	
	public DisplayModes displayMode;
	public int width, height;
	
	/**
	 * Constructs a new {@code DisplayModeContext} instance.
	 * @param displayMode The display mode.
	 * @param width       The screen width.
	 * @param height      The screen height.
	 */
	public DisplayModeContext(DisplayModes displayMode, int width, int height) {
		this.displayMode = displayMode;
		this.width = width;
		this.height = height;
	}
}
