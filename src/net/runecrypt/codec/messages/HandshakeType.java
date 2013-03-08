package net.runecrypt.codec.messages;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 4:53 PM
 * To change this template use File | Settings | File Templates.
 */
public final class HandshakeType {

    public static enum HandshakeTypes {HANDSHAKE_LOGIN, HANDSHAKE_ONDEMAND}

    private HandshakeTypes type;

    /**
     * Constructs a new {@code HandshakeType} instance.
     *
     * @param opcode The incomming handshake opcode.
     */
    public HandshakeType(int opcode) {
        this.type = forOpcode(opcode);
    }

    /**
     * Gets the handshake type based on the incomming opcode.
     *
     * @param opcode The incomming opcode from the handshake state.
     * @return The {@link HandshakeTypes} for the opcode.
     */
    public HandshakeTypes forOpcode(int opcode) {
        switch (opcode) {
            case 15:
                return HandshakeTypes.HANDSHAKE_ONDEMAND;
            case 14:
                return HandshakeTypes.HANDSHAKE_LOGIN;
        }
        throw new IllegalStateException("No such state for incomming opcode!");
    }

    /**
     * Gets the current type of the handshake.
     *
     * @return The handshake type.
     */
    public HandshakeTypes getType() {
        return type;
    }
}
