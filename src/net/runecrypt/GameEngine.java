package net.runecrypt;

import net.runecrypt.network.packet.PacketCodec;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngine {

    private static GameEngine instance = new GameEngine();
    private PacketCodec packetCodec;

    {
        packetCodec = new PacketCodec();
    }

    /**
     * Gets the singleton instance for the game engine.
     *
     * @return The single instance.
     */
    public static GameEngine getInstance() {
        return instance;
    }

    /**
     * Gets the packet codec instance.
     *
     * @return The codec instance
     */
    public PacketCodec getPacketCodec() {
        return packetCodec;
    }
}
