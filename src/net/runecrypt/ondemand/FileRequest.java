package net.runecrypt.ondemand;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public final class FileRequest {

	private final boolean priority;
	private final int type, file;

	public FileRequest(boolean priority, int type, int file) {
		this.priority = priority;
		this.type = type;
		this.file = file;
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

}
