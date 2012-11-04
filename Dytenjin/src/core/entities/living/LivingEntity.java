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

package core.entities.living;

import core.entities.Entity;
import core.geography.GeographicalLocation;
import core.management.individual.AspectManager;

public class LivingEntity extends Entity {
	
	protected GeographicalLocation currentLoc;
	
	public LivingEntity(String s, int id) {
		super(s, id);
		currentLoc = null;
	}

	public GeographicalLocation getCurrentLoc() {
		return currentLoc;
	}

	public GeographicalLocation setCurrentLoc(GeographicalLocation currentLoc) {
		GeographicalLocation l = this.currentLoc;
		this.currentLoc = currentLoc;
		return l;
	}
	
	public String getName() {
		return name;
	}

	public String setName(String name) {
		String s = this.name;
		this.name = name;
		return s;
	}

	@Override
	public AspectManager getAspectMan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AspectManager setAspectMan(AspectManager aMan) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
