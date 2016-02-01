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
	private static double totalSales = 0.0; //stores total for President & Sales Manager calculations 
	
	public static void main(String[] args) throws ParseException, IOException {		
		ArrayList<Integer> fitCriteria = new ArrayList<Integer>(); //holds values of all included records		
		DateFormat format = new SimpleDateFormat("MM/dd/yy"); //converts date strings to dates
		Date readDate; //current dates of records as they are read in
		Date startDate = format.parse(args[0]); //converts command line to date
		Date endDate = format.parse(args[1]); //converts command line to date
		
		//Stores locations of included records in fitCriteria
		for(int i=1; i<salesRows.size(); i++) {
			readDate = format.parse(salesRows.get(i).get(1));
			if((readDate.after(startDate) && readDate.before(endDate)) || readDate.equals(startDate) || readDate.equals(endDate)){
				fitCriteria.add(i);
			}
		}
		
		System.out.println("Read " + fitCriteria.size() + " employees");
		
		calculateSales(fitCriteria);
		addPresAndSales();		
		FilesLab.writeToFile(FilesLabConstants.SALES_OUTPUT_FILE, writeArray);		
	}
	
	/**
	 * Go through list of sales and total up each employee's information
	 * @param fitCriteria list of index numbers to examine in the sales record list
	 */
	public static void calculateSales(ArrayList<Integer> fitCriteria) {
		//Consolidates duplicate sales records to a single record per employee
		for(int i=0; i<fitCriteria.size(); i++) {
			String empPos = getEmployeePosition(salesRows.get(fitCriteria.get(i)).get(0), empRows);
			int id = Integer.parseInt(salesRows.get(fitCriteria.get(i)).get(0));
			double indSales = Double.parseDouble(salesRows.get(fitCriteria.get(i)).get(3));
			for(int j=i+1; j<fitCriteria.size(); j++) {
				int id2 = Integer.parseInt(salesRows.get(fitCriteria.get(j)).get(0));
				if (id2 == id) {
					double addlSales = Double.parseDouble(salesRows.get(fitCriteria.get(j)).get(3));
					indSales += addlSales;
					fitCriteria.remove(j);
					j--;
				}
			}
			totalSales += indSales;
			double indCommission = getEmployeeCommission(salesRows.get(fitCriteria.get(i)).get(0), empRows)*indSales;
			
			//Skip President or Sales Managers; they'll be calculated later
			if(!(empPos.equalsIgnoreCase("Sales Manager") || empPos.equalsIgnoreCase("President"))){
				addToWriteArray(id, indSales, indCommission);
			}
		}
	}
	
	/**
	 * Search for President and Sales Managers to calculate their commission separately
	 */
	public static void addPresAndSales() {
		//Scan for any employees with title of "President" or "Sales Manager"
		for(int i=1; i<empRows.size(); i++) {
			String empPos = getEmployeePosition(empRows.get(i).get(0), empRows);
			if(empPos.equalsIgnoreCase("Sales Manager") || empPos.equalsIgnoreCase("President")) {
				int id = Integer.parseInt(empRows.get(i).get(0));
				double commission = getEmployeeCommission(empRows.get(i).get(0), empRows)*totalSales;
				addToWriteArray(id, totalSales, commission);				
			}
		}
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
	 * Calculates employee's commission as a fractional decimal number
	 * 
	 * @param empNum employee number as string
	 * @param empRows list of all employee records
	 * @return decimal commission if found, 0 if not
	 */
	public static double getEmployeeCommission(String empNum, ArrayList<ArrayList<String>> empRows) {
		for(int i=1; i<empRows.size(); i++) {
			if(empRows.get(i).get(0).equalsIgnoreCase(empNum)) {				
				String empCom = empRows.get(i).get(7).replace('%', ' ');
				if(!empCom.isEmpty()) {
					double commission = Double.parseDouble(empCom)/100;
					return commission;
				}
			}
		}
		return 0;
	}
	
	/**
	 * Finds the employee's position title
	 * 
	 * @param empNum employee number as string
	 * @param empRows list of all employee records
	 * @return position title as string if found, empty string if not
	 */
	public static String getEmployeePosition(String empNum, ArrayList<ArrayList<String>> empRows) {
		for(int i=1; i<empRows.size(); i++) {
			if(empRows.get(i).get(0).equalsIgnoreCase(empNum)) {
				return empRows.get(i).get(4);
			}
		}
		return "";
	}
		
}
