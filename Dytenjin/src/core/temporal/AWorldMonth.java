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
public abstract class AWorldMonth implements IHasAspects {

	private AWorldYear parent;
	private int value;
	private String name;
	private ArrayList<AWorldDay> childDays;
	protected AspectManager aspects;
	
	public AWorldMonth(int value,
			String name,
			AspectManager aspects) {
		this.parent = null;
		this.value = value;
		this.name = name;
		this.childDays = new ArrayList<AWorldDay>();
		this.aspects = aspects;
	}
	
	public boolean hasCalendar() {
		return this.parent != null && parent.hasCalendar();
	}
	
	public void setParent(AWorldYear parent) {
		this.parent = parent;
	}
	
	public boolean appendDay(AWorldDay day) {
		if (childDays.size() == 0 || day.getDayValue() == childDays.size() + childDays.get(0).getDayValue()) {
			childDays.add(day);
			return true;
		}
		return false;
	}
	
	public AWorldDay removeLastDay() {
		if (childDays.size() == 0) {
			return null;
		} else {
			return childDays.remove(childDays.size() - 1);
		}
	}
	
	public int getMonthValue() {
		return value;
	}
	
	public int getTotalNumDays() {
		return childDays.size();
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Returns seasonal aspects or unique aspects related to the
	 * month on this particular date
	 * @param day
	 * @return
	 */
	public abstract AspectManager getSpecialAspects(AWorldDay day);
	
	public ArrayList<AWorldDay> getHolidays() {
		ArrayList<AWorldDay> holidays = new ArrayList<AWorldDay>();
		for (AWorldDay d : childDays) {
			if (d.isHoliday()) {
				holidays.add(d);
			}
		}
		return holidays;
	}
	
	public boolean isDayValueHoliday(int dayValue) {
		if (dayValue < 0 || dayValue >= childDays.size()) {
			return false;
		}
		return childDays.get(dayValue).isHoliday();
	}
	
	public AWorldDay getDayByValue(int dayValue) {
		if (dayValue < 0 || dayValue >= childDays.size()) {
			return null;
		}
		return childDays.get(dayValue);
	}
	
	public AWorldYear getParent() {
		return parent;
	}
	
	public int getNumDaysBetween(AWorldMonth other) {
		if (parent == null) {
			return -1;
		}
		return parent.getNumDaysBetween(this, other);
	}
	
	public int getNumDaysBetween(AWorldDay first, AWorldDay second) {
		if (first.getParent() != this || second.getParent() != this || first.getDayValue() >= second.getDayValue()) {
			return -1;
		}
		return second.getDayValue() - first.getDayValue();
	}
	
	@Override
	public AspectManager getAspects() {
		return aspects;
	}
}
