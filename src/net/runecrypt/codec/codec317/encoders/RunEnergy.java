package net.runecrypt.codec.codec317.encoders;

import net.runecrypt.network.Packet;
import net.runecrypt.network.PacketBuilder;
import net.runecrypt.network.packet.PacketEncoder;
import net.runecrypt.codec.codec317.context.RunEnergyContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:37 AM
 * To change this template use File | Settings | File Templates.
 */
public final class RunEnergy implements PacketEncoder<RunEnergyContext> {

    @Override
    public Packet encode(RunEnergyContext context) {
        return new PacketBuilder(110).put((byte)context.runEnergyAmount).toPacket();
    }
}
