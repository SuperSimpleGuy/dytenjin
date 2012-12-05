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

package core;

import java.util.HashMap;

import core.management.game.UniqueId;

/**
 * Manages UniqueIds for the engine, ensuring no
 * two similar ids are generated for an id type
 * @author SuperSimpleGuy
 */
public class CoreIdentityManager {
	
	/**
	 * The system's Identity Manager to use
	 */
	public static CoreIdentityManager SYS_IDMNGR = new CoreIdentityManager();

	private HashMap<String, UniqueId> identities;
	private int idTypeIndex;
	
	/**
	 * Constructs an Identity Manager with no entries and no
	 * various id types to manage
	 */
	private CoreIdentityManager() {
		identities = new HashMap<String, UniqueId>();
		idTypeIndex = 0;
	}
	
	/**
	 * Determines whether the id is free for the specified
	 * key.
	 * @param key the category of the id type to use
	 * @param id the id to determine if free
	 * @return true if available, false otherwise
	 */
	public boolean isIdFree(String key, int id) {
		return identities.get(key).getId() <= id;
	}
	
	/**
	 * Creates a new id type to manage new ids for
	 * @param key the String representing the id type
	 * @return true if a new id type was successfully
	 * created, false otherwise
	 */
	public boolean addNewIdTracking(String key) {
		if (identities.containsKey(key)) {
			return false;
		}
		identities.put(key, new UniqueId(0, idTypeIndex++));
		return true;
	}
	
	/**
	 * Returns a new UniqueId object to pass to IHasUniqueId
	 * constructors, guaranteed unique. Null-safe, as a key
	 * that is not yet being tracked will automatically
	 * create a new id type for it.
	 * @param key the String representing the id type
	 * @return the UniqueId to use for a new IHasUniqueId object
	 */
	public UniqueId getNextFreeId(String key) {
		addNewIdTracking(key);
		UniqueId temp = identities.get(key);
		identities.put(key, new UniqueId(temp.getId() + 1, temp.getIdType()));
		return temp;
	}
	
}
