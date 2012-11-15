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

import core.management.individual.AspectManager;

/**
 * Represents a location that links other locations together, allowing
 * travel between geographic locations. Maintains orientation details
 * and its own length.
 * @author SuperSimpleGuy
 */
public abstract class LocationLink extends GeographicalLocation {

	private GeographicalLocation loc1;
	private CardinalDirection dirFrom1;
	private GeographicalLocation loc2;
	private CardinalDirection dirFrom2;
	private double length;
	
	private HashMap<Integer, CardinalDirection> dirFromPath;
	
	/**
	 * Constructs a new fully-specified LocationLink
	 * @param name the name this LocationLink will have
	 * @param id the unique id of this LocationLink
	 * @param xCoord the x coordinate of this link's location
	 * @param yCoord the y coordinate of this link's location
	 * @param asp an AspectManager for this LocationLink
	 * @param parent the GeoRegion this location link is a part of
	 * @param loc1 the first GeoLocation this link connects
	 * @param loc2 the second GeoLocation this link connects
	 */
	public LocationLink(String name,
				int id,
				int xCoord,
				int yCoord,
				AspectManager asp,
				GeographicalRegion parent,
				GeographicalLocation loc1,
				GeographicalLocation loc2) {
		super(name, id, xCoord, yCoord, asp, parent);
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
		resetDirectionAndLength();
	}
	
	/**
	 * Creates a LocationLink that is not linking anything
	 * upon its creation.
	 * @param name the name this LocationLink will have
	 * @param id the unique id of this LocationLink
	 * @param xCoord the x coordinate of this link's location
	 * @param yCoord the y coordinate of this link's location
	 * @param asp an AspectManager for this LocationLink
	 * @param parent the GeoRegion this location link is a part of
	 */
	public LocationLink(String name,
			int id,
			int xCoord,
			int yCoord,
			AspectManager asp,
			GeographicalRegion parent) {
		super(name, id, xCoord, yCoord, asp, parent);
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
	}
	
	/**
	 * Resets this location link's directions and length values to match its
	 * coordinates and the coordinates of the two neighbors it is linking. Does
	 * nothing if one or both of the two neighbors is null.
	 */
	public void resetDirectionAndLength() {
		if (loc1 == null || loc2 == null) {
			return;
		}
		this.dirFrom1 = CardinalDirection.getDirFromCoords(loc1.getxCoord(), loc1.getyCoord(), this.getxCoord(), this.getyCoord());
		this.dirFrom2 = CardinalDirection.getDirFromCoords(loc2.getxCoord(), loc2.getyCoord(), this.getxCoord(), this.getyCoord());
		length = Math.sqrt((loc1.getxCoord() - this.getxCoord())*(loc1.getxCoord() - this.getxCoord()) + (loc1.getyCoord() - this.getyCoord())*(loc1.getyCoord() - this.getyCoord()));
		length += Math.sqrt((this.getxCoord() - loc2.getxCoord())*(this.getxCoord() - loc2.getxCoord()) + (this.getyCoord() - loc2.getyCoord())*(this.getyCoord() - loc2.getyCoord()));
	}
	
	@Override
	public void setCoords(int newXCoord, int newYCoord) {
		super.setCoords(newXCoord, newYCoord);
		resetDirectionAndLength();
	}
	
	@Override
	public CardinalDirection getDirFromPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId());
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	@Override
	public CardinalDirection getDirToPath(int id) {
		LocationLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId()).opposite();
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	/**
	 * Returns the direction from the specified GeoLocation if it is one
	 * of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param gL the GeographicalLocation to get the direction from
	 * @return a direction pointing from the specified location to this
	 * link, or an error if the specified location is not one of the two
	 * being linked by this LocationLink
	 */
	public CardinalDirection getDirFromGeoLoc(GeographicalLocation gL) {
		if (gL.equals(loc1)) {
			return dirFrom1;
		} else if (gL.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction towards the specified GeoLocation if it is one
	 * of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param gL the GeographicalLocation to get the direction towards
	 * @return a direction pointing towards the specified location from this
	 * link, or an error if the specified location is not one of the two
	 * being linked by this LocationLink
	 */
	public CardinalDirection getDirToGeoLoc(GeographicalLocation gL) {
		if (gL.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gL.equals(loc2)) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction from the GeoLocation specified by an ID if 
	 * it is one of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param id the id of the GeographicalLocation to get the direction from
	 * @return a direction pointing from the specified location towards this
	 * link, or an error if the specified location id is not one of the two id's
	 * belonging to the locations being linked by this LocationLink
	 */
	public CardinalDirection getDirFromGeoLoc(int id) {
		if (id == loc1.getId()) {
			return dirFrom1;
		} else if (id == loc2.getId()) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction towards the GeoLocation specified by an ID if 
	 * it is one of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param id the id of the GeographicalLocation to get the direction towards
	 * @return a direction pointing towards the specified location from this
	 * link, or an error if the specified location id is not one of the two id's
	 * belonging to the locations being linked by this LocationLink
	 */
	public CardinalDirection getDirToGeoLoc(int id) {
		if (id == loc1.getId()) {
			return dirFrom1.opposite();
		} else if (id == loc2.getId()) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}
	
	@Override
	public LocationLink removeLocationLink(int id) {
		if (!paths.containsKey(id)) {
			dirFromPath.remove(id);
			return paths.remove(id);
		}
		return null;
	}
	
	@Override
	public boolean addLocationLink(LocationLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), CardinalDirection.getDirFromCoords(l.getxCoord(), l.getyCoord(), this.getxCoord(), this.getyCoord()));
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a LocationLink with a direction pointing from the specified
	 * LocationLink towards this one. Returns true if successfully added
	 * and false otherwise.
	 * @param l the LocationLink to add that is linking this LocationLink
	 * with another GeographicLocation
	 * @param dir the direction pointing from the specified LocationLink
	 * towards this
	 * @return true if successfully added, false otherwise
	 */
	public boolean addLocationLink(LocationLink l, CardinalDirection dir) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), dir);
			return true;
		}
		return false;
	}
	
	/**
	 * Changes the CardinalDirection pointing from the specified
	 * LocationLink towards this one, returning true if successful
	 * and false if the specified LocationLink is not linking this
	 * @param l the LocationLink direction to change
	 * @param dirNew the new CardinalDirection pointing from the
	 * specified LocationLink towards this
	 * @return true if successful, false otherwise
	 */
	public boolean changeLocLinkDir(LocationLink l, CardinalDirection dirNew) {
		if (dirFromPath.containsKey(l.getId())) {
			dirFromPath.remove(l.getId());
			dirFromPath.put(l.getId(), dirNew);
			return true;
		}
		return false;
	}
	
	@Override
	public LocationLink getPathById(int id) {
		if (loc1 instanceof LocationLink && loc1.getId() == id) {
			return (LocationLink)loc1;
		} else if (loc2 instanceof LocationLink && loc2.getId() == id) {
			return (LocationLink)loc2;
		}
		return paths.get(id);
	}
	
	/**
	 * Returns the total length of this LocationLink between
	 * the two GeoLocations being linked
	 * @return the total length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Manually sets the length of this LocationLink. Note that
	 * many methods automatically update the length of this
	 * LocationLink, so use with caution. Returns the old
	 * length of this LocationLink
	 * @param length the length of this LocationLink between
	 * the two GeoLocations being linked
	 * @return the old length of this LocationLink
	 */
	public double setLength(double length) {
		double temp = this.length;
		this.length = length;
		return temp;
	}
	
	/**
	 * Returns the first location being linked
	 * @return the first location being linked
	 */
	public GeographicalLocation getLoc1() {
		return loc1;
	}
	
	/**
	 * Sets the first location to be linked, updating this
	 * location link's directions and length after doing so.
	 * @param gR the first location to be linked
	 */
	public void setLoc1(GeographicalLocation gR) {
		loc1 = gR;
		resetDirectionAndLength();
	}

	/**
	 * Returns the second location being linked
	 * @return the second location being linked
	 */
	public GeographicalLocation getLoc2() {
		return loc2;
	}
	
	/**
	 * Sets the second location to be linked, updating this
	 * location link's directions and length after doing so.
	 * @param gR the second location to be linked
	 */
	public void setLoc2(GeographicalLocation gR) {
		loc2 = gR;
		resetDirectionAndLength();
	}
}
