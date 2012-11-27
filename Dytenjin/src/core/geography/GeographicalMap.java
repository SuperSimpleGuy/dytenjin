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

import core.management.game.IHasUniqueId;
import core.management.game.UniqueId;

/**
 * Maintains all geographical regions and regional links, and
 * maintain the evolution of the entire map.
 * @author SuperSimpleGuy
 */
public class GeographicalMap implements IHasUniqueId {

	private HashMap<Integer, GeographicalRegion> geoRegions;
	private HashMap<Integer, RegionLink> regLinks;
	private String name;
	private UniqueId id;
	
	/**
	 * Creates a new geographical map with a unique id
	 * @param id a unique id to identify this map
	 */
	public GeographicalMap(UniqueId id) {
		this.geoRegions = new HashMap<Integer, GeographicalRegion>();
		this.regLinks = new HashMap<Integer, RegionLink>();
		this.name = "";
		this.id = id;
	}
	
	/**
	 * Creates a new geographical map with a unique id and
	 * a name
	 * @param name the name of this geographical map
	 * @param id a unique id to identify this map
	 */
	public GeographicalMap(String name, UniqueId id) {
		this(id);
		this.name = name;
	}
	
	/**
	 * Puts a LocationLink between the two specified GeographicalLocations that belong to the
	 * specified GeographicalRegions, returning true upon the successful insertion or false
	 * otherwise. The LocationLink's parent is set to be the first GeographicalRegion. Note
	 * that the GeographicalLocations must already be registered with a GeographicalRegion,
	 * usually through a GeographicalMap
	 * @param locLink the LocationLink to insert
	 * @param gR1 the GeographicalRegion parent of the first GeographicalLocation
	 * @param gL1 the first GeographicalLocation to be linked
	 * @param gR2 the GeographicalRegion parent of the second GeographicalLocation
	 * @param gL2 the second GeographicalLocation to be linked
	 * @return true if successfully inserted, false otherwise
	 */
	public boolean putLocationLink(LocationLink locLink, GeographicalRegion gR1, GeographicalLocation gL1, GeographicalRegion gR2, GeographicalLocation gL2) {
		return this.putLocationLink(locLink, gR1.getUniqueId().getId(), gL1.getUniqueId().getId(), gR2.getUniqueId().getId(), gL2.getUniqueId().getId());
	}
	
	/**
	 * Puts a LocationLink between the two specified GeographicalLocations that belong to the
	 * specified GeographicalRegions, returning true upon the successful insertion or false
	 * otherwise. The LocationLink's parent is set to be the first GeographicalRegion. Note
	 * that the GeographicalLocations must already be registered with a GeographicalRegion,
	 * usually through a GeographicalMap
	 * @param locLink the LocationLink to insert
	 * @param idOfGR1 the id of the GeographicalRegion parent of the first GeographicalLocation
	 * @param idOfGL1 the id of the first GeographicalLocation to be linked
	 * @param idOfGR2 the id of the GeographicalRegion parent of the second GeographicalLocation
	 * @param idOfGL2 the id of the second GeographicalLocation to be linked
	 * @return true if successfully inserted, false otherwise
	 */
	public boolean putLocationLink(LocationLink locLink, int idOfGR1, int idOfGL1, int idOfGR2, int idOfGL2) {
		GeographicalRegion gR1 = geoRegions.get(idOfGR1);
		if (gR1 == null) {
			gR1 = regLinks.get(idOfGR1);
		}
		if (gR1 == null) {
			return false;
		}
		GeographicalRegion gR2 = null;
		if (idOfGR1 != idOfGR2) {
			gR2 = geoRegions.get(idOfGR1);
			if (gR2 == null) {
				gR2 = regLinks.get(idOfGR1);
			}
			if (gR2 == null) {
				return false;
			}
			return gR1.putEdgeLocLinkBetween(locLink, idOfGL1, idOfGR2, idOfGL2);
		} else {
			return gR1.putLocLinkBetween(locLink, idOfGL1, idOfGL2);
		}
	}
	
	/**
	 * Puts a LocationLink between the two specified GeographicalLocations, returning true
	 * upon the successful insertion or false otherwise. The LocationLink's parent is set to
	 * be the first GeographicalRegion. Note that the GeographicalLocations must already be
	 * registered with a GeographicalRegion, usually through a GeographicalMap
	 * @param locLink the LocationLink to insert
	 * @param gL1 the first GeographicalLocation to be linked
	 * @param gL2 the second GeographicalLocation to be linked
	 * @return true if successfully inserted, false otherwise
	 */
	public boolean putLocationLink(LocationLink locLink, GeographicalLocation gL1, GeographicalLocation gL2) {
		if (gL1.getParent() == null || gL2.getParent() == null) {
			return false;
		}
		return this.putLocationLink(locLink, gL1.getParent().getUniqueId().getId(), gL1.getUniqueId().getId(), gL2.getParent().getUniqueId().getId(), gL2.getUniqueId().getId());
	}
	
	/**
	 * Puts a LocationLink between the two specified GeographicalLocations, returning true
	 * upon the successful insertion or false otherwise. The LocationLink's parent is set to
	 * be the first GeographicalRegion. Note that the GeographicalLocations must already be
	 * registered with a GeographicalRegion, usually through a GeographicalMap. Additionally,
	 * this putLocationLink method is the only one that requires a search to find the parent
	 * id's as well.
	 * @param locLink the LocationLink to insert
	 * @param idOfGL1 the id of the first GeographicalLocation to be linked
	 * @param idOfGL2 the id of the second GeographicalLocation to be linked
	 * @return true if successfully inserted, false otherwise
	 */
	public boolean putLocationLink(LocationLink locLink, int idOfGL1, int idOfGL2) {
		int idOfGR1 = -1;
		int idOfGR2 = -1;
		for (GeographicalRegion gR : geoRegions.values()) {
			if (gR.getChildLocs().containsKey(idOfGL1)) {
				idOfGR1 = gR.getUniqueId().getId();
			} else if (gR.getChildLocs().containsKey(idOfGL2)) {
				idOfGR2 = gR.getUniqueId().getId();
			}
			if (idOfGR1 != -1 && idOfGR2 != -1) {
				break;
			}
		}
		if (idOfGR1 == -1 || idOfGR2 == -1) {
			for (GeographicalRegion gR : regLinks.values()) {
				if (gR.getChildLocs().containsKey(idOfGL1)) {
					idOfGR1 = gR.getUniqueId().getId();
				} else if (gR.getChildLocs().containsKey(idOfGL2)) {
					idOfGR2 = gR.getUniqueId().getId();
				}
				if (idOfGR1 != -1 && idOfGR2 != -1) {
					break;
				}
			}
		}
		if (idOfGR1 == -1 || idOfGR2 == -1) {
			return false;
		}
		return this.putLocationLink(locLink, idOfGR1, idOfGL1, idOfGR2, idOfGL2);
	}
	
	/**
	 * Removes the specified LocationLink from the GeographicalRegion
	 * @param locLink the LocationLink to remove
	 * @param gR the GeographicalRegion parent of the LocationLink to remove
	 * @return the LocationLink removed, null if unsuccessful
	 */
	public LocationLink removeLocationLink(LocationLink locLink, GeographicalRegion gR) {
		return this.removeLocationLink(locLink.getUniqueId().getId(), gR.getUniqueId().getId());
	}
	
	/**
	 * Removes the specified LocationLink from the GeographicalRegion
	 * @param idOfLL the id of the LocationLink to remove
	 * @param idOfGR the id of the GeographicalRegion parent of the
	 * LocationLink to remove
	 * @return the LocationLink removed, null if unsuccessful
	 */
	public LocationLink removeLocationLink(int idOfLL, int idOfGR) {
		GeographicalRegion gR = geoRegions.get(idOfGR);
		if (gR == null) {
			gR = regLinks.get(idOfGR);
		}
		if (gR == null) {
			return null;
		}
		return gR.removeLocationLink(idOfLL);
	}
	
	/**
	 * Removes the specified LocationLink from the GeographicalMap
	 * @param locLink the LocationLink to remove
	 * @return the LocationLink removed, or null if unsuccessful.
	 */
	public LocationLink removeLocationLink(LocationLink locLink) {
		if (locLink.getParent() == null) {
			return null;
		}
		return this.removeLocationLink(locLink.getUniqueId().getId(), locLink.getParent().getUniqueId().getId());
	}
	
	/**
	 * Removes a LocaitonLink with the specified id from this GeographicalMap. Note that
	 * this removeLocationLink method requires a search to complete
	 * @param idOfLL the id of the LocationLink to remove
	 * @return the LocationLink removed if successful, or null if unsuccessful
	 */
	public LocationLink removeLocationLink(int idOfLL) {
		int idOfGR = -1;
		for (GeographicalRegion gR : geoRegions.values()) {
			if (gR.getChildLocs().containsKey(idOfLL)) {
				idOfGR = gR.getUniqueId().getId();
				break;
			}
		}
		if (idOfGR == -1) {
			for (GeographicalRegion gR : regLinks.values()) {
				if (gR.getChildLocs().containsKey(idOfLL)) {
					idOfGR = gR.getUniqueId().getId();
					break;
				}
			}
		}
		if (idOfGR == -1) {
			return null;
		}
		return this.removeLocationLink(idOfLL, idOfGR);
	}
	
	/**
	 * Adds a GeographicalLocation to a GeographicalRegion. LocationLinks should
	 * not be added this way
	 * @param gL the GeographicalLocation to add
	 * @param gR the GeographicalRegion to receive a child location
	 * @return true if the add was successful, false otherwise
	 */
	public boolean registerGeoLocation(GeographicalLocation gL, GeographicalRegion gR) {
		return this.registerGeoLocation(gL, gR.getUniqueId().getId());
	}
	
	/**
	 * Adds a GeographicalLocation to a GeographicalRegion. LocationLinks should
	 * not be added this way
	 * @param gL the GeographicalLocation to add
	 * @param idOfGeoReg the id of the GeographicalRegion to receive a child location
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
	public GeographicalLocation unregisterGeoLocation(GeographicalRegion gR, GeographicalLocation gL) {
		return this.unregisterGeoLocation(gR.getUniqueId().getId(), gL.getUniqueId().getId());
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
	public GeographicalLocation unregisterGeoLocation(int idOfGR, int idOfGL) {
		GeographicalRegion gR = geoRegions.get(idOfGR);
		if (gR == null) {
			gR = regLinks.get(idOfGR);
		}
		if (gR != null && !(gR.getChildLocs().get(idOfGL) instanceof LocationLink)) {
			return gR.unregisterChildLoc(idOfGL);
		}
		return null;
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion. This searches all
	 * available regions to attempt to remove the location. LocationLinks should
	 * not be removed in this manner. Returns true if the removal is successful, and
	 * false otherwise
	 * @param gL the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public GeographicalLocation unregisterGeoLocation(GeographicalLocation gL) {
		return this.unregisterGeoLocation(gL.getUniqueId().getId());
	}
	
	/**
	 * Remove a GeographicalLocation from a GeographicalRegion based off of ids.
	 * LocationLinks should not be removed in this manner. This searches all
	 * available regions to attempt to remove the location. Returns true if the
	 * removal is successful, and false otherwise
	 * @param idOfGl the id of the GeographicalLocation that will be removed
	 * @return true if the removal is successful, false otherwise
	 */
	public GeographicalLocation unregisterGeoLocation(int idOfGL) {
		for (GeographicalRegion gR : geoRegions.values()) {
			GeographicalLocation temp = gR.getChildLocById(idOfGL);
			if (temp != null && !(temp instanceof LocationLink)) {
				return gR.unregisterChildLoc(idOfGL);
			} else if (temp != null && temp instanceof LocationLink) {
				return null;
			}
		}
		return null;
	}
	
	/**
	 * Adds a GeographicalRegion to this map, returning true if
	 * successful and false otherwise
	 * @param gR the GeographicalRegion to add
	 * @return true if the region was successfully added to this
	 * map, false otherwise
	 */
	public boolean registerGeoRegion(GeographicalRegion gR) {
		if (gR instanceof RegionLink) {
			return false;
		}
		if (geoRegions.containsKey(gR.getUniqueId().getId())) {
			return false;
		}
		geoRegions.put(gR.getUniqueId().getId(), gR);
		return true;
	}
	
	public GeographicalRegion unregisterGeoRegion(GeographicalRegion gR) {
		return this.unregisterGeoRegion(gR.getUniqueId().getId());
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
		if (gr instanceof RegionLink) {
			return null;
		}
		gr.isolateThisRegionLocationLinks();
		for (RegionLink rL : gr.getPaths().values()) {
			this.removeRegionLink(rL.getUniqueId().getId());
			gr.unregisterRegionLink(rL.getUniqueId().getId());
		}
		return gr;
	}
	
	public RegionLink removeRegionLink(RegionLink rL) {
		return this.removeRegionLink(rL.getUniqueId().getId());
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
		temp.getLoc1().unregisterRegionLink(temp.getUniqueId().getId());
		temp.getLoc2().unregisterRegionLink(temp.getUniqueId().getId());
		temp.isolateThisRegionLocationLinks();
		temp.setLoc1(null);
		temp.setLoc2(null);
		for (RegionLink rL : temp.getPaths().values()) {
			temp.unregisterRegionLink(rL.getUniqueId().getId());
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
		return this.putRegLinkBetween(freshRL, gR1.getUniqueId().getId(), gR2.getUniqueId().getId());
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
			if (r1.getPathById(freshRL.getUniqueId().getId()) != null) {
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
			if (r2.getPathById(freshRL.getUniqueId().getId()) != null) {
				return false;
			}
			rLink2 = true;
		} else {
			return false;
		}
		freshRL.setLoc1(r1);
		freshRL.setLoc2(r2);
		if (!(r1.registerRegionLink(freshRL) && r2.registerRegionLink(freshRL))) {
			r1.unregisterRegionLink(freshRL.getUniqueId().getId());
			r2.unregisterRegionLink(freshRL.getUniqueId().getId());
			return false;
		}
		if (rLink1 && !((RegionLink)r1).changeRegLinkDir(freshRL, freshRL.getDirFromGeoReg(idFirstGR).opposite())) {
			r1.unregisterRegionLink(freshRL.getUniqueId().getId());
			r2.unregisterRegionLink(freshRL.getUniqueId().getId());
			return false;
		}
		if (rLink2 && !((RegionLink)r2).changeRegLinkDir(freshRL, freshRL.getDirFromGeoReg(idSecondGR).opposite())) {
			r1.unregisterRegionLink(freshRL.getUniqueId().getId());
			r2.unregisterRegionLink(freshRL.getUniqueId().getId());
			return false;
		}
		regLinks.put(freshRL.getUniqueId().getId(), freshRL);
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
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalMap)) {
			return false;
		}
		GeographicalMap gM = (GeographicalMap)other;
		return this.id.equals(gM.getUniqueId());
	}

	@Override
	public UniqueId getUniqueId() {
		return id;
	}
	
}
