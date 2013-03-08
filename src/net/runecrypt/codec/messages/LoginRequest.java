package net.runecrypt.codec.messages;

import net.burtleburtle.bob.rand.IsaacRandom;
import net.runecrypt.codec.CodecManifest;
import net.runecrypt.game.model.player.PlayerDef;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 10:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginRequest {

    private final PlayerDef playerDef;
    private final IsaacRandom decodingRandom, encodingRandom;
    private final CodecManifest codecManifest;

    /**
     * Constructs a new {@code LoginRequest} instance.
     *
     * @param playerDef      The definition of the player.
     * @param decodingRandom The random number generator for decoding packets.
     * @param encodingRandom The random number generator for encoding packets.
     */
    public LoginRequest(PlayerDef playerDef, IsaacRandom decodingRandom, IsaacRandom encodingRandom, CodecManifest codecManifest) {
        this.playerDef = playerDef;
        this.decodingRandom = decodingRandom;
        this.encodingRandom = encodingRandom;
        this.codecManifest = codecManifest;
    }

    /**
     * Gets the player def instance.
     *
     * @return The player def.
     */
    public PlayerDef getPlayerDef() {
        return playerDef;
    }

    /**
     * Gets the decoding random instance.
     *
     * @return The decoding random.
     */
    public IsaacRandom getDecodingRandom() {
        return decodingRandom;
    }

    /**
     * Gets the encoding random instance.
     *
     * @return The encoding random.
     */
    public IsaacRandom getEncodingRandom() {
        return encodingRandom;
    }

    /**
     * Gets the codec manifest instance.
     *
     * @return The codec manifest.
     */
    public CodecManifest getCodecManifest() {
        return codecManifest;
    }
}
