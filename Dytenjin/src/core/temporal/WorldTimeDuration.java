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
public class WorldTimeDuration {

	private int dayDur;
	private int hrDur;
	private int subHrDur;
	private WorldCalendar cal;
	
	public WorldTimeDuration(WorldDate start, WorldDate end) {
		if (start.getWorldCalendar().getName().equals(end.getWorldCalendar().getName())) {	
			if (start instanceof WorldCompleteDate && end instanceof WorldCompleteDate) {
				hrDur = ((WorldCompleteDate)end).getCurrentTime().getHour() - ((WorldCompleteDate)start).getCurrentTime().getHour();
				subHrDur = ((WorldCompleteDate)end).getCurrentTime().getSubHour() - ((WorldCompleteDate)start).getCurrentTime().getSubHour();
			} else {
				hrDur = 0;
				subHrDur = 0;
			}
			dayDur = start.getDaysBetween(end);
			this.cal = start.getWorldCalendar();
		}
	}
	
	public WorldTimeDuration(WorldCalendar calendar,
			int dayDur,
			int hrDur,
			int subHrDur) {
		this.cal = calendar;
		this.dayDur = dayDur;
		this.hrDur = hrDur;
		this.subHrDur = subHrDur;
		convertDurationsDown();
		convertDurationsUp();
	}
	
	private void convertDurationsDown() {
		while (subHrDur < 0) {
			hrDur--;
			subHrDur += cal.getMaxSubHr() - 1;
		}
		while (hrDur < 0) {
			dayDur--;
			hrDur += cal.getMaxHr() - 1;
		}
	}
	
	private void convertDurationsUp() {
		while (subHrDur >= cal.getMaxSubHr()) {
			hrDur++;
			subHrDur -= cal.getMaxSubHr() ;
		}
		while (hrDur >= cal.getMaxHr()) {
			dayDur++;
			hrDur -= cal.getMaxHr();
		}
	}
	
	public boolean isNonNegative() {
		return dayDur >= 0 && hrDur >= 0 && subHrDur >= 0;
	}
	
	public int getDayDuration() {
		return dayDur;
	}
	
	public int getHourDuration() {
		return hrDur;
	}
	
	public int getSubHourDuration() {
		return subHrDur;
	}
	
	public void addDuration(WorldTimeDuration other) {
		this.dayDur += other.getDayDuration();
		this.hrDur += other.getHourDuration();
		this.subHrDur += other.getSubHourDuration();
		convertDurationsUp();
	}
	
	public void decreaseDuration(WorldTimeDuration other) {
		this.dayDur -= other.getDayDuration();
		this.hrDur -= other.getHourDuration();
		this.subHrDur -= other.getSubHourDuration();
		convertDurationsDown();
	}
	
	public boolean longerThanOther(WorldTimeDuration other) {
		if (this.dayDur > other.getDayDuration()) {
			return true;
		} else if (this.dayDur < other.getDayDuration()) {
			return false;
		}
		if (this.hrDur > other.getHourDuration()) {
			return true;
		} else if (this.hrDur < other.getHourDuration()) {
			return false;
		}
		if (this.subHrDur > other.getSubHourDuration()) {
			return true;
		}
		return false;
	}
	
}
