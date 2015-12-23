

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

enum MONTHS
{
	Jan, Feb, March, Apr, May, June, July, Aug, Sep, Oct, Nov, Dec;
}

enum DAYS
{
	Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday ;
}

/**
 * View and Controller part of the MVC pattern. CalendarView displays the GUI, and also takes input from the CalendarModel and updates the GUI.
 * @author Kevin
 *
 */

public class CalendarView {
	private CalendarModel model;
	private JLabel monthLabel = new JLabel();
	private JPanel monthPanel;
	private JPanel eventDisplayPanel;
	private Calendar calendar;
	private JPanel quitPanel;
	private JButton previous, create, next, quitButton;
	private JPanel wholeMonthPanel;
	private JFrame frame;
	
	/**
	 * Displays the calendar.
	 * @param model the model to which the view will receive updates from.
	 */
	public CalendarView(CalendarModel model){
		
		this.model = model;
		frame = new JFrame();
		calendar = model.getCalendar();
		
		
		wholeMonthPanel = new JPanel();
		wholeMonthPanel.setLayout(new BoxLayout(wholeMonthPanel, BoxLayout.Y_AXIS));
		monthPanel = new JPanel();
		monthPanel.setLayout(new GridLayout(0,7,3,3));
		wholeMonthPanel.add(monthLabel);
		wholeMonthPanel.add(monthPanel);
		setMonth();
		setDayOfWeek();
		setDayOfMonth();
		
		//adds previous, create, next buttons to button panel
		JPanel buttonPanel = new JPanel();
		previous = new JButton("<");
		create = new JButton("create");
		next = new JButton(">");
		quitPanel = new JPanel();
		quitButton = new JButton("quit");
		quitPanel.add(quitButton);
		buttonPanel.add(previous); buttonPanel.add(create); buttonPanel.add(next); buttonPanel.add(quitPanel);
		addPreviousButtonListener();
		addNextButtonListener();
		addCreateButtonListener();
		addQuitButtonListener();
		
		eventDisplayPanel = new JPanel();
		eventDisplayPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		eventDisplayPanel.setLayout(new BoxLayout(eventDisplayPanel, BoxLayout.PAGE_AXIS));
		printWeekdayAndMonth();
		displayEvents();
		
		JScrollPane scrollbar = new JScrollPane(eventDisplayPanel);
		scrollbar.setPreferredSize(new Dimension(250,250));
		
		
		
		frame.add(scrollbar, BorderLayout.EAST);
		frame.add(wholeMonthPanel, BorderLayout.WEST);
		frame.add(buttonPanel, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/**
	 *Adds an action listener to the quit button. Updates the text file and terminates the program when pressed.
	 */
	public void addQuitButtonListener(){
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
					model.quit();
					frame.dispose();
			}
		});
	}
	
	/**
	 * Adds an action listener to the create button. Opens another frame that allows the user to create a new event.
	 */
	public void addCreateButtonListener(){
		create.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CreateEvent event = new CreateEvent();
				
			}
		});
	}
	
	
	
		
	/**
	 * Gets today's date in the format mm/dd/yy
	 * @return
	 */
	public String todaysDate(){
		String dayOfMonth = String.valueOf(calendar.get(calendar.DAY_OF_MONTH));
		String month = String.valueOf(calendar.get(calendar.MONTH) + 1);
		String year = String.valueOf(calendar.get(calendar.YEAR));
		return month + "/" + dayOfMonth + "/" + year;
	}
	
	
	/**
	 * sets the label on the eventDisplayPanel.
	 */
	public void printWeekdayAndMonth(){
		MONTHS[] arrayOfMonths = MONTHS.values();
	    DAYS[] arrayOfDays = DAYS.values();
	    DAYS dayOfWeek = arrayOfDays[calendar.get(calendar.DAY_OF_WEEK)-1];
	    int month = calendar.get(calendar.MONTH) + 1;
	    int date = calendar.get(calendar.DAY_OF_MONTH);
	    
	    String weekdayAndMonth =  "                             " + dayOfWeek + " " + month + "/" + date;
	    JLabel eventDisplayPanelLabel = new JLabel(weekdayAndMonth);
	    eventDisplayPanel.add(eventDisplayPanelLabel);
	    eventDisplayPanel.add(new JLabel(" "));
	}
	
	/**
	 * Adds an action listener to the previous button. When pressed, goes to the previous day.
	 */
	public void addPreviousButtonListener(){
		previous.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.setCalendarDay(-1);
				monthPanel.removeAll();
				setMonth();
				setDayOfWeek();
				setDayOfMonth();
				monthPanel.revalidate();
				monthPanel.repaint();
				
				eventDisplayPanel.removeAll();
				printWeekdayAndMonth();
		        displayEvents();
		        eventDisplayPanel.revalidate();
		        eventDisplayPanel.repaint();
			}
		});	
	}
	
	/**
	 * Adds an action listener to the next button. When pressed, goes to the next day.
	 */
	public void addNextButtonListener(){
		next.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				model.setCalendarDay(1);
				monthPanel.removeAll();
				setMonth();
				setDayOfWeek();
				setDayOfMonth();
				monthPanel.revalidate();
				monthPanel.repaint();
				
				eventDisplayPanel.removeAll();
				printWeekdayAndMonth();
		        displayEvents();
		        eventDisplayPanel.revalidate();
		        eventDisplayPanel.repaint();
			}
		});
	}
	
	/**
	 * Displays the events in the eventDisplayPanel.
	 */
	public void displayEvents(){
		ArrayList<String> data = model.getDayEvents();
		for(int i = 0; i < data.size(); i++){
			JLabel events = new JLabel(data.get(i));
			eventDisplayPanel.add(events);
		}
	}
	
	/**
	 *Sets the month and year of the date. 
	 *Example: April 2015, May 2015, etc.
	 */
	public void setMonth(){
		//gets the month
		MONTHS[] arrayOfMonths = MONTHS.values();
	    MONTHS month = arrayOfMonths[model.getCalendar().get(model.getCalendar().MONTH)];
	    
	    //gets the year
	    int year = model.getCalendar().get(model.getCalendar().YEAR);
		monthLabel.setText(month + " " + year );
	    
	}
	
	/**
	 * Sets day of the week on the calendar
	 * Example: Monday, Tuesday, Weds.,etc
	 */
	public void setDayOfWeek(){
		ArrayList<String> dayOfWeek = new ArrayList<String>();
		dayOfWeek.add("Sun"); dayOfWeek.add("Mon"); dayOfWeek.add("Tues"); dayOfWeek.add("Wed"); dayOfWeek.add("Thu"); dayOfWeek.add("Fri"); dayOfWeek.add("Sat");
		for(int a = 0; a < 7; a++){
			JLabel dayOfWeek2 = new JLabel(dayOfWeek.get(a));
			monthPanel.add(dayOfWeek2);
		}
	}
	
	/**
	 * Sets the day of the month or date onto the calendar
	 * Example: 1,2,3,4,5
	 */
	public void setDayOfMonth(){
		int counter = 1;
		int lastDateOfMonth = calendar.getActualMaximum(calendar.DAY_OF_MONTH);
		int maxWeekOfMonth = calendar.getActualMaximum(calendar.WEEK_OF_MONTH);
		for(int weekNumber = 1; weekNumber<=maxWeekOfMonth; weekNumber++){
			for(int weekday = 1; weekday<8 && counter <= lastDateOfMonth; weekday++ ){
				GregorianCalendar calendar2 = new GregorianCalendar(calendar.get(calendar.YEAR),calendar.get(calendar.MONTH), counter);
				if(weekday == calendar2.get(calendar2.DAY_OF_WEEK)){
					JLabel dayOfMonth;
					if(calendar.get(calendar.DAY_OF_MONTH) == counter){
						dayOfMonth = new JLabel(counter + ""); //needs to highlight to
						dayOfMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK));
						counter++;
					}
					else{
						dayOfMonth = new JLabel(counter + "");
						counter++;
					}
					dayOfMonth.addMouseListener(new MouseAdapter(){
						public void mouseClicked(MouseEvent e){
							String day = dayOfMonth.getText();
							int day2 = Integer.parseInt(day);
							model.setToDay(day2);
							monthPanel.removeAll();
							setMonth();
							setDayOfWeek();
							setDayOfMonth();
							monthPanel.revalidate();
							monthPanel.repaint();
							
							eventDisplayPanel.removeAll();
							printWeekdayAndMonth();
					        displayEvents();
					        eventDisplayPanel.revalidate();
					        eventDisplayPanel.repaint();
						}
					});
					monthPanel.add(dayOfMonth);
				}
				else{
					JLabel dayOfMonth = new JLabel("");
					monthPanel.add(dayOfMonth);
				}
			}
		}
	}
	
	/**
	 * @author Kevin
	 *Creates another frame that allows the user to add events.
	 */
	class CreateEvent{
		private JButton save;
		private JTextField description, date, startTime, endTime;
		private JFrame frame;
		
		/**
		 *Displays the frame to add new event.
		 */
		public CreateEvent(){
			frame = new JFrame();
			frame.setSize(400,100);
			frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
			JPanel descriptionPanel = new JPanel();
			description = new JTextField("Untitled Event");
			descriptionPanel.add(description);
		
			JPanel lowerPanel = new JPanel();
			date = new JTextField(todaysDate());
			startTime = new JTextField("00:00");
			JLabel to = new JLabel("to");
			endTime = new JTextField("00:00");
			save = new JButton("Save");
			lowerPanel.add(date); lowerPanel.add(startTime); lowerPanel.add(to); lowerPanel.add(endTime); lowerPanel.add(save);
			
			saveAddActionListener();
			frame.add(descriptionPanel); frame.add(lowerPanel);
			frame.setResizable(false);
			frame.setVisible(true);
		}
		
		/**
		 * Adds an action listener to the save button. When pressed, saves the event.
		 */
		public void saveAddActionListener(){
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					String description2 = description.getText();
					String startTime2 = startTime.getText();
					String endTime2 = endTime.getText();
					String date2 = date.getText();
					
					Event event = new Event(description2, date2, startTime2 + "-" + endTime2);
					ArrayList<Event> todayEvent = model.getDayEventsInEventFormat();
					boolean error = false;
					
					for(Event event3: todayEvent){
						try{
							SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
							Date saveStartTime = parser.parse(startTime2);
							Date saveEndTime = parser.parse(endTime2);
							
							Date existStartTime = parser.parse(event3.getStartTime());
							Date existEndTime = parser.parse(event3.getEndTime());
		
						
							boolean case1 = true ,case2 = true;
							if(existStartTime.before(saveStartTime) && existEndTime.before(saveStartTime)){ case1 =  false;}
							if(existStartTime.after(saveEndTime) && existEndTime.after(saveEndTime)){ case2 =  false;}
							
							if (case1 && case2) {
						        error = true;
							}
							
						}
						catch(Exception e1){
						}
					}
					if(error){
				        JOptionPane.showMessageDialog(frame, "Time conflict in schedule. Please re-enter.");
					}
					else{
						model.addEvent(event);
					}
					
					
					model.setToDay(event.getDayOfMonth());
					monthPanel.removeAll();
					setMonth();
					setDayOfWeek();
					setDayOfMonth();
					monthPanel.revalidate();
					monthPanel.repaint();
					
					eventDisplayPanel.removeAll();
					printWeekdayAndMonth();
			        displayEvents();
			        eventDisplayPanel.revalidate();
			        eventDisplayPanel.repaint();
			        
			        frame.dispose();
				}
			});
		}
	}
}
