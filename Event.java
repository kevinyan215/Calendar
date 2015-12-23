
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 * stores the info of the event
 * @author Kevin
 *
 */
public class Event {
	private GregorianCalendar calendar;
	private String title;
	private String eventDate;
	private String time;
	private int year, month, dayOfMonth;
	
	/**
	 * creates an event object
	 * @param title name of the event
	 * @param date date of the event
	 * @param time time of the event
	 */
	public Event(String title, String date, String time){
		this.title = title;
		eventDate = date;
		this.time = time;
		setCalendar();
	}
	
	/**
	 * Gets the title of the event
	 * @return title
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * Gets the date of the event
	 * @return date
	 */
	public String getDate(){
		return eventDate;
	}
	
	/**
	 * Gets the time of the event
	 * @return time
	 */
	public String getTime(){
		return time;
	}
	
	/**
	 * gets the start time of the event
	 * @return start time
	 */
	public String getStartTime(){
		return time.substring(0,5);
	}
	
	/**
	 * gets the end time of the event
	 * @return end time
	 */
	public String getEndTime(){
		return time.substring(6, time.length());
	}
	
	public String toString(){
		return title + ", " + eventDate + ", " + time;
	}
	
	/**
	 * Gets the Calendar object of the event
	 * @return calendar
	 */
	public Calendar getCalendar(){
		return calendar;
	}
	
	/**
	 * Gets the year of the event
	 * @return year
	 */
	public int getYear(){
		return year;
	}
	
	/**
	 * gets the month of the event
	 * @return month
	 */
	public int getMonth(){
		return month;
	}
	
	/**
	 * Gets the day of month of the event
	 * @return day of month
	 */
	public int getDayOfMonth(){
		return dayOfMonth;
	}

	/**
	 * creates a calendar object of the event based on its month, year and day of month
	 */
	public void setCalendar(){
		Scanner inputScanner = new Scanner(eventDate).useDelimiter("[/]");
		month = Integer.parseInt(inputScanner.next()) - 1;
		dayOfMonth = Integer.parseInt(inputScanner.next());
		year = Integer.parseInt(inputScanner.next());

		 calendar = new GregorianCalendar(year, month , dayOfMonth);
	}
}
