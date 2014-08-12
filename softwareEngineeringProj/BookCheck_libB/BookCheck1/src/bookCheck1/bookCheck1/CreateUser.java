package bookCheck1;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CreateUser extends JPanel implements ActionListener{
	private JPanel logo;
	private JTextField userNameTxFld;
	private JTextField passWordTxFld;
	private JTextField passWord2TxFld;
	private JTextField eMailTxFld;
	private boolean createInputOK=false;
	private boolean createUserOK=true;
	private String encryptedPassW=null;
	
	public CreateUser() {
		gui();
	}
	public void gui(){
		Layout layout=new Layout();
		setBackground(new Color(0, 0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		JPanel panel=layout.panelBackground();
		add(panel);
		
		JPanel logo = new JPanel();
		logo.setBounds(300, 26, 164, 151);
		panel.add(logo);

		JLabel title = new JLabel("Create Account");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setForeground(Color.BLACK);
		title.setBounds(280, 198, 200, 60);
		panel.add(title);

		JLabel userNameLbl = new JLabel("Enter Username : ");
		userNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userNameLbl.setBounds(158, 269, 98, 14);
		panel.add(userNameLbl);
		userNameTxFld = new JTextField();
		userNameTxFld .setBounds(295, 268, 175, 30);
		panel.add(userNameTxFld );
		userNameTxFld .setColumns(10);
		
		JLabel passWordLbl = new JLabel("Enter Password : ");
		passWordLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passWordLbl.setBounds(158, 307, 98, 14);
		panel.add(passWordLbl);
		passWordTxFld = new JPasswordField();
		passWordTxFld.setBounds(295, 306, 175, 30);
		panel.add(passWordTxFld);
		passWordTxFld.setColumns(10);
		
		JLabel repassWordLbl = new JLabel("Re-Enter Password :");
		repassWordLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		repassWordLbl.setBounds(158, 348, 118, 14);
		panel.add(repassWordLbl);
		passWord2TxFld= new JPasswordField();
		passWord2TxFld.setBounds(295, 347, 175, 30);
		panel.add(passWord2TxFld);
		passWord2TxFld.setColumns(10);
		
		JLabel eMailLbl = new JLabel("E-mail : ");
		eMailLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		eMailLbl.setBounds(158, 388, 98, 14);
		panel.add(eMailLbl);
		eMailTxFld= new JTextField();
		eMailTxFld.setBounds(295, 385, 175, 30);
		panel.add(eMailTxFld);
		eMailTxFld.setColumns(10);
		
		JButton submitBttn = new JButton("Create Account");
		submitBttn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		submitBttn.setBounds(320, 559, 120, 35);
		panel.add(submitBttn);

		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cancelBttn.setBounds(335, 603, 90, 25);
		panel.add(cancelBttn);
		
		//listens to buttons
		submitBttn.setActionCommand("Create");
		submitBttn.addActionListener(this);
		cancelBttn.setActionCommand("Home");
		cancelBttn.addActionListener(this);	
	}
	
	//listens to buttons and gives info to fireEvent
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand()=="Home")
    		fireEvent(e.getActionCommand());
    	else if(e.getActionCommand()=="Create"){
    		checkFields();
    		if(createInputOK==true){
	    		try{
		    		//TODO put in error checking 
		    		//insertDB(); insert into books table
		    		DBConnect.insertDB("users (username,passw,email,books,students)",
		    				"('"+userNameTxFld.getText()+"','"+ encryptedPassW+"','"+
		    		eMailTxFld.getText()+"','"+ ""+"','"+ ""+"')");		
		    		//DBConnect.updateDB("users", "users=CONCAT(user,'"+id+" ')","usersid="+Globals.userid);
		    		//System.out.println(id+"\n"+titleTxfld.getText());
		    		//http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html <-popup message
		    		//popup message saying book added 
		    		JOptionPane.showMessageDialog(BookCheckWindow.frame, userNameTxFld.getText()+" Has Been Created. Click Cancel to Log-In");
		        	createInputOK=false;
		        	createUserOK=true;
	    		}
	    		catch(Exception ex){
	    			
	    		}
    		}
    	}
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
	public void checkFields(){
		createInputOK=false;
		createUserOK=true;
		ResultSet user=DBConnect.queryDB("username", "users", "username='"+userNameTxFld.getText()+"'");
		try{
			while(user.next()){
				String checkuser=user.getString("username"); //get user
				JOptionPane.showMessageDialog(BookCheckWindow.frame, "Username: "+checkuser+" Already Exists!");
				createUserOK=false;
			}
		}
		catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(userNameTxFld.getText().equals("")||passWordTxFld.getText().equals("")||
    			passWord2TxFld.getText().equals("")||eMailTxFld.getText().equals(""))
    		JOptionPane.showMessageDialog(BookCheckWindow.frame,"All Fields Are Required!");
    	else if(!passWordTxFld.getText().equals(passWord2TxFld.getText()))
    		JOptionPane.showMessageDialog(BookCheckWindow.frame, "Passwords DO NOT Match!");
    	else if(!eMailTxFld.getText().contains("@")||!eMailTxFld.getText().contains(".com"))
    		JOptionPane.showMessageDialog(BookCheckWindow.frame, "Must be an Email");
		else if(createUserOK==true){
			encryptPass();
			createInputOK=true;
			}
		else
    		JOptionPane.showMessageDialog(BookCheckWindow.frame, "ERROR!");
	}
	public void encryptPass(){
		String passToEncrypt=passWordTxFld.getText();
			try{
			MessageDigest md=MessageDigest.getInstance("MD5");
			md.update(passToEncrypt.getBytes());
			byte[] bytes=md.digest();
			StringBuilder sb=new StringBuilder();
			for(int i=0;i<bytes.length;i++){
				sb.append(Integer.toString((bytes[i] & 0xff)+0x100,16).substring(1));
				}
			encryptedPassW=sb.toString();
			}
			catch(NoSuchAlgorithmException e){
				e.printStackTrace();
			}
		}
}