package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
/**
 * This is used to read and write data in a consistent format between the different functions.
 * It reads the data in as a Two dimensional ArrayList, of Strings. The first splitData.get(x) gives you the row, with an ArrayList of Strings, the second .get(y) will give you the column.
 * @author Adam Pine
 *
 */
public class FilesLab {
//	public static void main(String[] args) throws IOException {
//		ArrayList<ArrayList<String>> splitData = getArrayListFromFile(FilesLabConstants.EMPLOYEE_INPUT_FILE);
//		writeToFile(FilesLabConstants.EMPLOYEE_OUTPUT_FILE, splitData);
//	}
	/**
	 * Gets the file as one long string
	 * @param fileName - The name of the file that the program should look for.
	 * @return the file, as one string, separated by newlines.
	 */
	public String getFile(String fileName) {
		StringBuilder result = new StringBuilder("");
		Charset charset = Charset.forName("US-ASCII");
		Path path = FileSystems.getDefault().getPath(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
				result.append(line).append("\n");
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
		return result.toString();
	}
	
	public static ArrayList<ArrayList<String>> getArrayListFromFile(String fileName){
		FilesLab obj = new FilesLab();
		String data = obj.getFile(fileName);
		ArrayList<ArrayList<String>> splitData = new ArrayList<ArrayList<String>>();
		StringTokenizer st = new StringTokenizer(data, "\n");
		while (st.hasMoreTokens()){
			ArrayList<String> tempArrayList = new ArrayList<String>(Arrays.asList(st.nextToken().split("\\t")));
			splitData.add(tempArrayList);
		}
		return splitData;
	}
	
	public static void writeToFile(String fileName, ArrayList<ArrayList<String>> splitData) throws IOException{
		PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		for (ArrayList<String> row : splitData){
			for (String item : row){
				writer.print(item + "\t");
			}
			writer.print("\n");
		}
		writer.close();
	}
}
