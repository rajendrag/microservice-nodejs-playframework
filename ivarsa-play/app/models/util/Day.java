package models.util;

import org.joda.time.DateTime;

import java.util.Date;

public enum Day {

	MONDAY(1),
	TUESDAY(2),
	WEDNESDAY(3),
	THURSDAY(4),
	FRIDAY(5),
	SATURDAY(6),
	SUNDAY(7);

	private int dayIndex;

	private Day(int day){
		this.dayIndex=day;	
	}

	public int getDayIndex() {
		return dayIndex;
	}
	
	public static Day getDayFromString(String dayString){
	    for(Day day:Day.values()){
	        if(day.toString().equals(dayString)){
	            return day;
	        }
	    }
	    return null;
	}
	
	public static Day getDayFromDayIndex(int dayIndex){
	    for(Day day:Day.values()){
	        if(day.getDayIndex()==dayIndex){
	            return day;
	        }
	    }
	    return null;
	}
	
	public static Day getDayFromDate(Date previewDate){
	    return Day.getDayFromDayIndex((new DateTime(previewDate).getDayOfWeek()));
	}
	
}
