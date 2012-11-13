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

import core.temporal.IWorldTimeDuration;
import core.temporal.WorldCompleteDate;

/**
 * Provides an interface for executing events that are
 * dependent on a CalendarDate
 * @author SuperSimpleGuy
 */
public interface ICoreCalendarEvent extends ICalendarEvent {

	/**
	 * Returns the unique id of this event
	 * @return the unique id of this event
	 */
	int getId();
	
	/**
	 * Allows for post-event cleanup/modifying functions to be called
	 * @param d the date of the ending trigger event
	 */
	void endTriggerEvent(WorldCompleteDate d);
	
	/**
	 * Returns the duration of the event
	 * @return the IWorldTimeDuration of the event
	 */
	IWorldTimeDuration getDurationLength();
	
	/**
	 * 
	 * @param d
	 */
	void decreaseDuration(IWorldTimeDuration d);
	
}
