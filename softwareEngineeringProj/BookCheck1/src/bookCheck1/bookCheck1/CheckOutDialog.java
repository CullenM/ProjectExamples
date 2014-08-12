package bookCheck1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

public class CheckOutDialog extends ViewPanel{
	private JDialog checkOut;
	private JDialog dateD;
	private JButton button=new JButton();
	private String thisID="";
	
	public  CheckOutDialog(){}
	public void start(String id){
		thisID=id;
		checkOut=new JDialog(BookCheckWindow.frame,"Check Out Book");
		checkOut.getContentPane().add(createPane()); 
		checkOut.setResizable(false);
		checkOut.setSize(500,400);
		checkOut.setVisible(true);
		checkOut.setLocation(350,100);
	}
	
	public class ButtonEditor extends DefaultCellEditor {
		private String label;
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}
		public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column) {
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			return button;
		}
	}
	public class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "" : value.toString());
			return this;
		}

	}
	private Container createPane(){
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel listPanel = listP();		
		JPanel qrPanel = qrP();
		JPanel numPanel=numP();
		
		tabbedPane.addTab("List",null, listPanel,"List of Books");
		tabbedPane.addTab("QR Code",null, qrPanel,"Scan QR code of book");
		tabbedPane.addTab("ID Number",null, numPanel,"Enter book ID number");
		return tabbedPane;  
	}
	private JPanel qrP(){
		JPanel qrPanel = new JPanel();  
		qrPanel.setLayout(null);
		qrPanel.setBackground(Color.ORANGE); 
		
		Image pic=Layout.getImage(Globals.noPic);
		Image scaledpic = pic.getScaledInstance(150,150,Image.SCALE_SMOOTH);
		JLabel picPanel = new JLabel(new ImageIcon(scaledpic));
		picPanel.setBounds(175, 15, 150, 150);
		qrPanel.add(picPanel);
		
		JButton picBttn = new JButton("Take Picture"); 
		picBttn.setBounds(175,175,150,25);
		picBttn.setForeground(Color.BLACK);
		picBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		qrPanel.add(picBttn);
		
		JLabel bInfo=new JLabel();
		bInfo.setBounds(200,220,100,25);
		bInfo.setForeground(Color.BLACK);
		bInfo.setFont(new Font("Tahoma", Font.BOLD, 16));
		bInfo.setVisible(false);
		qrPanel.add(bInfo);
		
		JButton b2 = new JButton("Submit"); 
		b2.setBounds(200,260,100,25);
		b2.setForeground(Color.BLACK);
		b2.setFont(new Font("Tahoma", Font.BOLD, 16));
		qrPanel.add(b2);
		
		JButton cancel=new JButton("Cancel");
		cancel.setBounds(200,300,100,25);
		cancel.setForeground(Color.BLACK);
		cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		qrPanel.add(cancel);
		cancel.setActionCommand("Cancel");
		cancel.addActionListener(this);
		
		picBttn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						//TODO take pic & decode it
					}
		});
		b2.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						//TODO subimt to checkout
					}
		});
		
		return qrPanel;
	}
	private JPanel numP(){
		String bTit=null,rLvl=null;
		
		JPanel numPanel = new JPanel();  
		numPanel.setBackground(Color.ORANGE);
		numPanel.setLayout(null);
		
		JLabel title=new JLabel("Enter Book ID");
		title.setBounds(200,25,200,25);
		title.setForeground(Color.BLACK);
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		numPanel.add(title);
		
		final JTextField numTxt=new JTextField();
		numTxt.setBounds(175,60,150,25);
		numPanel.add(numTxt);
		
		JButton b3 = new JButton("Submit"); 
		b3.setBounds(200,110,100,25);
		b3.setForeground(Color.BLACK);
		b3.setFont(new Font("Tahoma", Font.BOLD, 16));
		numPanel.add(b3);
		
		JButton cancel=new JButton("Cancel");
		cancel.setBounds(200,300,100,25);
		cancel.setForeground(Color.BLACK);
		cancel.setFont(new Font("Tahoma", Font.BOLD, 16));
		numPanel.add(cancel);
		cancel.setActionCommand("Cancel");
		cancel.addActionListener(this);
		
		b3.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						String  bTit=null,rLvl=null;
						ResultSet query=DBConnect.queryDB("title,readinglvl","books","booksid='"+numTxt.getText()+"' LIMIT 1");
						try {while(query.next()){bTit=query.getString("title");
							rLvl=query.getString("readinglvl");}}
						catch(SQLException e) {e.printStackTrace();}
						
						String info[]={numTxt.getText(),bTit,rLvl};
						System.out.println(info[0]+" "+info[1]+" "+info[2]);
						dateDialog(info);
					}
		});
		
		return numPanel;
	}
	private JPanel listP(){
		String temp="",books="";
		int count=0;
		Object[][] cells = new Object[512][4];
		String[] columnNames = {"ID","Title","Reading Level","Check Out"};
		
		JPanel listPanel = new JPanel();  
		listPanel.setLayout(null);
		listPanel.setBackground(Color.ORANGE); 
		
		JLabel title =new JLabel("Check Out a Book");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setBounds(0,0,200,25);
		listPanel.add(title);
		
		JButton b1 = new JButton("Cancel");  
		b1.setBounds(390,0,100,25);
		listPanel.add(b1);  
		b1.setActionCommand("Cancel");
		b1.addActionListener(this);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 30,490,320);
		listPanel.add(scrollPane);
		
        final JTable table = new JTable(cells, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(450, 350));
        table.setFillsViewportHeight(true);
		
		JButton Bttn=new JButton("Button");
		
		ResultSet query=DBConnect.queryDB("books","users","usersid="+Globals.userid+" LIMIT 1");
		try {while(query.next()){books=query.getString("books");}}
		catch(SQLException e) {e.printStackTrace();}
		
		char[] ids= books.toCharArray();
		for(int i=0;i<books.length();i++){
			if(ids[i]==' '){
				ResultSet itemQuery=DBConnect.queryDB("title,readinglvl,duedate","books","booksid="+temp+" LIMIT 1");
				
				try {
					while (itemQuery.next()){
						Color check=Layout.checkDate(itemQuery.getString("duedate"));
						if(check==Color.BLACK){
							cells[count][0]=temp;
							cells[count][1]=itemQuery.getString("title");
							cells[count][2]=itemQuery.getString("readingLvl");
							cells[count][3]=("Check Out");
							count++;
						}
					}			
				} catch (SQLException e) {System.out.println("Error: "+e);}
				temp="";				
			}
			else{temp+=ids[i];}
		}        
		
		table.getColumn("Check Out").setCellRenderer(new CheckOutDialog.ButtonRenderer());
		table.getColumn("Check Out").setCellEditor(new CheckOutDialog.ButtonEditor(new JCheckBox()));
		
		button.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						//System.out.println();
						String[] info={(String) table.getValueAt(table.getSelectedRow(),0),
								(String) table.getValueAt(table.getSelectedRow(),1),
								(String) table.getValueAt(table.getSelectedRow(),2)};
						dateDialog(info);
						//JOptionPane.showMessageDialog(null,table.getValueAt(table.getSelectedRow(),0));
					}
		});
		
		scrollPane.setViewportView(table);

		return listPanel;
	}
	private void dateDialog(final String[] info){
		String[] dateList={"1 week","2 weeks","3 weeks","4 weeks"};
    	final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	final Calendar cal = Calendar.getInstance();
		
		dateD=new JDialog(BookCheckWindow.frame,"Choose Due Date");		
		dateD.setResizable(false);
		dateD.setSize(300,200);
		dateD.setVisible(true);
		dateD.setLocation(350,100);
		
		JPanel datePanel=new JPanel();
		datePanel.setSize(300,200);
		datePanel.setLayout(null);
		datePanel.setBackground(Color.ORANGE); 
		
		final JComboBox dateCBox = new JComboBox(dateList);
		dateCBox.setBounds(87, 50, 125, 25);
		datePanel.add(dateCBox);
		
		JLabel title=new JLabel("Choose when book will be due.");
		title.setBounds(30, 10, 225, 25);
		title.setFont(new Font("Tahoma",1, 14));
		datePanel.add(title);
		
		JButton submitBttn=new JButton("Submit");
		submitBttn.setBounds(87, 125, 125, 35);
		datePanel.add(submitBttn);
		
		dateD.setContentPane(datePanel);

		submitBttn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
				        int selected = dateCBox.getSelectedIndex();
						cal.add(Calendar.DATE, (selected+1)*7);
						String dueD = dateFormat.format(cal.getTime());						    
						boolean chck=checkout(thisID,info[0],dueD);
						if(chck==true){
							//dateD.dispose();
							Object[] newRowData;
							newRowData = new Object[] {info[0],info[1],info[2],dueD,"Check In"};
							defTModel.insertRow(0, newRowData);
							dateD.dispose();
							BookCheckWindow.chckOut1P.getBooks();
							checkOut.dispose();
						}
					}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand()=="Cancel"){checkOut.dispose();}
	}
}
