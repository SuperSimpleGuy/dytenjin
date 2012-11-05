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

import core.entities.Entity;
import core.geography.GeographicalLocation;
import core.management.individual.AspectManager;

/**
 * A living single individual is represented by a living entity
 * @author SuperSimpleGuy
 */
public class LivingEntity extends Entity {
	
	protected GeographicalLocation currentLoc;
	//TODO other fields like first/last/sur name? Or just a Name class?
	
	/**
	 * Living entities require a name, a unique id, and
	 * a location
	 * @param s the name of this living entity
	 * @param id the id of this living entity
	 * @param l the physical location of this entity
	 */
	public LivingEntity(String s, int id, GeographicalLocation l) {
		super(s, id);
		currentLoc = l;
	}
	
	/**
	 * Living entities require a name, a unique id, and
	 * a location
	 * @param s the name of this living entity
	 * @param id the id of this living entity
	 * @param l the physical location of this entity
	 * @param asp the starting AspectManager of this entity
	 */
	public LivingEntity(String s, int id, GeographicalLocation l, AspectManager asp) {
		super(s, id, asp);
		currentLoc = l;
	}

	/**
	 * Returns the location of this living entity
	 * @return
	 */
	public GeographicalLocation getCurrentLoc() {
		return currentLoc;
	}

	/**
	 * Sets the location of this living entity
	 * @param currentLoc the new current location of the living entity
	 * @return the old location of the living entity
	 */
	public GeographicalLocation setCurrentLoc(GeographicalLocation currentLoc) {
		//TODO actually use GeoLoc methods to navigate the living entity
		// but then what about things like teleportation? Maybe an aspect
		// and possession manager -> item use, or something
		GeographicalLocation l = this.currentLoc;
		this.currentLoc = currentLoc;
		return l;
	}
	
	/**
	 * Gets this entity's name
	 * @return the name of this living entity
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name for this entity, returning the old one
	 * @param name the new name for this entity
	 * @return the old name for this entity
	 */
	public String setName(String name) {
		String s = this.name;
		this.name = name;
		return s;
	}

	@Override
	public AspectManager getAspectMan() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
