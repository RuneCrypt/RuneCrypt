package net.runecrypt.codec.codec751.context;

import net.runecrypt.codec.messages.LoginResponse;
import net.runecrypt.network.packet.PacketContext;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/13/13
 * Time: 12:56PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginContext implements PacketContext {

    public LoginResponse response;

    /**
     * Constructs a new {@code LoginContext} instance.
     *
     * @param response The response for the login.
     */
    public LoginContext(LoginResponse response) {
        this.response = response;
    }
}
