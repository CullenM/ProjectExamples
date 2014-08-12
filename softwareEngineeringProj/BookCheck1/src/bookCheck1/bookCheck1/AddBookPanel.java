package bookCheck1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddBookPanel extends SuperClass implements ActionListener{
	private JPanel logo;
	private JTextField titleTxfld;
	private JTextField lvlofbkTxFld;
	private JTextField isbnTxFld;
	private JComboBox condCBox;
	private JComboBox langCBox;
	private JTextField setTxFld;
	public JPanel panel;
	private JLabel picPanel;
	public boolean addBkMod=false;
	private Image pic=null;
	
	//private static DBConnect connect;
	QRgenerator qrGen=new QRgenerator();
	Layout layout=new Layout();

	public AddBookPanel() {	
		QRgenerator qrGen=new QRgenerator();
		gui();
	}
	public void clear(){
		String condList[]={"Excellent","Good","Fair","Poor"};
		String langList[]={"English","Spanish"};

		titleTxfld.setText("");
		lvlofbkTxFld.setText("");
		isbnTxFld.setText("");
		condCBox.setSelectedItem(condList[0]);
		langCBox.setSelectedItem(langList[0]);
		setTxFld.setText("");
	}
	//set up interface
	public void gui(){
		String condList[]={"Excellent","Good","Fair","Poor"};
		String langList[]={"English","Spanish"};
		
		Layout layout=new Layout();

		setBackground(new Color(0, 0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		JPanel panel=layout.panelBackground();
		add(panel);

		pic=layout.getImage(Globals.logo);
		picPanel = new JLabel(new ImageIcon(pic));
		picPanel.setBounds(295, 26, 165, 150);
		panel.add(picPanel);

		JLabel title = new JLabel("Add Book");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setForeground(Color.BLACK);
		title.setBounds(295, 188, 164, 60);
		panel.add(title);

		JLabel titleLbl = new JLabel("Title :");
		titleLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		titleLbl.setBounds(158, 269, 150, 20);
		panel.add(titleLbl);

		JLabel rLvlLbl = new JLabel("Reading Level : ");
		rLvlLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rLvlLbl.setBounds(158, 307, 150, 20);
		panel.add(rLvlLbl);

		JLabel isbnLbl = new JLabel("ISBN : ");
		isbnLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		isbnLbl.setBounds(158, 348, 150, 14);
		panel.add(isbnLbl);

		JLabel condLbl = new JLabel("Condition : ");
		condLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		condLbl.setBounds(158, 388, 150, 14);
		panel.add(condLbl);

		JLabel langLbl = new JLabel("Language : ");
		langLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		langLbl.setBounds(158, 413, 150, 40);
		panel.add(langLbl);
		
		JLabel setLbl = new JLabel("Set:");
		setLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		setLbl.setBounds(158, 464, 150, 25);
		panel.add(setLbl);


		titleTxfld = new JTextField();
		titleTxfld .setBounds(295, 268, 174, 20);
		panel.add(titleTxfld );
		titleTxfld .setColumns(10);

		lvlofbkTxFld= new JTextField();
		lvlofbkTxFld.setBounds(295, 306, 174, 20);
		panel.add( lvlofbkTxFld);
		lvlofbkTxFld.setColumns(10);

		isbnTxFld= new JTextField();
		isbnTxFld.setBounds(295, 347, 174, 20);
		panel.add(isbnTxFld);
		isbnTxFld.setColumns(10);

		condCBox= new JComboBox(condList);
		condCBox.setBounds(295, 387, 174, 20);
		panel.add(condCBox);
		//condCBox.setColumns(10);

		langCBox= new JComboBox(langList);
		langCBox.setBounds(295, 425, 174, 20);
		panel.add(langCBox);	
		//langCBox.setColumns(10);

		setTxFld = new JTextField();
		setTxFld.setBounds(295, 468, 174, 20);
		panel.add(setTxFld);
		setTxFld.setColumns(10);
		
		JButton picBttn=new JButton("Take Cover Picture");
		picBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		picBttn.setBounds(302, 505, 160, 27);
		panel.add(picBttn);

		JButton submitBttn = new JButton("Submit");
		submitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		submitBttn.setBounds(335, 569, 89, 23);
		panel.add(submitBttn);

		JButton cancelBttn = new JButton("Go Back");
		cancelBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cancelBttn.setBounds(335, 603, 89, 23);
		panel.add(cancelBttn);
		
		picBttn.setActionCommand("Pic");
		picBttn.addActionListener(this);
		submitBttn.setActionCommand("Submit");
		submitBttn.addActionListener(this);
		cancelBttn.setActionCommand("Cancel");
		cancelBttn.addActionListener(this);	
	}
	//listens to buttons: Adds to DB and switches panels
    public void actionPerformed(ActionEvent e) {
    	//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	//Calendar cal = Calendar.getInstance();
    	
    	if(e.getActionCommand()=="Submit"){
    		int check=0;
    		String insert="INSERT INTO books (title,readinglvl,ISBN,cond,lang,bookset,duedate,coverpic,qrcode) VALUES ('";
    		insert+=titleTxfld.getText()+"','"+ lvlofbkTxFld.getText()
        			+"','"+isbnTxFld.getText()+"','"+condCBox.getSelectedItem()
        			+"','"+langCBox.getSelectedItem()+"','"+setTxFld.getText()+"','9999-12-31',?,?)";
    		
    		String id=DBConnect.pStmt(insert,"./cover.jpg");
    		check=Layout.checkInsert(id);
    		if(check==1){
    			id=DBConnect.pStmt(insert,"./cover.jpg");
    			check=Layout.checkInsert(id);}
    		if(check==2){
    			DBConnect.updateDB("users", "books=CONCAT('"+id+" ',books)","usersid="+Globals.userid);
    			QRgenerator.generate(id+"\n"+titleTxfld.getText());
    			//System.out.println("users updated");
    			String update="UPDATE books SET qrcode= ? WHERE booksid="+id;
    			DBConnect.pStmt(update, "./QR.png");
    			//System.out.println("qr updated");
    			String addedMsg=titleTxfld.getText()+" has been added with the ID "+id+"\n\n"
    					+"Do you want to add this book to the QR SpreadSheet?";
    			int selected= JOptionPane.showOptionDialog(BookCheckWindow.frame,addedMsg,
    					titleTxfld.getText()+" Added",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,
    					null,null,"No");
    			if(selected==0){
    				Image qrPic=null;
					try {qrPic = ImageIO.read(new File("./QR.png"));
					} catch (IOException e1) {e1.printStackTrace();}
    				qrPrint(titleTxfld.getText(),id,qrPic);}
    			addBkMod=true;
    			clear();
    		}
    		Layout.resetCover(picPanel);
		}
    	else if(e.getActionCommand()=="Pic"){
    		pic=Layout.takePic("cover.jpg");
    		if(pic!=null){
    			Image scaledpic = pic.getScaledInstance(165,150,Image.SCALE_SMOOTH);
    			ImageIcon icon = new ImageIcon(scaledpic);
    			icon.getImage().flush();
    			picPanel.setIcon( icon );
    		}
    	}
    	else{Layout.resetCover(picPanel);
    		fireEvent(e.getActionCommand());}
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