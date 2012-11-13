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

import core.temporal.IWorldTimeDuration;
import core.temporal.WorldCompleteDate;

/**
 * Maintains a singular way to manage different kinds of basic events by
 * daily calling the triggerEvent() method of event interfaces, and also
 * allows events longer than a day also can be triggered multiple times.
 * @author SuperSimpleGuy
 */
public class CoreEventHandler {

	private HashMap<Integer, ICoreEvent> dailyEvents;
	private HashMap<Integer, ICoreCalendarEvent> dailyCalEvents;
	private ArrayList<ICoreEvent> prevEventTriggers;
	private ArrayList<ICoreCalendarEvent> prevCalEventTriggers;
	
	/**
	 * Default constructor, initializes non-null values to
	 * private fields.
	 */
	public CoreEventHandler() {
		dailyEvents = new HashMap<Integer, ICoreEvent>();
		dailyCalEvents = new HashMap<Integer, ICoreCalendarEvent>();
		prevEventTriggers = new ArrayList<ICoreEvent>();
		prevCalEventTriggers = new ArrayList<ICoreCalendarEvent>();
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
	public boolean addDailyEvent(ICoreEvent e) {
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
	public ArrayList<ICoreEvent> addDailyEvents(ArrayList<ICoreEvent> e) {
		ArrayList<ICoreEvent> temp = new ArrayList<ICoreEvent>();
		for (ICoreEvent event : e) {
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
	public boolean addDailyCalEvents(ICoreCalendarEvent e) {
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
	public ArrayList<ICoreCalendarEvent> addRandomEvents(ArrayList<ICoreCalendarEvent> e) {
		ArrayList<ICoreCalendarEvent> temp = new ArrayList<ICoreCalendarEvent>();
		for (ICoreCalendarEvent event : e) {
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
	public ICoreEvent removeDailyEvent(ICoreEvent e) {
		return this.removeDailyEvent(e.getId());
	}
	
	/**
	 * Removes an event based on the id
	 * @param id the id of the event to remove
	 * @return the IEvent removed, or null if nothing was removed
	 */
	public ICoreEvent removeDailyEvent(int id) {
		ICoreEvent temp = dailyEvents.remove(id);
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
	public ICoreCalendarEvent removeDailyCalEvent(ICoreCalendarEvent e) {
		return this.removeDailyCalEvent(e.getId());
	}
	
	/**
	 * Removes a calendar event based on the id
	 * @param id the id of the event to remove
	 * @return the ICalendarEvent removed, or null if nothing was removed
	 */
	public ICoreCalendarEvent removeDailyCalEvent(int id) {
		ICoreCalendarEvent temp = dailyCalEvents.remove(id);
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
	 * Triggers events based on the date passed. The date is advanced if
	 * a period of time allows multiple triggers.
	 * @param timeSinceLast the duration since the last time events were
	 * triggered
	 * @param triggerStep the amount of time that must pass before events
	 * are triggered again
	 * @param d the date to trigger events on
	 */
	public void triggerDateEvents(IWorldTimeDuration timeSinceLast,
			IWorldTimeDuration triggerStep,
			WorldCompleteDate d) {
		while (timeSinceLast.longerThanOther(triggerStep)) {
			triggerEvents(triggerStep);
			triggerCalEvents(triggerStep, d);
			timeSinceLast.decreaseDuration(triggerStep);
			d.advanceTime(triggerStep);
		}
	}
	
	/**
	 * Given a trigger step, triggers all events and reduces the duration
	 * on any continuing events by the appropriate amount
	 * @param triggerStep the amount of time that passes by between the
	 * an event being re-triggered
	 */
	private void triggerEvents(IWorldTimeDuration triggerStep) {
		//Trigger previous IEvents
		for (int i = 0; i < prevEventTriggers.size(); i++) {
			ICoreEvent e = prevEventTriggers.get(i);
			if (e.getDurationLength().longerThanOther(triggerStep)) {
				e.triggerEvent();
				e.decreaseDuration(triggerStep);
			} else {
				e.endTriggerEvent();
				prevEventTriggers.remove(i);
			}
		}
		//Trigger possible IEvents
		for (ICoreEvent e : dailyEvents.values()) {
			if (e.triggerEvent()) {
				if (!prevEventTriggers.contains(e)) {
					prevEventTriggers.add(e);
				}
			}
		}		
	}
	
	/**
	 * Given a trigger step, triggers all events and reduces the duration
	 * on any continuing events by the appropriate amount
	 * @param triggerStep the amount of time that passes by between the
	 * an event being re-triggered
	 * @param d the date of the trigger
	 */
	private void triggerCalEvents(IWorldTimeDuration triggerStep, WorldCompleteDate d) {
		//Trigger previous ICalendarEvents
		for (int i = 0; i < prevCalEventTriggers.size(); i++) {
			ICoreCalendarEvent e = prevCalEventTriggers.get(i);
			if (e.getDurationLength().longerThanOther(triggerStep)) {
				e.triggerEvent(d);
				e.decreaseDuration(triggerStep);
			} else {
				e.endTriggerEvent(d);
				prevCalEventTriggers.remove(i);
			}
		}
		//Trigger possible ICalendarEvents
		for (ICoreCalendarEvent e : dailyCalEvents.values()) {
			if (e.triggerEvent(d)) {
				if (!prevCalEventTriggers.contains(e)) {
					prevCalEventTriggers.add(e);
				}
			}
		}
	}
	
}
