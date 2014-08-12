package bookCheck1;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Blob;

public class CheckOutPanel1 extends JPanel implements ActionListener{
	private JTextField textField;
	private JComboBox searchByCBox;
	private JComboBox viewCBox;
	private JComboBox sortCBox;
	String[] searchList1={"students","fname","lname","sgroup","books","pic","studentsid="};
	String[] searchList2={"books","title","ISBN","readinglvl","duedate","coverpic","booksid="};
	public String[] itemLabel={"FirstName","LastName","Reading Level: ##","Books Checked Out: ##"};
	public String viewMode="Students";
	public JPanel panel=new JPanel();
	public JPanel sPanel;
	public JPanel card1;
	public JPanel card2;
	public ListItem listItem;
	public String addMess="";
	public String[] orgList;
	public String[] searchList;
	public JScrollPane scrollPane;
	
	public CheckOutPanel1(){	
		gui();
		getItems();
	}
	public void init(){
		String[] orgList1 = { "First Name", "Last Name","Group","Books Checked Out"};
		String[] orgList2 = { "Title", "Checked Out","Available"};
		//String[] searchList1={"students","fname","lname","sgroup","books","pic","studentsid="};
		//String[] searchList2={"books","title","ISBN","readinglvl","duedate","coverpic","booksid="};
		
		//System.out.println("init");
		if(viewMode=="Students"){			
			orgList=orgList1;
			addMess="Add Student";
			searchList=searchList1;
		}
		else{			
			orgList=orgList2;
			addMess="Add Book";	
			searchList=searchList2;
		}	
		//set string arrays here according to mode
	}
	public void gui() {	
		String[] bkStdntList = {"Students","Books"};
		
		listItem=new ListItem();
		
		init();
		
		setBackground(new Color(0, 0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		
		//JPanel panel = new JPanel();
		panel.setBackground(Color.ORANGE);
		panel.setForeground(Color.BLACK);
		panel.setBounds(10, 30, 875, 130);
		add(panel);
		panel.setLayout(null);
			
		//menu bar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 895, 20);
		add(menuBar);	
		//File
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);			
		JMenu checkMItem = new JMenu("Check Book");
		fileMenu.add(checkMItem);	
		JMenuItem inMItem=new JMenuItem("In");
		checkMItem.add(inMItem);
		JMenuItem outMItem=new JMenuItem("Out");
		checkMItem.add(outMItem);
		JMenu addMItem = new JMenu("Add");
		fileMenu.add(addMItem);
		JMenuItem bookMItem=new JMenuItem("Book");
		addMItem.add(bookMItem);
		JMenuItem studMItem=new JMenuItem("Student");
		addMItem.add(studMItem);
		fileMenu.addSeparator();
		JMenuItem exitMItem = new JMenuItem("Exit");
		fileMenu.add(exitMItem);
		//Options
		JMenu optionMenu = new JMenu("Options");
		menuBar.add(optionMenu);
		//Help
		//TODO Contact Info to help
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		//page title
		JLabel title = new JLabel("Book Check");
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setBounds(362, 0, 164, 49);
		panel.add(title);		

		//Search things
		JButton submitBttn = new JButton("Submit");
		submitBttn.setBounds(208, 100, 89, 23);
		panel.add(submitBttn);		
		JLabel srchLbl = new JLabel("Search:");
		srchLbl.setBounds(10, 76, 46, 14);
		panel.add(srchLbl);
		textField = new JTextField();
		textField.setBounds(82, 73, 116, 20);
		panel.add(textField);
		textField.setColumns(10);		
		JLabel searchByLbl = new JLabel("Search By: ");
		searchByLbl.setBounds(10, 104, 70, 14);
		panel.add(searchByLbl);		
		JComboBox searchByCBox = new JComboBox(orgList);
		searchByCBox.setBounds(82, 100, 116, 20);
		panel.add(searchByCBox);
		
		//view things		
		JLabel viewLbl = new JLabel("View:");
		viewLbl.setBounds(700, 75, 46, 14);
		panel.add(viewLbl);		
		viewCBox = new JComboBox(bkStdntList);
		viewCBox.setBounds(750, 75, 116, 20);
		panel.add(viewCBox);		
		JLabel sortLbl = new JLabel("Sort By:");
		sortLbl.setBounds(700, 100, 46, 14);
		panel.add(sortLbl);		
		JComboBox sortCBox = new JComboBox(orgList);
		sortCBox.setBounds(750, 100, 116, 20);
		panel.add(sortCBox);
		final JButton addBttn = new JButton(addMess);
		addBttn.setBounds(565, 94, 116, 29);
		panel.add(addBttn);
		
		//List 		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 170, 894, 500);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);	
		
		add(scrollPane);
		
		//Action Lsiteners
		//Menu Items
		inMItem.setActionCommand("In");
		inMItem.addActionListener(this);
		outMItem.setActionCommand("Out");
		outMItem.addActionListener(this);
		bookMItem.setActionCommand("Add Book");
		bookMItem.addActionListener(this);
		studMItem.setActionCommand("Add Student");
		studMItem.addActionListener(this);
		exitMItem.setActionCommand("Exit");
		exitMItem.addActionListener(this);
		//Search Things
		searchByCBox.setActionCommand("combo.search");
		searchByCBox.addActionListener(this);
		submitBttn.setActionCommand("Submit");
		submitBttn.addActionListener(this);
		//View Things
		viewCBox.setActionCommand("combo.view");
		viewCBox.addActionListener(this);
		sortCBox.setActionCommand("combo.sort");
		sortCBox.addActionListener(this);
		addBttn.setActionCommand(addMess);
		addBttn.addActionListener(this);
	}
	public void sort(){
		//TODO
	}
	public void getBooks(){
		System.out.println("Books Updated: "+viewMode);
		reformat(card2);
		populate(card2,searchList2);
	}
	public void getStuds(){
		System.out.println("Students Updated: "+viewMode);
		reformat(card1);
		populate(card1,searchList1);
	}
	public void getItems(){
		//String[] searchList1={"students","fname","lname","sgroup","books","studentsid="};
		//String[] searchList2={"books","title","ISBN","readinglvl","duedate","booksid="};
				
		sPanel=new JPanel(new CardLayout());
		sPanel.setBounds(0,0,900,550);
		
		card1=new JPanel();
		card1.setBounds(0,0,900,550);
		card1.setLayout(new BoxLayout(card1, BoxLayout.PAGE_AXIS));		
		populate(card1,searchList1);		
		sPanel.add(card1,"Students");
		
		card2=new JPanel();
		card2.setBounds(0,0,900,550);
		card2.setLayout(new BoxLayout(card2, BoxLayout.PAGE_AXIS));
		populate(card2,searchList2);
		sPanel.add(card2,"Books");

		scrollPane.setViewportView(sPanel);
	}
	
	public void populate(JPanel cardPanel,String[] searchList){
		char[] students=null;
		String queryStr=null,temp="";
		String[] itemList={};
		itemList=new String[6];
		java.sql.Blob imageBlob = null;
		//TODO use LIMIT 1 on sql statements to speed it up
		//get students from teacher		
		ResultSet query=DBConnect.queryDB(searchList[0],"users","usersid="+Globals.userid+" Limit 0, 1");	
		
		try {
			while (query.next()){
				queryStr=query.getString(searchList[0]);
				students=queryStr.toCharArray();
				}			
		} catch (SQLException e) {System.out.println("Error: "+e);}
		//get student info and put into list
		for(int i=0;i<queryStr.length();i++){
			if(students[i]==' '){
				ResultSet studentQuery=DBConnect.queryDB(searchList[1]+","+searchList[2]+","+
						searchList[3]+","+searchList[4]+","+searchList[5],searchList[0],searchList[6]+temp);				
				try {
					while (studentQuery.next()){
						itemList[0]=studentQuery.getString(searchList[1]);
						itemList[1]=studentQuery.getString(searchList[2]);
						itemList[2]=studentQuery.getString(searchList[3]);
						//TODO query for books
						itemList[3]=studentQuery.getString(searchList[4]);
						imageBlob =studentQuery.getBlob(searchList[5]);
						//InputStream binaryStream = imageBlob.getBinaryStream(0, imageBlob.length());
						itemList[4]=temp;
						itemList[5]=searchList[0];
						}			
				} catch (SQLException e) {System.out.println("Error: "+e);}
				//System.out.println(itemList[0]);
				temp="";
				JPanel item=listItem.listGui(itemList,viewMode,imageBlob);
				cardPanel.add(item);
			}
			else{temp+=students[i];}
		}
	}
    public void reformat(JPanel panel){
    	panel.removeAll();
    	panel.revalidate();
    	panel.repaint();
    	   	
      	CardLayout cl = (CardLayout) (sPanel.getLayout());
      	System.out.println(viewMode);
    	cl.show(sPanel,viewMode);
    	sPanel.revalidate();
    	
    	gui();
    }
    public void actionPerformed(ActionEvent e) {    
    	//TODO 
    	//add in all the comboboxes and buttons
    	if(e.getActionCommand().contains("combo")){
    		if(e.getActionCommand()=="combo.view"){
    			JComboBox cb = (JComboBox)e.getSource();
    			String newSelection = (String)cb.getSelectedItem();
    			System.out.println((String)cb.getSelectedItem());
    			//cb.setSelectedItem((String)cb.getSelectedItem());    			
    			viewCBox.setSelectedItem("Books");
    			//TODO this ^ is not working
    			viewMode=newSelection;  
    			reformat(panel);}
    	}
    	else{fireEvent(e.getActionCommand());}
    }
	//These 3 methods talk to BoookCheckWindow and PanelListener
	public void fireEvent(String event){
		Object[] listeners=listenerList.getListenerList();
		for(int i=0;i<listeners.length;i+=2){
			if(listeners[i]==PanelListener.class){
				((PanelListener)listeners[i+1]).eventHappen(event);
			}
		}
	}
	public void addDetailListener(PanelListener listener){
		listenerList.add(PanelListener.class, listener);
	}
	public void rmDetailListener(PanelListener listener){
		listenerList.remove(PanelListener.class, listener);
	}
}