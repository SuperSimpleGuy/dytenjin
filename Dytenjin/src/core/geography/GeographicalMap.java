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

/**
 * Maintains all geographical regions and regional links, and
 * maintain the evolution of the entire map.
 * @author SuperSimpleGuy
 */
public class GeographicalMap {

	private HashMap<Integer, GeographicalRegion> geoRegions;
	private HashMap<Integer, RegionLink> regLinks;
	private String name;
	private int id;
	
	/**
	 * Creates a new geographical map with a unique id
	 * @param id a unique id to identify this map
	 */
	public GeographicalMap(int id) {
		this.name = "";
		this.id = id;
	}
	
	/**
	 * Creates a new geographical map with a unique id and
	 * a name
	 * @param name the name of this geographical map
	 * @param id a unique id to identify this map
	 */
	public GeographicalMap(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	/**
	 * Adds a GeographicalLocation to a GeographicalRegion
	 * @param gL the GeographicalLocation to add
	 * @param idOfGeoReg the GeographicalRegion to receive a child location
	 * @return true if the add was successful, false otherwise
	 */
	public boolean addGeoLocation(GeographicalLocation gL, int idOfGeoReg) {
		GeographicalRegion temp = geoRegions.get(idOfGeoReg);
		if (temp == null) {
			return false;
		}
		temp.addChildLoc(gL);
		return true;
	}
	
	/**
	 * Adds a GeographicalRegion to this map, returning true if
	 * successful and false otherwise
	 * @param gR the GeographicalRegion to add
	 * @return true if the region was successfully added to this
	 * map, false otherwise
	 */
	public boolean addGeoRegion(GeographicalRegion gR) {
		if (geoRegions.containsKey(gR.getId())) {
			return false;
		}
		geoRegions.put(gR.getId(), gR);
		return true;
	}
	
	/**
	 * Removes a GeographicalRegion from this map based on the
	 * id of the region, returning null if no such region id existed
	 * for this map
	 * @param id the unique id of the region to be removed
	 * @return the removed region, or null if no such region exists
	 * in this map
	 */
	public GeographicalRegion removeGeoRegion(int id) {
		return geoRegions.remove(id);
	}
	
	/**
	 * Creates a regional link between two regions, returning the
	 * newly created link
	 * @param idFirstGR the unique id of the first GeographialRegion to link
	 * @param idSecondGr the unique id of the second GeographicalRegion to link
	 * @return the new RegionalLink between the GeographicalRegions
	 */
	public RegionLink createRegLinkBetween(int idFirstGR, int idSecondGr) {
		//TODO Add stuff here
		return null;
	}
	
	/**
	 * Removes a link between regions, or null if no such link exists with
	 * the given id
	 * @param id the unique of id the regional link to remove
	 * @return the RegionLink removed, or null if no such link existed
	 */
	public RegionLink removeRegLink(int id) {
		//TODO Add stuff here
		return null;
	}

	/**
	 * Gets the name of this map
	 * @return the name of this GeographicalMap
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this map
	 * @param name the new name of this GeographicalMap
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns a HashMap of the GeographicalRegions in this map
	 * @return a HashMap of the GeographicalRegions in this map
	 */
	public HashMap<Integer, GeographicalRegion> getGeoRegions() {
		return geoRegions;
	}

	/**
	 * Returns a HashMap of the regional links for this map
	 * @return a HashMap of the RegionLinks in this map
	 */
	public HashMap<Integer, RegionLink> getRegLinks() {
		return regLinks;
	}

	/**
	 * Returns this map's unique id
	 * @return this map's unique id
	 */
	public int getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalMap)) {
			return false;
		}
		GeographicalMap gM = (GeographicalMap)other;
		return this.id == gM.getId();
	}
	
}
