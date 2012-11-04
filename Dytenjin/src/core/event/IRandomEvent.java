package core.event;

public interface IRandomEvent extends IEvent {

	double getProbOccurance();
	boolean doesTrigger();
	
}
