import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.SortingFocusTraversalPolicy;

public class FilesLab {
	public static void main(String[] args) throws IOException {
		// InputStream inputStream = FilesLab.class.getResourceAsStream();
		// BufferedReader file = new BufferedReader(new
		// FileReader("employees.txt"));
		FilesLab obj = new FilesLab();
		String data = obj.getFile("employees.txt");
		ArrayList<ArrayList<String>> splitData = new ArrayList<ArrayList<String>>();
		StringTokenizer st = new StringTokenizer(data, "\n");
		while (st.hasMoreTokens()) {
			ArrayList<String> tempArrayList = new ArrayList<String>(Arrays.asList(st.nextToken().split("\\t")));
			splitData.add(tempArrayList);
		}
		for (ArrayList<String> row : splitData) {
			System.out.println("");
			for (String item : row) {
				System.out.println(splitData.get(splitData.indexOf(row)).get(row.indexOf(item)));
			}
		}
		for (ArrayList<String> row : splitData) {
			System.out.println(row);
		}
		System.out.println(splitData.get(5).get(5));

	}

	private String getFile(String fileName) {

		StringBuilder result = new StringBuilder("");

		// Get file from resources folder
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}

			scanner.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.toString();

	}

}
