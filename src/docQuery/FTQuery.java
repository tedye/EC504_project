package docQuery;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import docAnalysis.Indexer;
import docAnalysis.PostList;

public class FTQuery {
	private int matchSize;
	private ArrayList<Integer> resultDocIDs;
	public ArrayList<Integer> scores;
	
	public FTQuery(ArrayList<String> terms, Indexer idx){

		Queue<PostList> plq = new PriorityQueue<PostList>(10, new PostListLengthComp());
		int cnt = 0;
		matchSize = 0;
		PostList temp,temp1;
		
		if (terms.size() == 1){
			resultDocIDs = (new Ranker(idx.getPostList(terms.get(0)))).getResult();
		} else {
			while ( cnt < terms.size() && idx.getPostList(terms.get(cnt)) == null) cnt++;
			
			
			if (cnt != terms.size()) 
				for (int ii = cnt; ii < terms.size(); ii++){
					matchSize++;
					temp = idx.getPostList(terms.get(ii));
					if (!temp.isEmpty()) plq.add(temp);
					else continue;
				}
			
			if (matchSize > 1){
				temp = plq.poll();
				temp1 = plq.poll();
				while (temp != null && temp1 != null && temp.Length > 10){
					PostList temp2 = idx.intersection(temp, temp1);
					if (temp2.isEmpty()){
						temp1 = plq.poll();
						continue;
					} else {
						temp1 = plq.poll();
						temp = temp2;
					}
				} 
				
				//for (Integer id : temp.getDocIDs()) resultDocIDs.add(id);
				
				//resultDocIDs = (new TF_IDF_Ranker(terms, resultDocIDs)).getResult();
				Ranker rk = new Ranker(temp);
				resultDocIDs = rk.getResult();
				scores = rk.scores;
			}
		}
		
		

		/*
		resultDocIDs = new ArrayList<Integer>();
		matchSize = 0;
		int cnt = 0;
		PostList temp,temp1;
		
		long t2 = System.currentTimeMillis();
		while ( cnt < terms.size() && idx.getPostList(terms.get(cnt)) == null) cnt++;
		temp = idx.getPostList(terms.get(cnt));
		for (int ii = 1; ii < terms.size();ii++){
			long t = System.currentTimeMillis();
			temp1 = idx.getPostList(terms.get(ii));
			long ta = System.currentTimeMillis()-t;
			if (temp1 != null){
				temp = idx.intersection(temp, temp1);
				matchSize++;
			}
			long tb = System.currentTimeMillis()-ta-t;
			System.out.println("getpostlist time: "+ ta);
			System.out.println("true intersection time: "+ tb);
		}
		for (Integer id : temp.getDocIDs()) resultDocIDs.add(id);
		long t3 = System.currentTimeMillis() - t2;
		resultDocIDs = (new TF_IDF_Ranker(terms, resultDocIDs)).getResult();
		
		System.out.println("FT time: "+ t3);
		*/
	}
	
	public ArrayList<Integer> getResults(){
		return resultDocIDs;
	}
	
	public int getMatchSize(){
		return matchSize;
	}
}
