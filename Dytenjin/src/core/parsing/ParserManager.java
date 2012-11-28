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

/**
 * @author SuperSimpleGuy
 */
public class ParserManager<T extends CoreFileParser> {
	
	private T parser;
	
	public ParserManager(T parser) {
		this.parser = parser;
	}
	
	public String getDescription(MultiGenericsWrapperMessage objArgs) {
		if (objArgs.doTypesMatch(parser.getTypeOfArgs())) {
			return parser.getMyDescription(objArgs);
		}
		return null;
	}
	
	public MultiGenericsWrapperMessage getArgs() {
		return new MultiGenericsWrapperMessage(parser.getTypeOfArgs());
	}
	
}
