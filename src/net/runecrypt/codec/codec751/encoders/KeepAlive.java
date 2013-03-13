package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketContext;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class KeepAlive implements PacketEncoder<PacketContext> {

    @Override
    public Frame encode(PacketContext context) {
        return new FrameBuffer(new Frame(3, 0)).getFrame();
    }
}
