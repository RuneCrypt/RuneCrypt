package net.runecrypt.codec.codec751;

import net.runecrypt.GameEngine;
import net.runecrypt.Server;
import net.runecrypt.codec.Codec;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.codec751.decoders.WorldListDecoder;
import net.runecrypt.codec.codec751.encoders.Config;
import net.runecrypt.codec.codec751.encoders.KeepAlive;
import net.runecrypt.codec.codec751.encoders.WorldList;
import net.runecrypt.codec.codec751.handlers.KeepAliveHandler;
import net.runecrypt.codec.codec751.handlers.WorldListHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
@CodecManifest(requiredProtocol = 751, authors = {"Thomas Le Godais"})
public class Codec751 extends Codec {

    private int[] PACKET_LENGTHS = new int[256];

    /**
     * Constructs a new {@code Codec751} instance.
     *
     * @param server The server instance for the codec.
     */
    public Codec751(Server server) {
        super(server);
    }

    @Override
    public void setPacketLengths() {
        PACKET_LENGTHS[84] = 6;
        PACKET_LENGTHS[70] = -1;
        PACKET_LENGTHS[2] = -1;
        PACKET_LENGTHS[12] = 3;
        PACKET_LENGTHS[4] = 8;
        PACKET_LENGTHS[5] = -1;
        PACKET_LENGTHS[50] = -2;
        PACKET_LENGTHS[7] = 16;
        PACKET_LENGTHS[8] = -1;
        PACKET_LENGTHS[9] = 0;
        PACKET_LENGTHS[10] = 3;
        PACKET_LENGTHS[11] = -1;
        PACKET_LENGTHS[32] = -1;
        PACKET_LENGTHS[13] = 4;
        PACKET_LENGTHS[85] = 11;
        PACKET_LENGTHS[15] = 4;
        PACKET_LENGTHS[27] = 3;
        PACKET_LENGTHS[1] = -2;
        PACKET_LENGTHS[18] = 8;
        PACKET_LENGTHS[26] = 1;
        PACKET_LENGTHS[20] = 7;
        PACKET_LENGTHS[95] = 3;
        PACKET_LENGTHS[22] = 8;
        PACKET_LENGTHS[52] = 3;
        PACKET_LENGTHS[24] = 2;
        PACKET_LENGTHS[25] = 7;
        PACKET_LENGTHS[78] = 3;
        PACKET_LENGTHS[37] = 11;
        PACKET_LENGTHS[28] = 6;
        PACKET_LENGTHS[101] = -1;
        PACKET_LENGTHS[30] = -1;
        PACKET_LENGTHS[31] = -2;
        PACKET_LENGTHS[73] = 9;
        PACKET_LENGTHS[33] = 8;
        PACKET_LENGTHS[34] = 8;
        PACKET_LENGTHS[35] = 4;
        PACKET_LENGTHS[36] = 8;
        PACKET_LENGTHS[21] = 1;
        PACKET_LENGTHS[38] = 9;
        PACKET_LENGTHS[39] = 4;
        PACKET_LENGTHS[40] = -2;
        PACKET_LENGTHS[19] = 2;
        PACKET_LENGTHS[23] = -1;
        PACKET_LENGTHS[43] = -2;
        PACKET_LENGTHS[44] = -1;
        PACKET_LENGTHS[45] = 9;
        PACKET_LENGTHS[46] = -1;
        PACKET_LENGTHS[47] = -2;
        PACKET_LENGTHS[51] = 2;
        PACKET_LENGTHS[49] = 3;
        PACKET_LENGTHS[55] = 15;
        PACKET_LENGTHS[83] = 8;
        PACKET_LENGTHS[53] = 3;
        PACKET_LENGTHS[54] = -1;
        PACKET_LENGTHS[56] = 6;
        PACKET_LENGTHS[58] = 16;
        PACKET_LENGTHS[107] = 5;
        PACKET_LENGTHS[91] = 7;
        PACKET_LENGTHS[16] = 8;
        PACKET_LENGTHS[60] = 0;
        PACKET_LENGTHS[61] = 9;
        PACKET_LENGTHS[62] = 8;
        PACKET_LENGTHS[63] = 12;
        PACKET_LENGTHS[64] = 3;
        PACKET_LENGTHS[57] = 7;
        PACKET_LENGTHS[74] = 4;
        PACKET_LENGTHS[67] = 17;
        PACKET_LENGTHS[17] = -1;
        PACKET_LENGTHS[71] = -1;
        PACKET_LENGTHS[69] = 3;
        PACKET_LENGTHS[42] = -1;
        PACKET_LENGTHS[72] = -1;
        PACKET_LENGTHS[80] = 4;
        PACKET_LENGTHS[59] = 9;
        PACKET_LENGTHS[75] = 9;
        PACKET_LENGTHS[76] = -1;
        PACKET_LENGTHS[77] = 3;
        PACKET_LENGTHS[6] = -1;
        PACKET_LENGTHS[79] = -1;
        PACKET_LENGTHS[65] = 7;
        PACKET_LENGTHS[81] = 3;
        PACKET_LENGTHS[82] = 15;
        PACKET_LENGTHS[100] = 3;
        PACKET_LENGTHS[29] = 1;
        PACKET_LENGTHS[3] = 1;
        PACKET_LENGTHS[86] = 3;
        PACKET_LENGTHS[87] = 2;
        PACKET_LENGTHS[88] = 4;
        PACKET_LENGTHS[89] = -1;
        PACKET_LENGTHS[90] = -2;
        PACKET_LENGTHS[0] = -1;
        PACKET_LENGTHS[92] = 15;
        PACKET_LENGTHS[93] = 3;
        PACKET_LENGTHS[94] = 7;
        PACKET_LENGTHS[48] = 0;
        PACKET_LENGTHS[96] = 9;
        PACKET_LENGTHS[97] = -1;
        PACKET_LENGTHS[98] = 0;
        PACKET_LENGTHS[99] = 4;
        PACKET_LENGTHS[41] = 3;
        PACKET_LENGTHS[68] = -1;
        PACKET_LENGTHS[102] = 7;
        PACKET_LENGTHS[103] = 4;
        PACKET_LENGTHS[104] = -1;
        PACKET_LENGTHS[105] = 1;
        PACKET_LENGTHS[106] = 8;
        PACKET_LENGTHS[14] = 3;
        PACKET_LENGTHS[108] = -1;
        PACKET_LENGTHS[109] = 18;
        PACKET_LENGTHS[110] = -2;
        PACKET_LENGTHS[111] = -1;
        PACKET_LENGTHS[66] = 4;
    }

    @Override
    public void setOutgoingPackets() {
        try {
            GameEngine.getInstance().getPacketCodec().register(Config.class);
            GameEngine.getInstance().getPacketCodec().register(WorldList.class);
            GameEngine.getInstance().getPacketCodec().register(KeepAlive.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setIncommingPackets() {
        try {
            GameEngine.getInstance().getPacketCodec().register(103, new WorldListDecoder(), new WorldListHandler());
            GameEngine.getInstance().getPacketCodec().register(9, new KeepAliveHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int[] getPacketLengths() {
        return PACKET_LENGTHS;
    }
}
