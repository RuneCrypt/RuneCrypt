package net.runecrypt.network.packet;

import java.util.HashMap;
import java.util.Map;

import net.runecrypt.network.Frame;

/**
 * @author Sean
 */
public final class PacketCodec {

    /**
     * The maximum packets.
     */
    private static final int MAX_PACKETS = 256;

    /**
     * An array of packet decoders.
     */
    private final PacketAssembler[] decoders = new PacketAssembler[MAX_PACKETS];

    /**
     * A map of packet encoders.
     */
    private final Map<Class<?>, PacketEncoder<? extends PacketContext>> encoders = new HashMap<Class<?>, PacketEncoder<? extends PacketContext>>();

    /**
     * Creates the PacketCodec
     */
    public PacketCodec() {
    }

    /**
     * Encodes a certain game packet.
     *
     * @param identifier The identifier of the encoder.
     * @param context    The game packet context.
     * @return The encoded game packet.
     */
    public Frame encode(Class<?> identifier, PacketContext context) {
        return get(identifier).encode(context);
    }

    /**
     * Gets a game packet encoder from the encoder map.
     *
     * @param <T>        The GamePacketContext type.
     * @param identifier The identifier of the encoder.
     * @return A encoder.
     */
    private <T extends PacketContext> PacketEncoder<T> get(Class<?> identifier) {
        @SuppressWarnings("unchecked")
        PacketEncoder<T> encoder = (PacketEncoder<T>) encoders.get(identifier);
        if (encoder == null)
            throw new RuntimeException("No GamePacketEncoder by this name:" + identifier);
        else
            return encoder;
    }

    /**
     * Gets a packet decoder.
     *
     * @param opcode The packet opcode.
     * @return The packet decoder.
     */
    public PacketAssembler get(int opcode) {
        return decoders[opcode];
    }

    /**
     * Registers an packet encoder.
     *
     * @param clazz The packet encoder to register.
     */
    public void register(Class<? extends PacketEncoder<?>> clazz) throws Exception {
        encoders.put(clazz, clazz.newInstance());
    }

    /**
     * Registers a decoder.
     *
     * @param opcode  The opcode of the decoder.
     * @param decoder The packet decoder to register.
     * @param handler The packet handler.
     */
    public void register(int opcode, PacketDecoder<?> decoder, PacketHandler<?> handler) {
        decoders[opcode] = new PacketAssembler(decoder, handler);
    }


    /**
     * Registers a decoder.
     *
     * @param opcode  The opcode of the decoder.
     * @param handler The packet handler
     */
    public void register(int opcode, PacketHandler<?> handler) {
        decoders[opcode] = new PacketAssembler(handler);
    }
}
