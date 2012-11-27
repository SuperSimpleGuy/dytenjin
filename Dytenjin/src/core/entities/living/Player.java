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

package core.entities.living;

import core.geography.GeographicalLocation;
import core.management.game.UniqueId;
import core.management.ingame.AspectManager;

/**
 * Stub, doesn't really do anything at this point.
 * @author SuperSimpleGuy
 */
public abstract class Player extends LivingEntity {
	
	/**
	 * Creates a new basic player
	 * @param s the player's name
	 * @param id the player's unique id
	 * @param aMan the player's aspects
	 * @param l the location of this player
	 */
	public Player(String s, UniqueId id, AspectManager aMan, GeographicalLocation l) {
		super(s, id, l, aMan);
	}
	
}
