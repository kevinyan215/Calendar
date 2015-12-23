import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 * Model version of the MVC pattern. Updates CalendarView with the information to display onto the calendar.
 * @author Kevin
 *
 */
public class CalendarModel {
	private ArrayList<Event> listOfEvents = new ArrayList<>();
	private GregorianCalendar calendar = new GregorianCalendar();
	public CalendarModel(){
		File file = new File("events.txt");
		if(file.exists()){
			loadFile(file);
		}
	}
	/**
	 *loads all the events to the data structure
	 * @param fileName file of the events
	 */
	public void loadFile(File fileName){
		try{
			ArrayList<String> fileInputs = new ArrayList<>();
			Scanner in = new Scanner(fileName);
			while(in.hasNextLine()){
				String input = in.nextLine();
				fileInputs.add(input);
			}
			
			String title = "", date = "", time = "";
			for(String list: fileInputs){
				Scanner in2 = new Scanner(list).useDelimiter("[.]");

					title = in2.next();
					date = in2.next();
					String date2 = date.substring(1, date.length());
					time = in2.next();
					String time2 = time.substring(1, time.length());
					Event e = new Event(title, date2, time2);
					listOfEvents.add(e);
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Saves all the events into a text file.
	 */
	public void quit(){
		Collections.sort(listOfEvents, new timeComparator());
		Collections.sort(listOfEvents, new dayOfMonthComparator());
		Collections.sort(listOfEvents, new monthComparator());
		Collections.sort(listOfEvents, new yearComparator());
		
		try {
			File file = new File("events.txt");
			PrintWriter out = new PrintWriter(file);
			for(Event e : listOfEvents){
				GregorianCalendar calendar = new GregorianCalendar(e.getYear(), e.getMonth(), e.getDayOfMonth());
				out.println(e.getTitle() + ". " + e.getDate() + ". " + e.getTime() + ". ");
			}
			out.close();
		}
		catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Increments/decrements the date of the calendar depending on the input.
	 * @param change the value to change with
	 */
	public void setCalendarDay(int change){
		calendar.add(calendar.DAY_OF_MONTH, change);
	}
	
	/**
	 * Sets the calendar to the appropriate date
	 * @param day sets the calendar to this day
	 */
	public void setToDay(int day){
		calendar.set(calendar.DAY_OF_MONTH, day);
	}
	
	
	/**
	 * returns the model's calendar
	 * @return the model's calendar
	 */
	public GregorianCalendar getCalendar(){
		return calendar;
	}
	
	/**
	 * adds an event to the model
	 * @param e the event to be added
	 */
	public void addEvent(Event e){
		listOfEvents.add(e);
	}
	
	/**
	 * gets the events that are on the given day
	 * @return an arraylist of the events that are on a given day
	 */
	public ArrayList<String> getDayEvents(){
		Collections.sort(listOfEvents, new timeComparator());
		ArrayList<String> dayEvent = new ArrayList<>();
		
		int inputYear = calendar.get(calendar.YEAR);
		int inputMonth = calendar.get(calendar.MONTH);
		int inputDayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
		
		int storedYear, storedMonth, storedDayOfMonth;
			for(Event e: listOfEvents){
				storedYear = e.getCalendar().get(e.getCalendar().YEAR);
				storedMonth = e.getCalendar().get(e.getCalendar().MONTH);
				storedDayOfMonth = e.getCalendar().get(e.getCalendar().DAY_OF_MONTH);
				if(inputYear == storedYear && inputMonth == storedMonth && inputDayOfMonth == storedDayOfMonth){
					dayEvent.add(e.getTime() + " " + e.getTitle() );
				}
			}
		return dayEvent;
	}
	
	/**
	 *  gets the events that are on the given day in a specific format
	 * @return an arraylist of the events that are on a given day
	 */
	public ArrayList<Event> getDayEventsInEventFormat(){
		Collections.sort(listOfEvents, new timeComparator());
		ArrayList<Event> dayEvent = new ArrayList<>();
		
		int inputYear = calendar.get(calendar.YEAR);
		int inputMonth = calendar.get(calendar.MONTH);
		int inputDayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
		
		int storedYear, storedMonth, storedDayOfMonth;
			for(Event e: listOfEvents){
				storedYear = e.getCalendar().get(e.getCalendar().YEAR);
				storedMonth = e.getCalendar().get(e.getCalendar().MONTH);
				storedDayOfMonth = e.getCalendar().get(e.getCalendar().DAY_OF_MONTH);
				if(inputYear == storedYear && inputMonth == storedMonth && inputDayOfMonth == storedDayOfMonth){
					dayEvent.add(e);
				}
			}
		return dayEvent;
	}
	
	
	/**
	 *Comparator class to sort the time from earliest to latest
	 */
	class timeComparator implements Comparator<Event>{
		@Override
		public int compare(Event a, Event b) {
			Scanner inA = new Scanner(a.getTime()).useDelimiter(":");
			Scanner inB = new Scanner(b.getTime()).useDelimiter(":");
			int hourA = Integer.parseInt(inA.next());
			int hourB = Integer.parseInt(inB.next());
			return hourA-hourB;
		}
	}
	
	/**
	 *Comparator class to sort the date in chronological order
	 */
	class dayOfMonthComparator implements Comparator<Event>{
		@Override
		public int compare(Event a, Event b) {
			return a.getDayOfMonth() - b.getDayOfMonth();
		}
	}
	
	/**
	 * Comparator class to sort the month in chronological order
	 */
	class monthComparator implements Comparator<Event>{
		@Override
		public int compare(Event a, Event b) {
			return a.getMonth() - b.getMonth();
		}
	}
	
	/**
	 * Comparator class to sort the year in chronological order
	 */
	class yearComparator implements Comparator<Event>{
		@Override
		public int compare(Event a, Event b) {
			return a.getYear() - b.getYear();
		}
	}
	
}
