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
	private static ArrayList<ArrayList<String>> payrollDB = new ArrayList<ArrayList<String>>(); //Array to be written to file
	
	private static Date startDate;
	private static Date endDate;
	
	/**
	 * Sub-ArrayLists of each department
	 */
	private static ArrayList<ArrayList<String>> AccountingList = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> AdministrationList = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> HRList = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> OperationsList = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> PurchasingList = new ArrayList<ArrayList<String>>();
	private static ArrayList<ArrayList<String>> SalesList = new ArrayList<ArrayList<String>>();
	
	
	
	
	
	public static void main(String[] args) throws ParseException, IOException
	{		
		
			
		
		/**
		 * Takes in command line arguments, aka date range and converts it to dates.
		 */
		
		DateFormat format = new SimpleDateFormat("MM/dd/yy"); //converts date strings to dates
		startDate = new Date(0);
		endDate = new Date(Long.MAX_VALUE);
		try {
			startDate = format.parse(args[0]); //converts command line to date
			endDate = format.parse(args[1]); //converts command line to date
		}
		catch(Exception e){
			System.out.println("Invalid date detected. Program will terminate.");
			System.exit(0);
		}
		
		
		
		/**
		 * ------------------------------------------------------------------------------------------ *
		 */
		
		
		
		findDepartment();											//Sort Employees
		
		
		formatListHeader("Department: Accounting");					//Print header to file
		checkWageType(AccountingList);								//Calculate Wage
		formatListHeader("Department: Administration");
		checkWageType(AdministrationList);
		formatListHeader("Department: Human Resources");
		checkWageType(HRList);
		formatListHeader("Department: Operations");
		checkWageType(OperationsList);
		formatListHeader("Department: Purchasing");
		checkWageType(PurchasingList);
		formatListHeader("Department: Sales");
		checkWageType(SalesList);

		FilesLab.writeToFile(FilesLabConstants.HOURS_OUTPUT_FILE, payrollDB);	//Print final report to .txt
	}
	
	

	
		// 1. Sort employees by department
		public static void findDepartment()
		{
			
			for(int i=1; i<employeeDB.size(); i++)
			{
				if(employeeDB.get(i).get(3).equalsIgnoreCase("accounting"))
				{
					AccountingList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3).equalsIgnoreCase("Administration"))
				{
					AdministrationList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3).equalsIgnoreCase("Human Resources"))
				{
					HRList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3).equalsIgnoreCase("Operations"))
				{
					OperationsList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3).equalsIgnoreCase("Purchasing"))
				{
					PurchasingList.add(employeeDB.get(i));
				}
				else if (employeeDB.get(i).get(3).equalsIgnoreCase("Sales"))
				{
					SalesList.add(employeeDB.get(i));
				}
			}
			
		}
		
		
		
		// 2. Check if hourly or salary-based pay level
		
		public static void checkWageType(ArrayList<ArrayList<String>> list) throws IOException, ParseException
		{
			double departmentPayrollTotal = 0.0;
			
			
			for(int i=1; i<list.size(); i++)
			{
				
				int employeeId = Integer.parseInt(list.get(i).get(0));
				
				//New row to store all employee financial information
				ArrayList<String> newRow = new ArrayList<String>();
				
				
				//Prints Employee's name to ArrayList
				String name = "		"+list.get(i).get(1)+", "+list.get(i).get(2);
				newRow.add(String.format("%d", name));
				//newRow.set(i, name);
			
				double weeklyPay = 0.0;
				
				
				if(list.get(i).get(6)=="Hour")
				{
					//Run hourly rate calculator
					//check if hours clocked fall within entered data parameters
				
					double hourlyRate = Double.parseDouble(list.get(i).get(5));
					double hoursWorked = 0;
					
					
					
					//Find hours within date range.
					
					DateFormat format = new SimpleDateFormat("MMM-dd"); //converts date strings to dates
					
					
					
					
					//Check if hours are over 40, if so apply business rules.
					if(hoursWorked>40)
					{
						double temp = hoursWorked-40.0;
						temp = temp*(hourlyRate*1.5);
						weeklyPay = (40.0*hourlyRate)+temp;
					}
					else
					{
						weeklyPay =  hoursWorked*hourlyRate;
					}
					
					//Send final number to ArrayList.
					newRow.add(String.format("%.2f", weeklyPay));

				}
				
				else
				{
					//Take salary and divide it by 52 for the weekly pay.
					
					double weeklySalary = Double.parseDouble(list.get(i).get(6));
					weeklyPay = weeklySalary/52;
					
					//Send this to final array.
					newRow.add(String.format("%.2f", weeklyPay));
				}	
				
				
				// 3. Check for commission using commission function in other java file.
				
				double[] commissionData = CommissionComputation.getCommForPeriod(list.get(i),startDate, endDate);
				double weeklyCommission = commissionData[1];
				
				newRow.add(String.format("%.2f", weeklyCommission));
				
				
				// 4. Add base pay and commission together to create total pay.
				
				double totalPay = weeklyPay+weeklyCommission;
				departmentPayrollTotal+=totalPay;

				//Add employee financial info row to payrollDB
				payrollDB.add(newRow);
				
			}
			
			// 5. Add total department pay together to create Total Payroll.
			
			ArrayList<String> newRow = new ArrayList<String>();
			newRow.add(String.format("%.2f", "Total Payroll:  " + departmentPayrollTotal));
			payrollDB.add(newRow);

			
		}
		
		
		
		
		
		public static void formatListHeader(String input)
		{
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(input);
			payrollDB.add(temp);
		}
		
	
}
