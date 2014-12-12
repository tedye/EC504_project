package docGUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import docAnalysis.Indexer;
import docIO.File_Reader;
import docQuery.QueryHandler;

public class Cmd_interface {
	
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		Indexer idx;
		try{
			boolean quit = false;
			while (!quit){
				System.out.println("Enter twitter file to search or type quit to leave:");
				String text = in.readLine();// read in the input file name
				if (text.equals("quit")){
					quit = true;
				} else {
					System.out.println("indexing start!");
					long t = System.currentTimeMillis();
					idx = new Indexer(text);  // do the indexing
					System.out.println("indexing time:");
					System.out.println(System.currentTimeMillis() - t);
					
					boolean done = false;
					while (!done){
						System.out.println("Enter keywords to search:");
						String query = in.readLine();// read in a qurey
						if (query.equals("quit")){
							done = true;
						} else {
							String Q = query;
							
							long start = System.currentTimeMillis();
							QueryHandler QH = new QueryHandler(Q,idx);
							
							int cnt = 1;
							for (Integer i : QH.getDocIDs()){
								int blockNo = i / 65536;
								File_Reader fr = new File_Reader("block"+blockNo);
								String content = fr.getLine(i%65536 + 1);
								Integer score = QH.scores.get(cnt-1);
								System.out.println("["+cnt+"]:"+content+" | score : "+ score);
								fr.closeFile();
								cnt++;
							}
							System.out.println("Query time:");
							System.out.println(System.currentTimeMillis() - start);
						}	
					}
					System.out.println("done");
					
				}	
			}
			System.out.println("done");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
