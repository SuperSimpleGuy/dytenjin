package core.temporal;

public abstract class WorldTime {

	private CalendarDate currDate;
	
	public WorldTime(CalendarDate currDate) {
		this.currDate = currDate;
	}
	
	public CalendarDate getCurrDate() {
		return currDate;
	}
	
}
