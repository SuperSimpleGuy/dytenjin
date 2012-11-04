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

import core.management.individual.AspectManager;

/**
 * An entity represents a living or non-living being or group of beings,
 * with a name, a unique id number, and aspects.
 * @author SuperSimpleGuy
 */
public abstract class Entity {
	
	protected String name;
	private int id;
	protected AspectManager aMan;
	
	/**
	 * Creates a default, nameless entity and default aspects.
	 * @param id the entity's unique id
	 */
	public Entity(int id) {
		this.id = id;
		this.name = "";
		this.aMan = new AspectManager();
	}
	
	/**
	 * Creates an entity with a name and id, and default aspects.
	 * @param s the entity's name
	 * @param identificationNumber the entity's unique id
	 */
	public Entity(String s, int identificationNumber) {
		name = s;
		id = identificationNumber;
		this.aMan = new AspectManager();
	}
	
	/**
	 * Returns the unique id of this entity
	 * @return the unique id of this entity
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of this entity.
	 * Abstract depending if subclasses have titles,
	 * middle or surnames
	 * @return the name of this entity
	 */
	public abstract String getName();
	
	/**
	 * Sets the name of this entity
	 * Abstract depending if subclasses have titles,
	 * middle or surnames
	 * @param name the new name of this entity
	 * @return the old name of the entity
	 */
	public abstract String setName(String name);
	
	/**
	 * Returns the original or copy of this entity's
	 * aspects, depending on subclass implementation
	 * @return the aspects of this entity
	 */
	public abstract AspectManager getAspectMan();
	
	/**
	 * Sets this entity's aspects.
	 * @param aMan the aspect manager to set for this entity
	 * @return the old aspects of this entity
	 */
	public abstract AspectManager setAspectMan(AspectManager aMan);
}
