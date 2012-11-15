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

package core.management.game;

import java.util.HashMap;

/**
 * 
 * @author SuperSimpleGuy
 */
public class IdentityManager {
	
	public static IdentityManager SYS_IDMNGR = new IdentityManager();

	private HashMap<String, Integer> identities;
	
	private IdentityManager() {
		identities = new HashMap<String, Integer>();
	}
	
	private IdentityManager(String[] keys) {
		this();
		for (String s : keys) {
			this.addNewIdTracking(s);
		}
	}
	
	public boolean isIdFree(String key, int id) {
		return identities.get(key) <= id;
	}
	
	public boolean addNewIdTracking(String key) {
		if (identities.containsKey(key)) {
			return false;
		}
		identities.put(key, 0);
		return true;
	}
	
	public int getNextFreeId(String key) {
		if (!identities.containsKey(key)) {
			return 0;
		}
		int temp = identities.get(key);
		identities.put(key, temp + 1);
		return temp;
	}
	
}
