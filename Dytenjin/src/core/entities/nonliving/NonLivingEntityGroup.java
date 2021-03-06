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

package core.entities.nonliving;

import core.entities.Entity;
import core.management.game.UniqueId;

/**
 * Currently a stub, doesn't really do anything
 * Things like political groups, etc
 * @author SuperSimpleGuy
 */
public abstract class NonLivingEntityGroup extends Entity {
	
	/**
	 * Creates a named group of nonliving entities
	 * @param s the name of the group
	 * @param id the unique id of this group
	 */
	public NonLivingEntityGroup(String s, UniqueId id) {
		super(s, id);
	}
	
}
