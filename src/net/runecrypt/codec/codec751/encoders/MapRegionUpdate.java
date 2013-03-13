package net.runecrypt.codec.codec751.encoders;

import net.runecrypt.codec.codec751.context.MapRegionContext;
import net.runecrypt.network.Frame;
import net.runecrypt.network.FrameBuffer;
import net.runecrypt.network.packet.PacketEncoder;

public class MapRegionUpdate implements PacketEncoder<MapRegionContext> {

	@Override
	public Frame encode(MapRegionContext context) {
		FrameBuffer builder = new FrameBuffer(new Frame(78, Frame.SHORT));
		
		if (context.sendGPI)
			context.renderInformation.enterWorld(builder);
		
		builder.writeLEShort(context.regionY);
		builder.writeByteA(1);
		builder.writeShort(context.regionX);
		builder.writeByteS(0);
		
		for (int i = 0; i < context.keyCount; i++) {
			for (int j = 0; j < 4; j++)
				builder.writeInt(context.keys[i][j]);
		}
		
		return builder.getFrame();
	}
}
