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

public class FilesLab {
	public static void main(String[] args) throws IOException {
		ArrayList<ArrayList<String>> splitData = getArrayListFromFile("employees.txt");
		writeToFile("testoutput.txt", splitData);
	}

	public String getFile(String fileName) {
		StringBuilder result = new StringBuilder("");
		Charset charset = Charset.forName("US-ASCII");
		Path path = FileSystems.getDefault().getPath(fileName);
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
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
				writer.print(item + " ");
			}
			writer.print("\n");
		}
		writer.close();
	}
}
