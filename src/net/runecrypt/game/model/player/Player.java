package net.runecrypt.game.model.player;

import net.runecrypt.GameEngine;
import net.runecrypt.codec.codec751.context.ConfigContext;
import net.runecrypt.codec.codec751.context.DisplayModeContext.DisplayModes;
import net.runecrypt.codec.codec751.context.GameInterfaceContext;
import net.runecrypt.codec.codec751.context.MainInterfaceContext;
import net.runecrypt.codec.codec751.context.MapRegionContext;
import net.runecrypt.codec.codec751.encoders.Config751;
import net.runecrypt.codec.codec751.encoders.GameInterface;
import net.runecrypt.codec.codec751.encoders.MainInterface;
import net.runecrypt.codec.codec751.encoders.MapRegionUpdate;
import net.runecrypt.game.World.LoginType;
import net.runecrypt.game.model.Entity;
import net.runecrypt.game.model.Position;
import net.runecrypt.network.packet.PacketContext;
import net.runecrypt.network.packet.PacketEncoder;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:31 AM
 * To change this template use File | Settings | File Templates.
 */
public final class Player extends Entity {

    public PlayerDef playerDef;
    public Channel channel;
	public Position updatePosition;
	public RenderInformation renderInformation;
	public boolean onLogin;
	public DisplayModes displayMode;
	private int paneId;
    public static final int[][] LOBBY_CONFIG = new int[][] { new int[] {0, 108}, new int[] {3, -1744825601}, new int[] {20, -1409286144}, new int[] {25, 4194304}, new int[] {26, 252}, new int[] {27, -1}, new int[] {37, 155853367}, new int[] {38, 335933448}, new int[] {39, 306576576}, new int[] {40, 69419200}, new int[] {41, -943176796}, new int[] {42, -260046702}, new int[] {43, 304}, new int[] {44, 384}, new int[] {45, 2}, new int[] {46, 1342177280}, new int[] {47, 1073807370}, new int[] {48, 34078976}, new int[] {49, 406847610}, new int[] {50, 1}, new int[] {51, 134232576}, new int[] {52, 536870916}, new int[] {53, 2532}, new int[] {54, 2048}, new int[] {55, 67633152}, new int[] {56, 67174408}, new int[] {57, 1024}, new int[] {58, 25165824}, new int[] {59, -2144919552}, new int[] {60, 1073744003}, new int[] {61, 868220927}, new int[] {62, 65535}, new int[] {63, 65535}, new int[] {65, 100673544}, new int[] {66, 393216}, new int[] {67, 40632322}, new int[] {68, -1792}, new int[] {69, 3670023}, new int[] {70, -536852992}, new int[] {71, 1551}, new int[] {76, 1073741823}, new int[] {77, 2147483647}, new int[] {78, 1073741823}, new int[] {79, 1073741823}, new int[] {80, 1073741823}, new int[] {81, 1073741823}, new int[] {85, -1}, new int[] {91, 6808001}, new int[] {92, 3230611}, new int[] {93, 963217}, new int[] {94, 6231199}, new int[] {97, -1}, new int[] {110, 134217728}, new int[] {111, 655}, new int[] {115, 576}, new int[] {120, -1}, new int[] {135, -1}, new int[] {142, -1}, new int[] {161, 1}, new int[] {186, -1}, new int[] {187, -1}, new int[] {188, -1}, new int[] {189, 5}, new int[] {248, 1056}, new int[] {249, 24890}, new int[] {250, -1}, new int[] {260, -1}, new int[] {261, -1}, new int[] {262, -1}, new int[] {285, 12292}, new int[] {286, 16777216}, new int[] {288, 128}, new int[] {289, 32768}, new int[] {292, 67108864}, new int[] {295, 8}, new int[] {299, -1}, new int[] {300, -1}, new int[] {304, -1}, new int[] {305, -1}, new int[] {306, -1}, new int[] {307, 16384}, new int[] {313, 37}, new int[] {429, -1}, new int[] {446, 530432}, new int[] {448, 65536}, new int[] {452, 0}, new int[] {453, 0}, new int[] {454, 0}, new int[] {455, 0}, new int[] {456, 0}, new int[] {457, 1}, new int[] {458, 32}, new int[] {460, 0}, new int[] {461, 2048}, new int[] {462, 1}, new int[] {463, 1}, new int[] {476, -1}, new int[] {481, 738197537}, new int[] {627, 145}, new int[] {659, 2610}, new int[] {664, 15}, new int[] {674, 21}, new int[] {679, 220}, new int[] {682, 2622004}, new int[] {686, -1}, new int[] {687, -1}, new int[] {688, -1}, new int[] {698, -1}, new int[] {699, -1}, new int[] {700, -1}, new int[] {723, 128}, new int[] {727, 17}, new int[] {728, 34}, new int[] {729, 65}, new int[] {730, 97}, new int[] {731, 18}, new int[] {732, 50}, new int[] {733, 113}, new int[] {734, 161}, new int[] {739, 17}, new int[] {740, 34}, new int[] {741, 65}, new int[] {742, 97}, new int[] {743, 18}, new int[] {744, 50}, new int[] {745, 113}, new int[] {746, 161}, new int[] {751, 822}, new int[] {752, 870}, new int[] {753, 22}, new int[] {754, 102}, new int[] {755, 54}, new int[] {756, 86}, new int[] {757, 38}, new int[] {758, 70}, new int[] {759, 134}, new int[] {760, 118}, new int[] {761, 198}, new int[] {763, 21}, new int[] {764, 117}, new int[] {765, 197}, new int[] {766, 69}, new int[] {811, -1}, new int[] {812, -1}, new int[] {813, -1}, new int[] {814, -1}, new int[] {815, -1}, new int[] {816, -1}, new int[] {817, -1}, new int[] {818, -1}, new int[] {819, -1}, new int[] {820, -1}, new int[] {821, -1}, new int[] {822, -1}, new int[] {823, -1}, new int[] {824, -1}, new int[] {825, -1}, new int[] {826, -1}, new int[] {827, -1}, new int[] {828, -1}, new int[] {829, -1}, new int[] {830, -1}, new int[] {831, -1}, new int[] {832, -1}, new int[] {833, -1}, new int[] {834, -1}, new int[] {835, -1}, new int[] {836, -1}, new int[] {837, -1}, new int[] {838, -1}, new int[] {839, -1}, new int[] {840, -1}, new int[] {841, -1}, new int[] {842, -1}, new int[] {843, -1}, new int[] {844, -1}, new int[] {845, -1}, new int[] {846, -1}, new int[] {847, -1}, new int[] {848, -1}, new int[] {849, -1}, new int[] {850, -1}, new int[] {851, -1}, new int[] {852, -1}, new int[] {853, -1}, new int[] {854, -1}, new int[] {855, -1}, new int[] {856, -1}, new int[] {857, -1}, new int[] {858, -1}, new int[] {859, -1}, new int[] {860, -1}, new int[] {861, -1}, new int[] {862, -1}, new int[] {863, -1}, new int[] {864, -1}, new int[] {865, -1}, new int[] {866, -1}, new int[] {867, -1}, new int[] {868, -1}, new int[] {869, -1}, new int[] {870, -1}, new int[] {871, -1}, new int[] {872, -1}, new int[] {873, -1}, new int[] {874, -1}, new int[] {875, -1}, new int[] {876, -1}, new int[] {877, -1}, new int[] {878, -1}, new int[] {879, -1}, new int[] {880, -1}, new int[] {881, -1}, new int[] {882, -1}, new int[] {883, -1}, new int[] {884, -1}, new int[] {885, -1}, new int[] {886, -1}, new int[] {887, -1}, new int[] {888, -1}, new int[] {889, -1}, new int[] {890, -1}, new int[] {891, -1}, new int[] {892, -1}, new int[] {893, -1}, new int[] {894, -1}, new int[] {1069, 16091}, new int[] {1070, 16125}, new int[] {1071, 1}, new int[] {1073, 1}, new int[] {1074, -2147483648}, new int[] {1075, 12288000}, new int[] {1091, 23068677}, new int[] {1092, 6144}, new int[] {1094, 31}, new int[] {1096, -2080366504}, new int[] {1097, 364}, new int[] {1098, 766}, new int[] {1099, 268438338}, new int[] {1101, -1}, new int[] {1102, 1048559}, new int[] {1103, 589721}, new int[] {1104, -1}, new int[] {1106, -1}, new int[] {1107, 32289}, new int[] {1168, -1}, new int[] {1169, -1}, new int[] {1170, -1}, new int[] {1172, 17784}, new int[] {1173, 805306368}, new int[] {1175, -1}, new int[] {1191, 3}, new int[] {1229, 20480000}, new int[] {1238, -1}, new int[] {1245, 1}, new int[] {1246, 10}, new int[] {1258, 32024}, new int[] {1261, 6267774}, new int[] {1262, -105513093}, new int[] {1263, 125836475}, new int[] {1264, -810684425}, new int[] {1265, -738197507}, new int[] {1266, -2147221502}, new int[] {1267, 76022792}, new int[] {1268, 33554433}, new int[] {1269, 542113665}, new int[] {1272, 421991}, new int[] {1273, 249561090}, new int[] {1293, 128}, new int[] {1295, 1000}, new int[] {1297, 10}, new int[] {1302, -1}, new int[] {1305, 469763584}, new int[] {1357, 67108864}, new int[] {1384, -1}, new int[] {1398, 1000}, new int[] {1399, 1000}, new int[] {1400, 1000}, new int[] {1408, 31457280}, new int[] {1442, -1}, new int[] {1444, 4003}, new int[] {1448, 1207959552}, new int[] {1449, 1310720}, new int[] {1450, 1023418369}, new int[] {1451, 5242880}, new int[] {1452, 575714}, new int[] {1455, -1}, new int[] {1481, 8388608}, new int[] {1483, 1}, new int[] {1499, -1}, new int[] {1500, -1}, new int[] {1501, -1}, new int[] {1502, -1}, new int[] {1503, -1}, new int[] {1504, -1}, new int[] {1506, -1}, new int[] {1507, -1}, new int[] {1508, -1}, new int[] {1509, -1}, new int[] {1510, -1}, new int[] {1543, 1}, new int[] {1569, 262144}, new int[] {1579, 8}, new int[] {1602, -1}, new int[] {1604, -1}, new int[] {1608, -1}, new int[] {1609, -1}, new int[] {1661, -1}, new int[] {1697, -1}, new int[] {1701, -1}, new int[] {1720, -1}, new int[] {1731, 134218304}, new int[] {1748, 225}, new int[] {1749, 3977}, new int[] {1750, 5747237}, new int[] {1751, 5791877}, new int[] {1752, 5778836}, new int[] {1753, 225}, new int[] {1754, 20}, new int[] {1760, -1}, new int[] {1761, -1}, new int[] {1762, -1}, new int[] {1763, -1}, new int[] {1764, -1}, new int[] {1765, -1}, new int[] {1766, -1}, new int[] {1767, -1}, new int[] {1770, 64}, new int[] {1776, 1024}, new int[] {1777, 134217728}, new int[] {1779, 33554432}, new int[] {1780, 819200}, new int[] {1784, -1}, new int[] {1785, 202}, new int[] {1787, 60}, new int[] {1793, 12183}, new int[] {1831, -1}, new int[] {2088, -1}, new int[] {2091, 46137344}, new int[] {2092, 131072}, new int[] {2107, 16777216}, new int[] {2145, 2097152}, new int[] {2214, 268435456}, new int[] {2279, -1}, new int[] {2280, -1}, new int[] {2281, -1}, new int[] {2282, -1}, new int[] {2283, -1}, new int[] {2284, -1}, new int[] {2324, 5}, new int[] {2325, 71}, new int[] {2334, 536871292}, new int[] {2337, 6593}, new int[] {2338, 16777216}, new int[] {2339, 80}, new int[] {2492, 2}, new int[] {2497, 536870912}, new int[] {2533, -1}, new int[] {2604, -1}, new int[] {2605, -1}, new int[] {2606, -1}, new int[] {2607, -1}, new int[] {2608, -1}, new int[] {2609, -1}, new int[] {2610, -1}, new int[] {2611, -1}, new int[] {2644, -1}, new int[] {2645, -1}, new int[] {2646, -1}, new int[] {2647, -1}, new int[] {2648, -1}, new int[] {2649, -1}, new int[] {2650, -1}, new int[] {2651, -1}, new int[] {2652, -1}, new int[] {2653, -1}, new int[] {2654, -1}, new int[] {2655, -1}, new int[] {2656, -1}, new int[] {2662, -1}, new int[] {2674, 4968}, new int[] {2694, 20616}, new int[] {2695, 4}, new int[] {2724, 22677347}, new int[] {2776, -1}, new int[] {2805, 4}, new int[] {2807, -1}, new int[] {2816, 237568}, new int[] {2858, 536870912}, new int[] {2863, 117440512}, new int[] {2897, -805091453}, new int[] {2898, 276855}, new int[] {2914, -2147483648}, new int[] {2947, -1}, new int[] {2956, -1}, new int[] {2957, -1}, new int[] {2958, -1}, new int[] {2975, -1}, new int[] {2985, -1}, new int[] {2999, -1}, new int[] {3000, 2056899890}, new int[] {3001, 2684520}, new int[] {3002, 786432}, new int[] {3013, -1}, new int[] {3014, -1}, new int[] {3015, -1}, new int[] {3016, -1}, new int[] {3028, 63}, new int[] {3038, 256}, new int[] {3043, 131072}, new int[] {3045, -1}, new int[] {3075, 1342214144}, new int[] {3076, 8}, new int[] {3077, 64}, new int[] {3079, 4003}, new int[] {3083, 1073742864}, new int[] {3091, 16388}, new int[] {3092, 1}, new int[] {3093, 16384}, new int[] {3094, 8193}, new int[] {3098, 4194305}, new int[] {3099, 16}, new int[] {3100, -2147483648}, new int[] {3103, 268435456}, new int[] {3109, 15}, new int[] {3120, 1308}, new int[] {3121, 184320}, new int[] {3166, 6852640}, new int[] {3170, -1}, new int[] {3178, -301989888}, new int[] {3180, 14680064}, new int[] {3181, 6291456}, new int[] {3184, 1}, new int[] {3185, 4}, new int[] {3226, -1}, new int[] {3239, 4003}, new int[] {3240, 1409286225}, new int[] {3241, 100663370}, new int[] {3242, 67109013}, new int[] {3243, 167772747}, new int[] {3244, 301989971}, new int[] {3249, 2147482087}, new int[] {3252, -1}, new int[] {3253, -1}, new int[] {3254, -1}, new int[] {3255, -1}, new int[] {3256, -1}, new int[] {3257, -1}, new int[] {3258, -1}, new int[] {3259, 1600}, new int[] {3260, 16779937}, new int[] {3261, 1048581}, new int[] {3262, 1073741824}, new int[] {3263, 16768}, new int[] {3264, -386733402}, new int[] {3265, 1}, new int[] {3274, 3478708}, new int[] {3276, 511305630}, new int[] {3277, 4}, new int[] {3285, 76}, new int[] {3294, 134217728}, new int[] {3353, 265}, new int[] {3354, -1}, new int[] {3472, -1}, new int[] {3473, -1}, new int[] {3500, 67108864}, new int[] {3501, 556497}, new int[] {3502, 1073741840}, new int[] {3506, -1}, new int[] {3515, 536870912}, new int[] {3516, 104857600}, new int[] {3518, 1024} };

    /**
     * Constructs a new {@code Player} instance.
     *
     * @param playerDef The definition of the player.
     * @param channel   The channel that represents a player's connection.
     */
    public Player(PlayerDef playerDef, Channel channel) {
        this.playerDef = playerDef;
        this.channel = channel;
        this.renderInformation = new RenderInformation(this);
    }

    /**
     * Sends the login configurations.
     *
     * @param currentProtocol The current protocol of the player.
     * @param loginType       The login type of the player.
     */
    public void sendLoginConfigs(int currentProtocol, LoginType loginType) {
        switch (currentProtocol) {
            case 751:
                if (loginType.equals(LoginType.LOBBY)) {
                    for (int i = 0; i < LOBBY_CONFIG.length; i++)
                       encode(Config751.class, new ConfigContext(LOBBY_CONFIG[i][0], LOBBY_CONFIG[i][1], false));
                } else {
                	this.onLogin = true;
                	encode(MapRegionUpdate.class, new MapRegionContext(this, true));
                }
                break;
        }
        String loginMessage = loginType.equals(LoginType.LOBBY) ? "lobby." : "game world.";
        System.out.println("Player [username=" + playerDef.getUsername() + ", password=" + playerDef.getPassword() + ", index=" + getIndex() + "] has entered " + loginMessage);
    }

    /**
     * Encodes a certain packet.
     * @param context The packet context type.
     * @param clazz The packet encoder to encode.
     */
    public ChannelFuture encode(Class<? extends PacketEncoder<?>> clazz, PacketContext context) {
        return channel.write(GameEngine.getInstance().packetCodec.encode(clazz, context));
    }

    /**
     * The operations to perform on login.
     */
	public void performLogin() {
		this.paneId = displayMode.paneId;
		encode(MainInterface.class, new MainInterfaceContext(paneId));
		
		int[][] FIXED_INTERFACES = new int[][] {
			new int[] { 751, 57 },
			new int[] { 752, 16 },
			new int[] { 754, 45 },
			new int[] { 748, 164 },
			new int[] { 749, 165 },
			new int[] { 750, 166 },
			new int[] { 747, 168 },
			new int[] { 745, 428 },
			/*
			 * Chatbox, need to figure this out.
			 */
			//new int[] { 137, 9 },
			new int[] { 464, 178 },
			new int[] { 1056, 179 },
			new int[] { 320, 180 },
			new int[] { 1374, 181 },
			new int[] { 679, 182 },
			new int[] { 387, 183 },
			new int[] { 271, 184 },
			new int[] { 275, 185 },
			new int[] { 1139, 186 },
			new int[] { 550, 187 },
			new int[] { 1109, 188 },
			new int[] { 1110, 189 },
			new int[] { 261, 190 },
			new int[] { 590, 191 },
			new int[] { 187, 192 },
			new int[] { 34, 193 },
			new int[] { 182, 196 },
			new int[] { 1213, 51 },
			new int[] { 1215, 54 },
			new int[] { 640, 19 },
			new int[] { 638, 53 },
		};
		
		if (onLogin) {
			for (int i = 0; i < FIXED_INTERFACES.length; i++) {
				encode(GameInterface.class, new GameInterfaceContext(paneId, FIXED_INTERFACES[i][0], FIXED_INTERFACES[i][1], true));
			}
		}
		
		encode(Config751.class, new ConfigContext(823, 1, true));
	}
}
