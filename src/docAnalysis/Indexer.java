package docAnalysis;

import docAnalysis.PostList;
import docIO.File_Reader;
import docIO.File_Writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Indexer {
	private ArrayList<Map<String,PostList>> index;
	public int blockNo;
	public int blockSize;
	public int DocID;
	
	public Indexer(String target){
		blockNo = 0;
		blockSize = 65536;
		indexBuilder(target);
		//writeIndex();
	}
	
	private void indexBuilder(String in){
		File_Reader fr = new File_Reader(in);
		File_Writer fw = new File_Writer("block"+blockNo);
		index = new ArrayList<Map<String,PostList>>();
		index.add(new HashMap<String, PostList>());
		String test = fr.nextLine();
		DocID = 0;
		while (test != null){
			fw.writeLine(test);
			cleaner cl = new cleaner(test);
			cl.clean();
			for (String term : cl.terms){
				if (index.get(blockNo).get(term) == null){
					PostList pl = new PostList();
					pl.updateNode(new DocNode(DocID,1,null));;
					index.get(blockNo).put(term,pl);
				} else {
					index.get(blockNo).get(term).updateNode(new DocNode(DocID,1,null));
				}
			}
			
			test = fr.nextLine();
			DocID++;
			if (DocID % blockSize == 0 && DocID != 1){
				fw.closeFile();
				blockNo++;
				fw = new File_Writer("block"+blockNo);
				index.add(new HashMap<String, PostList>());
			}
			
		}
		fr.closeFile();
		fw.closeFile();
	}
	
	public final Set<String> getTerms(){
		Set<String> result = new HashSet<String>();
		for (Map<String, PostList>idx : index)
			result.addAll(idx.keySet());
		return result;
	}
	
	public final PostList getPostList(String term){
		PostList result = new PostList();
		for (Map<String,PostList>idx:index){
			if (idx.get(term)!=null)
				result = mergePL(result,idx.get(term));
		}
		return result;
	}
	/* moved to index reader */
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
	
	public PostList mergePL(PostList A, PostList B){
		if (A.isEmpty())
			return new PostList(B);
		else if (B.isEmpty())
			return new PostList(A);
		
		PostList temp_A = new PostList(A);
		PostList temp_B = new PostList(B);
		
		temp_A.tail.next = temp_B.head;
		temp_A.tail = temp_B.tail;
		
		return temp_A;	
	}
	
	public void writeIndex(){
		File_Writer fw1 = new File_Writer("index");
		File_Writer fw2 = new File_Writer("term_map");
		

		for (String s: getTerms()){
			fw2.writeLine(s);
			String temp = "";
			PostList docs = getPostList(s);
			DocNode t = docs.head;
			while (t != null){
				temp += ""+t.DocID+":"+t.count+" ";
				t = t.next;
			}
			fw1.writeLine(temp);

		}
		fw1.closeFile();
		fw2.closeFile();
	}
	
}
