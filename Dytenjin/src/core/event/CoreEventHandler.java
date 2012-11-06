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

import java.util.ArrayList;
import java.util.HashMap;

import core.temporal.CalendarDate;

/**
 * Maintains a singular way to manage different kinds of basic events by
 * daily calling the triggerEvent() method of event interfaces, and also
 * allows events longer than a day also can be triggered multiple times.
 * @author SuperSimpleGuy
 */
public class CoreEventHandler {

	private HashMap<Integer, IEvent> dailyEvents;
	private HashMap<Integer, ICalendarEvent> dailyCalEvents;
	private ArrayList<IEvent> prevEventTriggers;
	private ArrayList<ICalendarEvent> prevCalEventTriggers;
	
	/**
	 * Default constructor, initializes non-null values to
	 * private fields.
	 */
	public CoreEventHandler() {
		dailyEvents = new HashMap<Integer, IEvent>();
		dailyCalEvents = new HashMap<Integer, ICalendarEvent>();
		prevEventTriggers = new ArrayList<IEvent>();
		prevCalEventTriggers = new ArrayList<ICalendarEvent>();
	}
	
	/**
	 * Checks every kind of event to determine if it isn't
	 * already included in any of the lists.
	 * @param id the id of the event to check for
	 * @return true if the key isn't in any of the lists, false otherwise
	 */
	private boolean isKeyFree(int id) {
		return !dailyEvents.containsKey(id) && !dailyCalEvents.containsKey(id);
	}
	
	/**
	 * Adds an event to the list of daily events
	 * @param e the event to add
	 * @return true if the event was successfully added, false otherwise
	 */
	public boolean addDailyEvent(IEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		dailyEvents.put(e.getId(), e);
		return true;
	}
	
	/**
	 * Adds multiple events to the list of daily events
	 * @param e an ArrayList of events to add
	 * @return an ArrayList of events that could not be added
	 */
	public ArrayList<IEvent> addDailyEvents(ArrayList<IEvent> e) {
		ArrayList<IEvent> temp = new ArrayList<IEvent>();
		for (IEvent event : e) {
			if (!this.addDailyEvent(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
	/**
	 * Adds an event to the random event list
	 * @param e the random event to add
	 * @return true if the event was successfully added, false otherwise
	 */
	public boolean addDailyCalEvents(ICalendarEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		dailyCalEvents.put(e.getId(), e);
		return true;
	}
	
	/**
	 * Adds multiple random events to the random event list
	 * @param e an ArrayList of random events to add
	 * @return an ArrayList of random events unsuccessfully added
	 */
	public ArrayList<ICalendarEvent> addRandomEvents(ArrayList<ICalendarEvent> e) {
		ArrayList<ICalendarEvent> temp = new ArrayList<ICalendarEvent>();
		for (ICalendarEvent event : e) {
			if (!this.addDailyCalEvents(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
	/**
	 * Removes an event based on the event object's id
	 * @param e the event object to remove
	 * @return the event removed, or null if nothing was removed
	 */
	public IEvent removeDailyEvent(IEvent e) {
		return this.removeDailyEvent(e.getId());
	}
	
	/**
	 * Removes an event based on the id
	 * @param id the id of the event to remove
	 * @return the IEvent removed, or null if nothing was removed
	 */
	public IEvent removeDailyEvent(int id) {
		IEvent temp = dailyEvents.remove(id);
		if (temp != null) {
			for (int i = 0; i < prevEventTriggers.size(); i++) {
				if (prevEventTriggers.get(i).getId() == id) {
					prevEventTriggers.remove(i);
					return temp;
				}
			}
		}
		return temp;
	}
	
	/**
	 * Removes a calendar event based on the event object's id
	 * @param e the event object to remove
	 * @return the event removed, or null if nothing was removed
	 */
	public ICalendarEvent removeDailyCalEvent(ICalendarEvent e) {
		return this.removeDailyCalEvent(e.getId());
	}
	
	/**
	 * Removes a calendar event based on the id
	 * @param id the id of the event to remove
	 * @return the ICalendarEvent removed, or null if nothing was removed
	 */
	public ICalendarEvent removeDailyCalEvent(int id) {
		ICalendarEvent temp = dailyCalEvents.remove(id);
		if (temp != null) {
			for (int i = 0; i < prevCalEventTriggers.size(); i++) {
				if (prevCalEventTriggers.get(i).getId() == id) {
					prevCalEventTriggers.remove(i);
					return temp;
				}
			}
		}
		return temp;
	}
	
	/**
	 * Triggers events based on the date passed
	 * @param d the date to trigger events on
	 */
	public void triggerDateEvents(CalendarDate d) {
		//Trigger previous IEvents
		for (int i = 0; i < prevEventTriggers.size(); i++) {
			IEvent e = prevEventTriggers.get(i);
			if (e.getDaysRemaining() > 0) {
				e.triggerEvent();
				e.decreaseDay();
			} else {
				e.endTriggerEvent();
				prevEventTriggers.remove(i);
			}
		}
		//Trigger previous ICalendarEvents
		for (int i = 0; i < prevCalEventTriggers.size(); i++) {
			ICalendarEvent e = prevCalEventTriggers.get(i);
			if (e.getDaysRemaining() > 0) {
				e.triggerEvent(d);
				e.decreaseDay();
			} else {
				e.endTriggerEvent(d);
				prevCalEventTriggers.remove(i);
			}
		}
		//Trigger possible IEvents
		for (IEvent e : dailyEvents.values()) {
			if (e.triggerEvent()) {
				prevEventTriggers.add(e);
			}
		}
		//Trigger possible ICalendarEvents
		for (ICalendarEvent e : dailyCalEvents.values()) {
			if (e.triggerEvent(d)) {
				prevCalEventTriggers.add(e);
			}
		}
	}
	
}
