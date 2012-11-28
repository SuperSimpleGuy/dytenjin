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
 * Contains identity information for another
 * object, with a unique id and a shared id
 * type. Within an id type, no two ids can
 * be the same.
 * @author SuperSimpleGuy
 */
public class UniqueId implements Comparable<UniqueId> {

	private int id;
	private int idType;
	
	/**
	 * Creates a UniqueId with an id and an id type
	 * @param id the id to contain
	 * @param idType the type of id stored
	 */
	public UniqueId(int id, int idType) {
		this.id = id;
		this.idType = idType;
	}

	/**
	 * Returns the id 
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the idType 
	 * @return the idType
	 */
	public int getIdType() {
		return idType;
	}
	
	/**
	 * True if this and another UniqueId share the
	 * same type, false otherwise
	 * @param other the other UniqueId
	 * @return true if the two UniqueId objects share
	 * the same id type, false otherwise
	 */
	public boolean hasSameType(UniqueId other) {
		return other.getIdType() == this.idType;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof UniqueId) {
			UniqueId temp = (UniqueId)o;
			return temp.getId() == this.id && temp.getIdType() == this.idType;
		}
		return false;
	}
	
	@Override
	public int compareTo(UniqueId u) {
		int temp = this.idType - u.getIdType();
		return (temp != 0 ? temp : this.id - u.getId());
	}
}
