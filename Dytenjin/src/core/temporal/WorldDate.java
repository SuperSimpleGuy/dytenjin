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
public class WorldDate {

	private IWorldYear currentYear;
	private IWorldMonth currentMonth;
	private IWorldDay currentDay;
	private String calendarName;
	
	public WorldDate(String calendarName,
			IWorldYear currentYear,
			IWorldMonth currentMonth,
			IWorldDay currentDay) {
		this.calendarName = calendarName;
		this.currentDay = currentDay;
		this.currentMonth = currentMonth;
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
	 * Returns the calendar name which this date applies to
	 * @return the calendar name which this date applies to
	 */
	public String getCalendarName() {
		return calendarName;
	}
	
}
