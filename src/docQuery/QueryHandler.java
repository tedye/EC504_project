package docQuery;

import java.util.ArrayList;

import docAnalysis.Indexer;
import docAnalysis.cleaner;

public class QueryHandler {
	private ArrayList<Integer> DocIDs;
	public QueryHandler(String Q, Indexer idx){
		cleaner cl = new cleaner(Q);
		cl.clean();
		DocIDs = (new FTQuery(cl.terms,idx)).getResults();
	}
	
	public ArrayList<Integer> getDocIDs(){
		return DocIDs;
	}
}
