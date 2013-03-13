package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.ConfigContext;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Config751 implements PacketEncoder<ConfigContext> {

    @Override
    public Frame encode(ConfigContext context) {
        FrameBuffer packetBuilder = null;
        if (!context.csConfig) {
        	if (context.value <= Byte.MAX_VALUE || context.value >= Byte.MAX_VALUE)
        		packetBuilder = new FrameBuffer(new Frame(129, 6)).writeShortA(context.id).writeInt(context.value);
        	else
        		packetBuilder = new FrameBuffer(new Frame(87, 3)).writeLEShortA(context.id).writeByte(context.value);
        } else {
        	if (context.value <= Byte.MAX_VALUE || context.value >= Byte.MAX_VALUE)
        		packetBuilder = new FrameBuffer(new Frame(2, 6)).writeInt(context.value).writeShortA(context.id);
        	else
        		packetBuilder = new FrameBuffer(new Frame(127, 3)).writeLEShortA(context.id).writeByteS(context.value);
        }
        return packetBuilder.getFrame();
    }
}
