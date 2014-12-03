package docGUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import docAnalysis.Indexer;
import docIO.File_Reader;
import docQuery.IndexReader;
import docQuery.QueryHandler;

public class GUITest extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel myPanel;
	private JButton searchButton,initButton;
	private JTextField myQueryText, myInputFile;
	private ArrayList<JLabel> result;
	private Indexer idx;
	
	public GUITest(){
		myPanel = new JPanel();
		myQueryText = new JTextField();
		myQueryText.setEditable(true);
		myQueryText.setSize(1000, 50);
		myInputFile = new JTextField();
		myInputFile.setEditable(true);
		myQueryText.setText("initialize engine first");
		myInputFile.setText("input source file");
		searchButton = new JButton("Search!");
		searchButton.addActionListener(this);
		initButton = new JButton("initialize Search Engine");
		initButton.addActionListener(this);

		
		Box bh = Box.createHorizontalBox();
		bh.add(myQueryText);
		bh.add(searchButton);
		
		Box bh1 = Box.createHorizontalBox();
		bh1.add(myInputFile);
		bh1.add(initButton);
		
		//Box bv0 = Box.createVerticalBox();
		//bv0.setSize(1200, 60);
		//bv0.add(bh);
		//bv0.add(bh1);
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));
		myPanel.setBounds(0, 0, 1200, 60);
		myPanel.add(bh);
		myPanel.add(bh1);
		
		Box bv = Box.createVerticalBox();
		result = new ArrayList<JLabel>();
		for (int ii = 0; ii < 10; ii++ ){
			JLabel temp = new JLabel();
			temp.setText(""+ii);
			result.add(temp);
			bv.add(temp);
		}
		
		
		Box bv1 = Box.createVerticalBox();
		bv1.add(myPanel);
		bv1.add(bv);
		
		//myPanel.add(bv1);
		//this.add(myPanel);
		this.add(bv1);
	}
	
	public void run_init(){
		myQueryText.setText("initialize...");
		long t = System.currentTimeMillis();
		idx = new Indexer(myInputFile.getText());
		System.out.println("indexing time:");
		System.out.println(System.currentTimeMillis() - t);
		myQueryText.setText("keyword1 keyword2 ...");
		//idx = new IndexReader();
	}
	
	@Override
	public void actionPerformed(ActionEvent event){
		if (event.getSource()== searchButton){
			String Q = myQueryText.getText();
			
			for (int ii = 0; ii < 10; ii++ ){
				result.get(ii).setText(" ");
			}
			
			long start1 = System.currentTimeMillis();
			QueryHandler QH = new QueryHandler(Q,idx);
			
			long start2 = System.currentTimeMillis();
			Integer cnt = 0;
			for (Integer i : QH.getDocIDs()){
				int blockNo = i / 65536;
				File_Reader fr = new File_Reader("block"+blockNo);
				String content = fr.getLine(i%65536 + 1);
				result.get(cnt).setText(content);
				fr.closeFile();
				cnt++;
			}
			System.out.println("Query time:");
			System.out.println(start2-start1);
			System.out.println("IO time:");
			System.out.println(System.currentTimeMillis() - start2);
		} else if (event.getSource()== initButton){
			run_init();
		}
	}

	public static void main(String[] args) {
		GUITest t = new GUITest();
		t.setTitle("Simple Twitter Search Engine");
		t.setSize(1200,600);
		t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t.setVisible(true);
		//t.run_init();
	}

}
