package docQuery;

import java.util.ArrayList;

import docAnalysis.Indexer;
import docAnalysis.cleaner;

public class QueryHandler {
	private ArrayList<Integer> DocIDs;
	public ArrayList<Integer> scores;
	public QueryHandler(String Q, Indexer idx){
		cleaner cl = new cleaner(Q);
		cl.clean();
		FTQuery ftq = new FTQuery(cl.terms,idx);
		DocIDs = ftq.getResults();
		scores = ftq.scores;
	}
	
	public ArrayList<Integer> getDocIDs(){
		return DocIDs;
	}
}
