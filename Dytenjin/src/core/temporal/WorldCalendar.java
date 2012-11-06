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
package core.temporal;

import java.util.ArrayList;

/**
 * Maintains the world-level calendar for both system mechanics,
 * game mechanics and possibly in-game mechanics.
 * @author SuperSimpleGuy
 */
public abstract class WorldCalendar {
	
	private ArrayList<IWorldYear> years;
	
	public WorldCalendar() {
		this.years = new ArrayList<IWorldYear>();
	}
	
	public WorldCalendar(IWorldYear years) {
		this();
		this.years.add(years);
	}
	
	
	
	/**
	 * Returns the number of days in a world year
	 * @return the total number of days per world year
	 */
	public int numDaysPerYear() {
		int sum = 0;
		for (int i = 0; i < ; i++) {
			
		}
		return sum;
	}
}
