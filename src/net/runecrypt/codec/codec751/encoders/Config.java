package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.ConfigContext;
import net.runecrypt.network.Packet;
import net.runecrypt.network.PacketBuilder;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 11:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Config implements PacketEncoder<ConfigContext> {

    @Override
    public Packet encode(ConfigContext context) {
        PacketBuilder packetBuilder;

        System.out.println(context.value <= Byte.MAX_VALUE || context.value >= Byte.MAX_VALUE);
        if (context.value <= Byte.MAX_VALUE || context.value >= Byte.MAX_VALUE)
            packetBuilder = new PacketBuilder(129).putShortA(context.id).putInt(context.value);
        else
            packetBuilder = new PacketBuilder(87).putLEShortA(context.id).put(context.value);
        return packetBuilder.toPacket();
    }
}
