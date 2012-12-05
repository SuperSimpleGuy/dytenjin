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

import core.management.ingame.AspectManager;
import core.management.ingame.IHasAspects;

/**
 * @author SuperSimpleGuy
 */
public abstract class AWorldYear implements IHasAspects {

	private WorldCalendar parent;
	private int value;
	private String name;
	private ArrayList<AWorldMonth> childMonths;
	protected AspectManager aspects;
	
	public AWorldYear(int value,
			String name,
			AspectManager aspects) {
		this.parent = null;
		this.value = value;
		this.name = name;
		this.childMonths = new ArrayList<AWorldMonth>();
		this.aspects = aspects;
	}
	
	public boolean hasCalendar() {
		return this.parent != null;
	}
	
	public void setParent(WorldCalendar parent) {
		this.parent = parent;
	}
	
	public boolean appendMonth(AWorldMonth month) {
		if (childMonths.size() == 0 || month.getMonthValue() == childMonths.size() + childMonths.get(0).getMonthValue()) {
			childMonths.add(month);
			return true;
		}
		return false;
	}
	
	public AWorldMonth getMonthByValue(int monthValue) {
		if (monthValue < 0 || monthValue >= childMonths.size()) {
			return null;
		}
		return childMonths.get(monthValue);
	}
	
	public AWorldMonth removeLastMonth() {
		if (childMonths.size() == 0) {
			return null;
		} else {
			return childMonths.remove(childMonths.size() - 1);
		}
	}
	
	/**
	 * Returns the unique value of the year, so there can't be
	 * two year 5134s for example
	 * @return the unique value of this year object
	 */
	public int getYearValue() {
		return value;
	}
	
	/**
	 * Returns the number of months within this year
	 * @return the number of months within this year
	 */
	public int getNumMonths() {
		return childMonths.size();
	}
	
	/**
	 * Returns the total number of days for this year
	 * @return the total number of days for this year
	 */
	public int getTotalNumDays() {
		int temp = 0;
		for (AWorldMonth m : childMonths) {
			temp += m.getTotalNumDays();
		}
		return temp;
	}
	
	/**
	 * Returns a name for the year (e.g. "Year of the Horse")
	 * @return a name for the year
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns special aspects related to a particular time of
	 * the month for this year
	 * @param month the month to determine special aspects of
	 * @return special aspects this year due to the month
	 */
	public abstract AspectManager getSpecialAspects(AWorldMonth month);
	
	/**
	 * Returns a list of all the months for this year
	 * @return a list of all the IWorldMonths for this year
	 */
	public ArrayList<AWorldMonth> getMonths() {
		return childMonths;
	}
	
	public WorldCalendar getParent() {
		return parent;
	}
	
	public int getNumDaysBetween(AWorldYear other) {
		if (parent == null) {
			return -1;
		}
		return parent.getNumDaysBetween(this, other);
	}
	
	public int getNumDaysBetween(AWorldMonth first, AWorldMonth second) {
		if (first.getParent() != this || second.getParent() != this || first.getMonthValue() >= second.getMonthValue()) {
			return -1;
		}
		int numDays = 0;
		for (int i = first.getMonthValue() - childMonths.get(0).getMonthValue(); i < second.getMonthValue() - childMonths.get(0).getMonthValue(); i++) {
			numDays += childMonths.get(i).getTotalNumDays();
		}
		return numDays;
	}
	
	@Override
	public AspectManager getAspects() {
		return aspects;
	}
}
