package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.MainInterfaceContext;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:57PM
 * To change this template use File | Settings | File Templates.
 */
public class MainInterface implements PacketEncoder<MainInterfaceContext> {

    @Override
    public Frame encode(MainInterfaceContext context) {
        int[] interfaceKeys = new int[]{0, 0, 0, 0};
        FrameBuffer buffer = new FrameBuffer(new Frame(148, 19));
        return buffer.writeInt(interfaceKeys[0]).writeInt(interfaceKeys[1]).writeShortA(context.interfaceId).writeInt2(interfaceKeys[3]).writeByteC(0).writeInt1(interfaceKeys[2]).getFrame();
    }
}
