package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.LoginContext;
import net.runecrypt.game.World.LoginType;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 12:57PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginResponseEncoder implements PacketEncoder<LoginContext> {

    @Override
    public Frame encode(LoginContext context) {
        if (context.response.returnCode != 2) {
            return new FrameBuffer(new Frame(-1, -1)).writeByte(context.response.returnCode).getFrame();
        }

        FrameBuffer buffer = null;
        if (context.response.loginType.equals(LoginType.LOBBY)) {
            buffer = new FrameBuffer(new Frame(context.response.returnCode, Frame.BYTE));

            buffer.writeByte(0);
            buffer.writeByte(0);
            buffer.writeByte(0);

            buffer.writeTriByte(343932928);
            buffer.writeByte(1);
            buffer.writeByte(0);
            buffer.writeByte(0);

            buffer.writeLong(0);
            buffer.write40BitInt(1012908003860L);
            buffer.writeByte(0);
            buffer.writeInt(0);

            buffer.writeByte(0);
            buffer.writeInt(0);

            buffer.writeShort(1);
            buffer.writeShort(3);
            buffer.writeShort(1);
            buffer.writeInt(0);

            buffer.writeByte(0);

            buffer.writeShort(53791);
            buffer.writeShort(53791);

            buffer.writeByte(0);
            buffer.writeGJString2(context.response.player.playerDef.getUsername());

            buffer.writeByte(0);
            buffer.writeInt(4650553);

            buffer.writeByte(0);
            buffer.writeShort(1);
            buffer.writeGJString2("127.0.0.1");
        } else {
            buffer = new FrameBuffer(new Frame(context.response.returnCode, Frame.BYTE));

            buffer.writeByte(context.response.player.playerDef.getRights().intValue);
            buffer.writeByte(0);
            buffer.writeByte(0);
            buffer.writeByte(0);
            buffer.writeByte(0);
            buffer.writeByte(0);

            buffer.writeShort(context.response.player.getIndex());

            buffer.writeByte(0);
            buffer.writeTriByte(0);
            buffer.writeByte(1);
        }
        return buffer.getFrame();
    }
}
