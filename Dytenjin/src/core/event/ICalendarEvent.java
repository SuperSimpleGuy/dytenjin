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
package core.event;

import core.temporal.IWorldCalendarDate;

/**
 * Provides an interface for executing events that are
 * dependent on a CalendarDate
 * @author SuperSimpleGuy
 */
public interface ICalendarEvent {

	/**
	 * Returns the unique id of this event
	 * @return the unique id of this event
	 */
	int getId();
	
	/**
	 * Possibly triggers this event. An event can maintain
	 * its own status that affects its probability of getting
	 * triggered
	 * @param d the CalendarDate of the world
	 * @return true if the event triggered, false otherwise
	 */
	boolean triggerEvent(IWorldCalendarDate d);
	
	/**
	 * Allows for post-event cleanup/modifying functions to be called
	 * @param d the date of the ending trigger event
	 */
	void endTriggerEvent(IWorldCalendarDate d);
	
	/**
	 * Returns the duration of the event in number of days. Valid
	 * returns should be zero or greater
	 * @return the duration of the event in number of days
	 */
	int getDurationLengthDays();
	
	/**
	 * Returns the number of days left for this event to be triggered,
	 * valid returns should be zero or greater or -1 if event is not
	 * currently being triggered
	 * @return the number of days left for this event to be triggered
	 */
	int getDaysRemaining();
	
	/**
	 * Possibly decreases the number of days left in the
	 * event, depending whether the event has a variable
	 * duration or not
	 */
	void decreaseDay();
	
}
