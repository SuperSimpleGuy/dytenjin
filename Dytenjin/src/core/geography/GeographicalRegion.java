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
import core.management.ingame.AspectManager;

/**
 * Manages a region of locations, and as a parent map. Maintains its
 * own set of aspects and region links between itself and neighboring
 * regions
 * @author SuperSimpleGuy
 */
public abstract class GeographicalRegion implements IHasUniqueId {
	
	private String name;
	private AspectManager aspects;
	private int xCoord;
	private int yCoord;
	private UniqueId id;
	
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
			UniqueId id,
			int xCoord,
			int yCoord,
			AspectManager asp) {
		this.id = id;
		this.name = name;
		this.aspects = asp;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		this.paths = new HashMap<Integer, RegionLink>();
		this.childLocs = new HashMap<Integer, GeographicalLocation>();
	}
	
	/**
	 * Adds a child GeographicalLocation to this region, returning true if
	 * successful and false otherwise. Note that passing in a LocationLink
	 * or one of its subtypes automatically returns false.
	 * @param gL the GeographicalLocation to add
	 * @return true if the GeographicalLocation was added, false
	 * otherwise
	 */
	public boolean registerChildLoc(GeographicalLocation gL) {
		if (gL instanceof LocationLink) {
			return false;
		}
		if (childLocs.containsKey(gL.getUniqueId().getId())) {
			return false;
		} else {
			childLocs.put(gL.getUniqueId().getId(), gL);
			gL.setParent(this);
			return true;
		}
	}
	
	/**
	 * Adds a linking LocationLink between a GeographicalLocation belonging to this region and
	 * a GeographicalLocation belonging to an adjacent GeographicalRegion as specified. If the
	 * child location cannot be found, cannot be successfully added, are trying to link other
	 * LocationLinks already linking with themselves, or are trying to link between this region
	 * and a non-adjacent region then this method will not succeed and will return false. Upon
	 * a successful insertion, returns true.
	 * @param newLL the new LocationLink to insert
	 * @param myLocation the id of the first GeographicalLocation to link
	 * @param neighborRegion the id of the neighboring GeographicalRegion
	 * @param neighborLocation the id of the second GeographicalLocation to link, belonging to
	 * the neighboring GeographicalRegion
	 * @return true when successfully inserted, false otherwise.
	 */
	public boolean putEdgeLocLinkBetween(LocationLink newLL, GeographicalLocation myLocation, GeographicalRegion neighborRegion, GeographicalLocation neighborLocation) {
		return this.putEdgeLocLinkBetween(newLL, myLocation.getUniqueId().getId(), neighborRegion.getUniqueId().getId(), neighborLocation.getUniqueId().getId());
	}
	
	/**
	 * Adds a linking LocationLink between a GeographicalLocation belonging to this region and
	 * a GeographicalLocation belonging to an adjacent GeographicalRegion as specified by the
	 * ids. If the child location cannot be found, cannot be successfully added, are trying to
	 * link other LocationLinks already linking with themselves, or are trying to link between
	 * this region and a non-adjacent region then this method will not succeed and will return
	 * false. Upon a successful insertion, returns true.
	 * @param newLL the new LocationLink to insert
	 * @param idFirstGL the id of the first GeographicalLocation to link
	 * @param idRegionParent the id of the neighboring GeographicalRegion
	 * @param idLocChild the id of the second GeographicalLocation to link, belonging to
	 * the neighboring GeographicalRegion
	 * @return true when successfully inserted, false otherwise.
	 */
	public boolean putEdgeLocLinkBetween(LocationLink newLL, int idFirstGL, int idRegionParent, int idLocChild) {
		if (idFirstGL == idLocChild) {
			return false;
		}
		GeographicalLocation r1 = null;
		boolean rLink1 = false;
		if (childLocs.containsKey(idFirstGL)) {
			r1 = childLocs.get(idFirstGL);
			if (r1 instanceof LocationLink) {
				rLink1 = true;
			}
			if (rLink1 && ((LocationLink)r1).getPathById(newLL.getUniqueId().getId()) != null) {
				return false;
			}
		} else {
			return false;
		}
		GeographicalRegion neighborParent = paths.get(idRegionParent);
		if (neighborParent == null) {
			return false;
		}
		GeographicalLocation r2 = neighborParent.getChildLocById(idLocChild);
		boolean rLink2 = false;
		if (r2 != null) {
			if (r2 instanceof LocationLink) {
				rLink2 = true;
			}
			if (rLink2 && ((LocationLink)r2).getPathById(newLL.getUniqueId().getId()) != null) {
				return false;
			}
		} else {
			return false;
		}
		if (!(r1.registerLocationLink(newLL) && r2.registerLocationLink(newLL))) {
			r1.unregisterLocationLink(newLL.getUniqueId().getId());
			r2.unregisterLocationLink(newLL.getUniqueId().getId());
			return false;
		}
		newLL.setParent(this);
		newLL.setLoc1(r1);
		newLL.setLoc2(r2);
		childLocs.put(newLL.getUniqueId().getId(), newLL);
		return true;
	}
	
	/**
	 * Adds a LocationLink between two GeographicalLocation children as specified by
	 * the specified object ids. If the children are not contained within this region,
	 * cannot be successfully added to the neighboring GeographicalLocation or are trying
	 * to link LocationLinks that are linking themselves, then this method will not
	 * succeed and return false
	 * @param newLL the new LocationLink to insert
	 * @param gL1 the first GeographicalLocation to link
	 * @param gL2 the second GeographicalLocation to link
	 * @return true when successfully inserted, false otherwise.
	 */
	public boolean putLocLinkBetween(LocationLink newLL, GeographicalLocation gL1, GeographicalLocation gL2) {
		return this.putLocLinkBetween(newLL, gL1.getUniqueId().getId(), gL2.getUniqueId().getId());
	}
	
	/**
	 * Adds a LocationLink between two GeographicalLocation children as specified by
	 * their ids. If the children are not contained within this region, cannot be
	 * successfully added to the neighboring GeographicalLocation or are trying
	 * to link LocationLinks that are linking themselves, then this method will not
	 * succeed and return false
	 * @param newLL the new LocationLink to insert
	 * @param idFirstGL the id of the first GeographicalLocation to link
	 * @param idSecondGL the id of the second GeographicalLocation to link
	 * @return true when successfully inserted, false otherwise.
	 */
	public boolean putLocLinkBetween(LocationLink newLL, int idFirstGL, int idSecondGL) {
		if (idFirstGL == idSecondGL) {
			return false;
		}
		GeographicalLocation r1 = null;
		boolean rLink1 = false;
		if (childLocs.containsKey(idFirstGL)) {
			r1 = childLocs.get(idFirstGL);
			if (r1 instanceof LocationLink) {
				rLink1 = true;
			}
			if (rLink1 && ((LocationLink)r1).getPathById(newLL.getUniqueId().getId()) != null) {
				return false;
			}
		} else {
			return false;
		}
		GeographicalLocation r2 = null;
		boolean rLink2 = false;
		if (childLocs.containsKey(idSecondGL)) {
			r2 = childLocs.get(idSecondGL);
			if (r2 instanceof LocationLink) {
				rLink2 = true;
			}
			if (rLink2 && ((LocationLink)r2).getPathById(newLL.getUniqueId().getId()) != null) {
				return false;
			}
		} else {
			return false;
		}
		if (!(r1.registerLocationLink(newLL) && r2.registerLocationLink(newLL))) {
			r1.unregisterLocationLink(newLL.getUniqueId().getId());
			r2.unregisterLocationLink(newLL.getUniqueId().getId());
			return false;
		}
		newLL.setParent(this);
		newLL.setLoc1(r1);
		newLL.setLoc2(r2);
		childLocs.put(newLL.getUniqueId().getId(), newLL);
		return true;
	}
	
	/**
	 * Removes a LocationLink from this GeographicalRegion by calling upon
	 * the LocationLink's removeSelfFromParent method, which removes any
	 * other LocationLinks that are linking with the one targeted for removal.
	 * @param l the LocationLink to remove
	 * @return the LocationLink removed, or null if no such link was removed.
	 */
	public LocationLink removeLocationLink(LocationLink l) {
		return this.removeLocationLink(l.getUniqueId().getId());
	}
	
	/**
	 * Removes a LocationLink from this GeographicalRegion by calling upon
	 * the LocationLink's removeSelfFromParent method, which removes any
	 * other LocationLinks that are linking with the one targeted for removal.
	 * @param id the id of the LocationLink to remove
	 * @return the LocationLink removed, or null if no such link was removed.
	 */
	public LocationLink removeLocationLink(int id) {
		GeographicalLocation temp = childLocs.get(id);
		if (temp == null || !(temp instanceof LocationLink)) {
			return null;
		}
		((LocationLink)temp).removeSelfFromParent();
		return (LocationLink)temp;
	}
	
	/**
	 * Removes a child location based upon a unique id. Also
	 * removes all associated LocationLinks connected to the
	 * GeographicalLcoation. Should not be called with a
	 * LocationLink object
	 * @param id the id of the GeographicalLocation to remove
	 * @return the GeographicalLocation removed, or null if
	 * not a child of this region
	 */
	public GeographicalLocation unregisterChildLoc(int id) {
		GeographicalLocation gL = childLocs.get(id);
		if ((gL instanceof LocationLink)) {
			return null;
		}
		gL.setParent(null);
		gL.removeSelfFromParent();
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
	 * Returns a regional link from its unique id, or null if no
	 * entry exists for that id
	 * @param id the unique id of the RegionLink
	 * @return the RegionLink object with the unique id, or null
	 * if no id was found
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
	public RegionLink unregisterRegionLink(int id) {
		return paths.remove(id);
	}
	
	/**
	 * Removes all edge LocationLinks that link this Region to
	 * neighboring regions. Retains any RegionLinks to neighbors,
	 * and all LocationLinks that link its own child locations
	 */
	public void isolateThisRegionLocationLinks() {
		for (GeographicalLocation gL : childLocs.values()) {
			if (gL instanceof LocationLink && ((LocationLink)gL).isEdge()) {
				gL.removeSelfFromParent();
			}
		}
	}
	
	/**
	 * Adds a regional link to this region, returning true
	 * if successfully added and false otherwise
	 * @param l the RegionLink to add
	 * @return true if successfully added to this region,
	 * false otherwise
	 */
	public boolean registerRegionLink(RegionLink l) {
		if (!paths.containsKey(l.getUniqueId().getId())) {
			paths.put(l.getUniqueId().getId(), l);
			return true;
		}
		return false;
	}
	
	@Override
	public UniqueId getUniqueId() {
		return id;
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
	
	/**
	 * Returns a HashMap of the child locations, where the
	 * unique ids of the locations are used as keys
	 * @return a HashMap of child GeographicalLocations
	 */
	public HashMap<Integer, GeographicalLocation> getChildLocs() {
		return childLocs;
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalRegion)) {
			return false;
		}
		GeographicalRegion gR = (GeographicalRegion)other;
		return this.id.equals(gR.getUniqueId());
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
	
	/**
	 * Sets the coordinates for this GeographicalLocation, which
	 * then updates the lengths of all adjacent paths.
	 * @param newXCoord the new x coordinate for this location
	 * @param newYCoord the new y coordinate for this location
	 */
	public void setCoords(int newXCoord, int newYCoord) {
		this.xCoord = newXCoord;
		this.yCoord = newYCoord;
		for (RegionLink rL : paths.values()) {
			rL.resetDirectionAndLength();
		}
	}
}
