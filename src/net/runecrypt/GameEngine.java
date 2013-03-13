package net.runecrypt;

import net.runecrypt.network.packet.PacketCodec;
import net.runecrypt.util.World;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameEngine {

    private static GameEngine instance = new GameEngine();
    public PacketCodec packetCodec;

    {
        packetCodec = new PacketCodec();
    }

    private List<World> worlds = new LinkedList<>();

    public GameEngine() {
        World world = new World(1, 0, 1, "Game-Server", "127.0.0.1", "United Kindom", 77);
        worlds.add(world);
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
     * Gets the current worlds.
     *
     * @return The worlds.
     */
    public List<World> getWorlds() {
        return worlds;
    }
}
