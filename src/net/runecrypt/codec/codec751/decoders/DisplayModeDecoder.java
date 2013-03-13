package net.runecrypt.codec.codec751.decoders;

import net.runecrypt.codec.codec751.context.DisplayModeContext;
import net.runecrypt.codec.codec751.context.DisplayModeContext.DisplayModes;
import net.runecrypt.network.FrameReader;
import net.runecrypt.network.packet.PacketDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:24PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayModeDecoder implements PacketDecoder<DisplayModeContext> {

    @Override
    public DisplayModeContext decode(FrameReader packet) {
        int displayMode = packet.readUnsignedByte();
        int width = packet.readUnsignedShort();
        int height = packet.readUnsignedShort();
        packet.readUnsignedByte();
        return new DisplayModeContext(DisplayModes.forId(displayMode), width, height);
    }
}
