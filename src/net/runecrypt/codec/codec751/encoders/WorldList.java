package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.GameEngine;
import net.runecrypt.codec.codec751.context.WorldListUpdateContext;
import net.runecrypt.network.Packet;
import net.runecrypt.network.PacketBuilder;
import net.runecrypt.network.packet.PacketEncoder;
import net.runecrypt.util.World;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldList implements PacketEncoder<WorldListUpdateContext> {

    @Override
    public Packet encode(WorldListUpdateContext context) {
        boolean skipUpdate = context.skipUpdate();
        boolean fullUpdate = context.isFullUpdate();

        List<World> worlds = GameEngine.getInstance().getWorlds();

        int size = worlds.size();

        PacketBuilder builder = new PacketBuilder(39, Packet.PacketType.SHORT);

        builder.put(skipUpdate ? 0 : 1);

        if(!skipUpdate) {
			/*
			 * The main block of the world list
			 */
            builder.put(2);

            builder.put(fullUpdate ? 1 : 0);
            if(fullUpdate) {
                builder.putSmart(size);
                for (World w : worlds) {
                    builder.putSmart(w.getCountry());
                    builder.putJagString(w.getRegion());
                }
                builder.putSmart(0);
                builder.putSmart(size + 1);
                builder.putSmart(size);
                for (World w : worlds) {
                    builder.putSmart(w.getWorldId());
                    builder.put(w.getLocation());
                    builder.putInt(w.getFlag());
                    builder.putJagString(w.getActivity());
                    builder.putJagString(w.getIp());
                }
                builder.putInt(-1723296702);
            }

			/*
			 * Player amount update
			 */
            for (World w : worlds) {
                builder.putSmart(w.getWorldId());
                builder.putShort(2000);
            }
        }
        return builder.toPacket();
    }
}
