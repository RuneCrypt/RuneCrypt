/*
    Copyright (C) 2013, RuneCrypt Development Team.

    RuneCrypt is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    RuneCrypt is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with RuneCrypt.  If not, see <http://www.gnu.org/licenses/>.
*/
package net.runecrypt.codec.codec317;

import net.runecrypt.GameEngine;
import net.runecrypt.Server;
import net.runecrypt.codec.Codec;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.codec.codec317.decoders.CommandDecoder;
import net.runecrypt.codec.codec317.encoders.RunEnergy;
import net.runecrypt.codec.codec317.handler.CommandHandler;

/**
 * Represents an {@link Codec} that is used to handle the 317 revision.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <4:01:29 PM - Mar 4, 2013>
 */
@CodecManifest(requiredProtocol = 317, authors = {"Thomas Le Godais", "James Barton"})
public class Codec317 extends Codec {

    private static final int[] PACKET_LENGTHS = new int[256];

    /**
     * Constructs a new {@code Codec317} instance.
     *
     * @param server The server instance.
     */
    public Codec317(Server server) {
        super(server);
    }

    /* (non-Javadoc)
     * @see net.runecrypt.codec.Codec#setPacketLengths()
     */
    @Override
    public void setPacketLengths() {
        PACKET_LENGTHS[0] = 0;
        PACKET_LENGTHS[1] = 0;
        PACKET_LENGTHS[2] = 0;
        PACKET_LENGTHS[3] = 1;
        PACKET_LENGTHS[4] = -1;
        PACKET_LENGTHS[5] = 0;
        PACKET_LENGTHS[6] = 0;
        PACKET_LENGTHS[7] = 0;
        PACKET_LENGTHS[8] = 0;
        PACKET_LENGTHS[9] = 0;
        PACKET_LENGTHS[10] = 0;
        PACKET_LENGTHS[11] = 0;
        PACKET_LENGTHS[12] = 0;
        PACKET_LENGTHS[13] = 0;
        PACKET_LENGTHS[14] = 8;
        PACKET_LENGTHS[15] = 0;
        PACKET_LENGTHS[16] = 6;
        PACKET_LENGTHS[17] = 2;
        PACKET_LENGTHS[18] = 2;
        PACKET_LENGTHS[19] = 0;
        PACKET_LENGTHS[20] = 0;
        PACKET_LENGTHS[21] = 2;
        PACKET_LENGTHS[22] = 0;
        PACKET_LENGTHS[23] = 6;
        PACKET_LENGTHS[24] = 0;
        PACKET_LENGTHS[25] = 12;
        PACKET_LENGTHS[26] = 0;
        PACKET_LENGTHS[27] = 0;
        PACKET_LENGTHS[28] = 0;
        PACKET_LENGTHS[29] = 0;
        PACKET_LENGTHS[30] = 0;
        PACKET_LENGTHS[31] = 0;
        PACKET_LENGTHS[32] = 0;
        PACKET_LENGTHS[33] = 0;
        PACKET_LENGTHS[34] = 0;
        PACKET_LENGTHS[35] = 8;
        PACKET_LENGTHS[36] = 4;
        PACKET_LENGTHS[37] = 0;
        PACKET_LENGTHS[38] = 0;
        PACKET_LENGTHS[39] = 2;
        PACKET_LENGTHS[40] = 2;
        PACKET_LENGTHS[41] = 6;
        PACKET_LENGTHS[42] = 0;
        PACKET_LENGTHS[43] = 6;
        PACKET_LENGTHS[44] = 0;
        PACKET_LENGTHS[45] = -1;
        PACKET_LENGTHS[46] = 0;
        PACKET_LENGTHS[47] = 0;
        PACKET_LENGTHS[48] = 0;
        PACKET_LENGTHS[49] = 0;
        PACKET_LENGTHS[50] = 0;
        PACKET_LENGTHS[51] = 0;
        PACKET_LENGTHS[52] = 0;
        PACKET_LENGTHS[53] = 12;
        PACKET_LENGTHS[54] = 0;
        PACKET_LENGTHS[55] = 0;
        PACKET_LENGTHS[56] = 0;
        PACKET_LENGTHS[57] = 0;
        PACKET_LENGTHS[58] = 8;
        PACKET_LENGTHS[59] = 0;
        PACKET_LENGTHS[60] = 0;
        PACKET_LENGTHS[61] = 8;
        PACKET_LENGTHS[62] = 0;
        PACKET_LENGTHS[63] = 0;
        PACKET_LENGTHS[64] = 0;
        PACKET_LENGTHS[65] = 0;
        PACKET_LENGTHS[66] = 0;
        PACKET_LENGTHS[67] = 0;
        PACKET_LENGTHS[68] = 0;
        PACKET_LENGTHS[69] = 0;
        PACKET_LENGTHS[70] = 6;
        PACKET_LENGTHS[71] = 0;
        PACKET_LENGTHS[72] = 2;
        PACKET_LENGTHS[73] = 2;
        PACKET_LENGTHS[74] = 8;
        PACKET_LENGTHS[75] = 6;
        PACKET_LENGTHS[76] = 0;
        PACKET_LENGTHS[77] = -1;
        PACKET_LENGTHS[78] = 0;
        PACKET_LENGTHS[79] = 6;
        PACKET_LENGTHS[80] = 0;
        PACKET_LENGTHS[81] = 0;
        PACKET_LENGTHS[82] = 0;
        PACKET_LENGTHS[83] = 0;
        PACKET_LENGTHS[84] = 0;
        PACKET_LENGTHS[85] = 1;
        PACKET_LENGTHS[86] = 4;
        PACKET_LENGTHS[87] = 6;
        PACKET_LENGTHS[88] = 0;
        PACKET_LENGTHS[89] = 0;
        PACKET_LENGTHS[90] = 0;
        PACKET_LENGTHS[91] = 0;
        PACKET_LENGTHS[92] = 0;
        PACKET_LENGTHS[93] = 0;
        PACKET_LENGTHS[94] = 0;
        PACKET_LENGTHS[95] = 3;
        PACKET_LENGTHS[96] = 0;
        PACKET_LENGTHS[97] = 0;
        PACKET_LENGTHS[98] = -1;
        PACKET_LENGTHS[99] = 0;
        PACKET_LENGTHS[100] = 0;
        PACKET_LENGTHS[101] = 13;
        PACKET_LENGTHS[102] = 0;
        PACKET_LENGTHS[103] = -1;
        PACKET_LENGTHS[104] = 0;
        PACKET_LENGTHS[105] = 0;
        PACKET_LENGTHS[106] = 0;
        PACKET_LENGTHS[107] = 0;
        PACKET_LENGTHS[108] = 0;
        PACKET_LENGTHS[109] = 0;
        PACKET_LENGTHS[110] = 0;
        PACKET_LENGTHS[111] = 0;
        PACKET_LENGTHS[112] = 0;
        PACKET_LENGTHS[113] = 0;
        PACKET_LENGTHS[114] = 0;
        PACKET_LENGTHS[115] = 0;
        PACKET_LENGTHS[116] = 0;
        PACKET_LENGTHS[117] = 6;
        PACKET_LENGTHS[118] = 0;
        PACKET_LENGTHS[119] = 0;
        PACKET_LENGTHS[120] = 1;
        PACKET_LENGTHS[121] = 0;
        PACKET_LENGTHS[122] = 6;
        PACKET_LENGTHS[123] = 0;
        PACKET_LENGTHS[124] = 0;
        PACKET_LENGTHS[125] = 0;
        PACKET_LENGTHS[126] = -1;
        PACKET_LENGTHS[127] = 0;
        PACKET_LENGTHS[128] = 2;
        PACKET_LENGTHS[129] = 6;
        PACKET_LENGTHS[130] = 0;
        PACKET_LENGTHS[131] = 4;
        PACKET_LENGTHS[132] = 6;
        PACKET_LENGTHS[133] = 8;
        PACKET_LENGTHS[134] = 0;
        PACKET_LENGTHS[135] = 6;
        PACKET_LENGTHS[136] = 0;
        PACKET_LENGTHS[137] = 0;
        PACKET_LENGTHS[138] = 0;
        PACKET_LENGTHS[139] = 2;
        PACKET_LENGTHS[140] = 0;
        PACKET_LENGTHS[141] = 0;
        PACKET_LENGTHS[142] = 0;
        PACKET_LENGTHS[143] = 0;
        PACKET_LENGTHS[144] = 0;
        PACKET_LENGTHS[145] = 6;
        PACKET_LENGTHS[146] = 0;
        PACKET_LENGTHS[147] = 0;
        PACKET_LENGTHS[148] = 0;
        PACKET_LENGTHS[149] = 0;
        PACKET_LENGTHS[150] = 0;
        PACKET_LENGTHS[151] = 0;
        PACKET_LENGTHS[152] = 1;
        PACKET_LENGTHS[153] = 2;
        PACKET_LENGTHS[154] = 0;
        PACKET_LENGTHS[155] = 2;
        PACKET_LENGTHS[156] = 6;
        PACKET_LENGTHS[157] = 0;
        PACKET_LENGTHS[158] = 0;
        PACKET_LENGTHS[159] = 0;
        PACKET_LENGTHS[160] = 0;
        PACKET_LENGTHS[161] = 0;
        PACKET_LENGTHS[162] = 0;
        PACKET_LENGTHS[163] = 0;
        PACKET_LENGTHS[164] = -1;
        PACKET_LENGTHS[165] = -1;
        PACKET_LENGTHS[166] = 0;
        PACKET_LENGTHS[167] = 0;
        PACKET_LENGTHS[168] = 0;
        PACKET_LENGTHS[169] = 0;
        PACKET_LENGTHS[170] = 0;
        PACKET_LENGTHS[171] = 0;
        PACKET_LENGTHS[172] = 0;
        PACKET_LENGTHS[173] = 0;
        PACKET_LENGTHS[174] = 0;
        PACKET_LENGTHS[175] = 0;
        PACKET_LENGTHS[176] = 0;
        PACKET_LENGTHS[177] = 0;
        PACKET_LENGTHS[178] = 0;
        PACKET_LENGTHS[179] = 0;
        PACKET_LENGTHS[180] = 0;
        PACKET_LENGTHS[181] = 8;
        PACKET_LENGTHS[182] = 0;
        PACKET_LENGTHS[183] = 3;
        PACKET_LENGTHS[184] = 0;
        PACKET_LENGTHS[185] = 2;
        PACKET_LENGTHS[186] = 0;
        PACKET_LENGTHS[187] = 0;
        PACKET_LENGTHS[188] = 8;
        PACKET_LENGTHS[189] = 1;
        PACKET_LENGTHS[190] = 0;
        PACKET_LENGTHS[191] = 0;
        PACKET_LENGTHS[192] = 12;
        PACKET_LENGTHS[193] = 0;
        PACKET_LENGTHS[194] = 0;
        PACKET_LENGTHS[195] = 0;
        PACKET_LENGTHS[196] = 0;
        PACKET_LENGTHS[197] = 0;
        PACKET_LENGTHS[198] = 0;
        PACKET_LENGTHS[199] = 0;
        PACKET_LENGTHS[200] = 2;
        PACKET_LENGTHS[201] = 0;
        PACKET_LENGTHS[202] = 0;
        PACKET_LENGTHS[203] = 0;
        PACKET_LENGTHS[204] = 0;
        PACKET_LENGTHS[205] = 0;
        PACKET_LENGTHS[206] = 0;
        PACKET_LENGTHS[207] = 0;
        PACKET_LENGTHS[208] = 4;
        PACKET_LENGTHS[209] = 0;
        PACKET_LENGTHS[210] = 4;
        PACKET_LENGTHS[211] = 0;
        PACKET_LENGTHS[212] = 0;
        PACKET_LENGTHS[213] = 0;
        PACKET_LENGTHS[214] = 7;
        PACKET_LENGTHS[215] = 8;
        PACKET_LENGTHS[216] = 0;
        PACKET_LENGTHS[217] = 0;
        PACKET_LENGTHS[218] = 10;
        PACKET_LENGTHS[219] = 0;
        PACKET_LENGTHS[220] = 0;
        PACKET_LENGTHS[221] = 0;
        PACKET_LENGTHS[222] = 0;
        PACKET_LENGTHS[223] = 0;
        PACKET_LENGTHS[224] = 0;
        PACKET_LENGTHS[225] = 0;
        PACKET_LENGTHS[226] = -1;
        PACKET_LENGTHS[227] = 0;
        PACKET_LENGTHS[228] = 6;
        PACKET_LENGTHS[229] = 0;
        PACKET_LENGTHS[230] = 1;
        PACKET_LENGTHS[231] = 0;
        PACKET_LENGTHS[232] = 0;
        PACKET_LENGTHS[233] = 0;
        PACKET_LENGTHS[234] = 6;
        PACKET_LENGTHS[235] = 0;
        PACKET_LENGTHS[236] = 6;
        PACKET_LENGTHS[237] = 8;
        PACKET_LENGTHS[238] = 1;
        PACKET_LENGTHS[239] = 0;
        PACKET_LENGTHS[240] = 0;
        PACKET_LENGTHS[241] = 4;
        PACKET_LENGTHS[242] = 0;
        PACKET_LENGTHS[243] = 0;
        PACKET_LENGTHS[244] = 0;
        PACKET_LENGTHS[245] = 0;
        PACKET_LENGTHS[246] = -1;
        PACKET_LENGTHS[247] = 0;
        PACKET_LENGTHS[248] = -1;
        PACKET_LENGTHS[249] = 4;
        PACKET_LENGTHS[250] = 0;
        PACKET_LENGTHS[251] = 0;
        PACKET_LENGTHS[252] = 6;
        PACKET_LENGTHS[253] = 6;
        PACKET_LENGTHS[254] = 0;
        PACKET_LENGTHS[255] = 0;
        PACKET_LENGTHS[256] = 0;
    }

    /* (non-Javadoc)
     * @see net.runecrypt.codec.Codec#setOutgoingPackets()
     */
    @Override
    public void setOutgoingPackets() {
        try {
            GameEngine.getInstance().getPacketCodec().register(RunEnergy.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see net.runecrypt.codec.Codec#setIncommingPackets()
     */
    @Override
    public void setIncommingPackets() {
        try {
            GameEngine.getInstance().getPacketCodec().register(103, new CommandDecoder(), new CommandHandler());
        } catch (Exception ex) {
            ex.printStackTrace();
            ;
        }
    }

    /* (non-Javadoc)
     * @see net.runecrypt.codec.Codec#getPacketLengths()
     */
    @Override
    public int[] getPacketLengths() {
        return PACKET_LENGTHS;
    }
}
