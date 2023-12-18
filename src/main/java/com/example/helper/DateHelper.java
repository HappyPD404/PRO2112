package com.example.helper;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {

	public static Date sumNumberDate(Date date, int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, number);
		Date newDate = calendar.getTime();
		return newDate;
	}
	
	public static int getYearNow() {
		Calendar instance = Calendar.getInstance();
		int year = instance.get(Calendar.YEAR);
		return year;
	}
	
	public static int getMonthNow() {
		Calendar instance = Calendar.getInstance();
		int month = instance.get(Calendar.MONTH);
		return month;
	}

}
