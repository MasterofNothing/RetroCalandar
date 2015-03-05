/*Calendar
28 feb 15
following example http://www.dreamincode.net/forums/topic/25042-creating-a-calendar-viewer-application/
todo:
day selection
comment section
comment adding
comment removal
etc.
*/

package calendarProgram;

import javax.swing.*;
//import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

//main with default table and scroll pane
public class CalendarMainProgram{
	static JLabel lblMonth, lblYear;
	static JButton btnPrev, btnNext;
	static JTable tblCalendar;
	static JComboBox<String> cmbYear;
	static JFrame frmMain;
	static Container pane;
	static DefaultTableModel mtblCalendar;
	static JScrollPane stblCalendar;
	static JPanel pnlCalendar, pnlInfoPane;
	static int realYear, realMonth, realDay, currentYear, currentMonth;

	public static void main (String args[]){
		//layout
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		//exceptions empty
		catch (ClassNotFoundException e) {}
		catch (InstantiationException e) {}
		catch (IllegalAccessException e) {}
		catch (UnsupportedLookAndFeelException e) {}

		//frame
		frmMain = new JFrame ("Calendar v0.01"); //frame
		frmMain.setSize(700, 455); //size
		pane = frmMain.getContentPane(); //get content pane
		pane.setLayout(null); //null layout
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close app with X

		//controls
		lblMonth = new JLabel ("January");
		lblYear = new JLabel ("Change year:");
		cmbYear = new JComboBox<String>();
		btnPrev = new JButton ("<<");
		btnNext = new JButton (">>");
		mtblCalendar = new DefaultTableModel(){/**
			 * 
			 */
			private static final long serialVersionUID = 1L; //quick fix

		@Override
		public boolean isCellEditable(int rowIndex, int mColIndex){return false;}}; //edit date boxes off
		tblCalendar = new JTable(mtblCalendar);
		stblCalendar = new JScrollPane(tblCalendar);
		pnlCalendar = new JPanel(null);

		//border
		pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		
		//action listeners
		btnPrev.addActionListener(new btnPrev_Action());
		btnNext.addActionListener(new btnNext_Action());
		cmbYear.addActionListener(new cmbYear_Action());
		
		//controls to pane
		pane.add(pnlCalendar);
		pnlCalendar.add(lblMonth);
		pnlCalendar.add(lblYear);
		pnlCalendar.add(cmbYear);
		pnlCalendar.add(btnPrev);
		pnlCalendar.add(btnNext);
		pnlCalendar.add(stblCalendar);
		
		//bounds
		pnlCalendar.setBounds(25, 25, 320, 365);
//		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25); //does nothing
//		lblYear.setBounds(10, 315, 80, 20);//hiding for now, not necessary
		cmbYear.setBounds(230, 315, 80, 20);
		btnPrev.setBounds(10, 15, 50, 25);
		btnNext.setBounds(260, 15, 50, 25);
		stblCalendar.setBounds(10, 50, 300, 250);
		
		//visibility and resize
		frmMain.setResizable(true); //should probably set to false
		frmMain.setVisible(true);
		
		//real month/year
		GregorianCalendar cal = new GregorianCalendar(); //create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //get day
		realMonth = cal.get(GregorianCalendar.MONTH); //get month
		realYear = cal.get(GregorianCalendar.YEAR); //get year
		currentMonth = realMonth; //match month and year
		currentYear = realYear;
		
		//headers
		String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
		for (int i=0; i<7; i++){
			mtblCalendar.addColumn(headers[i]);
		}
		
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //set background

		//resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(true); //should probably set to false
		tblCalendar.getTableHeader().setReorderingAllowed(true); //should probably set to false

		//single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//set row/column count
		tblCalendar.setRowHeight(38);
		mtblCalendar.setColumnCount(7);
		mtblCalendar.setRowCount(6);
		
		//populate table, +- 5 years
		for (int i=realYear-5; i<=realYear+5; i++){
			cmbYear.addItem(String.valueOf(i));
		}
		
		//refresh calendar
		refreshCalendar (realMonth, realYear);
		
		//infopane
		
		pnlInfoPane = new JPanel(null);

		//border
		pnlInfoPane.setBorder(BorderFactory.createTitledBorder("Info"));
		
		//controls to pane
		pane.add(pnlInfoPane);
		
		//bounds
		pnlInfoPane.setBounds(350, 25, 320, 365);
//		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25); //does nothing
//		lblYear.setBounds(10, 315, 80, 20);//hiding for now, not necessary

	}
	
	public static void refreshCalendar(int month, int year){
		//vars
		String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, sum; //number of days, start of month
			
		//allow/disallow buttons
		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early
		if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late
		lblMonth.setText(months[month]); //Refresh the month label (at the top)
		lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		
		//clear table
		for (int i=0; i<6; i++){
			for (int j=0; j<7; j++){
				mtblCalendar.setValueAt(null, i, j);
			}
		}
		
		//get first day of month and number of days
		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		sum = cal.get(GregorianCalendar.DAY_OF_WEEK);
		
		//draw calendar
		for (int i=1; i<=nod; i++){
			int row = new Integer((i+sum-2)/7);
			int column  =  (i+sum-2)%7;
			mtblCalendar.setValueAt(i, row, column);
		}

		//apply render
		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);
			//week-end's color
			if (column == 0 || column == 6){
				setBackground(new Color(210, 210, 210));
			}
			//week's color
			else{ //week
				setBackground(new Color(255, 255, 255));
			}
			//today's color
			if (value != null){
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //today
					setBackground(new Color(230, 230, 230));
				}
			}
			setBorder(null);
			setForeground(Color.black);
			return this;  
		}
	}

	static class btnPrev_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 0){ //back one year
				currentMonth = 11;
				currentYear -= 1;
			}
			else{ //back one month
				currentMonth -= 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class btnNext_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (currentMonth == 11){ //forward one year
				currentMonth = 0;
				currentYear += 1;
			}
			else{ //forward one month
				currentMonth += 1;
			}
			refreshCalendar(currentMonth, currentYear);
		}
	}
	static class cmbYear_Action implements ActionListener{
		public void actionPerformed (ActionEvent e){
			if (cmbYear.getSelectedItem() != null){
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}
	//public class SidePane extends CalendarSidePane {
		//stub for now
	//doing it differently for now
}
	
