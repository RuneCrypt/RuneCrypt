package net.runecrypt.codec.codec317.decoders;

import net.runecrypt.codec.codec317.context.CommandContext;
import net.runecrypt.network.PacketReader;
import net.runecrypt.network.packet.PacketDecoder;
import net.runecrypt.util.BufferUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandDecoder implements PacketDecoder<CommandContext> {

    @Override
    public CommandContext decode(PacketReader packet) {
        String command = BufferUtils.readString(packet.getPayload());
        String[] parts = command.toLowerCase().split(" ");
        return new CommandContext(command, parts);
    }
}
