package docQuery;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import docAnalysis.DocNode;
import docAnalysis.PostList;

public class Ranker {
	private ArrayList<Integer> results;
	
	public Ranker(PostList docs){
		Queue<DocNode> pqNode = new PriorityQueue<DocNode>(10, new DocNodeComp());
		DocNode temp = docs.head;
		results = new ArrayList<Integer>();
		while(temp != null){
			pqNode.add(temp);
			temp = temp.next;
		}
		
		temp = pqNode.poll();
		int cnt = 0;
		while (temp != null && cnt < 10){
			results.add(temp.DocID);
			temp = pqNode.poll();
			cnt++;
		}
	}
	public ArrayList<Integer> getResult(){
		return results;
	}
}
