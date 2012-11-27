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
 * Methods required to keep track of the world time
 * @author SuperSimpleGuy
 */
public interface IWorldTime {

	/**
	 * Returns the current hour
	 * @return the current hour
	 */
	int getHour();
	
	/**
	 * Returns the number of hours in a day
	 * @return the number of hours in a day
	 */
	int getHoursPerDay();
	
	/**
	 * Returns true if this time is within the first half of
	 * the day, false otherwise
	 * @return true if this time is within the first half of
	 * the day, false otherwise
	 */
	boolean isFirstHalfDay();
	
	/**
	 * Returns the current subhour (e.g. minutes)
	 * @return the current subhour
	 */
	int getSubHour();
	
	/**
	 * Returns the number of subhours in an hour
	 * (e.g. 60 minutes in an hour)
	 * @return the number of subhours in an hour
	 */
	int getSubHoursPerHour();
	
	/**
	 * Returns the name of the hour singularly
	 * (e.g. "hour")
	 * @return the name of the hour singularly
	 */
	String getHourNameSing();
	
	/**
	 * Returns the name of the subhour singularly
	 * (e.g. "minute")
	 * @return the name of the subhour singularly
	 */
	String getSubHourNameSing();
	
	/**
	 * Returns the name of the hour pluraly
	 * (e.g. "hours")
	 * @return the name of the hour pluraly
	 */
	String getHourNamePlural();
	
	/**
	 * Returns the name of the subhour pluraly
	 * (e.g. "minutes")
	 * @return the name of the subhour pluraly
	 */
	String getSubHourNamePlural();
	
	/**
	 * Returns a new IWorldTime object representing the progression of
	 * time
	 * @param deltaHour the amount of time passed in hours
	 * @param deltaSubHour the amount of time passed in subhours
	 * @return a new IWorldTime object representing the new time
	 */
	IWorldTime changeTime(int deltaHour, int deltaSubHour);
	
	/**
	 * Returns an object representing the length of time between this
	 * time and another time
	 * @param other the ending time of the duration
	 * @return a duration representing a period of time from now until
	 * the specified ending time
	 */
	IWorldTimeDuration durationFromNowUntil(IWorldTime other);
	
	/**
	 * Represents aspects related to this particular time 
	 * @return aspects related to this particular time
	 */
	AspectManager getSpecialAspects();
	
	String getCalendarName();
	
	IWorldTimeDuration getDuration(IWorldTime other);
}
