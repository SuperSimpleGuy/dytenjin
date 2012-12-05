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

import java.util.logging.Level;

import core.Constants;
import core.system.CoreLogfileManager;
import core.system.ExceptionManager;

/**
 * @author SuperSimpleGuy
 */
public class WorldDate implements Comparable<WorldDate> {

	private AWorldYear currentYear;
	private AWorldMonth currentMonth;
	private AWorldDay currentDay;
	private WorldCalendar cal;
	
	public WorldDate(WorldCalendar cal,
			AWorldYear currentYear,
			AWorldMonth currentMonth,
			AWorldDay currentDay) {
		if (!currentYear.hasCalendar() ||
				!currentMonth.hasCalendar() ||
				!currentDay.hasCalendar() ||
				!currentYear.getParent().getName().equals(cal.getName()) ||
				!currentMonth.getParent().getParent().getName().equals(cal.getName()) ||
				!currentDay.getParent().getParent().getParent().getName().equals(cal.getName())) {
			ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new IllegalArgumentException("WorldDate created with mismatched day, month, and years from differing calendars, or not apart of a calendar"), Level.SEVERE, Constants.SYS_ERR_FILE);
		}
		this.cal = cal;
		this.currentDay = currentDay;
		this.currentMonth = currentMonth;
		this.currentYear = currentYear;
	}

	/**
	 * Returns the currentYear 
	 * @return the currentYear
	 */
	public AWorldYear getCurrentYear() {
		return currentYear;
	}

	/**
	 * Returns the currentMonth 
	 * @return the currentMonth
	 */
	public AWorldMonth getCurrentMonth() {
		return currentMonth;
	}

	/**
	 * Returns the currentDay 
	 * @return the currentDay
	 */
	public AWorldDay getCurrentDay() {
		return currentDay;
	}
	
	/**
	 * Returns the calendar which this date applies to
	 * @return the calendar which this date applies to
	 */
	public WorldCalendar getWorldCalendar() {
		return cal;
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof WorldDate) || !this.getWorldCalendar().getName().equals(((WorldDate)o).getWorldCalendar().getName())) {
			return false;
		}
		WorldDate date = (WorldDate) o;
		return this.getCurrentYear().getYearValue() == date.getCurrentYear().getYearValue() &&
				this.getCurrentMonth().getMonthValue() == date.getCurrentMonth().getMonthValue() &&
				this.getCurrentDay().getDayValue() == date.getCurrentDay().getDayValue();
	}
	
	@Override
	public int compareTo(WorldDate o) {
		if (!o.getWorldCalendar().getName().equals(this.getWorldCalendar().getName())) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithoutParams(Constants.SYS_LOG_FILE, Level.WARNING, this.getClass(), "compareTo", "Cannot compare WorldDates belonging to different calendars.");
			return 0;
		}
		
		if (this.currentYear.getYearValue() < o.getCurrentYear().getYearValue()) {
			return -1;
		} else if (this.currentYear.getYearValue() > o.getCurrentYear().getYearValue()) {
			return 1;
		}
		
		if (this.currentMonth.getMonthValue() < o.getCurrentMonth().getMonthValue()) {
			return -1;
		} else if (this.currentMonth.getMonthValue() > o.getCurrentMonth().getMonthValue()) {
			return 1;
		}
		
		if (this.currentDay.getDayValue() < o.getCurrentDay().getDayValue()) {
			return -1;
		} else if (this.currentDay.getDayValue() > o.getCurrentDay().getDayValue()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int getDaysBetween(WorldDate other) {
		if (!other.getWorldCalendar().getName().equals(this.getWorldCalendar().getName())) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithoutParams(Constants.SYS_LOG_FILE, Level.WARNING, this.getClass(), "getTimeBetween", "Cannot get time between WorldDates belonging to different calendars.");
			return -1;
		}
		int temp = this.getCurrentYear().getNumDaysBetween(other.getCurrentYear());
		temp += this.getCurrentMonth().getNumDaysBetween(other.getCurrentMonth());
		temp += this.getCurrentDay().getNumDaysBetween(other.getCurrentDay());
		return temp;
	}
	
	public WorldDate getDateAfterDuration(WorldTimeDuration duration) {
		return cal.getDateFromTotalDays(cal.getTotalDaysToDate(this) + duration.getDayDuration());
	}
}
