package net.runecrypt.codec.session.impl;

import net.runecrypt.codec.Codec;
import net.runecrypt.codec.messages.LoginRequest;
import net.runecrypt.codec.messages.LoginResponse;
import net.runecrypt.codec.session.Session;
import net.runecrypt.game.World;
import net.runecrypt.game.model.player.Player;
import net.runecrypt.network.RS2PacketDecoder;
import net.runecrypt.network.RS2PacketEncoder;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 5:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginSession extends Session {

    private final Codec codec;

    /**
     * Constructs a new {@code LoginSession} instance.
     *
     * @param ctx   The channel handler context for the session.
     * @param codec The current codec for the server.
     */
    public LoginSession(ChannelHandlerContext ctx, Codec codec) {
        super(ctx);
        this.codec = codec;
    }

    @Override
    public void disconnected() {

    }

    @Override
    public void message(Object obj) {
        if (!(obj instanceof LoginRequest))
            return;

        LoginRequest loginRequest = (LoginRequest) obj;
        Player player = new Player(loginRequest.getPlayerDef(), getChannel());

        int returnCode = 2;

        if (World.getInstance().getPlayer(player.playerDef.getUsername()) != null)
            returnCode = 3;
        else if (!World.getInstance().register(player)) {
            returnCode = 7;
        }

        ChannelFuture future = getChannel().write(new LoginResponse(returnCode, player.playerDef.getRights(), player.getIndex()));
        disconnected();

        if (player != null && returnCode == 2) {

            channel.getPipeline().addFirst("packetEncoder", new RS2PacketEncoder(loginRequest.getEncodingRandom()));
            channel.getPipeline().addAfter("packetEncoder", "packetDecoder", new RS2PacketDecoder(loginRequest.getDecodingRandom(), codec));

            GameSession gameSession = new GameSession(getContext(), player);
            getContext().setAttachment(gameSession);

            player.sendLoginConfigs();

            channel.getPipeline().remove("loginDecoder");
            channel.getPipeline().remove("loginEncoder");
        } else
            future.addListener(ChannelFutureListener.CLOSE);
    }
}
