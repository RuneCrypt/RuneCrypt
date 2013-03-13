package net.runecrypt.codec.codec751.network.codec.login;

import net.burtleburtle.bob.rand.IsaacRandom;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.messages.LoginRequest;
import net.runecrypt.game.World;
import net.runecrypt.game.model.player.PlayerDef;
import net.runecrypt.util.Base37Utils;
import net.runecrypt.util.BufferUtils;
import net.runecrypt.util.XTEA;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import java.math.BigInteger;
import java.net.ProtocolException;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoginDecoder extends FrameDecoder {

    public enum Stage {CONNECTION_TYPE, CLIENT_DETAILS, LOBBY_PAYLOAD, GAME_PAYLOAD;}

    public enum LoginTypes {LOBBY, WORLD;}

    private Stage loginStage = Stage.CONNECTION_TYPE;
    private LoginTypes currentLoginType;
    private int loginSize;
    public static final BigInteger PRIVATE_KEY = new BigInteger("19995208279434640096892507256096908221263874149780530219738086866146143290110260804309969772164835044634499773739318947735348019930047603447049989345934040121703500372765332102218934039932345980975951248449320248313689115121009055683300030846159090149354162475251010111949487591870953214320230424535985099641");
    public static final BigInteger MODULUS_KEY = new BigInteger("137231748351587392190809953716915182123465338795074521835896428835126169526019076587293170903588521763557567459582547479079642180558752726684397858599275255304449851625071569461332634322311365163954923133814322684645819581379454656384728571691404975282936307990170244347701323826199274022610768178400420620079");
    private CodecManifest codecManifest;

    /**
     * Constructs a new {@code LoginDecoder} instance.
     *
     * @param codecManifest The codec manifest for the decoder.
     */
    public LoginDecoder(CodecManifest codecManifest) {
        this.codecManifest = codecManifest;
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        switch (loginStage) {
            case CONNECTION_TYPE:
                return decodeConnectionType(buffer);
            case CLIENT_DETAILS:
                return decodeClientDetails(buffer);
            case LOBBY_PAYLOAD:
                return decodeLobbyPayload(buffer);
            case GAME_PAYLOAD:
                return decodeGamePayload(buffer);
        }
        return null;
    }

    /**
     * Decodes the lobby payload of the login procedure.
     *
     * @param buffer The buffer for reading data.
     * @return The frame or {@code Null}.
     */
    private Object decodeLobbyPayload(ChannelBuffer buffer) throws ProtocolException {
        if (!(buffer.readableBytes() >= 2))
            throw new IllegalStateException("Not enough readable bytes from buffer.");

        int secureBufferSize = buffer.readShort() & 0xFFFF;
        if (!(buffer.readableBytes() >= secureBufferSize))
            throw new IllegalStateException("Not enough readable bytes from buffer.");

        byte[] secureBytes = new byte[secureBufferSize];
        buffer.readBytes(secureBytes);
        ChannelBuffer secureBuffer = ChannelBuffers.wrappedBuffer(new BigInteger(secureBytes).modPow(PRIVATE_KEY, MODULUS_KEY).toByteArray());

        int blockOpcode = secureBuffer.readByte() & 0xFF;
        if (blockOpcode != 10)
            throw new ProtocolException("Invalid block opcode: " + blockOpcode);

        int[] xteaKey = new int[4];
        for (int i = 0; i < xteaKey.length; i++)
            xteaKey[i] = secureBuffer.readInt();

        long loginHash = secureBuffer.readLong();
        if (loginHash != 0)
            throw new ProtocolException("Invalid login hash: " + loginHash);

        String password = BufferUtils.readJagexString(secureBuffer);

        long[] loginSeeds = new long[2];
        for (int i = 0; i < loginSeeds.length; i++)
            loginSeeds[i] = secureBuffer.readLong();

        byte[] xteaBlock = new byte[buffer.readableBytes()];
        buffer.readBytes(xteaBlock);
        ChannelBuffer xteaBuffer = ChannelBuffers.wrappedBuffer(XTEA.decipher(xteaKey, xteaBlock));

        boolean decodeAsString = xteaBuffer.readByte() == 1;
        String username = decodeAsString ? BufferUtils.readJagexString(xteaBuffer) : Base37Utils.decodeBase37(xteaBuffer.readLong());

        @SuppressWarnings("unused")
        int displayMode = xteaBuffer.readByte() & 0xFF;
        xteaBuffer.readByte();

        byte[] randomData = new byte[24];
        for (int i = 0; i < randomData.length; i++)
            randomData[i] = (byte) (xteaBuffer.readByte() & 0xFF);

        @SuppressWarnings("unused")
        String clientSettings = BufferUtils.readJagexString(xteaBuffer);
        int indexFiles = xteaBuffer.readByte() & 0xFF;


        int[] crcValues = new int[indexFiles];
        for (int i = 0; i < crcValues.length; i++)
            crcValues[i] = xteaBuffer.readInt();

        int[] serverKeys = new int[xteaKey.length];
        for (int i = 0; i < serverKeys.length; i++)
            serverKeys[i] = xteaKey[i] + 50;

        int[] clientKeys = xteaKey;
        IsaacRandom encodingRandom = new IsaacRandom(serverKeys);
        IsaacRandom decodingRandom = new IsaacRandom(clientKeys);

        PlayerDef playerDef = new PlayerDef(username.trim(), password.trim(), PlayerDef.Rights.STANDARD);
        return new LoginRequest(playerDef, decodingRandom, encodingRandom, codecManifest, World.LoginType.LOBBY);
    }


    /**
     * Decodes the game payload of the login procedure.
     *
     * @param buffer The buffer for reading data.
     * @return The frame or {@code Null}.
     */
    private Object decodeGamePayload(ChannelBuffer buffer) throws ProtocolException {
        if (!(buffer.readableBytes() >= 2))
            throw new IllegalStateException("Not enough readable bytes from buffer.");

        int secureBufferSize = buffer.readShort() & 0xFFFF;
        if (!(buffer.readableBytes() >= secureBufferSize))
            throw new IllegalStateException("Not enough readable bytes from buffer.");

        byte[] secureBytes = new byte[secureBufferSize];
        buffer.readBytes(secureBytes);
        ChannelBuffer secureBuffer = ChannelBuffers.wrappedBuffer(new BigInteger(secureBytes).modPow(PRIVATE_KEY, MODULUS_KEY).toByteArray());

        int blockOpcode = secureBuffer.readByte() & 0xFF;
        if (blockOpcode != 10)
            throw new ProtocolException("Invalid block opcode: " + blockOpcode);

        int[] xteaKey = new int[4];
        for (int i = 0; i < xteaKey.length; i++)
            xteaKey[i] = secureBuffer.readInt();

        long loginHash = secureBuffer.readLong();
        if (loginHash != 0)
            throw new ProtocolException("Invalid login hash: " + loginHash);

        String password = BufferUtils.readJagexString(secureBuffer);

        long[] loginSeeds = new long[2];
        for (int i = 0; i < loginSeeds.length; i++)
            loginSeeds[i] = secureBuffer.readLong();

        byte[] xteaBlock = new byte[buffer.readableBytes()];
        buffer.readBytes(xteaBlock);
        ChannelBuffer xteaBuffer = ChannelBuffers.wrappedBuffer(XTEA.decipher(xteaKey, xteaBlock));

        boolean decodeAsString = xteaBuffer.readByte() == 1;
        String username = decodeAsString ? BufferUtils.readJagexString(xteaBuffer) : Base37Utils.decodeBase37(xteaBuffer.readLong());

        @SuppressWarnings("unused")
        int displayMode = xteaBuffer.readByte() & 0xFF;
        xteaBuffer.readByte();

        xteaBuffer.readShort();
        xteaBuffer.readShort();

        byte[] randomData = new byte[24];
        for (int i = 0; i < randomData.length; i++)
            randomData[i] = (byte) (xteaBuffer.readByte() & 0xFF);

        @SuppressWarnings("unused")
        String clientSettings = BufferUtils.readJagexString(xteaBuffer);

        xteaBuffer.readInt();
        xteaBuffer.skipBytes(xteaBuffer.readByte() & 0xFF);

        int[] serverKeys = new int[xteaKey.length];
        for (int i = 0; i < serverKeys.length; i++)
            serverKeys[i] = xteaKey[i] + 50;

        int[] clientKeys = xteaKey;
        IsaacRandom encodingRandom = new IsaacRandom(serverKeys);
        IsaacRandom decodingRandom = new IsaacRandom(clientKeys);

        PlayerDef playerDef = new PlayerDef(username.trim(), password.trim(), PlayerDef.Rights.STANDARD);
        return new LoginRequest(playerDef, decodingRandom, encodingRandom, codecManifest, World.LoginType.WORLD);
    }

    /**
     * Decodes the client details of the login procedure.
     *
     * @param buffer The buffer for reading data.
     * @return The frame or {@code Null}.
     */
    private Object decodeClientDetails(ChannelBuffer buffer) {
        if (!(buffer.readableBytes() >= loginSize))
            throw new IllegalStateException("Not enough readable bytes from buffer!");

        int version = buffer.readInt();
        int subVersion = buffer.readInt();

        if (version != 751 && subVersion != 1)
            throw new IllegalStateException("Invalid client version/sub-version!");

        if (!currentLoginType.equals(LoginTypes.LOBBY)) buffer.readByte();

        if (currentLoginType.equals(LoginTypes.LOBBY))
            loginStage = Stage.LOBBY_PAYLOAD;
        else
            loginStage = Stage.GAME_PAYLOAD;
        return null;
    }

    /**
     * Decodes the connection type of the login procedure.
     *
     * @param buffer The buffer for reading data.
     * @return The frame or {@code Null}.
     */
    private Object decodeConnectionType(ChannelBuffer buffer) throws ProtocolException {
        if (!buffer.readable())
            throw new IllegalStateException("Not enough readable bytes from buffer!");

        int loginType = buffer.readByte() & 0xFF;
        if (loginType != 16 && loginType != 19 && loginType != 18)
            throw new ProtocolException("Invalid login type: " + loginType);

        currentLoginType = loginType == 19 ? LoginTypes.LOBBY : LoginTypes.WORLD;
        loginSize = buffer.readShort() & 0xFFFF;

        loginStage = Stage.CLIENT_DETAILS;
        return null;
    }
}
