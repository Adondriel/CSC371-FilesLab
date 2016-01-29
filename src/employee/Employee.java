package employee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import main.FilesLab;

public class Employee {
	public static void main(String[] args) throws IOException {
		if ((args.length != 0)) {
			if (args[0].equals("add")) {
				addEmployee();
			}
			if (args.length > 1 && args[0].equals("del")) {
				
			}
		}
	}

	public static void addEmployee() throws IOException {
		ArrayList<String> tempArrayList = new ArrayList<String>();
		ArrayList<ArrayList<String>> splitData = FilesLab.getArrayListFromFile("employees.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		for (String columnName : splitData.get(0)) {
			System.out.println("Enter New " + columnName + ":");
			String s = br.readLine();
			tempArrayList.add(s);
		}
		splitData.add(tempArrayList);
		FilesLab.writeToFile("testoutput.txt", splitData);
	}
	
	public static void delEmployee(){
		
	}
}
