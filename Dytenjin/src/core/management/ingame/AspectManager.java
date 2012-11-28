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

package core.management.ingame;

import java.util.HashMap;

import core.management.game.UniqueId;
import core.parsing.IIsParsable;

/**
 * Manages a set of aspects for a single entity,
 * place, group, or otherwise describable object
 * through use of {@link IIsParsable}
 * @author SuperSimpleGuy
 */
public class AspectManager {

	private HashMap<Integer, IIsParsable> aspects;
	
	public AspectManager() {
		aspects = new HashMap<Integer, IIsParsable>();
	}
	
	public boolean containsAspectType(UniqueId uId) {
		return aspects.containsKey(uId.getIdType());
	}
	
	public boolean addAspect(IIsParsable ip) {
		if (aspects.containsKey(ip.getUniqueId().getIdType())) {
			return false;
		}
		aspects.put(ip.getUniqueId().getId(), ip);
		return true;
	}
	
	public IIsParsable removeAspect(UniqueId uId) {
		return aspects.remove(uId.getIdType());
	}
	
	public IIsParsable getAspect(UniqueId uId) {
		return aspects.get(uId.getIdType());
	}
	
}
