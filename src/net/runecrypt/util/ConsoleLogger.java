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
package net.runecrypt.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * An {@link PrintStream} that is used to format the PrintStream.
 * 
 * @author Thomas Le Godais <thomaslegodais@live.com>
 *
 */
public class ConsoleLogger extends PrintStream {

	/** 
	 * The simple date format.
	 */
	private SimpleDateFormat simpleDate;

	/**
	 * Constructs the ConsoleLogger.
	 * 
	 * @param stream The logger stream.
	 */
	public ConsoleLogger(PrintStream stream) {
		super(stream);
	}

	/* (non-Javadoc)
	 * 
	 * @see java.io.PrintStream#println(java.lang.String)
	 */
	@Override
	public void println(String message) {
		String date = "["+format(new Date())+"]: ";
		super.println(date + message);
	}

	/**
	 * Formats the requested date.
	 * 
	 * @param date The date.
	 * @return The formatted date.
	 */
	private String format(Date date) {
		simpleDate = new SimpleDateFormat();
		return simpleDate.format(date);
	}
}