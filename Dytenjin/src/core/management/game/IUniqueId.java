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

/**
 * Requires the implemented object to have a unique
 * id compared to others that share its id type
 * @author SuperSimpleGuy
 */
public interface IUniqueId {

	/**
	 * Returns the unique id of this object
	 * @return the unique id of this object
	 */
	int getId();
	
	/**
	 * Gets the type of this id, all other objects
	 * that share this id type must have a unique
	 * id amongst themselves. Objects with different
	 * id types can share the same id.
	 * @return the id type
	 */
	String getIdType();
}
