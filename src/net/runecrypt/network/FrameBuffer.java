package net.runecrypt.network;

import net.runecrypt.util.JagString;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/11/13
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class FrameBuffer {

    private Frame frame;

    /**
     * Constructs a new {@code FrameBuffer} instance.
     *
     * @param frame The frame for the buffer.
     */
    public FrameBuffer(Frame frame) {
        this.frame = frame;
    }

    /**
     * Writes a single byte into the buffer.
     *
     * @param value The byte value.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeByte(int value) {
        frame.ensureCapacity(1);
        frame.frameBuffer[frame.writePos++] = (byte) value;
        return this;
    }

    /**
     * Writes an array of bytes into the buffer.
     *
     * @param bytes The array the bytes are stored in.
     * @return This instance for chaining.
     */
    public FrameBuffer writeBytes(byte... bytes) {
        writeBytes(bytes, 0, bytes.length);
        return this;
    }

    /**
     * Writes an array of bytes into the buffer.
     *
     * @param bytes  The array the bytes are stored in.
     * @param offset The offset to begin writting.
     * @param length The length of the bytes.
     * @return This instance, for chaining.
     */
    private FrameBuffer writeBytes(byte[] bytes, int offset, int length) {
        frame.ensureCapacity(length);
        System.arraycopy(bytes, offset, frame.frameBuffer, frame.writePos, length);
        frame.writePos += length;
        return this;
    }

    /**
     * Writes a value type A short to the buffer.
     *
     * @param value The value of the short.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeShortA(int value) {
        frame.ensureCapacity(2);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value + 128);
        return this;
    }

    /**
     * Writes a value type A, byte order little endian, short to the buffer.
     *
     * @param value The value of the short.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeLEShortA(int value) {
        frame.ensureCapacity(2);

        frame.frameBuffer[frame.writePos++] = (byte) (value + 128);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        return this;
    }

    /**
     * Writes a integer to the buffer.
     *
     * @param value The value of the integer.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeInt(int value) {
        frame.ensureCapacity(4);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) value;
        return this;
    }

    /**
     * Writes a little-endian short to the buffer.
     *
     * @param value The value of the short.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeLEShort(int value) {
        frame.ensureCapacity(2);

        frame.frameBuffer[frame.writePos++] = (byte) value;
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        return this;
    }

    /**
     * Writes a short to the buffer.
     *
     * @param value The value of the short.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeShort(int value) {
        frame.ensureCapacity(2);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) value;
        return this;
    }

    /**
     * Writes a value type S byte to the buffer.
     *
     * @param value The value of the byte.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeByteS(int value) {
        writeByte((byte) (128 - (value & 0xFF)));
        return this;
    }

    /**
     * Writes a value type A byte to the buffer.
     *
     * @param value The value of the byte.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeByteA(int value) {
        writeByte((byte) ((value & 0xFF) + 128));
        return this;
    }

    /**
     * Writes a little-endian int to the buffer.
     *
     * @param value The value of the int.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeLEInt(int value) {
        frame.ensureCapacity(4);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        return this;
    }

    /**
     * Writes a inverse-middle int to the buffer.
     *
     * @param value The value of the int.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeInt2(int value) {
        frame.ensureCapacity(4);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        return this;
    }

    /**
     * Writes a middle int to the buffer.
     *
     * @param value The value of the int.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeInt1(int value) {
        frame.ensureCapacity(4);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        return this;
    }

    /**
     * Writes a value type C byte to the buffer.
     *
     * @param value The value of the byte.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeByteC(int value) {
        writeByte((byte) -(value & 0xFF));
        return this;
    }

    /**
     * Writes a smart into the buffer.
     *
     * @param value The value of the smart.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeSmart(int value) {
        if (value >= 0 && value < 128) {
            writeByte(value);
        } else if (value >= 0 && value < 32768) {
            writeShort(value + 32768);
        } else {
            throw new IllegalArgumentException("Invalid smart value : " + value);
        }
        return this;
    }

    /**
     * Writes a JAG string into the buffer.
     *
     * @param value The value of the jag string.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeJagString(String value) {
        writeByte(0);
        writeBytes(value.getBytes());
        writeByte(0);
        return this;
    }

    /**
     * Writes a tri-byte to the buffer.
     *
     * @param value The value of the try-byte.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeTriByte(int value) {
        frame.ensureCapacity(3);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        return this;
    }

    /**
     * Writes a long to the buffer.
     *
     * @param value The value of the long.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeLong(long value) {
        frame.ensureCapacity(8);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 56);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 48);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 40);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 32);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        return this;
    }

    /**
     * Writes a JAG String 2 to the buffer.
     *
     * @param value The value of the string.
     * @return This instance, for chaining.
     */
    public FrameBuffer writeGJString2(String string) {
        byte[] packed = new byte[JagString.calculateGJString2Length(string)];
        int length = JagString.packGJString2(0, packed, string);
        writeByte(0).writeBytes(packed, 0, length).writeByte(0);
        return this;
    }

    /**
     * Writes a 40-bit integer to the buffer.
     *
     * @param value The value of the int.
     * @return This instance, for chaining.
     */
    public FrameBuffer write40BitInt(long value) {
        frame.ensureCapacity(5);

        frame.frameBuffer[frame.writePos++] = (byte) (value >> 32);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 24);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 16);
        frame.frameBuffer[frame.writePos++] = (byte) (value >> 8);
        frame.frameBuffer[frame.writePos++] = (byte) (value);
        return this;
    }


    /**
     * Puts a number of bits into the buffer.
     *
     * @param numBits The number of bits to added into the buffer.
     * @param value   The value
     * @return This instance, for chaining.
     */
    public FrameBuffer writeBits(int numBits, int value) {
        if (frame.bitWritePos == -1) {
            frame.initBitWritePos();
        }

        int bytePos = frame.bitWritePos >> 3;
        int bitOffset = 8 - (frame.bitWritePos & 7);

        frame.bitWritePos += numBits;
        frame.ensureCapacity((numBits + 7) * 8);
        frame.writePos = (frame.bitWritePos + 7) / 8;

        for (; numBits > bitOffset; bitOffset = 8) {
            frame.frameBuffer[bytePos] &= ~Frame.BIT_MASK[bitOffset];
            frame.frameBuffer[bytePos++] |= (value >> (numBits - bitOffset)) & Frame.BIT_MASK[bitOffset];

            numBits -= bitOffset;
        }

        if (numBits == bitOffset) {
            frame.frameBuffer[bytePos] &= ~Frame.BIT_MASK[bitOffset];
            frame.frameBuffer[bytePos] |= value & Frame.BIT_MASK[bitOffset];
        } else {
            frame.frameBuffer[bytePos] &= ~(Frame.BIT_MASK[numBits] << (bitOffset - numBits));
            frame.frameBuffer[bytePos] |= (value & Frame.BIT_MASK[numBits]) << (bitOffset - numBits);
        }
        return this;
    }

    /**
     * Gets the frame.
     *
     * @return the frame
     */
    public Frame getFrame() {
        return frame;
    }
}
