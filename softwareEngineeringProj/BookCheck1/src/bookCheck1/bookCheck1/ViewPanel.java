package bookCheck1;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class ViewPanel extends SuperClass implements ActionListener{
	private JLabel Lbl1;
	private JLabel Lbl2;
	private JLabel Lbl3;
	private JLabel Lbl4;
	private JLabel Lbl5;
	private JLabel Lbl6;
	private JLabel Lbl7;
	private JTextField TxtFld1;
	private JTextField TxtFld2;
	private JTextField TxtFld3;
	private JTextField TxtFld4;
	private JTextField TxtFld5;
	private JTextField TxtFld6;
	private JTextField TxtFld7;
	private JButton updateBttn;
	private JButton delBttn;
	private JButton cancelBttn;
	protected static JPanel bPanel;
	private static JTable table;
	protected JScrollPane scrollPane;
	private String[] vars={};
	private String thisID;
	private String thisTable;
	public boolean viewMod=false;
	private Image pic=Layout.getImage(Globals.logo);
	private JLabel picPanel;
	protected static String viewBooks;
	private Object[][] cells;
	protected static  DefaultTableModel defTModel;
	protected static BookCheckWindow bookCW;
	
	private JButton button = new JButton();
	
	public ViewPanel() {
		cells = new Object[256][5];
		String[] columnNames = {"ID","Title","Reading Level","Due Date",""};
		
		setBorder(new LineBorder(new Color(0, 0, 0), 3));
		setBackground(Color.ORANGE);
		setLayout(null);
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.ORANGE);
		topPanel.setForeground(Color.BLACK);
		topPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		topPanel.setBounds(0, 0, 894, 320);
		add(topPanel);
		topPanel.setLayout(null);
		
		bPanel = new JPanel();
		bPanel.setBounds(3, 320, 888, 350);
		bPanel.setLayout(null);
		bPanel.setBackground(Color.ORANGE);
		add(bPanel);
		
        defTModel = new DefaultTableModel(cells,columnNames);
        table = new JTable(defTModel);
		
        //table = new JTable(cells, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
		//table.getColumn("").setCellRenderer(new ButtonRenderer());
		//table.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		picPanel = new JLabel();
		picPanel.setBounds(25, 70, 220, 195);
		topPanel.add(picPanel);
	
		
		Lbl1 = new JLabel();
		Lbl1.setForeground(Color.BLACK);
		Lbl1.setBounds(275, 35, 100, 25);
		topPanel.add(Lbl1);
		Lbl1.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Lbl2 = new JLabel();
		Lbl2.setForeground(Color.BLACK);
		Lbl2.setBounds(475, 35, 125, 25);
		topPanel.add(Lbl2);
		Lbl2.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Lbl3 = new JLabel();
		Lbl3.setForeground(Color.BLACK);
		Lbl3.setBounds(675, 35, 100, 25);
		topPanel.add(Lbl3);
		Lbl3.setFont(new Font("Tahoma", Font.BOLD, 16));
				
		Lbl4 = new JLabel();
		Lbl4.setForeground(Color.BLACK);
		Lbl4.setBounds(275, 130, 100, 25);
		topPanel.add(Lbl4);
		Lbl4.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Lbl5 = new JLabel();
		Lbl5.setForeground(Color.BLACK);
		Lbl5.setBounds(475, 130, 100, 25);
		topPanel.add(Lbl5);
		Lbl5.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Lbl6 = new JLabel();
		Lbl6.setForeground(Color.BLACK);
		Lbl6.setBounds(675, 130, 100, 25);
		topPanel.add(Lbl6);
		Lbl6.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		Lbl7 = new JLabel();
		Lbl7.setForeground(Color.BLACK);
		Lbl7.setBounds(275, 215, 100, 25);
		topPanel.add(Lbl7);
		Lbl7.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		TxtFld1 = new JTextField();
		TxtFld1.setForeground(Color.BLACK);
		TxtFld1.setBounds(275, 70, 150, 25);
		topPanel.add(TxtFld1);
		TxtFld1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld1.setColumns(10);
		
		TxtFld2 = new JTextField();
		TxtFld2.setForeground(Color.BLACK);
		TxtFld2.setBounds(475, 70, 150, 25);
		topPanel.add(TxtFld2);
		TxtFld2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld2.setColumns(10);
		
		TxtFld3 = new JTextField();
		TxtFld3.setForeground(Color.BLACK);
		TxtFld3.setBounds(675, 70, 150, 25);
		topPanel.add(TxtFld3);
		TxtFld3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld3.setColumns(10);
		
		TxtFld4 = new JTextField();
		TxtFld4.setForeground(Color.BLACK);
		TxtFld4.setBounds(275, 165, 150, 25);
		topPanel.add(TxtFld4);
		TxtFld4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld4.setColumns(10);
		
		TxtFld5 = new JTextField();
		TxtFld5.setForeground(Color.BLACK);
		TxtFld5.setBounds(475, 165, 150, 25);
		topPanel.add(TxtFld5);
		TxtFld5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld5.setColumns(10);
		
		TxtFld6 = new JTextField();
		TxtFld6.setForeground(Color.BLACK);
		TxtFld6.setBounds(675, 165, 150, 25);
		topPanel.add(TxtFld6);
		TxtFld6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld6.setColumns(10);
		
		TxtFld7 = new JTextField();
		TxtFld7.setForeground(Color.BLACK);
		TxtFld7.setBounds(275, 250, 156, 26);
		topPanel.add(TxtFld7);
		TxtFld7.setFont(new Font("Tahoma", Font.PLAIN, 13));
		TxtFld7.setColumns(10);
		
		updateBttn = new JButton("Update");
		updateBttn.setForeground(Color.BLACK);
		updateBttn.setBounds(525, 280, 95, 25);
		topPanel.add(updateBttn);
		updateBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		delBttn = new JButton("Delete");
		delBttn.setForeground(Color.BLACK);
		delBttn.setBounds(650, 280, 95, 25);
		topPanel.add(delBttn);
		delBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		cancelBttn = new JButton("Cancel");
		cancelBttn.setForeground(Color.BLACK);
		cancelBttn.setBounds(775, 272, 105, 35);
		topPanel.add(cancelBttn);
		cancelBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
						
		JButton returnBttn = new JButton("<Go Back");
		returnBttn.setForeground(Color.BLACK);
		returnBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		returnBttn.setActionCommand("Update");
		returnBttn.setBounds(25, 10, 125, 30);
		topPanel.add(returnBttn);
		
		JButton picBttn = new JButton("Change Picture");
		picBttn.setForeground(Color.BLACK);
		picBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		picBttn.setActionCommand("Update");
		picBttn.setBounds(45, 280, 180, 25);
		topPanel.add(picBttn);
		
		updateBttn.setActionCommand("Update");
		updateBttn.addActionListener(this);
		delBttn.setActionCommand("Delete");
		delBttn.addActionListener(this);	
		cancelBttn.setActionCommand("Cancel");
		cancelBttn.addActionListener(this);	
		returnBttn.setActionCommand("Return");
		returnBttn.addActionListener(this);
		picBttn.setActionCommand("Pic");
		picBttn.addActionListener(this);
				
	}
	public static void reformat(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
	    model.setRowCount(0);
	}
	public void create(String id,String table){
		System.out.println("CREATE");
		String[] studVars={"fname","lname","parents","addr","sgroup","books","pic"};
		String[] bookVars={"title","readinglvl","ISBN","cond","lang","bookset","duedate","coverpic"};	
		Blob imageBlob = null;
		String[] vals={};
		vals=new String[7];
		
		if(table=="students"){vars=studVars;}
		else if (table=="books"){vars=bookVars;}
		thisID=id;
		thisTable=table;
		
		viewMod=false;
		
		ResultSet query=DBConnect.queryDB("*",table,table+"id="+id);
		try {
			while (query.next()){
				for(int i=0;i<vars.length-1;i++){
					vals[i]=query.getString(vars[i]);
				}
				imageBlob =query.getBlob(vars[vars.length-1]);
			}			
		} catch (SQLException e) {System.out.println("Error: "+e);}
		viewBooks=vals[5];
		setFields(table,vals,imageBlob);
		//TODO send something for list if book
		if(table=="books"){qrInfo();}
		else{populate(vals[5]);}
	}
	public void setFields(String table,String[] vals,Blob imageBlob){
		char[] address=null;
		String temp="";
		JTextField[] addr={TxtFld4,TxtFld5,TxtFld6};
		String[] labels={};
		int j=0;
		
		if(table=="students"){labels=Globals.studList;}
		else if (table=="books"){labels=Globals.bookList;}
		
		//set text fields
		TxtFld1.setText(vals[0]);
		TxtFld2.setText(vals[1]);
		TxtFld3.setText(vals[2]);
		if(table=="students"){
			address=vals[3].toCharArray();
			for(int i=0;i<vals[3].length();i++){
				if(address[i]=='\n'){
					addr[j].setText(temp);
					j++;
					temp="";
				}
				else{temp+=address[i];}
			}
			TxtFld7.setText(vals[4]);
		}
		else{
			TxtFld4.setText(vals[3]);
			TxtFld5.setText(vals[4]);
			TxtFld6.setText(vals[5]);
			TxtFld7.setText("Poop");
			//TxtFld7.setText(vals[6]);
		}		
		//read blob info into image
		byte[] bytePic = null;
		try {bytePic = imageBlob.getBytes(1, (int) imageBlob.length());
		} catch (SQLException e2) {e2.printStackTrace();}
		Image pic = null;
		try {pic = ImageIO.read(new ByteArrayInputStream(bytePic));}
		catch (IOException e1) {e1.printStackTrace();}		
	    try {ImageIO.write((RenderedImage)pic, "jpg",new File("./cover.jpg"));
		} catch (IOException e1) {e1.printStackTrace();}
		//scale image & put it in panel
		Image scaledpic = pic.getScaledInstance(220,195,Image.SCALE_SMOOTH);
		picPanel.setIcon( new ImageIcon(scaledpic));

		//set labels	If I could incriment the variable names like PHP then this could just be a loop :(
		Lbl1.setText(labels[0]);
		Lbl2.setText(labels[1]);
		Lbl3.setText(labels[2]);
		Lbl4.setText(labels[3]);
		Lbl5.setText(labels[4]);
		Lbl6.setText(labels[5]);
		Lbl7.setText(labels[6]);
		
	}
	private void qrInfo(){
		Image pic = null;
		byte[] bytePic = null;
		Blob qrImg=null;
		final String idLabel="Book ID: "+thisID;
		
		bPanel.setBackground(Color.WHITE);		
		
		JLabel qrPanel = new JLabel();
		qrPanel.setBounds(325, 65, 220, 195);
		qrPanel.setBorder(new LineBorder(new Color(0, 0, 0), 3));
		bPanel.add(qrPanel);
		
		JLabel idLbl=new JLabel(idLabel);
		idLbl.setForeground(Color.BLACK);
		idLbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		idLbl.setBounds(365,0,200,100);
		bPanel.add(idLbl);
		
		JButton printBttn=new JButton("Add to QR Spreadsheet");
		printBttn.setForeground(Color.BLACK);
		printBttn.setFont(new Font("Tahoma", Font.BOLD, 16));
		printBttn.setBounds(325, 280, 220, 40);
		bPanel.add(printBttn);	
		
		//qr picture stuff
		ResultSet qrQuery=DBConnect.queryDB("qrcode","books","booksid="+thisID);
		try {
			while (qrQuery.next()){
				try {qrImg =qrQuery.getBlob("qrcode");
				} catch (SQLException e) {e.printStackTrace();}
			}
		} catch (SQLException e) {e.printStackTrace();}				
		try {bytePic = qrImg.getBytes(1, (int) qrImg.length());
		} catch (SQLException e2) {e2.printStackTrace();}		
		try {pic = ImageIO.read(new ByteArrayInputStream(bytePic));}
		catch (IOException e1) {e1.printStackTrace();}
		final Image printPic=pic;
		//scale image & put it in panel
		Image scaledpic = pic.getScaledInstance(220,195,Image.SCALE_SMOOTH);
		qrPanel.setIcon( new ImageIcon(scaledpic));
		
		
		printBttn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent event){
						qrPrint(TxtFld1.getText(),thisID,printPic);
					}
		});	
	}
	//Makes table and populates
	public void populate(String books){
		String temp="";
		int count=0;
		
		table.getColumn("").setCellRenderer(new ButtonRenderer());
		table.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
		
		JLabel historyLbl = new JLabel("History:");
		historyLbl.setForeground(Color.BLACK);
		historyLbl.setFont(new Font("Tahoma", Font.BOLD, 22));
		historyLbl.setBounds(10, 10, 100, 25);
		bPanel.add(historyLbl);
		
		JButton btnClearHistory = new JButton("Clear History");
		btnClearHistory.setForeground(Color.BLACK);
		btnClearHistory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnClearHistory.setBounds(120, 10, 150, 25);
		bPanel.add(btnClearHistory);
		
		JButton btnCheckoutBook = new JButton("Checkout Book");
		btnCheckoutBook.setForeground(Color.BLACK);
		btnCheckoutBook.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCheckoutBook.setBounds(300, 5, 175, 35);
		bPanel.add(btnCheckoutBook);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 45, 888, 305);
		bPanel.add(scrollPane);
		
		char[] ids= books.toCharArray();
		for(int i=0;i<books.length();i++){
			if(ids[i]==' '){
				ResultSet itemQuery=DBConnect.queryDB("title,readinglvl,duedate","books","booksid="+temp);				
				try {
					while (itemQuery.next()){
						Object[] newRowData;
						Color check=Layout.checkDate(itemQuery.getString("duedate"));
						if(check!=Color.BLACK){
							newRowData = new Object[] {temp,itemQuery.getString("title"),
									itemQuery.getString("readingLvl"),itemQuery.getString("duedate"),("Check In")};
						}
						else{
							newRowData = new Object[] {temp,itemQuery.getString("title"),
									itemQuery.getString("readingLvl"),"Not Checked Out",""};
						}
				        defTModel.addRow(newRowData);
					}			
				} catch (SQLException e) {System.out.println("Error: "+e);}
				temp="";
				count++;
			}
			else{temp+=ids[i];}
		}
        
        scrollPane.setViewportView(table);
        
		btnClearHistory.setActionCommand("Clear");
		btnClearHistory.addActionListener(this);
		btnCheckoutBook.setActionCommand("CheckOut");
		btnCheckoutBook.addActionListener(this);
	}
	//all this stuff is is for the table 
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}
	class ButtonEditor extends DefaultCellEditor {
		  protected JButton button;
		  private String label;
		  private boolean isPushed;

		  public ButtonEditor(JCheckBox checkBox) {
		    super(checkBox);
		    button = new JButton();
		    button.setOpaque(true);
		    button.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        fireEditingStopped();
		      }
		    });
		  }

		  public Component getTableCellEditorComponent(JTable table, Object value,
		      boolean isSelected, int row, int column) {
		    if (isSelected) {
		      button.setForeground(table.getSelectionForeground());
		      button.setBackground(table.getSelectionBackground());
		    } else {
		      button.setForeground(table.getForeground());
		      button.setBackground(table.getBackground());
		    }
		    label = (value == null) ? "" : value.toString();
		    button.setText(label);
		    isPushed = true;
		    return button;
		  }

		  public Object getCellEditorValue() {
		    if (isPushed) { 
		    	String status=(String)table.getValueAt(table.getSelectedRow(),3);
				//System.out.println(status);
				if(status.equals("Not Checked Out")){
					JOptionPane.showMessageDialog(null,"Book already checked in.");}
				else{
					boolean chck=checkin((String) table.getValueAt(table.getSelectedRow(),0));
					if(chck==false){JOptionPane.showMessageDialog(null,"Error: Book could not be checked in.");}
					else{	
						table.setValueAt("Not Checked Out", table.getSelectedRow(), 3); 
						table.setValueAt("", table.getSelectedRow(), 4);	
						JOptionPane.showMessageDialog(null,"Book checked in.");
						BookCheckWindow.chckOut1P.getBooks();
					}
				}
		    }
		    isPushed = false;
		    return new String(label);
		  }
		  public boolean stopCellEditing() {
		    isPushed = false;
		    return super.stopCellEditing();
		  }
		  protected void fireEditingStopped() {
		    super.fireEditingStopped();
		  }
	}
	//actions for buttons
	public void actionPerformed(ActionEvent e) {
		String upStat="",update="";
		switch (e.getActionCommand()){
		case "Update": 
			if(thisTable=="students"){
				update="UPDATE students SET "+vars[0]+"='"+TxtFld1.getText()+"', "+
						vars[1]+"='"+TxtFld2.getText()+"', "+vars[2]+"='"+TxtFld3.getText()+"', "+
						vars[3]+"='"+TxtFld4.getText()+"\n"+TxtFld5.getText()+"\n"+TxtFld6.getText()
						+"', "+vars[4]+"='"+TxtFld7.getText()+"', pic= ? WHERE studentsid="+thisID;
			}
			else{
				update="UPDATE books SET "+vars[0]+"='"+TxtFld1.getText()+"', "+
						vars[1]+"='"+TxtFld2.getText()+"', "+vars[2]+"='"+TxtFld3.getText()+"', "+
						vars[3]+"='"+TxtFld4.getText()+"', "+vars[4]+"='"+TxtFld5.getText()+"', "+
						vars[5]+"='"+TxtFld6.getText()+"', "+vars[6]+"='"+TxtFld7.getText()+
						"', coverpic= ? WHERE booksid="+thisID;
			}
			System.out.println(update);
			viewMod=true;
			//DBConnect.updateDB(thisTable,upStat,thisTable+"id= "+thisID);
			
   			//String update="UPDATE books SET qrcode= ? WHERE booksid="+id;
			DBConnect.pStmt(update, "./cover.jpg");
			
			break;
		case "Delete":String name=TxtFld1.getText();
			int selected= JOptionPane.showOptionDialog(BookCheckWindow.frame,
					"Are you sure you want to delete "+name+"?","Delete "+name+"?",
					JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, null,null,"No");
			if(selected==0){
				DBConnect.deleteFromDB(thisTable, thisTable+"id="+thisID);
				Layout.removeFKey("users",thisTable,Globals.userid,thisID);    			
    			viewMod=true;
    			fireEvent(thisTable);
    			JOptionPane.showMessageDialog(BookCheckWindow.frame,name+" has been deleted",name+" deleted", JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case "Cancel":create(thisID,thisTable);
			break;
		case "Clear"://TODO
			break;
		case "CheckOut": BookCheckWindow.chckOD.start(thisID);
			//JOptionPane.showMessageDialog(null,">:(");
			break;
		case "Pic":
    		pic=Layout.takePic("cover.jpg");
    		if(pic!=null){    
    			Image scaledpic = pic.getScaledInstance(220,195,Image.SCALE_SMOOTH);
    			ImageIcon icon = new ImageIcon(scaledpic);
    			icon.getImage().flush();
    			picPanel.setIcon( icon );}
			break;
		case "Return":bPanel.removeAll();
			RenderedImage img = null;
			try {img = ImageIO.read(new File("./no.jpg"));
			} catch (IOException e1) {e1.printStackTrace();}
			try {ImageIO.write(img, "jpg",new File("./cover.jpg"));
			} catch (IOException e1) {e1.printStackTrace();}
			fireEvent(thisTable);
			break;
		}
    }
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
	//Check In and Out
	public boolean checkout(String studID,String bookID,String dueDate){
		String bkUpd=DBConnect.updateDB("books", "duedate='"+dueDate+"'", "booksid="+bookID);
		String stdUpd=DBConnect.updateDB("students", "books=CONCAT('"+bookID+" ',books)", "studentsid="+studID);
		if(bkUpd.contains("Error:")||stdUpd.contains("Error:")){
			JOptionPane.showMessageDialog(null,"Book could not be checkedout.");
			return false;}
		else{
			JOptionPane.showMessageDialog(null,"Book checkedout.");
			return true;
		}
	}
	public boolean checkin(String bookID){
		String result=DBConnect.updateDB("books","duedate='9999-12-31'","booksid="+bookID);
		if(result.contains("Error:")){return false;}
		else{return true;}
	}
}
