package docQuery;

import java.util.HashMap;
import java.util.Map;

import docAnalysis.DocNode;
import docAnalysis.PostList;
import docIO.File_Reader;

public class IndexReader {
	public Map<String,Integer> indexmap;
	
	public IndexReader(){
		indexmap = new HashMap<String,Integer>();
		File_Reader fr1 = new File_Reader("term_map");
		String temp = fr1.nextLine();
		int cnt = 1;
		while (temp != null){
			indexmap.put(temp, cnt);
			temp = fr1.nextLine();
			cnt++;
		}
		fr1.closeFile();
	}
	
	public PostList intersection(PostList A,PostList B){
		// return a post list with DocID intersected
		PostList intersect = new PostList();
		DocNode A_temp = A.head;
		DocNode B_temp = B.head;
		
		while (A_temp != null && B_temp != null){

			if (A_temp.DocID ==B_temp.DocID){
				intersect.updateNode(new DocNode(A_temp.DocID,A_temp.count+B_temp.count,null));
				A_temp = A_temp.next;
				B_temp = B_temp.next;
			} else if (A_temp.DocID > B_temp.DocID){
				B_temp = B_temp.next;
			} else { // A.DocID < B.DocID
				A_temp = A_temp.next;
			}
		}
		
		return intersect;
	
	}
	
	public PostList getPostList(String term){
		PostList result = new PostList();
		File_Reader fr2 = new File_Reader("index");
		String docs = fr2.getLine(indexmap.get(term));
		
		for (String temp:docs.split(" ")){
			int i = temp.indexOf(':');
			if (i != -1){
				DocNode t = new DocNode(Integer.parseInt(temp.substring(0,i)),Integer.parseInt(temp.substring(i+1)),null);
				result.updateNode(t);
			}
		}
		fr2.closeFile();
		return result;
	}
}
