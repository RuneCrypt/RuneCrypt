package net.runecrypt.codec.messages;

import net.runecrypt.game.World.LoginType;
import net.runecrypt.game.model.player.Player;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 6:10 PM
 * To change this template use File | Settings | File Templates.
 */
public final class LoginResponse {

    public final int returnCode;
    public final Player player;
    public final LoginType loginType;

    /**
     * Constructs a new {@code LoginResponse} instance.
     *
     * @param returnCode The login response return code.
     * @param player     The player for the login response.
     * @param loginType  The login type for the request.
     */
	public LoginResponse(int returnCode, Player player, LoginType loginType) {
		this.player = player;
		this.returnCode = returnCode;
		this.loginType = loginType;
	}
}
