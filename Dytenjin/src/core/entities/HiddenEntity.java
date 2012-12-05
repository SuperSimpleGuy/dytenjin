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

package core.entities;

import core.management.game.UniqueId;

/**
 * Represents an {@link Entity} whose fields are essentially a mask for
 * another, secret entity. Think of the Dread Pirate Roberts from
 * Princess Bride
 * @author SuperSimpleGuy
 * @param <T> class type of the entity to hide
 */
public abstract class HiddenEntity<T extends Entity> extends Entity {

	protected T hiddenEntity;
	
	/**
	 * Creates an entity with a name and id, and default aspects.
	 * @param s the entity's name
	 * @param id the entity's {@link UniqueId}
	 */
	public HiddenEntity(String s, UniqueId id) {
		super(s, id);
	}
	
	/**
	 * Used to determine if able to hide an entity
	 * @return true if the current entity is acting as a mask, false if
	 * a genuine entity
	 */
	public boolean isHidingEntity() {
		return hiddenEntity != null;
	}
	
	/**
	 * Hides an entity of type T within this entity, returning whether
	 * the given entity is successfully hidden or not
	 * @param entity the entity to hide in this mask
	 * @return true if the entity was successfully hidden, false if
	 * another entity is already hidden in this mask
	 */
	public boolean hideEntity(T entity) {
		if (hiddenEntity != null) {
			return false;
		}
		hiddenEntity = entity;
		return true;
	}
	
	/**
	 * Removes the entity from hiding behind this mask, allows
	 * other entities to hide behind it.
	 * @return the entity that was being hidden by this mask
	 */
	public T unhideEntity() {
		T temp = hiddenEntity;
		hiddenEntity = null;
		return temp;
	}
	
	/**
	 * Returns the entity that is hiding behind this mask
	 * @return the entity that is hiding like a coward behind this mask
	 */
	public T getHiddenEntity() {
		return hiddenEntity;
	}

}
