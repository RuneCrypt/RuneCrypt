package net.runecrypt.codec.codec317.network.codec.login;

import net.burtleburtle.bob.rand.IsaacRandom;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.messages.LoginRequest;
import net.runecrypt.game.World.LoginType;
import net.runecrypt.game.model.player.PlayerDef;
import net.runecrypt.util.BufferUtils;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.net.ProtocolException;
import java.security.SecureRandom;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginDecoder extends FrameDecoder {

    private enum LoginState {HANDSHAKE, HEADER, PAYLOAD}

    private LoginState loginState = LoginState.HANDSHAKE;
    private long serverSeed;
    private int loginSize;
    private final CodecManifest codecManifest;

    public LoginDecoder(CodecManifest codecManifest) {
        this.codecManifest = codecManifest;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        switch (loginState) {
            case HANDSHAKE:
                return decodeHandshake(ctx, channel, buffer);
            case HEADER:
                return decodeHeader(ctx, channel, buffer);
            case PAYLOAD:
                return decodePayload(ctx, channel, buffer);
        }
        return null;
    }

    /**
     * Decodes the payload for the login procedure.
     *
     * @param ctx     The channel handler context.
     * @param channel The channel we're decoding for.
     * @param buffer  The buffer for reading or writing data.
     * @return The decoded frame, or {@code Null}
     */
    private Object decodePayload(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws ProtocolException {
        if (!(buffer.readableBytes() >= loginSize))
            throw new IllegalStateException("Not enough readable bytes in the buffer.");

        ChannelBuffer channelBuffer = buffer.readBytes(loginSize);

        int magicValue = channelBuffer.readByte() & 0xFF;
        if (magicValue != 255)
            throw new ProtocolException("Invalid magic value: " + magicValue);

        int clientVersion = channelBuffer.readShort() & 0xFFFF;
        if (clientVersion != codecManifest.requiredProtocol())
            throw new ProtocolException("Invalid client version: " + clientVersion);

        int memoryFlag = channelBuffer.readByte() & 0xFF;
        if (memoryFlag != 0 && memoryFlag != 1)
            throw new ProtocolException("Invalid memory flag: " + memoryFlag);

        int[] cacheArchive = new int[9];
        for (int pos = 0; pos < cacheArchive.length; pos++)
            cacheArchive[pos] = channelBuffer.readInt();

        int securePayloadLength = channelBuffer.readByte();
        if (!(securePayloadLength == loginSize - 41))
            throw new ProtocolException("Invalid secure payload size: " + securePayloadLength);

        ChannelBuffer secureBuffer = channelBuffer.readBytes(securePayloadLength);

        int rsaOpcode = secureBuffer.readByte() & 0xFF;
        if (!(rsaOpcode == 10))
            throw new ProtocolException("Invalid RSA opcode: " + rsaOpcode);

        long clientSeed = secureBuffer.readLong();
        long secureServerSeed = secureBuffer.readLong();
        if (!(secureServerSeed == serverSeed))
            throw new ProtocolException("Invalid server seed: " + serverSeed);

        @SuppressWarnings("unused")
        int uniqueId = secureBuffer.readInt();

        String username = BufferUtils.readString(secureBuffer).trim();
        String password = BufferUtils.readString(secureBuffer).trim();

        if (username.length() > 12 || password.length() > 20)
            throw new ProtocolException("Invalid credentials length!");

        int[] isaacSeed = new int[4];
        isaacSeed[0] = (int) clientSeed >> 32;
        isaacSeed[1] = (int) clientSeed;
        isaacSeed[2] = (int) serverSeed >> 32;
        isaacSeed[3] = (int) serverSeed;

        IsaacRandom decodingRandom = new IsaacRandom(isaacSeed);
        for (int seed = 0; seed < isaacSeed.length; seed++)
            isaacSeed[seed] += 50;
        IsaacRandom encodingRandom = new IsaacRandom(isaacSeed);

        // XXX: Find a better place to do this..
        PlayerDef.Rights rights = PlayerDef.Rights.STANDARD;
        if (username.trim().equalsIgnoreCase("Tom"))
            rights = PlayerDef.Rights.ADMINISTRATOR;
        PlayerDef playerDef = new PlayerDef(username, password, rights);

        LoginRequest loginRequest = new LoginRequest(playerDef, decodingRandom, encodingRandom, codecManifest, LoginType.WORLD);
        return buffer.readable() ? new Object[]{loginRequest, buffer.readBytes(buffer.readableBytes())} : loginRequest;
    }

    /**
     * Decodes the header for the login procedure.
     *
     * @param ctx     The channel handler context.
     * @param channel The channel we're decoding for.
     * @param buffer  The buffer for reading or writing data.
     * @return The decoded frame, or {@code Null}
     */
    private Object decodeHeader(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws ProtocolException {
        if (!(buffer.readableBytes() >= 2))
            throw new IllegalStateException("Not enough readable bytes in the buffer.");

        int loginType = buffer.readByte() & 0xFF;
        if (loginType != 16 && loginType != 18)
            throw new ProtocolException("Invalid login type: " + loginType);

        loginSize = buffer.readByte() & 0xFF;
        loginState = LoginState.PAYLOAD;
        return null;
    }

    /**
     * Decodes the handshake for the login procedure.
     *
     * @param ctx     The channel handler context.
     * @param channel The channel we're decoding for.
     * @param buffer  The buffer for reading or writing data.
     * @return The decoded frame, or {@code Null}
     */
    private Object decodeHandshake(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
        if (!buffer.readable())
            throw new IllegalStateException("The buffer is not readable.");

        @SuppressWarnings("unused")
        int usernameHash = buffer.readByte() & 0xFF;
        serverSeed = new SecureRandom().nextLong();

        ChannelBuffer channelBuffer = ChannelBuffers.buffer(17);
        channelBuffer.writeByte(0);
        channelBuffer.writeLong(0);
        channelBuffer.writeLong(serverSeed);
        channel.write(channelBuffer);

        loginState = LoginState.HEADER;
        return null;
    }
}
