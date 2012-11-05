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

import core.management.individual.EnvironmentManager;
import core.management.individual.HistoryManager;
import core.management.individual.Terrain;
import core.stats.GeographicalRegionStatsManager;
import core.temporal.ITimeChanging;

/**
 * 
 * @author SuperSimpleGuy
 */
public abstract class GeographicalRegion implements ITimeChanging {
	
	private GeographicalRegionStatsManager gStats;
	private int id;
	private String name;
	private HistoryManager history;
	private Terrain terrain;
	private EnvironmentManager environment;
	
	protected HashMap<Integer, RegionLink> paths;
	protected HashMap<Integer, GeographicalLocation> childLocs;
	
	public GeographicalRegion (String name,
							   int id,
							   HistoryManager history,
							   Terrain terrain,
							   EnvironmentManager env) {
		this.id = id;
		this.name = name;
		this.history = history;
		this.terrain = terrain;
		this.environment = env;
		this.paths = new HashMap<Integer, RegionLink>();
		this.childLocs = new HashMap<Integer, GeographicalLocation>();
		this.gStats = new GeographicalRegionStatsManager();
	}
	
	public abstract void updateYear();
	
	public void updateMonth() {
		HashMap<String, Integer> hmGeo = new HashMap<String, Integer>();
		HashMap<String, Integer> hmReg = new HashMap<String, Integer>();
		for (GeographicalLocation gL : childLocs.values()) {
			String key = gL.getOwner().getName();
			if (gL instanceof LocationLink) {
				if (!hmReg.containsKey(key)) {
					hmReg.put(key, 1);
				} else {
					int tempVal = hmReg.get(key);
					hmReg.put(key, tempVal + 1);
				}
			} else {
				if (!hmGeo.containsKey(key)) {
					hmGeo.put(key, 1);
				} else {
					int tempVal = hmGeo.get(key);
					hmGeo.put(key, tempVal + 1);
				}
			}
		}
		gStats.addStatBatch(hmGeo, hmReg);
	}
	
	public abstract void updateWeek();
	
	public abstract void updateDay();
	
	public boolean addChildLoc(GeographicalLocation gL) {
		if (childLocs.containsKey(gL.getId())) {
			return false;
		} else {
			childLocs.put(gL.getId(), gL);
			return true;
		}
	}
	
	public GeographicalLocation removeChildLoc(int id) {
		if (childLocs.containsKey(id)) {
			return childLocs.remove(id);
		} else {
			return null;
		}
	}
	
	public GeographicalLocation getChildLocById(int id) {
		return childLocs.get(id);
	}
	
	public RegionLink getPathById(int id) {
		return paths.get(id);
	}
	
	public CardinalDirection getDirToPath(int id) {
		RegionLink temp = this.getPathById(id);
		if (temp != null) {
			return temp.getDirFromGeoReg(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public CardinalDirection getDirFromPath(int id) {
		RegionLink temp = this.getPathById(id);
		if (temp != null) {
			return temp.getDirToGeoReg(this);
		} else {
			return CardinalDirection.ERR;
		}
	}
	
	public RegionLink removeRegionLink(int id) {
		if (!paths.containsKey(id)) {
			return paths.remove(id);
		}
		return null;
	}
	
	public boolean addRegionLink(RegionLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HistoryManager getHistory() {
		return history;
	}

	public void setHistory(HistoryManager history) {
		this.history = history;
	}

	public Terrain getTerrain() {
		return terrain;
	}

	public void setTerrain(Terrain terrain) {
		this.terrain = terrain;
	}

	public EnvironmentManager getEnvironment() {
		return environment;
	}

	public void setEnvironment(EnvironmentManager environment) {
		this.environment = environment;
	}

	public HashMap<Integer, RegionLink> getPaths() {
		return paths;
	}

	public int getId() {
		return id;
	}
	
	public HashMap<Integer, GeographicalLocation> getChildLocs() {
		return childLocs;
	}
	
	public GeographicalRegionStatsManager getGeoStats() {
		return gStats;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalRegion)) {
			return false;
		}
		GeographicalRegion gR = (GeographicalRegion)other;
		return this.id == gR.getId();
	}
	
}
