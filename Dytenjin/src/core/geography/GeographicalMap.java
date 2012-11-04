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

import core.temporal.CalendarDate;
import core.temporal.TimeChanging;

public class GeographicalMap implements TimeChanging {

	private HashMap<Integer, GeographicalRegion> geoRegions;
	private HashMap<Integer, RegionLink> regLinks;
	private String name;
	private int id;
	
	public GeographicalMap(int id) {
		this.name = "";
		this.id = id;
	}
	
	public GeographicalMap(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public boolean addGeoLocation(GeographicalLocation gL, int idOfGeoReg) {
		GeographicalRegion temp = geoRegions.get(idOfGeoReg);
		if (temp == null) {
			return false;
		}
		temp.addChildLoc(gL);
		return true;
	}
	
	public boolean addGeoRegion(GeographicalRegion gR) {
		if (geoRegions.containsKey(gR.getId())) {
			return false;
		}
		geoRegions.put(gR.getId(), gR);
		return true;
	}
	
	public GeographicalRegion removeGeoRegion(int id) {
		if (!geoRegions.containsKey(id)) {
			return null;
		}
		return geoRegions.remove(id);
	}
	
	public RegionLink createRegLinkBetween(int idFirstGR, int idSecondGr) {
		
		return null;
	}
	
	public RegionLink removeRegLink(int id) {
		
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<Integer, GeographicalRegion> getGeoRegions() {
		return geoRegions;
	}

	public HashMap<Integer, RegionLink> getRegLinks() {
		return regLinks;
	}

	public int getId() {
		return id;
	}
	
	public boolean equals(Object other) {
		if (!(other instanceof GeographicalMap)) {
			return false;
		}
		GeographicalMap gM = (GeographicalMap)other;
		return this.id == gM.getId();
	}

	@Override
	public void updateDay(CalendarDate d) {
		// TODO Auto-generated method stub
		
	}
	
}
