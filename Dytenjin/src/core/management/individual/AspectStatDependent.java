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

package core.management.individual;

import java.util.ArrayList;

import core.parsing.FileParser;

/**
 * 
 * @author SuperSimpleGuy
 */
public abstract class AspectStatDependent implements IAspect {
	
	protected ArrayList<String> statsDependent;
	
	public AspectStatDependent() {
		statsDependent = new ArrayList<String>();
	}
	
	public AspectStatDependent(String s) {
		this();
		statsDependent.add(s);
	}
	
	public AspectStatDependent(String[] sArr) {
		this();
		for (String s : sArr) {
			statsDependent.add(s);
		}
	}
	
	@Override
	public abstract String getDescription(FileParser p);

}
