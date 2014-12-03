package docAnalysis;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class is to "clean" the input string by
 * 1. delete the ending flip.it/xxxx 
 * 2. convert all to lower case
 * 3. replace all non alphabet numerical symbols with space
 * 4. get rid of stop words
 * 5. use PorterStemmer to stem the words
 */

public class cleaner {
	private String results;
	public ArrayList<String> terms;
	public cleaner(String s){
		int pos = s.indexOf("flip.it");
		if (pos != -1)
			results = s.substring(0,pos);
		else results = s;
		results =results.toLowerCase();
		results = results.replaceAll("[^0-9a-z]", " ");
	}
	
	public void clean(){
		StringTokenizer st = new StringTokenizer(results);
		PorterStemmer pst;
		terms = new ArrayList<String>();
		while (st.hasMoreElements()){
			String element = st.nextToken();
			pst = new PorterStemmer();
			pst.add(element.toCharArray(),element.length());
			pst.stem();
			//System.out.println(pst.toString());
			terms.add(pst.toString());
		}
	}
}
