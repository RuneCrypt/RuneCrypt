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
package net.runecrypt;

/**
 * A class that is used to store the context of the server, this will mostly
 * come in handy later, when we're handling networking tasks (because this will
 * be used with the networking pool) and we're going to store the cache instance
 * here for the ondemand procedure.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * @since 1.0 <5:08:14 PM - Mar 3, 2013>
 */
public class ServerContext {

    private String serverAddress;
    private int serverPort;
    private boolean isVerboseMode;

    /**
     * Constructs a new {@code ServerContext} instance.
     *
     * @param serverAddress The address host the server is on.
     * @param serverPort    The server port that matches along with the address.
     * @param isVerboseMode A flag used to indicate if the server is running in verbose mode.
     */
    public ServerContext(String serverAddress, int serverPort, boolean isVerboseMode) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.isVerboseMode = isVerboseMode;
    }

    /**
     * Gets the current address the server is on.
     *
     * @return the server address.
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * The port the server is currently running on.
     *
     * @return The server port.
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * A flag that is used to indicate if the server is running on verbose mode.
     *
     * @return The flag value.
     */
    public boolean isVerboseMode() {
        return isVerboseMode;
    }
}
