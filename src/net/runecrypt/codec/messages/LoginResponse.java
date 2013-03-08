package net.runecrypt.codec.messages;

import net.runecrypt.game.World.LoginType;
import net.runecrypt.game.model.player.PlayerDef;
import org.jboss.netty.buffer.ChannelBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public final class LoginResponse {

    public final int returnCode;
    public final PlayerDef.Rights rights;
    public final int index;
    public LoginType loginType;
    public final ChannelBuffer buffer;

    /**
     * Constructs a new {@code LoginResponse} instance.
     *
     * @param returnCode The login response return code.
     * @param rights     The rights of the player for the return code.
     * @param index      The index of the player.
     * @param loginType
     */
    public LoginResponse(int returnCode, PlayerDef.Rights rights, int index, LoginType loginType, ChannelBuffer buffer) {
        this.returnCode = returnCode;
        this.rights = rights;
        this.index = index;
        this.loginType = loginType;
        this.buffer = buffer;
    }
}
