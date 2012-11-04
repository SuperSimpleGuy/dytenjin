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
 * 
 * @author SuperSimpleGuy
 */
public class CoreEventHandler {

	private HashMap<Integer, IEvent> dailyEvents;
	private HashMap<Integer, IRandomEvent> randomEvents;
	private HashMap<CalendarDate, HashMap<Integer, ITemporalEvent>> tempEvents;
	
	private ArrayList<IEvent> prevTriggers;
	
	public CoreEventHandler() {
		dailyEvents = new HashMap<Integer, IEvent>();
		randomEvents = new HashMap<Integer, IRandomEvent>();
		tempEvents = new HashMap<CalendarDate, HashMap<Integer, ITemporalEvent>>();
		prevTriggers = new ArrayList<IEvent>();
	}
	
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
	
	public boolean addDailyEvent(IEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		dailyEvents.put(e.getId(), e);
		return true;
	}
	
	public ArrayList<IEvent> addDailyEvents(ArrayList<IEvent> e) {
		ArrayList<IEvent> temp = new ArrayList<IEvent>();
		for (IEvent event : e) {
			if (!this.addDailyEvent(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
	public boolean addRandomEvent(IRandomEvent e) {
		if (!isKeyFree(e.getId())) {
			return false;
		}
		randomEvents.put(e.getId(), e);
		return true;
	}
	
	public ArrayList<IRandomEvent> addRandomEvents(ArrayList<IRandomEvent> e) {
		ArrayList<IRandomEvent> temp = new ArrayList<IRandomEvent>();
		for (IRandomEvent event : e) {
			if (!this.addRandomEvent(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
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
	
	public ArrayList<ITemporalEvent> addTemporalEvents(ArrayList<ITemporalEvent> e) {
		ArrayList<ITemporalEvent> temp = new ArrayList<ITemporalEvent>();
		for (ITemporalEvent event : e) {
			if (!this.addTemporalEvent(event)) {
				temp.add(event);
			}
		}
		return temp;
	}
	
	public IEvent removeEvent(IEvent e) {
		return this.removeEvent(e.getId());
	}
	
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
