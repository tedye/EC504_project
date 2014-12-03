package docIO;

import java.io.*;

public class File_Reader {
	private String fileName;
	BufferedReader br;
	FileInputStream fstream;
	
	/**
	 * Default constructor open a read file stream for input data 
	 */
	public File_Reader(){
		changeFileName("input.txt");
		try {
			fstream = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(fstream));
		} catch (FileNotFoundException e) {
			System.out.println("Error in opening input file.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor open specified read file stream for input data 
	 */	
	public File_Reader(String name){
		changeFileName(name);
		try {
			fstream = new FileInputStream(fileName);
			br = new BufferedReader(new InputStreamReader(fstream));
		} catch (FileNotFoundException e) {
			System.out.println("Error in opening input file.");
			e.printStackTrace();
		}
	}
	
	public void changeFileName(String name){
		fileName = new String(name);
	}
	
	public void closeFile(){
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in closing input file.");
		}
	}
	
	/**
	 *  return next line of the input file or Null if END-OF-FILE happens
	 * */
	public String nextLine(){
		String next = null;
		try {
			next = br.readLine();
		} catch (IOException e) {
			System.out.println("EOF!");
		}
		return next;
	}
	
	/**
	 *  return specified line wanted
	 * */
	public String getLine(int no){
		String target =  null;
		if (no < 1) return null;
		for (int i = 0; i < no; i++){
			target = nextLine();
		}
		return target;
	}

}
