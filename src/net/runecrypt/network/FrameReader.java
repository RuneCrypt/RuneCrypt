package net.runecrypt.network;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/11/13
 * Time: 7:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrameReader {
	
	private Frame frame;

	/**
	 * Constructs a new {@code FrameReader} instance.
	 * @param frame The frame for the reader.
	 */
	public FrameReader(Frame frame) {
		this.frame = frame;
	}
	
	/**
	 * Reads a byte value from the buffer.
	 * @return The byte value.
	 */
	public int readByte() {
		return frame.frameBuffer[frame.readPos++];
	}
	
	/**
	 * Reads a byte value from the buffer.
	 * @return The byte value.
	 */
	public int readUnsignedByte() {
		return frame.frameBuffer[frame.readPos++] & 0xFF;
	}
	
	/**
	 * Reads a int value from the buffer.
	 * @return The int value.
	 */
	public int readInt() {
		return (readUnsignedByte() << 24) | (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | readUnsignedByte();
	}
	
	/**
	 * Reads a short value from the buffer.
	 * @return The short value.
	 */
	public int readUnsignedShort() {
		return readShort() & 0xffff;
	}
	
	/**
	 * Reads a short value from the buffer.
	 * @return The short value.
	 */
	public int readShort() {
		int i = (readUnsignedByte() << 8) | readUnsignedByte();
		if (i > 32767) {
			i -= 0x10000;
		}
		return i;
	}


	/**
	 * Gets the frame for the reader.
	 * @return the frame
	 */
	public Frame getFrame() {
		return frame;
	}
}
