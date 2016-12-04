package jtemp.bingossorder.utils;

import java.util.Calendar;

public class DateUtils {
	public int year, monthOfYear, dayOfMonth;
	public static int hourOfDay;
	public static int minute;
	
//    year = calendar.get(Calendar.YEAR);
//    monthOfYear = calendar.get(Calendar.MONTH);
//    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
   
	public static int getHourOfDay(){
		Calendar calendar = Calendar.getInstance();
		hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
		return hourOfDay;
	}
	public static int getMinute(){
		Calendar calendar = Calendar.getInstance();
		minute = calendar.get(Calendar.MINUTE);
		return minute;
	}
}
