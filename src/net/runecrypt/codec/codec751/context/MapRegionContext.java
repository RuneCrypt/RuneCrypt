package net.runecrypt.codec.codec751.context;

import net.runecrypt.game.model.player.Player;
import net.runecrypt.game.model.player.RenderInformation;
import net.runecrypt.network.packet.PacketContext;
import net.runecrypt.util.BinaryLandscapeHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/11/13
 * Time: 8:40PM
 * To change this template use File | Settings | File Templates.
 */
public class MapRegionContext implements PacketContext {

    public static final int[] REGION_SIZE = new int[]{104, 120, 136, 168};
    public int regionX = -1;
    public int regionY = -1;
    public int regionSizeIndex = 0;
    public int[][] keys = null;
    public int keyCount = 0;
    public boolean sendGPI = false;
    public RenderInformation renderInformation;

    public MapRegionContext(Player player, boolean onLogin) {
        this.regionX = player.getPosition().getRegionX();
        this.regionY = player.getPosition().getRegionY();

        this.regionSizeIndex = 0;
        this.keys = new int[16][];

        int depth = REGION_SIZE[regionSizeIndex] >> 4;
        for (int xCalc = (regionX - depth) >> 3; xCalc <= (regionX + depth) >> 3; xCalc++) {
            for (int yCalc = (regionY - depth) >> 3; yCalc <= (regionY + depth) >> 3; yCalc++) {
                int region = yCalc + (xCalc << 8);

                int[] xteaKey = BinaryLandscapeHandler.get(region);
                if (xteaKey == null)
                    xteaKey = new int[4];

                keys[keyCount++] = xteaKey;
            }
        }

        if (onLogin)
            sendGPI = true;

        this.renderInformation = player.renderInformation;
    }
}
