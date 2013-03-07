package net.runecrypt.network;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PacketBuilder {

    /**
     * The maximum bits that can be assigned to the buffer.
     */
    private static final int MAX_BITS = 32;

    /**
     *  TheBit mask array.
     */
    public static final int[] BIT_MASKS = new int[MAX_BITS];

    /**
     * Populates the bit mask array.
     */
    static {
        for(int i = 0; i < BIT_MASKS.length; i++) {
            BIT_MASKS[i] = (1 << i) - 1;
        }
    }

    /**
     * The packet opcode
     */
    private final int opcode;

    /**
     * The packet type
     */
    private final Packet.PacketType type;

    /**
     * The packets payload
     */
    private final ChannelBuffer payload;

    /**
     * The access mode of the builder.
     */
    private Packet.AccessMode mode = Packet.AccessMode.BYTE_ACCESS;

    /**
     * The bit index.
     */
    private int bitIndex;

    /**
     * Creates a new PacketBuilder with no opcode or header.
     */
    public PacketBuilder() {
        this(-1, null);
    }

    /**
     * Creates a new PacketBuilder with the packet type set to Fixed.
     * @param opcode The opcode.
     */
    public PacketBuilder(int opcode) {
        this(opcode, Packet.PacketType.FIXED);
    }

    /**
     * Creates a new PacketBuilder.
     * @param opcode The opcode id.
     * @param type The packet type.
     */
    public PacketBuilder(int opcode, Packet.PacketType type) {
        this.opcode = opcode;
        this.type = type;
        payload = ChannelBuffers.dynamicBuffer();
    }

    /**
     * Gets the length of the buffer.
     * @return The length of the buffer.
     */
    public int getLength() {
        return payload.writerIndex();
    }

    /**
     * Checks if this packet builder is empty.
     * @return <code>true</code> if so, <code>false</code> if not.
     */
    public boolean isEmpty() {
        return payload.writerIndex() == 0;
    }

    /**
     * Writes an array of bytes.
     * @param b The byte array.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(byte[] b) {
        payload.writeBytes(b);
        return this;
    }

    /**
     * Puts a sequence of bytes in the buffer.
     * @param data The bytes.
     * @param offset The offset.
     * @param length The length.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(byte[] data, int offset, int length) {
        payload.writeBytes(data, offset, length);
        return this;
    }

    /**
     * Puts an <code>ChannelBuffer</code>.
     * @param buf The buffer.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(ChannelBuffer buf) {
        payload.writeBytes(buf);
        return this;
    }

    /**
     * Writes a byte.
     * @param b The byte to write.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(int b) {
        payload.writeByte(b);
        return this;
    }

    /**
     * Puts a GamePackets payload into this buffer.
     * @param packet The game packet.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(Packet packet) {
        put(packet.getPayload());
        return this;
    }

    /**
     * Adds the bytes of another builders payload..
     * @param builder The game packet builder.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder put(PacketBuilder builder) {
        payload.writeBytes(builder.payload);
        return this;
    }

    /**
     * Puts a number of bits into the buffer.
     * @param numberOfBits The number of bits to added into the buffer.
     * @param value The value
     */
    public void putBits(int numberOfBits, int value) {
        if(numberOfBits > MAX_BITS) {
            throw new IllegalStateException("Error! Cant assign more than "+MAX_BITS+" to the buffer");
        }
        if (mode != Packet.AccessMode.BIT_ACCESS) {
            throw new IllegalStateException("Error! AccessMode set to BYTE_ACCESS");
        }
        int bytePos = bitIndex >> 3;
        int bitOffset = 8 - (bitIndex & 7);
        bitIndex += numberOfBits;
        int requiredSpace = bytePos - payload.writerIndex() + 1;
        requiredSpace += (numberOfBits + 7) / 8;
        payload.ensureWritableBytes(requiredSpace);
        for (; numberOfBits > bitOffset; bitOffset = 8) {
            int tmp = payload.getByte(bytePos);
            tmp &= ~BIT_MASKS[bitOffset];
            tmp |= (value >> (numberOfBits-bitOffset)) & BIT_MASKS[bitOffset];
            payload.setByte(bytePos++, tmp);
            numberOfBits -= bitOffset;
        }
        if (numberOfBits == bitOffset) {
            int tmp = payload.getByte(bytePos);
            tmp &= ~BIT_MASKS[bitOffset];
            tmp |= value & BIT_MASKS[bitOffset];
            payload.setByte(bytePos, tmp);
        } else {
            int tmp = payload.getByte(bytePos);
            tmp &= ~(BIT_MASKS[numberOfBits] << (bitOffset - numberOfBits));
            tmp |= (value & BIT_MASKS[numberOfBits]) << (bitOffset - numberOfBits);
            payload.setByte(bytePos, tmp);
        }
    }

    /**
     * Puts a type-A byte in the buffer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putByteA(byte val) {
        payload.writeByte(val + 128);
        return this;
    }

    /**
     * Writes a type-A byte.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putByteA(int val) {
        payload.writeByte(val + 128);
        return this;
    }

    /**
     * Puts a type-C byte in the buffer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putByteC(byte val) {
        payload.writeByte(-val);
        return this;
    }

    /**
     * Writes a type-C byte.
     * @param val The value to write.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putByteC(int val) {
        put(-val);
        return this;
    }

    /**
     * Adds the contents of <code>byte</code> array <code>data</code>
     * to the packet. The size of this packet will grow by the length of
     * the provided array.
     *
     * @param data The bytes to add to this packet
     * @return A reference to this object
     */
    public PacketBuilder putBytes(byte[] data) {
        return putBytes(data, 0, data.length);
    }

    /**
     * Adds the contents of <code>byte</code> array <code>data</code>,
     * starting at index <code>offset</code>. The size of this packet will
     * grow by <code>len</code> bytes.
     *
     * @param data   The bytes to add to this packet
     * @param offset The index of the first byte to append
     * @param len	The number of bytes to append
     * @return A reference to this object
     */
    public PacketBuilder putBytes(byte[] data, int offset, int len) {
        for (int i = offset; i < len - offset; i++) {
            put(data[i]);
        }
        return this;
    }
    /**
     * Puts a type-S byte in the buffer.
     * @param i The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putByteS(int i) {
        payload.writeByte(128 - i);
        return this;
    }

    /**
     * Puts several type-A bytes into the buffer.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putBytesA(byte[] data, int offset, int len) {
        for (int i = offset; i < len - offset; i++) {
            putByteA(data[i]);
        }
        return this;
    }

    /**
     * Puts several type-A bytes into the buffer.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putBytesA(Packet data, int offset, int len) {
        for (int i = offset; i < len - offset; i++) {
            putByteA(data.getPayload().getByte(i));
        }
        return this;
    }

    /**
     * Writes an integer.
     * @param i The integer.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putInt(int i) {
        payload.writeInt(i);
        return this;
    }

    /**
     * Writes a type-1 integer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putInt1(int val) {
        payload.writeByte(val >> 8);
        payload.writeByte(val);
        payload.writeByte(val >> 24);
        payload.writeByte(val >> 16);
        return this;
    }

    /**
     * Writes a type-2 integer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putInt2(int val) {
        payload.writeByte(val >> 16);
        payload.writeByte(val >> 24);
        payload.writeByte(val);
        payload.writeByte(val >> 8);
        return this;
    }

    /**
     * Puts a string into the buffer. (it prefixes & postfixes the string)
     * @param str The string.
     * @return The PacketBuilder for chaining.
     */
    public PacketBuilder putJagString(String str) {
        put(0);
        putBytes(str.getBytes());
        put(0);
        return this;
    }

    /**
     * Writes a little-endian integer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putLEInt(int val) {
        payload.writeByte(val);
        payload.writeByte(val >> 8);
        payload.writeByte(val >> 16);
        payload.writeByte(val >> 24);
        return this;
    }

    /**
     * Writes a little-endian short.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putLEShort(int val) {
        payload.writeByte(val);
        payload.writeByte(val >> 8);
        return this;
    }

    /**
     * Writes a little endian type-A short.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putLEShortA(int val) {
        payload.writeByte(val + 128);
        payload.writeByte(val >> 8);
        return this;
    }

    /**
     * Writes a long.
     * @param l The long.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putLong(long l) {
        payload.writeLong(l);
        return this;
    }

    /**
     * Puts a series of reversed bytes in the buffer.
     * @param is The source byte array.
     * @param offset The offset.
     * @param length The length.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putReverse(byte[] is, int offset, int length) {
        for(int i = (offset + length - 1); i >= offset; i--) {
            payload.writeByte(is[i]);
        }
        return this;
    }

    /**
     * Puts a series of reversed type-A bytes in the buffer.
     * @param is The source byte array.
     * @param offset The offset.
     * @param length The length.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putReverseA(byte[] is, int offset, int length) {
        for(int i = (offset + length - 1); i >= offset; i--) {
            putByteA(is[i]);
        }
        return this;
    }

    /**
     * Writes a short.
     * @param s The short.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putShort(int s) {
        payload.writeShort(s);
        return this;
    }

    /**
     * Writes a type-A short.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putShortA(int val) {
        payload.writeByte(val >> 8);
        payload.writeByte(val + 128);
        return this;
    }

    /**
     * Puts a byte or short for signed use.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putSignedSmart(int val) {
        if(val >= 128) {
            putShort(val + 49152);
        } else {
            put(val + 64);
        }
        return this;
    }

    /**
     * Puts a byte or short.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putSmart(int val) {
        if(val >= 128) {
            putShort(val + 32768);
        } else {
            put(val);
        }
        return this;
    }

    /**
     * Puts a 3-byte integer.
     * @param val The value.
     * @return The PacketBuilder instance, for chaining.
     */
    public PacketBuilder putTriByte(int val) {
        payload.writeByte(val >> 16);
        payload.writeByte(val >> 8);
        payload.writeByte(val);
        return this;
    }

    /**
     * Switches the access mode to BIT_ACCESS
     */
    public void switchToBitAccess() {
        if (mode == Packet.AccessMode.BIT_ACCESS) {
            throw new IllegalStateException("Already set to bit access mode");
        }
        mode = Packet.AccessMode.BIT_ACCESS;
        bitIndex = payload.writerIndex() * 8;
    }

    /**
     * Switches the access mode to Byte_Access.
     */
    public void switchToByteAccess() {
        if (mode == Packet.AccessMode.BYTE_ACCESS) {
            throw new IllegalStateException("Already set to byte access mode");
        }
        mode = Packet.AccessMode.BYTE_ACCESS;
        payload.writerIndex((bitIndex + 7) / 8);
    }

    /**
     * Returns the PacketBuilder data to a new Packet.
     * @return A new Packet with the data from the PacketBuilder.
     */
    public Packet toPacket() {
        return new Packet(opcode, type, payload);
    }
}

