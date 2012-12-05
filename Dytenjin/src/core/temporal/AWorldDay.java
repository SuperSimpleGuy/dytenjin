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

import core.management.ingame.AspectManager;

/**
 * @author SuperSimpleGuy
 */
public abstract class AWorldDay {

	private AWorldMonth parent;
	private int value;
	private String name;
	protected AspectManager aspects;
	
	public AWorldDay(int value,
			String name,
			AspectManager aspects) {
		this.parent = null;
		this.value = value;
		this.name = name;
		this.aspects = aspects;
	}
	
	public boolean hasCalendar() {
		return this.parent != null && parent.hasCalendar();
	}
	
	public void setParent(AWorldMonth parent) {
		this.parent = parent;
	}
	
	public int getDayValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public AWorldMonth getParent() {
		return parent;
	}
	
	public abstract boolean isHoliday();
	public abstract String getHolidayName();
	public abstract AspectManager getSpecialAspects(AWorldTime time);
	
	public String getCalendarName() {
		if (parent != null && parent.getParent() != null && parent.getParent().getParent() != null) {
			return parent.getParent().getParent().getName();
		}
		return null;
	}
	
	public int getNumDaysBetween(AWorldDay other) {
		if (parent == null) {
			return -1;
		}
		return parent.getNumDaysBetween(this, other);
	}
}
