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

import core.Constants;
import core.management.game.IUniqueId;
import core.system.CoreLogfileManager;
import core.system.ExceptionManager;

/**
 * Maintains the world-level calendar for both system mechanics,
 * game mechanics and possibly in-game mechanics.
 * @author SuperSimpleGuy
 */
public abstract class WorldCalendar implements IUniqueId {
	
	private ArrayList<IWorldYear> years;
	private String name;
	private int id;
	
	public WorldCalendar(int id, String name) {
		this.years = new ArrayList<IWorldYear>();
		this.name = name;
		this.id = id;
		CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_LOG_FILE, Level.INFO, this.getClass().toString(), "WorldCalendar", "WorldCalendar constructed with no years.", new Object[] {id});
	}
	
	public WorldCalendar(int id, String name, IWorldYear years) {
		this(id, name);
		this.years.add(years);
		CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_LOG_FILE, Level.INFO, this.getClass().toString(), "WorldCalendar", "WorldCalendar constructed with a year.", new Object[] {id, years});
	}
	
	/**
	 * Creates a WorldCalendar from an array of years.
	 * @param years an array of years to generate a calendar from
	 */
	public WorldCalendar(int id, String name, IWorldYear[] years) {
		this(id, name);
		sortYears(years);
		for (IWorldYear y : years) {
			this.years.add(y);
		}
		CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_LOG_FILE, Level.INFO, this.getClass().toString(), "WorldCalendar", "WorldCalendar constructed with array of years.", new Object[] {id, years});
	}
	
	private void sortYears(IWorldYear[] years) {
		if (years.length == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_LOG_FILE, Level.FINEST, this.getClass().toString(), "sortYears", "Parameter of years has length zero.", years);
			return;
		}
		for (int finalPos = 0; finalPos < years.length - 1; finalPos++) {
			int index = finalPos;
			for (int checkIndex = finalPos + 1; checkIndex < years.length; checkIndex++) {
				if (years[checkIndex].getYearValue() < years[index].getYearValue()) {
					index = checkIndex;
				}
			}
			IWorldYear temp = years[finalPos];
			years[finalPos] = years[index];
			years[index] = temp;
			if (finalPos > 0 && years[finalPos].getYearValue() != years[finalPos-1].getYearValue() + 1) {
				ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new IllegalArgumentException(Constants.ERR_WORLDCAL_YEARS), Level.WARNING, Constants.SYS_ERR_FILE);
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "sortYears", "Void method done.");
	}
	
	public boolean isCalendarValid() {
		for (int i = 1; i < years.size(); i++) {
			if (years.get(i).getYearValue() != years.get(i-1).getYearValue() + 1) {
				ExceptionManager.SYS_EXCEPTION_MANAGER.throwException(new IllegalStateException(Constants.ERR_WORLDCAL_YEARS), Level.WARNING, Constants.SYS_ERR_FILE);
				CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "isCalendarValid", "Boolean method done.", false);
				return false;
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "isCalendarValid", "Boolean method done.", true);
		return true;
	}
	
	public boolean addYearToEnd(IWorldYear year) {
		if (years.size() == 0 || years.get(years.size() - 1).getYearValue() + 1 == year.getYearValue()) {
			years.add(year);
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "addYearToEnd", "Boolean method done.", true);
			return true;
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "addYearToEnd", "Boolean method done.", false);
		return false;
	}
	
	public IWorldYear removeYearFromEnd() {
		if (years.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "removeYearFromEnd", "IWorldYear method done, returning null");
			return null;
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "removeYearFromEnd", "IWorldYear method done, returning removed");
			return years.remove(years.size() - 1);
		}
	}
	
	public WorldDate getDateFromOtherCalendarDate(WorldDate otherDate,
														  WorldCalendar otherCal) {
		int totalDays = otherCal.getTotalDaysToDate(otherDate);
		if (totalDays == -1) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "getDateFromOtherCalendarDate", "otherCal.getTotalDaysToDate(otherDate) returned -1", new Object[] {otherDate, otherCal});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromOtherCalendarDate", "WorldDate method done", null);
			return null;
		}
		WorldDate temp = this.getDateFromTotalDays(totalDays);
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromOtherCalendarDate", "WorldDate method done", new Object[] {temp});
		return temp;
	}
	
	public WorldDate getDateFromTotalDays(int totalDays) {
		if (years.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "getDateFromTotalDays", "years.size() is zero", new Object[] {totalDays});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		int yearNumDays = years.get(0).getTotalNumDays();
		int index = 1;
		while (index < years.size() && totalDays > yearNumDays + years.get(index).getTotalNumDays()) {
			yearNumDays += years.get(index).getTotalNumDays();
			index++;
		}
		if (index == years.size()) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "getDateFromTotalDays", "totalDays refers to a date beyond this calendar's years", new Object[] {totalDays});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		IWorldYear tempYear = years.get(index).clone();
//		SILLY REFLECTION I DONT NEED YOU!
//		for (Constructor<?> c : years.get(index).getClass().getConstructors()) {
//			if (c.getParameterTypes().length == 1 && c.getParameterTypes()[0].equals(Integer.class)) {
//				try {
//					temp = (IWorldYear)c.newInstance(years.get(index).getYearValue());
//				} catch (Exception e) {
//					// Decide how exception handling is going to be done
//				}
//			}
//		}
//		if (temp == null) {
//			// Decide how exception handling is going to be done
//		}
		ArrayList<IWorldMonth> months = years.get(index).getMonths();
		if (months.size() == 0) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "getDateFromTotalDays", "years.get(index).getMonths() returned a month array with size zero", new Object[] {totalDays, months});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromTotalDays", "WorldDate method done, returning null");
			return null;
		}
		int monthNumDays = months.get(0).getNumDays();
		index = 1;
		while (index < months.size() && totalDays - yearNumDays > monthNumDays + months.get(index).getNumDays()) {
			monthNumDays += months.get(index).getNumDays();
			index++;
		}
		IWorldMonth tempMonth = months.get(index).clone();
		IWorldDay tempDay = months.get(index).getDayByValue(totalDays - yearNumDays - monthNumDays);
		
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getDateFromTotalDays", "WorldDate method done, returning new WorldDate");
		return new WorldDate(this.name, tempYear, tempMonth, tempDay);
	}
	
	/**
	 * Returns the number of days in a world year
	 * @param yearVal the value of the year to get the number of
	 * days for
	 * @return the total number of days per world year
	 */
	public int numDaysInYear(int yearVal) {
		int index = yearVal + years.get(0).getYearValue();
		if (index >= 0 && index < years.size() && years.get(index).getYearValue() == yearVal) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "numDaysInYear", "Integer method done", years.get(index).getTotalNumDays());
			return years.get(index).getTotalNumDays();
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "numDaysInYear", "yearVal not found or index outside bounds", new Object[] {yearVal, index});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "numDaysInYear", "Integer method done", -1);
			return -1;
		}
	}
	
	/**
	 * Returns a reference array of years contained within this calendar
	 * @return references to the years contained within this calendar
	 */
	public IWorldYear[] getYearsList() {
		IWorldYear[] temp = new IWorldYear[years.size()];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = years.get(i);
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getYearsList", "IWorldYear[] method done", temp);
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
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getTotalDays", "Integer method done", temp);
		return temp;
	}
	
	public int getTotalDaysToDate(WorldDate otherDate) {
		if (!otherDate.getCalendarName().equals(name)) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(this.getClass().toString(), Constants.SYS_ERR_FILE, Level.WARNING, this.getClass().toString(), "getTotalDaysToDate", "otherDate's calendar name doesn't match", new Object[] {otherDate});
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getTotalDaysToDate", "Integer method done", -1);
			return -1;
		}
		int temp = 0;
		for (int i = 0; i < otherDate.getCurrentYear().getYearValue()
				- years.get(0).getYearValue(); i++) {
			temp += years.get(i).getTotalNumDays();
		}
		for (IWorldMonth m : years.get(otherDate.getCurrentYear().getYearValue()
				- years.get(0).getYearValue()).getMonths()) {
			if (m.getMonthValue() < otherDate.getCurrentMonth().getMonthValue()) {
				temp += m.getNumDays();
			} else if (m.getMonthValue() == otherDate.getCurrentMonth().getMonthValue()) {
				temp += otherDate.getCurrentDay().getDayValue();
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass().toString(), Constants.SYS_LOG_FILE, "getTotalDaysToDate", "Integer method done", temp);
		return temp;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public String getIdType() {
		return Constants.ID_CAL;
	}
}
