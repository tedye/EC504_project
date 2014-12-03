package docIO;

import java.io.*;

public class File_Writer {
	private String fileName;
	PrintWriter pw;
	
	public File_Writer(String pathname){
		fileName = pathname;

		try {
			pw = new PrintWriter(new FileWriter(fileName));
		} catch (IOException e) {
			System.out.println("Error in openning output file!");
			e.printStackTrace();
		}

	}
	
	public void closeFile(){
		pw.close();
	}
	
	public void writeLine(String line){
		pw.write(line+"\n");
	}
}
