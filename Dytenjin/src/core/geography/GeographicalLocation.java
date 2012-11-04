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

package core.geography;

import java.util.HashMap;

import core.entities.nonliving.NonLivingEntityGroup;
import core.management.individual.AspectManager;
import core.temporal.TimeChanging;

/**
 * 
 * @author SuperSimpleGuy
 */
public abstract class GeographicalLocation implements TimeChanging {

	private GeographicalRegion parent;
	private int id;
	private String name;
	private AspectManager aspMan;
	private int xCoord;
	private int yCoord;
	
	protected HashMap<Integer, LocationLink> paths;
	protected NonLivingEntityGroup owner;
	
	public GeographicalLocation(String name,
								int id,
								int xCoord,
								int yCoord,
								AspectManager aspMan,
								GeographicalRegion parent) {
		this.id = id;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.setName(name);
		this.aspMan = aspMan;
		this.setParent(parent);
		paths = new HashMap<Integer, LocationLink>();
		owner = null;
	}
	
	public CardinalDirection getDirToPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirFromGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public CardinalDirection getDirFromPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirToGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public LocationLink removeLocationLink(int id) {
		if (!paths.containsKey(id)) {
			return paths.remove(id);
		}
		return null;
	}
	
	public boolean addLocationLink(LocationLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			return true;
		}
		return false;
	}

	public GeographicalRegion getParent() {
		return parent;
	}

	public void setParent(GeographicalRegion parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AspectManager getAspect() {
		return aspMan;
	}

	public HashMap<Integer, LocationLink> getPaths() {
		return paths;
	}

	public NonLivingEntityGroup getOwner() {
		return owner;
	}

	public void setOwner(NonLivingEntityGroup owner) {
		this.owner = owner;
	}
	
	public int getId() {
		return id;
	}
	
	public int getxCoord() {
		return xCoord;
	}

	public int getyCoord() {
		return yCoord;
	}

	public boolean equals(Object other) {
		if (!(other instanceof GeographicalLocation)) {
			return false;
		}
		GeographicalLocation gL = (GeographicalLocation)other;
		return this.id == gL.getId();
	}
}
