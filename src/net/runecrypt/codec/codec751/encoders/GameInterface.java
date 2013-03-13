package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.GameInterfaceContext;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 1:57PM
 * To change this template use File | Settings | File Templates.
 */
public class GameInterface implements PacketEncoder<GameInterfaceContext> {

	@Override
	public Frame encode(GameInterfaceContext context) {
		int[] interfaceKeys = new int[] { 0, 0, 0, 0 };
		
		FrameBuffer buffer = new FrameBuffer(new Frame(38, 23));
		
		buffer.writeLEInt(interfaceKeys[1]);
		buffer.writeByteS(context.walkable ? 1 : 0);
		buffer.writeLEShortA(context.interfaceId);
		buffer.writeLEInt(interfaceKeys[0]);
		buffer.writeInt1(interfaceKeys[3]);
		buffer.writeInt2(interfaceKeys[2]);
		buffer.writeInt2(context.windowId << 16 | context.windowLocation);
		return buffer.getFrame();
	}
}
