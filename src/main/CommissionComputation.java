package main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 * Computes commissions for sales by employee
 * 
 * @author Josh Varone
 *
 */
public class CommissionComputation {

	private static ArrayList<ArrayList<String>> salesRows = FilesLab.getArrayListFromFile(FilesLabConstants.SALES_INPUT_FILE); //Creates 2D ArrayList with data
	private static ArrayList<ArrayList<String>> empRows = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE); //Creates 2D ArrayList with data
	private static ArrayList<ArrayList<String>> writeArray = new ArrayList<ArrayList<String>>(); //Array to be written to file
	private static DateFormat format = new SimpleDateFormat("MM/dd/yy"); //converts date strings to dates
	
	public static void main(String[] args) throws ParseException, IOException {		
		//Read in dates from argument values, terminate if invalid
		Date startDate = new Date(0);
		Date endDate = new Date(Long.MAX_VALUE);
		try {
			startDate = format.parse(args[0]); //converts command line to date
			endDate = format.parse(args[1]); //converts command line to date
		}
		catch(Exception e){
			System.out.println("Invalid date detected. Program will terminate.");
			System.exit(0);
		}
		
		//Get total sales and commissions for each employee, ignoring duplicate IDs
		for(int i=1; i<empRows.size(); i++) {
			int empId = Integer.parseInt(empRows.get(i).get(0));
			double[] salesCom = getCommForPeriod(empRows.get(i), startDate, endDate);			
			if(!alreadyInArray(empId)) addToWriteArray(empId, salesCom[0], salesCom[1]);
		}
				
		FilesLab.writeToFile(FilesLabConstants.SALES_OUTPUT_FILE, writeArray);
		
		System.out.println("Read " + empRows.size() + " employees");
	}
	
	/**
	 * Makes sure an ID can appear only once if there are duplicates
	 */
	public static boolean alreadyInArray(int empId) {
		for(ArrayList<String> s : writeArray) {
			if(empId == Integer.parseInt(s.get(0))) {
				return true;
			}
		}
		return false;
	}	
	
	/**
	 * Adds the necessary elements to an ArrayList for writing to file
	 */
	public static void addToWriteArray(int id, double sales, double commission) {
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(String.format("%d", id));
		temp.add(String.format("%.2f", sales));
		temp.add(String.format("%.2f", commission));
		writeArray.add(temp);
	}
	
	/**
	 * Returns calculated commission for given employee within given period
	 * @returns a double array, element 0 is total sales for the period, 1 is total commission
	 * @throws ParseException 
	 */
	public static double[] getCommForPeriod(ArrayList<String> employee, Date start, Date end) throws ParseException {
		ArrayList<Integer> validDates = getValidDateIndices(start, end);
		double[] salesCom = new double[2];
		String empPos = employee.get(4);
		int empId = Integer.parseInt(employee.get(0));
		double empSales = 0;
		double empCom = 0;
		for(int j=0; j<validDates.size(); j++) {
			if((Integer.parseInt(salesRows.get(validDates.get(j)).get(0)) == empId ||
					empPos.equalsIgnoreCase("President") || empPos.equalsIgnoreCase("Sales Manager"))) {
				empSales += Double.parseDouble(salesRows.get(validDates.get(j)).get(3));
			}
		}
		String tempCom = employee.get(7).replace('%', ' ');
		if(!tempCom.isEmpty()) {
			empCom = Double.parseDouble(tempCom)/100.0;
		}
		empCom = empCom * empSales;
		
		salesCom[0] = empSales;
		salesCom[1] = empCom;		
		
		return salesCom;
	}
	
	/**
	 * Returns a list of indices of valid sales records within date period
	 */
	public static ArrayList<Integer> getValidDateIndices(Date startDate, Date endDate) throws ParseException {
		ArrayList<Integer> fitCriteria = new ArrayList<Integer>(); //holds values of all included records		
		Date readDate; //current dates of records as they are read in
		
		//Stores locations of included records in fitCriteria
		for(int i=1; i<salesRows.size(); i++) {
			readDate = format.parse(salesRows.get(i).get(1));
			if((readDate.after(startDate) && readDate.before(endDate)) || readDate.equals(startDate) || readDate.equals(endDate)){
				fitCriteria.add(i);
			}
		}
		
		return fitCriteria;
	}
		
}
