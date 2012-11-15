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

/**
 * 
 * @author SuperSimpleGuy
 */
public class RegionLink extends GeographicalRegion implements IUniqueId {

	private GeographicalRegion loc1;
	private CardinalDirection dirFrom1;
	private GeographicalRegion loc2;
	private CardinalDirection dirFrom2;
	private double length;
	
	private HashMap<Integer, CardinalDirection> dirFromPath;
	
	public RegionLink(String name,
			   int id,
			   int xCoord,
			   int yCoord,
			   AspectManager asp,
			  GeographicalRegion loc1,
			  CardinalDirection dirFrom1,
			  GeographicalRegion loc2,
			  CardinalDirection dirFrom2) {
		super(name, id, xCoord, yCoord, asp);
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = 0;
	}
	
	public RegionLink(String name,
			   int id,
			   int xCoord,
			   int yCoord,
			   AspectManager asp,
			   GeographicalRegion loc1,
			   CardinalDirection dirFrom1,
			   GeographicalRegion loc2,
			   CardinalDirection dirFrom2,
			   double length) {
		super(name, id, xCoord, yCoord, asp);
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = length;
	}
	
	public CardinalDirection getDirFromGeoReg(GeographicalRegion gR) {
		if (gR.equals(loc1)) {
			return dirFrom1;
		} else if (gR.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	public CardinalDirection getDirToGeoReg(GeographicalRegion gR) {
		if (gR.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gR.equals(loc2)) {
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
	public RegionLink removeRegionLink(int id) {
		if (!paths.containsKey(id)) {
			dirFromPath.remove(id);
			return paths.remove(id);
		}
		return null;
	}
	
	@Override
	public boolean addRegionLink(RegionLink l) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), CardinalDirection.ERR);
			return true;
		}
		return false;
	}
	
	public boolean addRegionLink(RegionLink l, CardinalDirection dir) {
		if (!paths.containsKey(l.getId())) {
			paths.put(l.getId(), l);
			dirFromPath.put(l.getId(), dir);
			return true;
		}
		return false;
	}
	
	public boolean changeRegLinkDir(RegionLink l, CardinalDirection dirNew) {
		if (dirFromPath.containsKey(l.getId())) {
			dirFromPath.remove(l.getId());
			dirFromPath.put(l.getId(), dirNew);
			return true;
		}
		return false;
	}

	public double getLength() {
		return length;
	}
	
	public double setLength(double length) {
		double temp = this.length;
		this.length = length;
		return temp;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public GeographicalRegion getLoc1() {
		return loc1;
	}

	public GeographicalRegion getLoc2() {
		return loc2;
	}

	@Override
	public String getIdType() {
		return Constants.ID_RLINK;
	}
}
