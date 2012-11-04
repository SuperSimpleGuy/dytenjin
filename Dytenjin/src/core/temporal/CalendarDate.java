package core.temporal;

public interface CalendarDate {

	int getYear();
	String getYearName();
	int getMonth();
	String getMonthName();
	int getDay();
	String getDayName();
	boolean isHoliday();
	String getHolidayName();
	String getFullDateString();
	
	void advanceOneDay();
	
	
}
