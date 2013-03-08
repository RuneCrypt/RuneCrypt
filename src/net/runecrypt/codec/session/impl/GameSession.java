package net.runecrypt.codec.session.impl;

import net.runecrypt.GameEngine;
import net.runecrypt.codec.session.Session;
import net.runecrypt.game.model.player.Player;
import net.runecrypt.network.Packet;
import net.runecrypt.network.PacketReader;
import net.runecrypt.network.packet.PacketAssembler;
import net.runecrypt.network.packet.PacketContext;
import net.runecrypt.network.packet.PacketDecoder;
import net.runecrypt.network.packet.PacketHandler;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 11:34 PM
 * To change this template use File | Settings | File Templates.
 */
public final class GameSession extends Session {

    private final Player player;

    /**
     * Creates a new Session.
     *
     * @param context The channel handler context.
     * @param player  The player the session is for.
     */
    public GameSession(ChannelHandlerContext context, Player player) {
        super(context);
        this.player = player;
    }

    @Override
    public void disconnected() {
         // XXX: Unregister player.
    }

    @Override
    public void message(Object obj) {
        Packet gamePacket = (Packet) obj;
        PacketAssembler assembler = GameEngine.getInstance().getPacketCodec().get(gamePacket.getOpcode());
        if(assembler != null) {
            PacketDecoder<?> decoder = assembler.getDecoder();
            if(decoder != null) {
                PacketContext context = decoder.decode(new PacketReader(gamePacket));
                if(context != null) {
                    @SuppressWarnings("unchecked")
                    PacketHandler<PacketContext> handler = (PacketHandler<PacketContext>) assembler.getHandler();
                    if(handler != null) {
                        handler.handle(player, context);
                    }
                }
            } else {
                PacketHandler<?> handler = assembler.getHandler();
                if(handler != null) {
                    handler.handle(player, null);
                }
            }
        }
    }
}
