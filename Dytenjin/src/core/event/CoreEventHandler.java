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
import java.util.logging.Level;

import core.Constants;
import core.system.CoreLogfileManager;
import core.temporal.WorldTimeDuration;
import core.temporal.WorldCompleteDate;

/**
 * Maintains a singular way to manage different kinds of basic events by
 * daily calling the triggerEvent() method of event interfaces, and also
 * allows events longer than a day also can be triggered multiple times.
 * @author SuperSimpleGuy
 */
public class CoreEventHandler implements ICalendarEventCaller, IEventCaller {

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
		CoreLogfileManager.ENGINE_LOGMNGR.logWithoutParams(Constants.SYS_LOG_FILE, Level.INFO, this.getClass(), "CoreEventHandler", "Created a CoreEventHandler");
	}
	
	/**
	 * Checks every kind of event to determine if it isn't
	 * already included in any of the lists.
	 * @param id the id of the event to check for
	 * @return true if the key isn't in any of the lists, false otherwise
	 */
	private boolean isKeyFree(int id) {
		boolean temp = !dailyEvents.containsKey(id) && !dailyCalEvents.containsKey(id);;
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "isKeyFree", "Boolean method done", temp);
		return temp;
	}
	
	/**
	 * Adds an event to the list of daily events
	 * @param e the event to add
	 * @return true if the event was successfully added, false otherwise
	 */
	public boolean addDailyEvent(ICoreEvent e) {
		if (!isKeyFree(e.getUniqueId().getId())) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addDailyEvent", "Boolean method done, did not add", false);
			return false;
		}
		dailyEvents.put(e.getUniqueId().getId(), e);
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addDailyEvent", "Boolean method done, successfully added", true);
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
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addDailyEvents", "ArrayList<ICoreEvent> method done", temp);
		return temp;
	}
	
	/**
	 * Adds an event to the random event list
	 * @param e the random event to add
	 * @return true if the event was successfully added, false otherwise
	 */
	public boolean addDailyCalEvents(ICoreCalendarEvent e) {
		if (!isKeyFree(e.getUniqueId().getId())) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addDailyCalEvents", "Boolean method done, did not add", false);
			return false;
		}
		dailyCalEvents.put(e.getUniqueId().getId(), e);
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addDailyCalEvents", "Boolean method done, successfully added", true);
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
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "addRandomEvents", "ArrayList<ICoreCalendarEvent> method done", temp);
		return temp;
	}
	
	/**
	 * Removes an event based on the event object's id
	 * @param e the event object to remove
	 * @return the event removed, or null if nothing was removed
	 */
	public ICoreEvent removeDailyEvent(ICoreEvent e) {
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "removeDailyEvent(ICoreEvent e)", "ICoreEvent method done");
		return this.removeDailyEvent(e.getUniqueId().getId());
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
				if (prevEventTriggers.get(i).getUniqueId().getId() == id) {
					CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "removeDailyEvent(int id)", "ICoreEvent method done. Removing previously triggering event: id/index", new Object[] {id, i});
					prevEventTriggers.remove(i);
					return temp;
				}
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "removeDailyEvent(int id)", "ICoreEvent method done", temp);
		return temp;
	}
	
	/**
	 * Removes a calendar event based on the event object's id
	 * @param e the event object to remove
	 * @return the event removed, or null if nothing was removed
	 */
	public ICoreCalendarEvent removeDailyCalEvent(ICoreCalendarEvent e) {
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "removeDailyCalEvent(ICoreCalendarEvent e)", "ICoreCalendarEvent method done");
		return this.removeDailyCalEvent(e.getUniqueId().getId());
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
				if (prevCalEventTriggers.get(i).getUniqueId().getId() == id) {
					CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "removeDailyCalEvent(int id)", "ICoreCalendarEvent method done. Removing previously triggering event: id/index", new Object[] {id, i});
					prevCalEventTriggers.remove(i);
					return temp;
				}
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithResult(this.getClass(), Constants.SYS_FINER_FILE, "removeDailyCalEvent(int id)", "ICoreCalendarEvent method done", temp);
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
	public void triggerDateEvents(WorldTimeDuration timeSinceLast,
			WorldTimeDuration triggerStep,
			WorldCompleteDate d) {
		while (timeSinceLast.longerThanOther(triggerStep)) {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithoutParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerDateEvents", "Triggering another set of events");
			triggerEvents(triggerStep);
			triggerCalEvents(triggerStep, d);
			timeSinceLast.decreaseDuration(triggerStep);
			d = (WorldCompleteDate)d.getDateAfterDuration(triggerStep);
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "triggerDateEvents", "Void method done");
		
	}
	
	/**
	 * Given a trigger step, triggers all events and reduces the duration
	 * on any continuing events by the appropriate amount
	 * @param triggerStep the amount of time that passes by between the
	 * an event being re-triggered
	 */
	private void triggerEvents(WorldTimeDuration triggerStep) {
		//Trigger previous IEvents
		for (int i = 0; i < prevEventTriggers.size(); i++) {
			ICoreEvent e = prevEventTriggers.get(i);
			if (e.getDurationLength().longerThanOther(triggerStep)) {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerEvents(IWorldTimeDuration triggerStep)", "Triggering event & reducing duration", new Object[] {e.getUniqueId().getId()});;
				e.triggerEvent();
				e.decreaseDuration(triggerStep);
			} else {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerEvents(IWorldTimeDuration triggerStep)", "Ending trigger on event", new Object[] {e.getUniqueId().getId()});
				e.endTriggerEvent();
				prevEventTriggers.remove(i);
			}
		}
		//Trigger possible IEvents
		for (ICoreEvent e : dailyEvents.values()) {
			if (e.triggerEvent()) {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerEvents(IWorldTimeDuration triggerStep)", "Triggered new event", new Object[] {e.getUniqueId().getId()});
				if (!prevEventTriggers.contains(e)) {
					prevEventTriggers.add(e);
				}
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "triggerEvents(IWorldTimeDuration triggerStep)", "Void method done");
	}
	
	/**
	 * Given a trigger step, triggers all events and reduces the duration
	 * on any continuing events by the appropriate amount
	 * @param triggerStep the amount of time that passes by between the
	 * an event being re-triggered
	 * @param d the date of the trigger
	 */
	private void triggerCalEvents(WorldTimeDuration triggerStep, WorldCompleteDate d) {
		//Trigger previous ICalendarEvents
		for (int i = 0; i < prevCalEventTriggers.size(); i++) {
			ICoreCalendarEvent e = prevCalEventTriggers.get(i);
			if (e.getDurationLength().longerThanOther(triggerStep)) {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerCalEvents(IWorldTimeDuration triggerStep, WorldCompleteDate d)", "Triggering event & reducing duration", new Object[] {e.getUniqueId().getId()});;
				e.triggerEvent(d);
				e.decreaseDuration(triggerStep);
			} else {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerCalEvents(IWorldTimeDuration triggerStep, WorldCompleteDate d)", "Ending trigger on event", new Object[] {e.getUniqueId().getId()});
				e.endTriggerEvent(d);
				prevCalEventTriggers.remove(i);
			}
		}
		//Trigger possible ICalendarEvents
		for (ICoreCalendarEvent e : dailyCalEvents.values()) {
			if (e.triggerEvent(d)) {
				CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.FINEST, this.getClass(), "triggerCalEvents(IWorldTimeDuration triggerStep, WorldCompleteDate d)", "Triggered new event", new Object[] {e.getUniqueId().getId()});
				if (!prevCalEventTriggers.contains(e)) {
					prevCalEventTriggers.add(e);
				}
			}
		}
		CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "triggerCalEvents(IWorldTimeDuration triggerStep, WorldCompleteDate d)", "Void method done");
	}

	@Override
	public boolean registerEvent(IEvent e) {
		if (e instanceof ICoreEvent) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "registerEvent(IEvent e)", "Boolean method done");
			return addDailyEvent((ICoreEvent)e);
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.INFO, this.getClass(), "registerEvent(IEvent)", "IEvent parameter passed not of ICoreEvent type.", new IEvent[] {e});
			return false;
		}
	}

	@Override
	public boolean registerEvent(ICalendarEvent e) {
		if (e instanceof ICoreCalendarEvent) {
			CoreLogfileManager.ENGINE_LOGMNGR.exitingWithoutResult(this.getClass(), Constants.SYS_FINER_FILE, "registerEvent(ICalendarEvent e)", "Boolean method done");
			return addDailyCalEvents((ICoreCalendarEvent)e);
		} else {
			CoreLogfileManager.ENGINE_LOGMNGR.logWithParams(Constants.SYS_LOG_FILE, Level.INFO, this.getClass(), "registerEvent(IEvent)", "ICalendarEvent parameter passed not of ICoreCalendarEvent type.", new ICalendarEvent[] {e});
			return false;
		}
	}
	
}
