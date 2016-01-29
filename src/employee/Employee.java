package employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import static com.wagnerandade.coollection.Coollection.*;
import main.FilesLabConstants;
import main.EmployeeColumnNameMappings;
import main.FilesLab;

/**
 * Used to manage the Employee Data.
 * 
 * @author Adam
 *
 */
public class Employee {
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

	public static void addEmployee() throws IOException {
		ArrayList<String> tempArrayList = new ArrayList<String>();
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (String columnName : splitData.get(0)) {
			System.out.println("Enter New " + columnName + ":");
			String line = br.readLine();
			tempArrayList.add(line);
		}
		System.out.println("Writing Employee To File");
		splitData.add(tempArrayList);
		FilesLab.writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
	}

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

	public static void modEmployee(String employeeID) throws IOException {
		ArrayList<String> newEmployeeArrayList = new ArrayList<String>();
		ArrayList<String> selectedEmployeeRow = new ArrayList<String>();
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
		ArrayList<ArrayList<String>> copySplitData = (ArrayList<ArrayList<String>>) splitData.clone();
		//get the selected row
		for (ArrayList<String> row : splitData){
			if (row.get(0).equals(employeeID)){
				selectedEmployeeRow = row;
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
		for (String item: selectedEmployeeRow){
			int currentIndex = selectedEmployeeRow.indexOf(item);
			if (currentIndex == column){
				newEmployeeArrayList.add(line);
			}else{
				newEmployeeArrayList.add(item);
			}
		}
		for (ArrayList<String> row : splitData){
			if (row.get(0).equals(employeeID)){
				splitData.set(splitData.indexOf(row), newEmployeeArrayList);
				break;
			}
		}
		FilesLab.writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
		
	}
}
