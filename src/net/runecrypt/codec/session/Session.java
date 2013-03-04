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
package net.runecrypt.codec.session;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;

/**
 * Represents a single incomming system from a {@link Channel}.
 *
 * @author Thomas Le Godais <thomaslegodais@live.com>
 * @author James Barton <sirjames1996@hotmail.com>
 * 
 * @since 1.0 <4:49:15 PM - Mar 4, 2013>
 */
public abstract class Session {

	/**
	 * The channel.
	 */
	protected final Channel channel;
	
	/**
	 * The channel handler context.
	 */
	protected final ChannelHandlerContext context;
	
	/**
	 * Creates a new Session.
	 * @param context The channel handler context.
	 */
	public Session(ChannelHandlerContext context) {
		this.channel = context.getChannel();
		this.context = context;
	}
	
	/**
	 * Handles the disconnection of a certain channel.
	 */
	public abstract void disconnected();
	
	/**
	 * Gets the channel.
	 * @return The channel.
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * Gets the channel handler context.
	 * @return The channel handler context.
	 */
	public ChannelHandlerContext getContext() {
		return context;
	}

	/**
	 * Receives the message fired from the frame decoder.
	 * @param obj The object fired.
	 */
	public abstract void message(Object obj);
}
