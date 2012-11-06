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

/**
 * @author SuperSimpleGuy
 */
public class WorldCompleteDate {

	private IWorldYear currentYear;
	private IWorldMonth currentMonth;
	private IWorldDay currentDay;
	private IWorldTime currentTime;
	
	public WorldCompleteDate(IWorldYear currentYear,
			IWorldMonth currentMonth,
			IWorldDay currentDay,
			IWorldTime currentTime) {
		this.currentDay = currentDay;
		this.currentMonth = currentMonth;
		this.currentTime =currentTime;
		this.currentYear = currentYear;
	}
	
	

	/**
	 * Returns the currentYear 
	 * @return the currentYear
	 */
	public IWorldYear getCurrentYear() {
		return currentYear;
	}

	/**
	 * Returns the currentMonth 
	 * @return the currentMonth
	 */
	public IWorldMonth getCurrentMonth() {
		return currentMonth;
	}

	/**
	 * Returns the currentDay 
	 * @return the currentDay
	 */
	public IWorldDay getCurrentDay() {
		return currentDay;
	}

	/**
	 * Returns the currentTime 
	 * @return the currentTime
	 */
	public IWorldTime getCurrentTime() {
		return currentTime;
	}
	
	public void advanceTime(IWorldTimeDuration d) {
		
	}
}
