import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.SortingFocusTraversalPolicy;

public class FilesLab {
	public static void main(String[] args) throws IOException {
		// InputStream inputStream = FilesLab.class.getResourceAsStream();
		//BufferedReader file = new BufferedReader(new FileReader("employees.txt"));
		FilesLab obj = new FilesLab();
		String data = obj.getFile("employees.txt");
		String[] splitData= data.split("\\t");
		System.out.println(splitData);
		
//		StringTokenizer st = new StringTokenizer(data);
//		 while (st.hasMoreTokens()) {
//		     System.out.println(st.nextToken());
//		 }
		//System.out.println(obj.getFile("employees.txt"));
		//file.close();
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
