package docAnalysis;

import java.util.ArrayList;

import docAnalysis.DocNode;;

public class PostList {
	public int Length;
	public DocNode head,tail;
	// constructor
	public PostList(){
		Length = 0; 
		tail = null;
		head = null;
	}
	// Copy constructor
	
	public PostList(PostList target){
		if (target.isEmpty()){
			Length = 0; 
			tail = null;
			head = null;
		} else {
			Length = target.Length; 
			DocNode temp = target.head;
			DocNode temp1= new DocNode(temp.DocID,temp.count,null);
			head = temp1;
			while (temp.next != null){
				temp = temp.next;
				temp1.next = new DocNode(temp.DocID,temp.count,null);
				temp1 = temp1.next;
			}
			tail = temp1;
		}
	}
	
	public void updateNode(DocNode node){
		// add new node and keep the descending order.
		DocNode temp = head;
		
		// case 1: first node
		if (head == null){
			head = new DocNode(node.DocID,node.count,tail);
			Length++;
			tail = head; 
			return;
		}
		
		// case 2: head docId >= adding node docID
		if (head.DocID > node.DocID){
			head= new DocNode(node.DocID,node.count,head);
			Length++;
			return;
		} else if (head.DocID == node.DocID) {
			head.count = head.count+node.count;
			return;
		}
		// case 3: head docId < adding node docID
		DocNode temp1 = temp;
		while (temp.DocID < node.DocID && temp.next != null){
			temp1 = temp;
			temp =  temp.next;
		}
		
		if (temp.DocID > node.DocID){
			temp1.next = new DocNode(node.DocID,node.count,temp);
			Length++;
			return;
		} else if (temp.DocID == node.DocID){
			temp.count = temp.count+node.count;
		} else if (temp.next == null){
			temp.next = new DocNode(node.DocID,node.count, null);
			Length++;
			tail = temp.next;
			return;
		}
	}
	
	public void TraverseList(){
		DocNode temp = head;
		while (temp != null){
			System.out.println("DocID: "+temp.DocID+" count: "+temp.count);
			temp = temp.next;
		}
	}
	
	public ArrayList<Integer> getDocIDs(){
		ArrayList<Integer> IDs = new ArrayList<Integer>();
		DocNode temp = head;
		while(temp != null){
			IDs.add(temp.DocID);
			temp = temp.next;
		}
		return IDs;
	}
	
	public boolean isEmpty(){
		return head == null;
	}
}
