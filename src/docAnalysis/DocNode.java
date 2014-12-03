package docAnalysis;

public class DocNode {
	public int DocID;		// document ID
	public int count;		// occurrence of terms
	public DocNode next;
	
	// constructor
	public DocNode(int DID,int count, DocNode n){
		this.DocID = DID;
		this.count = count;
		this.next = n;
	}
}
