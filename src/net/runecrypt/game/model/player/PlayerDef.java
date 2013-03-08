package net.runecrypt.game.model.player;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/7/13
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */
public final class PlayerDef {

    public static enum Rights {

        STANDARD(0),

        MODERATOR(1),

        ADMINISTRATOR(2);

        public int intValue;

        Rights(int intValue) {
            this.intValue = intValue;
        }
    }

    private String username, password;
    private Rights rights;

    /**
     * Constructs a new {@code PlayerDef} instance.
     *
     * @param username The username of the player.
     * @param password The password of the player.
     * @param rights   The rights of the player.
     */
    public PlayerDef(String username, String password, Rights rights) {
        this.username = username;
        this.password = password;
        this.rights = rights;
    }

    /**
     * Gets the username.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password.
     *
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the rights.
     *
     * @return The rights.
     */
    public Rights getRights() {
        return rights;
    }
}
