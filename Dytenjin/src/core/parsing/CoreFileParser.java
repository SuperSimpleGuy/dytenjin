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

package core.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;

import core.Constants;
import core.system.ExceptionManager;

/**
 * 
 * @author SuperSimpleGuy
 */
public abstract class CoreFileParser {

	protected Scanner inFile;
	protected Class<?>[] args;
	
	public CoreFileParser(String f, Class<?>[] args) {
		try {
			inFile = new Scanner(new File(f));
		} catch (FileNotFoundException e) {
			ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(e, Level.SEVERE, Constants.SYS_ERR_FILE);
		}
		this.args = args;
	}
	
	public Class<?>[] getTypeOfArgs() {
		return this.args;
	}
	
	public abstract String getMyDescription(MultiGenericsWrapperMessage objArgs);
	
}
