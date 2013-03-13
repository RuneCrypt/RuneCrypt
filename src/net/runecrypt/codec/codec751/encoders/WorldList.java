package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.GameEngine;
import net.runecrypt.codec.codec751.context.WorldListUpdateContext;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
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
    public Frame encode(WorldListUpdateContext context) {
        boolean skipUpdate = context.skipUpdate();
        boolean fullUpdate = context.isFullUpdate();

        List<World> worlds = GameEngine.getInstance().getWorlds();

        int size = worlds.size();

        FrameBuffer builder = new FrameBuffer(new Frame(39, Frame.SHORT));

        builder.writeByte(skipUpdate ? 0 : 1);

        if(!skipUpdate) {
			/*
			 * The main block of the world list
			 */
            builder.writeByte(2);

            builder.writeByte(fullUpdate ? 1 : 0);
            if(fullUpdate) {
                builder.writeSmart(size);
                for (World w : worlds) {
                    builder.writeSmart(w.getCountry());
                    builder.writeJagString(w.getRegion());
                }
                builder.writeSmart(0);
                builder.writeSmart(size + 1);
                builder.writeSmart(size);
                for (World w : worlds) {
                    builder.writeSmart(w.getWorldId());
                    builder.writeByte(w.getLocation());
                    builder.writeInt(w.getFlag());
                    builder.writeJagString(w.getActivity());
                    builder.writeJagString(w.getIp());
                }
                builder.writeInt(-1723296702);
            }

			/*
			 * Player amount update
			 */
            int playersAmount = 0;
            for (int i = 1; i < 2048; i++) {
            	if (net.runecrypt.game.World.getInstance().players[i] != null)
            		playersAmount += 1;
            }
            for (World w : worlds) {
                builder.writeSmart(w.getWorldId());
                builder.writeShort(playersAmount);
            }
        }
        return builder.getFrame();
    }
}
