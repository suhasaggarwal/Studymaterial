package tuts.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A utility class for time and date related methods.
 * 
 * @author Arun Mehra
 */
public class TimeUtils {

	/**
	 * Privatize the constructor
	 */
	private TimeUtils(){
		//Do nothing
	}
	
	
	/**
	 * Adds the 'millisToAdd' no. of milliseconds to the 'initialTime' and returns the resultant timestamp
	 * as a <tt>java.util.Date</tt> object.
	 * @param initialTime
	 * @param millisToAdd
	 * @return Date
	 */
	public static Date getFutureTime(Date initialTime, long millisToAdd) {
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTimeInMillis(initialTime.getTime() + millisToAdd);
		
		return cal.getTime();
	}
	
}
