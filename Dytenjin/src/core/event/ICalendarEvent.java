/**
 * 
 */
package core.event;

import core.temporal.CalendarDate;

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
	boolean triggerEvent(CalendarDate d);
	
	/**
	 * Allows for post-event cleanup/modifying functions to be called
	 * @param d the date of the ending trigger event
	 */
	void endTriggerEvent(CalendarDate d);
	
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
