package net.runecrypt.codec.codec751.decoders;

import net.runecrypt.codec.codec751.context.WorldListContext;
import net.runecrypt.network.PacketReader;
import net.runecrypt.network.packet.PacketDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorldListDecoder implements PacketDecoder<WorldListContext> {

    @Override
    public WorldListContext decode(PacketReader packet) {
        return new WorldListContext(packet.getInt());
    }
}
