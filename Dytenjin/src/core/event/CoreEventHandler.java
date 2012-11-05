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
import java.util.Collection;
import java.util.HashMap;

import core.temporal.CalendarDate;

/**
 * Maintains a singular way to manage different kinds of basic events:
 * those that happen on a daily basis, those that happen on specific
 * dates, and those that happen randomly. Furthermore, events that last
 * longer than a day also can be triggered multiple times.
 * @author SuperSimpleGuy
 */
public class CoreEventHandler {

	private HashMap<Integer, IEvent> dailyEvents;
	private HashMap<Integer, IRandomEvent> randomEvents;
	private HashMap<CalendarDate, HashMap<Integer, ITemporalEvent>> tempEvents;
	private ArrayList<IEvent> prevTriggers;
	
	/**
	 * Default constructor, initializes non-null values to
	 * private fields.
	 */
	public CoreEventHandler() {
		dailyEvents = new HashMap<Integer, IEvent>();
		randomEvents = new HashMap<Integer, IRandomEvent>();
		tempEvents = new HashMap<CalendarDate, HashMap<Integer, ITemporalEvent>>();
		prevTriggers = new ArrayList<IEvent>();
	}
	
	/**
	 * Checks every kind of event to determine if it isn't
	 * already included in any of the lists.
	 * @param id the id of the event to check for
	 * @return true if the key isn't in any of the lists, false otherwise
	 */
	private boolean isKeyFree(int id) {
		Collection<CalendarDate> keys = tempEvents.keySet();
		for (CalendarDate key : keys) {
			HashMap<Integer, ITemporalEvent> hMap = tempEvents.get(key);
			if (hMap.containsKey(id)) {
				return false;
			}
		}
		for (int i = 0; i < prevTriggers.size(); i++) {
			if (prevTriggers.get(i).getId() == id) {
				return false;
			}
		}
		return !dailyEvents.containsKey(id) &&
			   !randomEvents.containsKey(id);
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
	public boolean addRandomEvent(IRandomEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		randomEvents.put(e.getId(), e);
		return true;
	}
	
	/**
	 * Adds multiple random events to the random event list
	 * @param e an ArrayList of random events to add
	 * @return an ArrayList of random events unsuccessfully added
	 */
	public ArrayList<IRandomEvent> addRandomEvents(ArrayList<IRandomEvent> e) {
		ArrayList<IRandomEvent> temp = new ArrayList<IRandomEvent>();
		for (IRandomEvent event : e) {
			if (!this.addRandomEvent(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
	/**
	 * Adds a temporal event to the list of temporal events
	 * @param e the event to add
	 * @return true if successfully added, false otherwise
	 */
	public boolean addTemporalEvent(ITemporalEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		HashMap<Integer, ITemporalEvent> temp = tempEvents.get(e.getTriggerDate());
		if (temp == null) {
			temp = new HashMap<Integer, ITemporalEvent>();
			temp.put(e.getId(), e);
			tempEvents.put(e.getTriggerDate(), temp);
		} else {
			temp.put(e.getId(), e);
		}
		return true;
	}
	
	/**
	 * Adds a list of temporal events to the current list
	 * @param e the ArrayList of temporal events to add
	 * @return an ArrayList of temporal events unsuccessfully added
	 */
	public ArrayList<ITemporalEvent> addTemporalEvents(ArrayList<ITemporalEvent> e) {
		ArrayList<ITemporalEvent> temp = new ArrayList<ITemporalEvent>();
		for (ITemporalEvent event : e) {
			if (!this.addTemporalEvent(event)) {
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
	public IEvent removeEvent(IEvent e) {
		return this.removeEvent(e.getId());
	}
	
	/**
	 * Removes an event based on the id
	 * @param id the id of the event to remove
	 * @return the IEvent removed, or null if nothing was removed
	 */
	public IEvent removeEvent(int id) {
		IEvent temp = dailyEvents.remove(id);
		if (temp == null) {
			temp = randomEvents.remove(id);
		}
		if (temp == null) {
			Collection<CalendarDate> keys = tempEvents.keySet();
			for (CalendarDate key : keys) {
				temp = tempEvents.get(key).remove(id);
				if (temp != null) {
					break;
				}
			}
		}
		if (temp != null) {
			for (int i = 0; i < prevTriggers.size(); i++) {
				if (prevTriggers.get(i).getId() == id) {
					prevTriggers.remove(i);
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
	public void thisDateEvents(CalendarDate d) {
		for (int i = 0; i < prevTriggers.size(); i++) {
			IEvent e = prevTriggers.get(i);
			if (e instanceof IDurationEvent && ((IDurationEvent)e).getDaysRemaining() > 0) {
				((IDurationEvent)e).triggerEvent();
				((IDurationEvent)e).decreaseDay();
			} else {
				e.endTriggerEvent();
				prevTriggers.remove(i);
			}
		}
		for (IEvent e : dailyEvents.values()) {
			e.triggerEvent();
			prevTriggers.add(e);
		}
		for (IRandomEvent e : randomEvents.values()) {
			if (e.doesTrigger()) {
				e.triggerEvent();
				prevTriggers.add(e);
			}
		}
		Collection<ITemporalEvent> temp = tempEvents.get(d).values();
		for (ITemporalEvent e : temp) {
			e.triggerEvent();
			prevTriggers.add(e);
		}
	}
	
}
