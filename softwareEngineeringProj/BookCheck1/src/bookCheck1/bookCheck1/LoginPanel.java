package bookCheck1;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPanel extends JPanel implements ActionListener{
	private JTextField pswrdField;
	private JTextField unameField;
	private JLabel unameLabel;
	private JLabel pswrdLabel;
	public static JLabel errorLabel;
	private JPanel panel;
	private boolean loginInputOK=false;
	private boolean loginUserOK=false;
	private boolean loginPasswordOK=false;
	private String encryptedPassW=null;
	
	public LoginPanel() {
		gui();
	}
	public void gui(){
		String mess=null;
		Layout layout=new Layout();

		setBackground(new Color(0, 0, 0));
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);		
		
		panel=layout.panelBackground();
		add(panel);
		
		JButton loginBttn = new JButton("Login");
		loginBttn.setBounds(325, 500, 150, 50);
		panel.add(loginBttn);
		
		JButton ccBttn = new JButton("Create Account");
		ccBttn.setBounds(325, 560, 150, 23);
		panel.add(ccBttn);

		JButton forgotBttn = new JButton("Forgot Login");
		forgotBttn.setBounds(325, 620, 150, 23);
		panel.add(forgotBttn);
		
		pswrdField = new JPasswordField();
		pswrdField.setBounds(325, 469, 150, 20);
		panel.add(pswrdField);
		pswrdField.setColumns(10);
		
		unameField = new JTextField();
		unameField.setBounds(325, 438, 150, 20);
		panel.add(unameField);
		unameField.setColumns(10);
		
		unameLabel = new JLabel();
		unameLabel.setBounds(229, 438, 86, 20);
		panel.add(unameLabel);
		unameLabel.setForeground(Color.BLACK);
		unameLabel.setBackground(Color.ORANGE);
		unameLabel.setText("Username: ");
		
		pswrdLabel = new JLabel();
		pswrdLabel.setBounds(229, 469, 86, 20);
		panel.add(pswrdLabel);
		pswrdLabel.setForeground(Color.BLACK);
		pswrdLabel.setText("Password: ");
		pswrdLabel.setBackground(Color.ORANGE);
		
		errorLabel=new JLabel(mess);
		errorLabel.setBounds(325,410,100,20);
		errorLabel.setForeground(Color.BLACK);
		errorLabel.setVisible(false);
		panel.add(errorLabel);

		Image image=Layout.getImage(Globals.logo);
		JLabel picPanel = new JLabel(new ImageIcon(image));
		picPanel.setBounds(250, 75, 300, 300);
		panel.add(picPanel);
		
		//listens to buttons
		loginBttn.setActionCommand("Login");
		loginBttn.addActionListener(this);
		ccBttn.setActionCommand("Register");
		ccBttn.addActionListener(this);	
		forgotBttn.setActionCommand("Forgot");
		forgotBttn.addActionListener(this);
	}
	//listens to buttons and gives info to fireEvent
    public void actionPerformed(ActionEvent e) {
    	
    	if(e.getActionCommand()=="Login"){		
    		if(Globals.example==true){
    			fireEvent(e.getActionCommand());
    		}
    		else{
    			boolean loginInputOK=checkFields();
    			if(loginInputOK==true){
    				errorLabel.setText("Loading...");
    				errorLabel.setVisible(true);
    				panel.repaint();
    				getID();
    				BookCheckWindow.createRest();
    				fireEvent(e.getActionCommand());
    			}
    		}
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
	public boolean checkFields(){
		boolean stat=false;
		loginInputOK=false;
		loginUserOK=false;
		loginPasswordOK=false;

		ResultSet user=DBConnect.queryDB("username", "users", "username='"+unameField.getText()+"'");
		try{
			while(user.next()){
				loginUserOK=true;
			}
		}
		catch (SQLException e){e.printStackTrace();}

		encryptPass();

		ResultSet pswrd=DBConnect.queryDB("passw", "users", "passw='"+encryptedPassW+"'");
		try{
			while(pswrd.next()){
				loginPasswordOK=true;
			}
		}
		catch (SQLException e){e.printStackTrace();}


		if(loginUserOK==false&&loginPasswordOK==false)
			JOptionPane.showMessageDialog(BookCheckWindow.frame, "Wrong Username and Password.");
		else if(loginUserOK==false)
			JOptionPane.showMessageDialog(BookCheckWindow.frame, "Wrong Username.");
		else if(loginPasswordOK==false)
			JOptionPane.showMessageDialog(BookCheckWindow.frame, "Wrong Password.");
		else if(loginUserOK==true||loginPasswordOK==true)
			stat=true;
			//loginInputOK=true;
		else
			JOptionPane.showMessageDialog(BookCheckWindow.frame, "Please Enter a Username and Password.");
		return stat;
	}
	public void getID(){
		ResultSet userID=DBConnect.queryDB("usersid", "users", "username='"+unameField.getText()+"'");
		try{
			while(userID.next()){
				String id=userID.getString("usersid");
				Globals.userid=id;
			}
		}
		catch (SQLException e){e.printStackTrace();}
	}
	public void encryptPass(){
		String passToEncrypt=pswrdField.getText();
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
			catch(NoSuchAlgorithmException e){e.printStackTrace();}
		}
}
