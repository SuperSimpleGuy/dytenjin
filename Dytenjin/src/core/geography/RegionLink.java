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

import core.Constants;
import core.management.game.IUniqueId;

/**
 * 
 * @author SuperSimpleGuy
 */
public class RegionLink implements IUniqueId {

	private GeographicalRegion loc1;
	private CardinalDirection dirFrom1;
	private GeographicalRegion loc2;
	private CardinalDirection dirFrom2;
	private int length;
	private int id;
	
	public RegionLink(int id,
			  GeographicalRegion loc1,
			  CardinalDirection dirFrom1,
			  GeographicalRegion loc2,
			  CardinalDirection dirFrom2) {
		this.id = id;
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = 0;
	}
	
	public RegionLink(int id,
					  GeographicalRegion loc1,
					  CardinalDirection dirFrom1,
					  GeographicalRegion loc2,
					  CardinalDirection dirFrom2,
					  int length) {
		this.id = id;
		this.loc1 = loc1;
		this.dirFrom1 = dirFrom1;
		this.loc2 = loc2;
		this.dirFrom2 = dirFrom2;
		this.length = length;
	}
	
	public CardinalDirection getDirFromGeoReg(GeographicalRegion gL) {
		if (gL.equals(loc1)) {
			return dirFrom1;
		} else if (gL.equals(loc2)) {
			return dirFrom2;
		}
		return CardinalDirection.ERR;
	}
	
	public CardinalDirection getDirToGeoReg(GeographicalRegion gL) {
		if (gL.equals(loc1)) {
			return dirFrom1.opposite();
		} else if (gL.equals(loc2)) {
			return dirFrom2.opposite();
		}
		return CardinalDirection.ERR;
	}

	public CardinalDirection getDirFrom1() {
		return dirFrom1;
	}

	public void setDirFrom1(CardinalDirection dirFrom1) {
		this.dirFrom1 = dirFrom1;
	}

	public CardinalDirection getDirFrom2() {
		return dirFrom2;
	}

	public void setDirFrom2(CardinalDirection dirFrom2) {
		this.dirFrom2 = dirFrom2;
	}

	public int getLength() {
		return length;
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
	public int getId() {
		return id;
	}

	@Override
	public String getIdType() {
		return Constants.ID_RLINK;
	}
}
