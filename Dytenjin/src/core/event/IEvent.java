package core.event;

public interface IEvent {

	int getId();
	void triggerEvent();
	void endTriggerEvent();
	
}
