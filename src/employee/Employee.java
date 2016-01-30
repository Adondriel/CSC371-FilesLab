package employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import main.FilesLabConstants;
import main.EmployeeColumnNameMappings;
import main.FilesLab;

/**
 * Used to manage the Employee Data.
 * 
 * @author Adam Pine
 *
 */
public class Employee {
	/**
	 * Takes in the cmdline args, and directs control to the correct method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		if ((args.length != 0)) {
			if (args[0].equals("help")) {
				System.out.println("Available command arguments are:");
				System.out.println("    add");
				System.out.println("    del <employee ID>");
				System.out.println("    mod <employee ID>");
			}
			if (args[0].equalsIgnoreCase("add")) {
				addEmployee();
			}
			if (args.length > 1) {
				if (args[0].equalsIgnoreCase("del")) {
					delEmployee(args[1]);
				}
				if (args[0].equalsIgnoreCase("mod")) {
					modEmployee(args[1]);
				}
			}
		}
	}
	/**
	 * Prompts the user to add an employee.
	 * Does this by looping through the very first row to find the column names,
	 * for each column name, it asks for the new value then adds that new value to a
	 * secondary arraylist that stores our new row.
	 * @throws IOException
	 */
	public static void addEmployee() throws IOException {
		ArrayList<String> newRow = new ArrayList<String>();
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (String columnName : splitData.get(0)) {
			System.out.println("Enter New " + columnName + ":");
			String line = br.readLine();
			newRow.add(line);
		}
		System.out.println("Writing Employee To File");
		splitData.add(newRow);
		FilesLab.writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
	}
	/**
	 * Deletes the specified employee from the table.
	 * @param employeeID
	 * @throws IOException
	 */
	public static void delEmployee(String employeeID) throws IOException {
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
		ArrayList<ArrayList<String>> copySplitData = (ArrayList<ArrayList<String>>) splitData.clone();
		for (ArrayList<String> row : copySplitData) {
			if (row.get(0).equals(employeeID)) {
				splitData.remove(row);
				System.out.println("Removing: " + row.get(EmployeeColumnNameMappings.ID.ordinal()) + " "
						+ row.get(EmployeeColumnNameMappings.LASTNAME.ordinal()) + " "
						+ row.get(EmployeeColumnNameMappings.FIRSTNAME.ordinal()));
			}
		}
		FilesLab.writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
	}
	/**
	 * Allows the user to modify a row in the employee table
	 * @param employeeID
	 * @throws IOException
	 */
	public static void modEmployee(String employeeID) throws IOException {
		ArrayList<String> selectedEmployeeRow = new ArrayList<String>();
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
		int selectedIndex = -1;
		//get the selected row and selected index.
		for (ArrayList<String> row : splitData){
			if (row.get(0).equals(employeeID)){
				selectedEmployeeRow = row;
				selectedIndex = splitData.indexOf(row);
				break;
			}
		}
		//Ask them what column they want to change.
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("What column would you like to change?");
		String line = br.readLine();
		//turn the name, into a number
		int column = EmployeeColumnNameMappings.valueOf(line.toUpperCase()).ordinal();
		System.out.println("Enter new value:");
		//get their new value
		line = br.readLine();
		//Set the selected row's column value, to the new value.
		selectedEmployeeRow.set(column, line);
		//Find the original row, and get it's index.
		splitData.set(selectedIndex, selectedEmployeeRow);
		FilesLab.writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
		System.out.println("Updated Row: " + splitData.get(selectedIndex));
	}
}
