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
public class WorldCompleteDate extends WorldDate {

	private AWorldTime currentTime;
	
	public WorldCompleteDate(WorldCalendar calendarName,
			AWorldYear currentYear,
			AWorldMonth currentMonth,
			AWorldDay currentDay,
			AWorldTime currentTime) {
		super(calendarName, currentYear, currentMonth, currentDay);
		this.currentTime = currentTime;
	}

	/**
	 * Returns the currentTime 
	 * @return the currentTime
	 */
	public AWorldTime getCurrentTime() {
		return currentTime;
	}
	
	@Override
	public WorldDate getDateAfterDuration(WorldTimeDuration duration) {
		WorldDate temp = super.getDateAfterDuration(duration);
		AWorldTime tempTime = currentTime.changeTime(duration);
		if (tempTime.getHour() >= currentTime.getHour()) {
			return new WorldCompleteDate(temp.getWorldCalendar(),
					temp.getCurrentYear(),
					temp.getCurrentMonth(),
					temp.getCurrentDay(),
					tempTime);
		}
		AWorldDay tempDay = temp.getCurrentMonth().getDayByValue(temp.getCurrentDay().getDayValue() + 1);
		if (tempDay != null) {
			return new WorldCompleteDate(temp.getWorldCalendar(),
					temp.getCurrentYear(),
					temp.getCurrentMonth(),
					tempDay,
					tempTime);
		}
		AWorldMonth tempMonth = temp.getCurrentYear().getMonthByValue(temp.getCurrentMonth().getMonthValue() + 1);
		if (tempMonth != null) {
			return new WorldCompleteDate(temp.getWorldCalendar(),
					temp.getCurrentYear(),
					tempMonth,
					tempMonth.getDayByValue(0),
					tempTime);
		}
		AWorldYear tempYear = temp.getWorldCalendar().getYearFromValue(temp.getCurrentYear().getYearValue() + 1);
		if (tempYear != null) {
			return new WorldCompleteDate(temp.getWorldCalendar(),
					tempYear,
					tempYear.getMonthByValue(0),
					tempYear.getMonthByValue(0).getDayByValue(0),
					tempTime);
		}
		return null;
	}
}
