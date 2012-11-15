/*
 *  Dytenjin is an engine for making dynamic text-based java games.
 *  Copyright (C) 2012 SuperSimpleGuy
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package core.system;

import java.text.DateFormat;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * @author SuperSimpleGuy
 */
public class DytenjinFormatter extends SimpleFormatter {
	
	@Override
	public String format(LogRecord lRecord) {
		String formattedString = super.format(lRecord);
		
		Object[] params = lRecord.getParameters();
		if (params == null || params.length == 0) {
			return formattedString;
		}
		String temp = params[0].toString();
		formattedString += "\"" + temp;
		int count = temp.length();
		int i = 1;
		for (; i < params.length; i++) {
			temp = params[i].toString();
			if (count + temp.length() >= 76) {
				break;
			}
			formattedString += "\", \"" + temp;
			count += temp.length();
		}
		if (i != params.length) {
			formattedString += "\"...["+ (params.length-i) +" more]";
		} else {
			formattedString += "\"";
		}
		
		return formattedString + "\n";
	}

}
