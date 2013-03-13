package net.runecrypt.network;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/11/13
 * Time: 6:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class Frame {

    public static final int BYTE = -1, SHORT = -2;
    protected static final int[] BIT_MASK = new int[32];

    static {
        for (int i = 0; i < BIT_MASK.length; i++)
            BIT_MASK[i] = (1 << i) - 1;
    }

    private int opcode, length;
    protected int readPos = 0;
    protected int writePos = 0;
    protected int bitReadPos = -1, bitWritePos = -1;
    protected byte[] frameBuffer;

    /**
     * Constructs a new {@code Frame} instance.
     *
     * @param opcode The opcode for the frame.
     * @param length The length of the frame.
     */
    public Frame(int opcode, int length) {
        this.opcode = opcode;
        if (length < 0) {
            switch (length) {
                case BYTE:
                    frameBuffer = new byte[256];
                    break;
                case SHORT:
                    frameBuffer = new byte[1024];
                    break;
            }
        } else
            frameBuffer = new byte[length];
        this.length = length;

    }

    /**
     * Constructs a new {@code Frame} instance.
     *
     * @param opcode      The opcode of the frame.
     * @param frameBuffer The buffer for the frame.
     */
    public Frame(int opcode, byte[] frameBuffer) {
        this.opcode = opcode;
        this.frameBuffer = frameBuffer;
        this.length = frameBuffer.length;
        this.writePos = frameBuffer.length;
    }

    /**
     * Ensures the amount of bytes are readable/writtable to the buffer.
     *
     * @param byteValue The numeric amount of bytes.
     */
    public void ensureCapacity(int byteValue) {
        int minCapacity = writePos + byteValue;
        if (minCapacity >= frameBuffer.length) {
            int newCapacity = (frameBuffer.length + 1) * 2;

            if (minCapacity > newCapacity)
                newCapacity = minCapacity;
            else if (newCapacity < 0)
                newCapacity = Integer.MAX_VALUE;

            byte[] newFrameBuffer = new byte[newCapacity];
            System.arraycopy(frameBuffer, 0, newFrameBuffer, 0, writePos);
            this.frameBuffer = newFrameBuffer;

            if (!(length <= 0))
                length = frameBuffer.length;
        }
    }

    /**
     * Initiates the bit write pos for writtig bits.
     *
     * @return This instance for chaining.
     */
    public Frame initBitWritePos() {
        bitWritePos = writePos * 8;
        return this;
    }

    /**
     * Initiates the bit read pos for reading bits.
     *
     * @return This instance for chaining.
     */
    public Frame initBitReadPos() {
        bitReadPos = readPos * 8;
        return this;
    }

    /**
     * Gets the opcode of the frame.
     *
     * @return the opcode
     */
    public int getOpcode() {
        return opcode;
    }

    /**
     * Gets the frame length.
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the read pos.
     *
     * @return the readPos
     */
    public int getReadPos() {
        return readPos;
    }

    /**
     * Gets the write pos.
     *
     * @return the writePos
     */
    public int getWritePos() {
        return writePos;
    }

    /**
     * Gets the bit read pos.
     *
     * @return the bitReadPos
     */
    public int getBitReadPos() {
        return bitReadPos;
    }

    /**
     * Gets the bit write pos.
     *
     * @return the bitWritePos
     */
    public int getBitWritePos() {
        return bitWritePos;
    }

    /**
     * Gets the frame buffer.
     *
     * @return the frameBuffer
     */
    public byte[] getFrameBuffer() {
        return frameBuffer;
    }
}
