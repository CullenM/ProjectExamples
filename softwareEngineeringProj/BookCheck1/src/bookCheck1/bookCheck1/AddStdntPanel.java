package bookCheck1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddStdntPanel extends JPanel implements ActionListener{
	private JPanel logo;
	private JTextField fNameTxfld;
	private JTextField lNameTxFld;
	private JTextField pNameTxFld;
	private JTextField addrTxFld;
	private JTextField cityTxFld;
	private JTextField zipTxFld;
	private JTextField grpTxFld;
	private Image pic=null;
	private JLabel picPanel;
	public boolean addStdMod=false;
	
	public AddStdntPanel() {
		gui();
	}
	public void clear(){
		String condList[]={"Excellent","Good","Fair","Poor"};
		String langList[]={"English","Spanish"};
		
		System.out.println("INIT");
		fNameTxfld.setText("");
		lNameTxFld.setText("");
		pNameTxFld.setText("");
		addrTxFld.setText("");
		cityTxFld.setText("");
		zipTxFld.setText("");
		grpTxFld.setText("");
	}
	public void gui(){
		Layout layout=new Layout();

		setBackground(new Color(0, 0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		JPanel panel=layout.panelBackground();
		add(panel);

		pic=layout.getImage(Globals.logo);
		picPanel = new JLabel(new ImageIcon(pic));
		picPanel.setBounds(295, 26, 164, 151);
		panel.add(picPanel);

		JLabel title = new JLabel("Add Student");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 30));
		title.setForeground(Color.BLACK);
		title.setBounds(290, 188, 200, 60);
		panel.add(title);

		final JLabel fNameLbl = new JLabel("First Name : ");
		fNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fNameLbl.setBounds(158, 240, 98, 14);
		panel.add(fNameLbl);

		JLabel lNameLbl = new JLabel("Last Name : ");
		lNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lNameLbl.setBounds(158, 277, 98, 14);
		panel.add(lNameLbl);

		JLabel pNameLbl = new JLabel("Parent's Name :");
		pNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pNameLbl.setBounds(158, 318, 118, 14);
		panel.add(pNameLbl);

		JLabel addrLbl = new JLabel("Address : ");
		 addrLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		 addrLbl.setBounds(158, 358, 98, 14);
		panel.add(addrLbl);

		JLabel cityLbl = new JLabel("City : ");
		cityLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cityLbl.setBounds(158, 383, 46, 40);
		panel.add(cityLbl);

		JLabel zipLbl = new JLabel("Zip : ");
		zipLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		zipLbl.setBounds(158, 434, 46, 25);
		panel.add(zipLbl);

		JLabel grpLbl = new JLabel("Group : ");
		grpLbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		grpLbl.setBounds(158, 470, 67, 25);
		panel.add(grpLbl);

		fNameTxfld = new JTextField();
		fNameTxfld .setBounds(295, 240, 175, 20);
		panel.add(fNameTxfld );
		fNameTxfld .setColumns(10);

		lNameTxFld = new JTextField();
		lNameTxFld.setBounds(295, 277, 175, 20);
		panel.add(lNameTxFld);
		lNameTxFld.setColumns(10);

		pNameTxFld= new JTextField();
		pNameTxFld.setBounds(295, 317, 175, 20);
		panel.add(pNameTxFld);
		pNameTxFld.setColumns(10);

		addrTxFld= new JTextField();
		addrTxFld.setBounds(295, 357, 175, 20);
		panel.add(addrTxFld);
		addrTxFld.setColumns(10);

		cityTxFld= new JTextField();
		cityTxFld.setBounds(295, 395, 175, 20);
		panel.add(cityTxFld);
		cityTxFld.setColumns(10);

		zipTxFld = new JTextField();
		zipTxFld.setBounds(295, 438, 175, 20);
		panel.add(zipTxFld);
		zipTxFld.setColumns(10);

		grpTxFld = new JTextField();
		grpTxFld.setBounds(295, 474, 175, 20);
		panel.add(grpTxFld);
		grpTxFld.setColumns(10);
		
		JButton picBttn=new JButton("Take Picture");
		picBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		picBttn.setBounds(302, 505, 160, 27);
		panel.add(picBttn);

		JButton submitBttn = new JButton("Submit");
		submitBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		submitBttn.setBounds(335, 569, 90, 25);
		panel.add(submitBttn);

		JButton cancelBttn = new JButton("Go Back");
		cancelBttn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		cancelBttn.setBounds(335, 603, 90, 25);
		panel.add(cancelBttn);
		
		//listens to buttons
		picBttn.setActionCommand("Pic");
		picBttn.addActionListener(this);
		submitBttn.setActionCommand("Submit");
		submitBttn.addActionListener(this);
		cancelBttn.setActionCommand("Cancel");
		cancelBttn.addActionListener(this);	
	}
	//listens to buttons and gives info to fireEvent
    public void actionPerformed(ActionEvent e) {
    	//TODO set pic to no.png if no picture taken
    	if(e.getActionCommand()=="Submit"){
    		int check=0;
    		
    		String insert="INSERT INTO students (fname,lname,parents,addr,sgroup,pic) VALUES ('";
    			insert+=fNameTxfld.getText()+"','"+ lNameTxFld.getText()+"','"+pNameTxFld.getText()
    				+"','"+addrTxFld.getText()+"\n"+cityTxFld.getText()+"\n"+zipTxFld.getText()
    				+"\n','"+grpTxFld.getText()+"',?)"; 
    		
    		String id=DBConnect.pStmt(insert,"./cover.jpg");
    		check=Layout.checkInsert(id);
    		if(check==1){
    			id=DBConnect.pStmt(insert,"./cover.jpg");
    			check=Layout.checkInsert(id);}
    		if(check==2){
    			DBConnect.updateDB("users", "students=CONCAT('"+id+" ',students)","usersid="+Globals.userid);
    			//System.out.println("users updated");
    			JOptionPane.showMessageDialog(BookCheckWindow.frame, fNameTxfld.getText()+" has been added.");
    			addStdMod=true;
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
