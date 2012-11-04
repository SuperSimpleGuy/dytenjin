package core.event;

import core.temporal.CalendarDate;

public interface ITemporalEvent extends IEvent {

	boolean timeToTrigger(CalendarDate d);
	CalendarDate getTriggerDate();
	
}
