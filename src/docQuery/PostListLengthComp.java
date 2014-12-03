package docQuery;

import java.util.Comparator;

import docAnalysis.PostList;

public class PostListLengthComp implements Comparator<PostList>{
	@Override  // This is overriding the compare method
	public int compare(PostList pl1, PostList pl2){
		return pl1.Length - pl2.Length;
	}

}
