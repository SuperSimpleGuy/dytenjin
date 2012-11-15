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

/**
 * Maintains all geographical regions and regional links, and
 * maintain the evolution of the entire map.
 * @author SuperSimpleGuy
 */
public class GeographicalMap implements IUniqueId {

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
	
	
	public boolean addLocationLink(LocationLink locLink, GeographicalLocation gL1, GeographicalLocation gL2) {
		return this.addLocationLink(locLink, gL1.getId(), gL2.getId());
	}
	
	public boolean addLocationLink(LocationLink locLink, int idOfGL1, int idOfGL2) {
		//TODO: Shit
		return true;
	}
	
	
	public boolean removeLocationLink(LocationLink locLink) {
		return this.removeLocationLink(locLink.getId());
	}
	
	public boolean removeLocationLink(int idOfLL) {
		//TODO: Shit
		return true;
	}
	
	/**
	 * Adds a GeographicalLocation to a GeographicalRegion. LocationLinks should
	 * not be added this way
	 * @param gL the GeographicalLocation to add
	 * @param idOfGeoReg the GeographicalRegion to receive a child location
	 * @return true if the add was successful, false otherwise
	 */
	public boolean registerGeoLocation(GeographicalLocation gL, GeographicalRegion gR) {
		return this.registerGeoLocation(gL, gR.getId());
	}
	
	/**
	 * Adds a GeographicalLocation to a GeographicalRegion. LocationLinks should
	 * not be added this way
	 * @param gL the GeographicalLocation to add
	 * @param idOfGeoReg the GeographicalRegion to receive a child location
	 * @return true if the add was successful, false otherwise
	 */
	public boolean registerGeoLocation(GeographicalLocation gL, int idOfGeoReg) {
		if (gL instanceof LocationLink) {
			return false;
		}
		GeographicalRegion temp = geoRegions.get(idOfGeoReg);
		if (temp == null) {
			return false;
		}
		return temp.registerChildLoc(gL);
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion. LocationLinks should
	 * not be removed in this manner. Returns true if the removal is successful, and
	 * false otherwise
	 * @param gR the GeographicalRegion that will have a Location removed from it
	 * @param gL the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean unregisterGeoLocation(GeographicalRegion gR, GeographicalLocation gL) {
		return this.unregisterGeoLocation(gR.getId(), gL.getId());
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion based off of ids.
	 * LocationLinks should not be removed in this manner. Returns true if the
	 * removal is successful, and false otherwise
	 * @param idOfGR the id of the GeographicalRegion that will have a Location
	 * removed from it
	 * @param idOfGL the id of the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean unregisterGeoLocation(int idOfGR, int idOfGL) {
		GeographicalRegion gR = geoRegions.get(idOfGR);
		if (gR == null) {
			gR = regLinks.get(idOfGR);
		}
		if (gR != null) {
			gR.unregisterChildLoc(idOfGL);
			return true;
		}
		return false;
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion. This searches all
	 * available regions to attempt to remove the location. LocationLinks should
	 * not be removed in this manner. Returns true if the removal is successful, and
	 * false otherwise
	 * @param gL the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean unregisterGeoLocation(GeographicalLocation gL) {
		return this.unregisterGeoLocation(gL.getId());
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion based off of ids.
	 * LocationLinks should not be removed in this manner. This searches all
	 * available regions to attempt to remove the location. Returns true if the
	 * removal is successful, and false otherwise
	 * @param idOfGl the id of the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean unregisterGeoLocation(int idOfGL) {
		for (GeographicalRegion gR : geoRegions.values()) {
			if (gR.getChildLocById(idOfGL) != null) {
				gR.unregisterChildLoc(idOfGL);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Adds a GeographicalRegion to this map, returning true if
	 * successful and false otherwise
	 * @param gR the GeographicalRegion to add
	 * @return true if the region was successfully added to this
	 * map, false otherwise
	 */
	public boolean registerGeoRegion(GeographicalRegion gR) {
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
	public GeographicalRegion unregisterGeoRegion(int id) {
		GeographicalRegion gr = geoRegions.remove(id);
		if (gr == null) {
			return null;
		}
		gr.isolateThisRegionLocationLinks();
		for (RegionLink rL : gr.getPaths().values()) {
			this.removeRegionLink(rL.getId());
			gr.unregisterRegionLink(rL.getId());
		}
		return gr;
	}
	
	/**
	 * Removes a link between regions, or null if no such link exists with
	 * the given id. Also isolates the GeographicalLocations that belong
	 * to the RegionLink from the rest of the map.
	 * @param id the unique of id the regional link to remove
	 * @return the RegionLink removed, or null if no such link existed
	 */
	public RegionLink removeRegionLink(int id) {
		RegionLink temp = regLinks.remove(id);
		if (temp == null) {
			return null;
		}
		temp.getLoc1().unregisterRegionLink(temp.getId());
		temp.getLoc2().unregisterRegionLink(temp.getId());
		temp.isolateThisRegionLocationLinks();
		temp.setLoc1(null);
		temp.setLoc2(null);
		for (RegionLink rL : temp.getPaths().values()) {
			temp.unregisterRegionLink(rL.getId());
		}
		return temp;
	}
	
	/**
	 * Puts a regional link between two regions, returning true if successfully
	 * inserted and false otherwise. None, some or both of these Geographical
	 * Regions that the link will be inserted between can be Region Links.
	 * @param freshRL the new RegionLink to insert between two regions
	 * @param gR1 the first GeographialRegion to link
	 * @param gR2 the second GeographicalRegion to link
	 * @return true when the RegionLink was successfully inserted between the
	 * Geographical Regions, false if no such regions exist for this map or when
	 * one of the GeoRegions is an instance of a RegionLink and already links to
	 * the new RegionLink trying to be inserted (prevents double-pathing)
	 */
	public boolean putRegLinkBetween(RegionLink freshRL, GeographicalRegion gR1, GeographicalRegion gR2) {
		return this.putRegLinkBetween(freshRL, gR1.getId(), gR2.getId());
	}
	
	/**
	 * Puts a regional link between two regions, returning true if successfully
	 * inserted and false otherwise. None, some or both of these Geographical
	 * Regions that the link will be inserted between can be Region Links.
	 * @param freshRL the new RegionLink to insert between two regions
	 * @param idFirstGR the unique id of the first GeographialRegion to link
	 * @param idSecondGr the unique id of the second GeographicalRegion to link
	 * @return true when the RegionLink was successfully inserted between the
	 * Geographical Regions, false if no such regions exist for this map or when
	 * one of the GeoRegions is an instance of a RegionLink and already links to
	 * the new RegionLink trying to be inserted (prevents double-pathing)
	 */
	public boolean putRegLinkBetween(RegionLink freshRL, int idFirstGR, int idSecondGR) {
		if (idFirstGR == idSecondGR) {
			return false;
		}
		GeographicalRegion r1 = null;
		boolean rLink1 = false;
		if (geoRegions.containsKey(idFirstGR)) {
			r1 = geoRegions.get(idFirstGR);
		} else if (regLinks.containsKey(idFirstGR)) {
			r1 = regLinks.get(idFirstGR);
			if (r1.getPathById(freshRL.getId()) != null) {
				return false;
			}
			rLink1 = true;
		} else {
			return false;
		}
		GeographicalRegion r2 = null;
		boolean rLink2 = false;
		if (geoRegions.containsKey(idSecondGR)) {
			r2 = geoRegions.get(idSecondGR);
		} else if (regLinks.containsKey(idSecondGR)) {
			r2 = regLinks.get(idFirstGR);
			if (r2.getPathById(freshRL.getId()) != null) {
				return false;
			}
			rLink2 = true;
		} else {
			return false;
		}
		freshRL.setLoc1(r1);
		freshRL.setLoc2(r2);
		if (!(r1.registerRegionLink(freshRL) && r2.registerRegionLink(freshRL))) {
			r1.unregisterRegionLink(freshRL.getId());
			r2.unregisterRegionLink(freshRL.getId());
			return false;
		}
		if (rLink1 && !((RegionLink)r1).changeRegLinkDir(freshRL, freshRL.getDirFromGeoReg(idFirstGR).opposite())) {
			r1.unregisterRegionLink(freshRL.getId());
			r2.unregisterRegionLink(freshRL.getId());
			return false;
		}
		if (rLink2 && !((RegionLink)r2).changeRegLinkDir(freshRL, freshRL.getDirFromGeoReg(idSecondGR).opposite())) {
			r1.unregisterRegionLink(freshRL.getId());
			r2.unregisterRegionLink(freshRL.getId());
			return false;
		}
		regLinks.put(freshRL.getId(), freshRL);
		return true;
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

	@Override
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

	@Override
	public String getIdType() {
		return Constants.ID_MAP;
	}
	
}
