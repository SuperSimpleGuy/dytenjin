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
import java.util.logging.Level;

import core.CoreConstants;
import core.management.game.IHasUniqueId;
import core.management.game.UniqueId;
import core.system.CoreLogfileManager;
import core.temporal.AWorldYear;

/**
 * Maintains the world-level calendar for both system mechanics,
 * game mechanics and possibly in-game mechanics.
 * @author SuperSimpleGuy
 */
public abstract class WorldCalendar implements IHasUniqueId {
	
	private ArrayList<AWorldYear> years;
	private String name;
	private UniqueId id;
	private int maxSubHr;
	private int maxHr;
	
	public WorldCalendar(UniqueId id, String name, int maxSubHr, int maxHr) {
		this.years = new ArrayList<AWorldYear>();
		this.name = name;
		this.id = id;
		this.maxHr = maxHr;
		this.maxSubHr = maxSubHr;
		CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_LOG_FILE, Level.INFO, this.getClass(), "WorldCalendar", "WorldCalendar constructed with no years.", new Object[] {id});
	}
	
	public boolean appendYear(AWorldYear year) {
		if (years.size() == 0 || year.getYearValue() == years.size() + years.get(0).getYearValue()) {
			years.add(year);
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "addYearToEnd", "Boolean method done.", true);
			return true;
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "addYearToEnd", "Boolean method done.", false);
		return false;
	}
	
	public AWorldYear removeYearFromEnd() {
		if (years.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "removeYearFromEnd", "IWorldYear method done, returning null");
			return null;
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "removeYearFromEnd", "IWorldYear method done, returning removed");
			return years.remove(years.size() - 1);
		}
	}
	
	public WorldDate getDateFromOtherCalendarDate(WorldDate otherDate, WorldCalendar otherCal) {
		int totalDays = otherCal.getTotalDaysToDate(otherDate);
		if (totalDays == -1) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "getDateFromOtherCalendarDate", "otherCal.getTotalDaysToDate(otherDate) returned -1", new Object[] {otherDate, otherCal});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromOtherCalendarDate", "WorldDate method done", null);
			return null;
		}
		WorldDate temp = this.getDateFromTotalDays(totalDays);
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromOtherCalendarDate", "WorldDate method done", new Object[] {temp});
		return temp;
	}
	
	public AWorldYear getYearFromValue(int yearVal) {
		if (years.size() == 0) {
			return null;
		}
		if (yearVal - years.get(0).getYearValue() < 0 || yearVal - years.get(0).getYearValue() >= years.size()) {
			return null;
		}
		return years.get(yearVal);
	}
	
	public WorldDate getDateFromTotalDays(int totalDays) {
		if (years.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "getDateFromTotalDays", "years.size() is zero", new Object[] {totalDays});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		int yearNumDays = 0;
		int index = 0;
		while (index < years.size() && totalDays > yearNumDays + years.get(index).getTotalNumDays()) {
			yearNumDays += years.get(index).getTotalNumDays();
			index++;
		}
		if (index == years.size()) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "getDateFromTotalDays", "totalDays refers to a date beyond this calendar's years", new Object[] {totalDays});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		AWorldYear tempYear = years.get(index);
		AWorldMonth[] months = (AWorldMonth[])years.get(index).getMonths().toArray();
		if (months.length == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "getDateFromTotalDays", "years.get(index).getMonths() returned a month array with size zero", new Object[] {totalDays, months});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		int monthNumDays = 0;
		index = 0;
		while (index < months.length && totalDays - yearNumDays > monthNumDays + months[index].getTotalNumDays()) {
			monthNumDays += months[index].getTotalNumDays();
			index++;
		}
		AWorldMonth tempMonth = months[index];
		AWorldDay tempDay = months[index].getDayByValue(totalDays - yearNumDays - monthNumDays);
		
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getDateFromTotalDays", "WorldDate method done, returning new WorldDate");
		return new WorldDate(this, tempYear, tempMonth, tempDay);
	}
	
	/**
	 * Returns the number of days in a world year
	 * @param yearVal the value of the year to get the number of
	 * days for
	 * @return the total number of days per world year
	 */
	public int numDaysInYear(int yearVal) {
		if (years.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "numDaysInYear", "years size is zero", new Object[] {yearVal});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "numDaysInYear", "Integer method done", -1);
			return -1;
		}
		int index = yearVal + years.get(0).getYearValue();
		if (index >= 0 && index < years.size() && years.get(index).getYearValue() == yearVal) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "numDaysInYear", "Integer method done", years.get(index).getTotalNumDays());
			return years.get(index).getTotalNumDays();
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "numDaysInYear", "yearVal not found or index outside bounds", new Object[] {yearVal, index});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "numDaysInYear", "Integer method done", -1);
			return -1;
		}
	}
	
	/**
	 * Returns a reference array of years contained within this calendar
	 * @return references to the years contained within this calendar
	 */
	public AWorldYear[] getYearsList() {
		AWorldYear[] temp = new AWorldYear[years.size()];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = years.get(i);
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getYearsList", "AWorldYear[] method done", temp);
		return temp;
	}
	
	/**
	 * Returns the name of this calendar
	 * @return the name of the calendar
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the total number of days from "year zero" to
	 * the most current year placed in the calendar
	 * @return the total number of days contained in the calendar
	 */
	public int getTotalDays() {
		int temp = 0;
		for (int i = 0; i < years.size(); i++) {
			temp += years.get(i).getTotalNumDays();
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getTotalDays", "Integer method done", temp);
		return temp;
	}
	
	public int getTotalDaysToDate(WorldDate otherDate) {
		if (otherDate.getWorldCalendar() == null || !otherDate.getWorldCalendar().getName().equals(name)) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(CoreConstants.SYS_ERR_FILE, Level.WARNING, this.getClass(), "getTotalDaysToDate", "otherDate's calendar name doesn't match or is null", new Object[] {otherDate, otherDate.getWorldCalendar()});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getTotalDaysToDate", "Integer method done", -1);
			return -1;
		}
		int temp = 0;
		for (int i = 0; i < otherDate.getCurrentYear().getYearValue() - years.get(0).getYearValue(); i++) {
			temp += years.get(i).getTotalNumDays();
		}
		for (AWorldMonth m : years.get(otherDate.getCurrentYear().getYearValue() - years.get(0).getYearValue()).getMonths()) {
			if (m.getMonthValue() < otherDate.getCurrentMonth().getMonthValue()) {
				temp += m.getTotalNumDays();
			} else if (m.getMonthValue() == otherDate.getCurrentMonth().getMonthValue()) {
				temp += otherDate.getCurrentDay().getDayValue();
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), CoreConstants.SYS_FINER_FILE, "getTotalDaysToDate", "Integer method done", temp);
		return temp;
	}
	
	public int getNumDaysBetween(AWorldYear first, AWorldYear second) {
		if (first.getParent() != this || second.getParent() != this || first.getYearValue() >= second.getYearValue()) {
			return -1;
		}
		int numDays = 0;
		for (int i = first.getYearValue() - years.get(0).getYearValue(); i < second.getYearValue() - years.get(0).getYearValue(); i++) {
			numDays += years.get(i).getTotalNumDays();
		}
		return numDays;
	}
	
	
	
	/**
	 * Returns the maxSubHr 
	 * @return the maxSubHr
	 */
	public int getMaxSubHr() {
		return maxSubHr;
	}

	/**
	 * Returns the maxHr 
	 * @return the maxHr
	 */
	public int getMaxHr() {
		return maxHr;
	}

	@Override
	public UniqueId getUniqueId() {
		return id;
	}
}
