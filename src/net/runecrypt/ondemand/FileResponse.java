package net.runecrypt.ondemand;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public final class FileResponse {

	private final boolean priority;
	private final int type, file;
	private final ChannelBuffer container;

	public FileResponse(boolean priority, int type, int file, ChannelBuffer container) {
		this.priority = priority;
		this.type = type;
		this.file = file;
		this.container = container;
	}

	public boolean isPriority() {
		return priority;
	}

	public int getType() {
		return type;
	}

	public int getFile() {
		return file;
	}

	public ChannelBuffer getContainer() {
		return container;
	}

}
