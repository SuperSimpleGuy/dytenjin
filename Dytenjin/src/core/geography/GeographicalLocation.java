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

import core.Constants;
import core.entities.Entity;
import core.management.game.IUniqueId;
import core.management.individual.AspectManager;

/**
 * Represents a geographical location that entities can occupy and own.
 * Has certain aspects depending on the kind of location a subclass
 * instantiates.
 * @author SuperSimpleGuy
 */
public abstract class GeographicalLocation implements IUniqueId {

	private GeographicalRegion parent;
	private int id;
	private String name;
	private AspectManager aspMan;
	private int xCoord;
	private int yCoord;
	
	protected HashMap<Integer, LocationLink> paths;
	protected Entity owner;
	
	/**
	 * The constructor for a geographical location, completely
	 * isolated (containing no adjacent links), with a name, a
	 * unique id, an x and y coordinate of this location,
	 * predefined aspects and a parent GeographicalRegion. It
	 * starts off with no owning Entity.
	 * @param name the name of this Geo Location
	 * @param id the unique id of this Geo Location
	 * @param xCoord the x coordinate of this Geo Location
	 * @param yCoord the y coordinate of this Geo Location
	 * @param aspMan the AspectManager for this Geo Location
	 * @param parent the parent Geo Region for this Geo Location
	 */
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
	
	/**
	 * Given a link id, returns the cardinal direction towards
	 * that LocationLink
	 * @param id the id of the Location Link
	 * @return the CardinalDirection towards that LocationLink
	 */
	public CardinalDirection getDirToPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirFromGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	/**
	 * Given a link id, returns the cardinal direction from
	 * that LocationLink
	 * @param id the id of the Location Link
	 * @return the CardinalDirection from that LocationLink
	 */
	public CardinalDirection getDirFromPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null) {
			return temp.getDirToGeoLoc(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	/**
	 * Removes a link with the unique id passed as a parameter,
	 * returning the link or null if none was found
	 * @param id the id of the link to remove
	 * @return the link with the unique id, or null if no link
	 * was found
	 */
	public LocationLink unregisterLocationLink(int id) {
		return paths.remove(id);
	}
	
	/**
	 * Returns a location link from its unique id, or null if no
	 * entry exists for that id
	 * @param id the unique id of the LocationLink
	 * @return the LocationLink object with the unique id, or null
	 * if no id was found
	 */
	public LocationLink getPathById(int id) {
		return paths.get(id);
	}
	
	/**
	 * Adds a location link, returning true if the addition
	 * was successful and false otherwise
	 * @param l the link to add
	 * @return true if the link was successfully added, false
	 * otherwise
	 */
	public boolean registerLocationLink(LocationLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			return true;
		}
		return false;
	}

	/**
	 * Returns this location's regional parent
	 * @return the GeographicalRegion parent
	 */
	public GeographicalRegion getParent() {
		return parent;
	}
	
	/**
	 * The GeographicalLocation will call upon its parent
	 * to remove itself, which will also remove any linking
	 * LocationLinks from the parent as well
	 */
	public void removeSelfFromParent() {
		for (LocationLink l : paths.values()) {
			l.removeSelfFromParent();
			this.unregisterLocationLink(l.getId());
		}
		if (parent != null) {
			parent.unregisterChildLoc(this.getId());
		}
	}

	/**
	 * Sets this location's regional parent
	 * @param parent the new GeographicalRegion parent
	 */
	public void setParent(GeographicalRegion parent) {
		this.parent = parent;
	}

	/**
	 * Returns the name of this location
	 * @return the name of this location
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this location
	 * @param name the new name of this location
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns this location's aspects
	 * @return the AspectManager for this location
	 */
	public AspectManager getAspect() {
		return aspMan;
	}

	/**
	 * Returns a HashMap of links connecting this location
	 * to other locations
	 * @return the HashMap of connected links
	 */
	public HashMap<Integer, LocationLink> getPaths() {
		return paths;
	}

	/**
	 * Returns the current owning entity of this location
	 * @return the current owning entity of this location
	 */
	public Entity getOwner() {
		return owner;
	}

	/**
	 * Sets the current owning entity of this location
	 * @param owner the new owning entity of this location
	 */
	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getIdType() {
		return Constants.ID_LOC;
	}
	
	/**
	 * Returns this location's x coordinate
	 * @return this location's x coordinate
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * Returns this location's y coordinate
	 * @return this location's y coordinate
	 */
	public int getyCoord() {
		return yCoord;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalLocation)) {
			return false;
		}
		GeographicalLocation gL = (GeographicalLocation)other;
		return this.id == gL.getId();
	}
	
	/**
	 * Sets the coordinates for this GeographicalLocation, which
	 * then updates the lengths of all adjacent paths.
	 * @param newXCoord the new x coordinate for this location
	 * @param newYCoord the new y coordinate for this location
	 */
	public void setCoords(int newXCoord, int newYCoord) {
		this.xCoord = newXCoord;
		this.yCoord = newYCoord;
		for (LocationLink rL : paths.values()) {
			rL.resetDirectionAndLength();
		}
	}
}
