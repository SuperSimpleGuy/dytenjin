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
import core.management.game.IUniqueId;
import core.management.individual.AspectManager;
import core.stats.GeographicalRegionStatsManager;

/**
 * Manages a region of locations, and as a parent map. Maintains its
 * own set of aspects and region links between itself and neighboring
 * regions
 * @author SuperSimpleGuy
 */
public abstract class GeographicalRegion implements IUniqueId {
	
	private GeographicalRegionStatsManager gStats;
	private int id;
	private String name;
	private AspectManager aspects;
	private int xCoord;
	private int yCoord;
	
	protected HashMap<Integer, RegionLink> paths;
	protected HashMap<Integer, GeographicalLocation> childLocs;
	
	/**
	 * Constructor for a GeographicalRegion, assigning it a name,
	 * unique id, aspects and initializing managers that aid in
	 * game statistics, child locations, and region links
	 * @param name the name of the region
	 * @param id the unique id for the region
	 * @param asp initial aspects for the region
	 */
	public GeographicalRegion (String name,
							   int id,
							   int xCoord,
							   int yCoord,
							   AspectManager asp) {
		this.id = id;
		this.name = name;
		this.aspects = asp;
		this.paths = new HashMap<Integer, RegionLink>();
		this.childLocs = new HashMap<Integer, GeographicalLocation>();
		this.gStats = new GeographicalRegionStatsManager();
	}
	
//  Legacy:	
//	public void updateMonth() {
//		HashMap<String, Integer> hmGeo = new HashMap<String, Integer>();
//		HashMap<String, Integer> hmReg = new HashMap<String, Integer>();
//		for (GeographicalLocation gL : childLocs.values()) {
//			String key = gL.getOwner().getName();
//			if (gL instanceof LocationLink) {
//				if (!hmReg.containsKey(key)) {
//					hmReg.put(key, 1);
//				} else {
//					int tempVal = hmReg.get(key);
//					hmReg.put(key, tempVal + 1);
//				}
//			} else {
//				if (!hmGeo.containsKey(key)) {
//					hmGeo.put(key, 1);
//				} else {
//					int tempVal = hmGeo.get(key);
//					hmGeo.put(key, tempVal + 1);
//				}
//			}
//		}
//		gStats.addStatBatch(hmGeo, hmReg);
//	}
	
	/**
	 * Adds a child location to this region, returning true if
	 * successful and false otherwise
	 * @param gL the GeographicalLocation to add
	 * @return true if the GeographicalLocation was added, false
	 * otherwise
	 */
	public boolean addChildLoc(GeographicalLocation gL) {
		if (childLocs.containsKey(gL.getId())) {
			return false;
		} else {
			childLocs.put(gL.getId(), gL);
			return true;
		}
	}
	
	/**
	 * Removes a child location based upon a unique id
	 * @param id the id of the GeographicalLocation to remove
	 * @return the GeographicalLocation removed, or null if
	 * not a child of this region
	 */
	public GeographicalLocation removeChildLoc(int id) {
		return childLocs.remove(id);
	}
	
	/**
	 * Returns a child location from its unique id
	 * @param id the unique id of the GeographicalLocation
	 * @return the GeographicalLocation object with the
	 * unique id
	 */
	public GeographicalLocation getChildLocById(int id) {
		return childLocs.get(id);
	}
	
	/**
	 * Returns a regional link from its unique id
	 * @param id the unique id of the RegionLink
	 * @return the RegionLink object with the unique id
	 */
	public RegionLink getPathById(int id) {
		return paths.get(id);
	}
	
	/**
	 * Returns the cardinal direction pointing to a regional
	 * link with the specified unique id
	 * @param id the unique id of the RegionLink
	 * @return the CardinalDirection representing the geographic
	 * relationship from this region towards the RegionLink. Returns
	 * CardinalDirection.ERR if no such id exists for this region
	 */
	public CardinalDirection getDirToPath(int id) {
		RegionLink temp = this.getPathById(id);
		if (temp != null) {
			return temp.getDirFromGeoReg(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	/**
	 * Returns the cardinal direction pointing from a regional
	 * link with the specified unique id
	 * @param id the unique id of the RegionLink
	 * @return the CardinalDirection representing the geographic
	 * relationship to this region from the RegionLink. Returns
	 * CardinalDirection.ERR if no such id exists for this region
	 */
	public CardinalDirection getDirFromPath(int id) {
		RegionLink temp = this.getPathById(id);
		if (temp != null) {
			return temp.getDirToGeoReg(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	/**
	 * Removes a regional link based upon a unique id
	 * @param id the id of the RegionalLink to remove
	 * @return the RegionalLink object removed, or null if
	 * not linked to this region
	 */
	public RegionLink removeRegionLink(int id) {
		return paths.remove(id);
	}
	
	/**
	 * Adds a regional link to this region, returning true
	 * if successfully added and false otherwise
	 * @param l the RegionLink to add
	 * @return true if successfully added to this region,
	 * false otherwise
	 */
	public boolean addRegionLink(RegionLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			return true;
		}
		return false;
	}

	/**
	 * Returns the name of this region
	 * @return the name of this region
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this region
	 * @param name the new name of this region
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the aspects of this region
	 * @return the aspects of this region
	 */
	public AspectManager getAspects() {
		return aspects;
	}

	/**
	 * Returns a HashMap of neighboring regional links,
	 * where the unique ids of the links are used as keys
	 * @return a HashMap of neighboring regional links
	 */
	public HashMap<Integer, RegionLink> getPaths() {
		return paths;
	}

	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getIdType() {
		return Constants.ID_REG;
	}
	
	/**
	 * Returns a HashMap of the child locations, where the
	 * unique ids of the locations are used as keys
	 * @return a HashMap of child GeographicalLocations
	 */
	public HashMap<Integer, GeographicalLocation> getChildLocs() {
		return childLocs;
	}
	
	/**
	 * Returns the game statistics for this region
	 * @return the game statistics for this region
	 */
	public GeographicalRegionStatsManager getGeoStats() {
		return gStats;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalRegion)) {
			return false;
		}
		GeographicalRegion gR = (GeographicalRegion)other;
		return this.id == gR.getId();
	}

	/**
	 * Returns the xCoord 
	 * @return the xCoord
	 */
	public int getxCoord() {
		return xCoord;
	}

	/**
	 * Returns the yCoord 
	 * @return the yCoord
	 */
	public int getyCoord() {
		return yCoord;
	}
}
