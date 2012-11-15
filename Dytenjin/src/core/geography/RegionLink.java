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
 * Represents a location that links other locations together on a regional scale,
 * allowing travel between geographic regions. Maintains orientation details
 * @author SuperSimpleGuy
 */
public abstract class RegionLink extends GeographicalRegion {

	private GeographicalRegion loc1;
	private CardinalDirection dirFrom1;
	private GeographicalRegion loc2;
	private CardinalDirection dirFrom2;
	private double length;
	
	private HashMap<Integer, CardinalDirection> dirFromPath;
	
	/**
	 * Creates a new fully-specified RegionLink
	 * @param name the name of this RegionLink
	 * @param id the unique id of this RegionLink
	 * @param xCoord the x coordinate of this link's location
	 * @param yCoord the y coordinate of this link's location
	 * @param asp an AspectManager for this RegionLink
	 * @param loc1 the first GeographicalRegion this link connects
	 * @param loc2 the second GeographicalRegion this link connects
	 */
	public RegionLink(String name,
			   int id,
			   int xCoord,
			   int yCoord,
			   AspectManager asp,
			  GeographicalRegion loc1,
			  GeographicalRegion loc2) {
		super(name, id, xCoord, yCoord, asp);
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
		resetDirectionAndLength();
	}
	
	/**
	 * Creates a RegionLink that is not linking anything
	 * upon its creation.
	 * @param name the name of this RegionLink
	 * @param id the unique id of this RegionLink
	 * @param xCoord the x coordinate of this link's location
	 * @param yCoord the y coordinate of this link's location
	 * @param asp an AspectManager for this RegionLink
	 */
	public RegionLink(String name,
			   int id,
			   int xCoord,
			   int yCoord,
			   AspectManager asp) {
		super(name, id, xCoord, yCoord, asp);
		this.dirFromPath = new HashMap<Integer, CardinalDirection>();
	}
	
	/**
	 * Resets this region link's directions and length values to match its
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
	
	/**
	 * Returns the direction from the specified GeoRegion if it is one
	 * of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param gL the GeographicalRegion to get the direction from
	 * @return a direction pointing from the specified region to this
	 * link, or an error if the specified region is not one of the two
	 * being linked by this RegionLink
	 */
	public CardinalDirection getDirFromGeoReg(GeographicalRegion gR) {
		if (gR.equals(loc1)) {
			return dirFrom1;
		} else if (gR.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction towards the specified GeoRegion if it is one
	 * of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param gL the GeographicalRegion to get the direction towards
	 * @return a direction pointing towards the specified region from this
	 * link, or an error if the specified region is not one of the two
	 * being linked by this RegionLink
	 */
	public CardinalDirection getDirToGeoReg(GeographicalRegion gR) {
		if (gR.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gR.equals(loc2)) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction from the GeoRegion specified by an ID if 
	 * it is one of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param id the id of the GeographicalRegion to get the direction from
	 * @return a direction pointing from the specified region towards this
	 * link, or an error if the specified region id is not one of the two id's
	 * belonging to the locations being linked by this RegionLink
	 */
	public CardinalDirection getDirFromGeoReg(int id) {
		if (id == loc1.getId()) {
			return dirFrom1;
		} else if (id == loc2.getId()) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	/**
	 * Returns the direction towards the GeoRegion specified by an ID if 
	 * it is one of the two currently being linked. Returns the error direction
	 * otherwise.
	 * @param id the id of the GeographicalRegion to get the direction towards
	 * @return a direction pointing towards the specified region from this
	 * link, or an error if the specified region id is not one of the two id's
	 * belonging to the locations being linked by this RegionLink
	 */
	public CardinalDirection getDirToGeoReg(int id) {
		if (id == loc1.getId()) {
			return dirFrom1.opposite();
		} else if (id == loc2.getId()) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}

	@Override
	public CardinalDirection getDirFromPath(int id) {
		RegionLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId());
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	@Override
	public CardinalDirection getDirToPath(int id) {
		RegionLink temp = this.paths.get(id);
		if (temp != null && dirFromPath.containsKey(temp.getId())) {
			return dirFromPath.get(temp.getId()).opposite();
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	@Override
	public RegionLink unregisterRegionLink(int id) {
		if (paths.containsKey(id)) {
			dirFromPath.remove(id);
			return paths.remove(id);
		}
		return null;
	}
	
	@Override
	public boolean registerRegionLink(RegionLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), CardinalDirection.getDirFromCoords(l.getxCoord(), l.getyCoord(), this.getxCoord(), this.getyCoord()));
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a RegionLink with a direction pointing from the specified
	 * RegionLink towards this one. Returns true if successfully added
	 * and false otherwise.
	 * @param l the RegionLink to add that is linking this RegionLink
	 * with another GeographicalRegion
	 * @param dir the direction pointing from the specified RegionLink
	 * towards this
	 * @return true if successfully added, false otherwise
	 */
	public boolean addRegionLink(RegionLink l, CardinalDirection dir) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), dir);
			return true;
		}
		return false;
	}
	
	/**
	 * Changes the CardinalDirection pointing from the specified
	 * RegionLink towards this one, returning true if successful
	 * and false if the specified RegionLink is not linking this
	 * @param l the RegionLink direction to change
	 * @param dirNew the new CardinalDirection pointing from the
	 * specified RegionLink towards this
	 * @return true if successful, false otherwise
	 */
	public boolean changeRegLinkDir(RegionLink l, CardinalDirection dirNew) {
		if (dirFromPath.containsKey(l.getId())) {
			dirFromPath.remove(l.getId());
			dirFromPath.put(l.getId(), dirNew);
			return true;
		}
		return false;
	}
	
	@Override
	public RegionLink getPathById(int id) {
		if (loc1 instanceof RegionLink && loc1.getId() == id) {
			return (RegionLink)loc1;
		} else if (loc2 instanceof RegionLink && loc2.getId() == id) {
			return (RegionLink)loc2;
		}
		return paths.get(id);
	}

	/**
	 * Returns the total length of this RegionLink between
	 * the two GeoRegions being linked
	 * @return the total length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Manually sets the length of this RegionLink. Note that
	 * many methods automatically update the length of this
	 * RegionLink, so use with caution. Returns the old
	 * length of this RegionLink
	 * @param length the length of this RegionLink between
	 * the two GeoRegions being linked
	 * @return the old length of this RegionLink
	 */
	public double setLength(double length) {
		double temp = this.length;
		this.length = length;
		return temp;
	}

	/**
	 * Returns the first region being linked
	 * @return the first region being linked
	 */
	public GeographicalRegion getLoc1() {
		return loc1;
	}
	
	/**
	 * Sets the first region to be linked, updating this
	 * region link's directions and length after doing so.
	 * @param gR the first region to be linked
	 */
	public void setLoc1(GeographicalRegion gR) {
		loc1 = gR;
		resetDirectionAndLength();
	}

	/**
	 * Returns the second region being linked
	 * @return the second region being linked
	 */
	public GeographicalRegion getLoc2() {
		return loc2;
	}
	
	/**
	 * Sets the second region to be linked, updating this
	 * region link's directions and length after doing so.
	 * @param gR the second region to be linked
	 */
	public void setLoc2(GeographicalRegion gR) {
		loc2 = gR;
		resetDirectionAndLength();
	}
}
