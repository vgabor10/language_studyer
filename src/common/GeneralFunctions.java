package common;

import java.util.*;

public class GeneralFunctions {

	public void toScreenActualDateInFormat1() {	//for debug, date works according to timeZone GMT
		Date date = new Date();
		long now = date.getTime();
		int numberOfDay = (int)(now/(long)(1000*3600*24));
		long milisecsToday = (int)(now - (long)(numberOfDay * (1000*3600*24)));
		int numberOfHoursToday = (int)(milisecsToday/(long)(1000*3600));
		System.out.println(numberOfDay + " " + milisecsToday + " " + numberOfHoursToday);
		System.out.println(date.toString());
	}

	public int milisecToDay(long milisecTime) {
		int timeZone = 2;
		return (int)((milisecTime + (long)(timeZone * 1000 * 3600))/(long)(1000*3600*24));
	}

	public int milisecToHour(long milisec) {
		return (int)Math.floor( (int)(milisec % (1000*3600*24)) / (int)(1000*3600));
	}

	public boolean isInteger(String s) {
		try {
			int cardIndex = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean isDouble() {	//TODO: implement
		return false;
	}

}
