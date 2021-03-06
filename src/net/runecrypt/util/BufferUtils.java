package net.runecrypt.util;

import org.jboss.netty.buffer.ChannelBuffer;

import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:26 AM
 * To change this template use File | Settings | File Templates.
 */
public final class BufferUtils {

    /**
     * Puts a 5 byte integer into the buffer.
     *
     * @param buf   The channel buffer
     * @param value The value to be added.
     */
    public static void put5ByteInteger(ChannelBuffer buf, long value) {
        buf.writeByte((int) (value >> 32));
        buf.writeInt((int) (value & 0xffffffff));
    }

    /**
     * Writes a string
     *
     * @param buffer The ChannelBuffer
     * @param string The string being wrote.
     */
    public static void putJagString(ChannelBuffer buffer, String string) {
        buffer.writeByte(0);
        buffer.writeBytes(string.getBytes());
        buffer.writeByte(0);
    }

    /**
     * Puts a string into a buffer.
     *
     * @param buf    The buffer.
     * @param string The string.
     */
    public static void putString(ChannelBuffer buf, String string) {
        for (char c : string.toCharArray()) {
            buf.writeByte(c);
        }
        buf.writeByte(0);
    }

    /**
     * Writes a 'tri-byte' to the specified buffer.
     *
     * @param buf   The buffer.
     * @param value The value.
     */
    public static void putTriByte(ChannelBuffer buf, int value) {
        buf.writeByte(value >> 16);
        buf.writeByte(value >> 8);
        buf.writeByte(value);
    }

    /**
     * Reads a string from a bytebuffer.
     *
     * @param buf The bytebuffer.
     * @return The decoded string.
     */
    public static String readString(ByteBuffer buf) {
        StringBuilder bldr = new StringBuilder();
        byte b;
        while ((b = buf.get()) != 0) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }

    /**
     * Reads a RuneScape string from a buffer.
     *
     * @param buf The buffer.
     * @return The string.
     */
    public static String readString(ChannelBuffer buf) {
        StringBuilder bldr = new StringBuilder();
        byte b;
        while (buf.readable() && (b = buf.readByte()) != 10) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }

    /**
     * Reads a RuneScape string from a buffer.
     *
     * @param buf The buffer.
     * @return The string.
     */
    public static String readJagexString(ChannelBuffer buf) {
        StringBuilder bldr = new StringBuilder();
        byte b;
        while (buf.readable() && (b = buf.readByte()) != 0) {
            bldr.append((char) b);
        }
        return bldr.toString();
    }

    public static void write40BitInt(ChannelBuffer buffer, long value) {
        buffer.writeByte((byte) (value >> 32));
        buffer.writeByte((byte) (value >> 24));
        buffer.writeByte((byte) (value >> 16));
        buffer.writeByte((byte) (value >> 8));
        buffer.writeByte((byte) (value));
    }

    public static void writeGJString2(ChannelBuffer buffer, String gjString) {
        byte[] packed = new byte[JagString.calculateGJString2Length(gjString)];
        int length = JagString.packGJString2(0, packed, gjString);
        buffer.writeByte(0);
        buffer.writeBytes(packed, 0, length);
        buffer.writeByte(0);
    }

    public static void writeByte(int val, int index, byte[] buffer) {
        buffer[index++] = (byte) val;
    }

    public static void writeShort(int val, int index, byte[] buffer) {
        buffer[index++] = (byte) (val >> 8);
        buffer[index++] = (byte) val;
    }
}