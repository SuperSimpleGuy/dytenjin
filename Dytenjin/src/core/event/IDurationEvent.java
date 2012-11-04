package core.event;

public interface IDurationEvent extends IEvent {

	int getDurationLengthDays();
	int getDaysRemaining();
	void decreaseDay();
	
}
