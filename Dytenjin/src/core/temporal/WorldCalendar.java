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
	}
	
	public WorldCalendar(int id, String name, IWorldYear years) {
		this(id, name);
		this.years.add(years);
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
	}
	
	private void sortYears(IWorldYear[] years) {
		if (years.length == 0) {
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
	}
	
	public boolean isCalendarValid() {
		for (int i = 1; i < years.size(); i++) {
			if (years.get(i).getYearValue() != years.get(i-1).getYearValue() + 1) {
				return false;
			}
		}
		return true;
	}
	
	public boolean addYearToEnd(IWorldYear year) {
		if (years.size() == 0 || years.get(years.size() - 1).getYearValue() + 1 == year.getYearValue()) {
			years.add(year);
			return true;
		}
		return false;
	}
	
	public IWorldYear removeYearFromEnd() {
		if (years.size() == 0) {
			return null;
		} else {
			return years.remove(years.size() - 1);
		}
	}
	
	public WorldDate getDateFromOtherCalendarDate(WorldDate otherDate,
														  WorldCalendar otherCal) {
		int totalDays = otherCal.getTotalDaysToDate(otherDate);
		if (totalDays == -1) {
			return null;
		}
		return this.getDateFromTotalDays(totalDays);
	}
	
	public WorldDate getDateFromTotalDays(int totalDays) {
		if (years.size() == 0) {
			return null;
		}
		int yearNumDays = years.get(0).getTotalNumDays();
		int index = 1;
		while (index < years.size() && totalDays > yearNumDays + years.get(index).getTotalNumDays()) {
			yearNumDays += years.get(index).getTotalNumDays();
			index++;
		}
		if (index == years.size()) {
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
			return years.get(index).getTotalNumDays();
		} else {
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
		return temp;
	}
	
	public int getTotalDaysToDate(WorldDate otherDate) {
		if (!otherDate.getCalendarName().equals(name)) {
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
