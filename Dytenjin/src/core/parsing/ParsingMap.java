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
import java.util.HashMap;
import java.util.Scanner;

import core.Constants;

/**
 * 
 * @author SuperSimpleGuy
 */
public class ParsingMap {

	private HashMap<String, String> map;
	
	public ParsingMap() {
		map = new HashMap<String, String>();
		buildMap();
	}
	
	private void buildMap() {
		Scanner s = null;
		try {
			s = new Scanner(new File(Constants.MAP_PARSING_FILENAME));
		} catch (FileNotFoundException e) {
			System.out.println(Constants.MAP_PARSING_ERROR_STRING + e.getLocalizedMessage());
			System.exit(Constants.MAP_PARSING_ERROR_CODE);
		}
		while (s.hasNextLine()) {
			String line = s.nextLine();
			int pivot = line.indexOf(' ');
			if (pivot == -1) {
				System.out.println(Constants.MAP_PARSING_ERROR_INCORRECT_FORMAT);
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			String fromClass = line.substring(0, pivot);
			String toParser = line.substring(pivot + 1);
			if (toParser.contains(" ")) {
				System.out.println(Constants.MAP_PARSING_ERROR_INCORRECT_FORMAT);
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			try {
				Class.forName(fromClass);
				Class.forName(toParser);
			} catch (Exception e) {
				System.out.println(Constants.MAP_PARSING_ERROR_NO_CLASS + e.getLocalizedMessage());
				System.exit(Constants.MAP_PARSING_ERROR_CODE);
			}
			map.put(fromClass, toParser);
		}
	}
	
	public CoreFileParser getParserFromClass(Class<?> classz) {
		CoreFileParser p = null;
		try {
			p = (CoreFileParser)Class.forName(map.get(classz.getName())).newInstance();
		} catch (Exception e) {
			System.out.println(Constants.MAP_PARSING_ERROR_NO_CLASS + e.getLocalizedMessage());
			System.exit(Constants.MAP_PARSING_ERROR_CODE);
		}
		return p;
	}
	
}
