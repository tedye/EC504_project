package docQuery;

import java.util.Comparator;

import docAnalysis.DocNode;

public class DocNodeComp implements Comparator<DocNode>{
	@Override  // This is overriding the compare method
	public int compare(DocNode n1, DocNode n2){
		return n2.count - n1.count;
	}
}
