package net.runecrypt.codec.messages;

/**
 * Created with IntelliJ IDEA.
 * User: Thomas Le Godais
 * Date: 3/8/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UpdateVersionMessage {

    private final int version;
    private final int subVersion;
    private final String key;

    public UpdateVersionMessage(int version, int subVersion, String key) {
        this.version = version;
        this.subVersion = subVersion;
        this.key = key;
    }

    public int getVersion() {
        return version;
    }

    public int getSubVersion() {
        return subVersion;
    }

    public String getKey() {
        return key;
    }

}
