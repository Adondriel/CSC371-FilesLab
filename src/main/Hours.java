package main;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

/**
 * Computes salaries of employees based on list of given hours.
 * 
 * @author Jordan Long
 *
 */


public class Hours 
{

	private static ArrayList<ArrayList<String>> employeeDB = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE); //Employee TXT File
	private static ArrayList<ArrayList<String>> writeArray = new ArrayList<ArrayList<String>>(); //Array to be written to file
	
	public static void main(String[] args) throws ParseException, IOException 
	{		
		ArrayList<Integer> fitCriteria = new ArrayList<Integer>(); //holds values of all included records		
		DateFormat format = new SimpleDateFormat("MM/dd/yy"); //converts date strings to dates
		Date readDate; //current dates of records as they are read in
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
	}
	
	
		
		private static ArrayList<ArrayList<String>> AccountingList = new ArrayList<ArrayList<String>>();
		private static ArrayList<ArrayList<String>> AdministrationList = new ArrayList<ArrayList<String>>();
		private static ArrayList<ArrayList<String>> HRList = new ArrayList<ArrayList<String>>();
		private static ArrayList<ArrayList<String>> OperationsList = new ArrayList<ArrayList<String>>();
		private static ArrayList<ArrayList<String>> PurchasingList = new ArrayList<ArrayList<String>>();
		private static ArrayList<ArrayList<String>> SalesList = new ArrayList<ArrayList<String>>();

		
	
		// 1. Sort employees by department
		public void findDepartment()
		{
			
			for(int i=1; i<employeeDB.size(); i++)
			{
				if(employeeDB.get(i).get(3)=="Accounting")
				{
					AccountingList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3)=="Administration")
				{
					AdministrationList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3)=="Human Resources")
				{
					HRList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3)=="Operations")
				{
					OperationsList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3)=="Purchasing")
				{
					PurchasingList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3)=="Sales")
				{
					SalesList.add(employeeDB.get(i));
				}
			}
			
		}
		
		
		
		// 2. Check if hourly or salary-based pay level
		
		public void checkWageType(ArrayList<ArrayList<String>> list)
		{
			
			for(int i=1; i<list.size(); i++)
			{
				if(list.get(i).get(6)=="Hour")
				{
					//Run hourly rate calc
					//check if hours clocked fall within entered data parameters
				}
				//run commission calc
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		// 3. Check for commission from commission txt file.
		
		// 4. Add base pay and commission together to create total pay.
		
		// 5. Add total department pay together to create Total Payroll.
	
		

		
		
		public static void calculateSalesForEmps(ArrayList<Integer> fitCriteria) {
			for(int i=1; i<employeeDB.size(); i++) {
				String employeePosition = employeeDB.get(i).get(4);
				int empId = Integer.parseInt(employeeDB.get(i).get(0));
				double employeeSales = 0;
				double employeeCommission = 0;
				for(int j=0; j<fitCriteria.size(); j++) {
					if((Integer.parseInt(salesRows.get(fitCriteria.get(j)).get(0)) == empId ||
							employeePosition.equalsIgnoreCase("President") || employeePosition.equalsIgnoreCase("Sales Manager"))) {
						employeeSales += Double.parseDouble(salesRows.get(fitCriteria.get(j)).get(3));
					}
				}
				String tempCom = employeeDB.get(i).get(7).replace('%', ' ');
				if(!tempCom.isEmpty()) {
					employeeCommission = Double.parseDouble(tempCom)/100.0;
				}
				employeeCommission = employeeCommission * employeeSales;
				if(employeeSales != 0 && !alreadyInArray(empId))
					addToWriteArray(empId, employeeSales, employeeCommission);
			}
		}
	
	
	
	
	
}
