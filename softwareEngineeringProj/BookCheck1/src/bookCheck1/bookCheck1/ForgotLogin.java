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

public class ForgotLogin extends JPanel implements ActionListener{
	private JPanel logo;
	private JTextField eMailTxFld;
	private boolean forgotInputOK=false;
	
	public ForgotLogin() {
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

		JLabel title = new JLabel("Recover Login Information");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setForeground(Color.BLACK);
		title.setBounds(280, 198, 200, 60);
		panel.add(title);

		JLabel userNameLbl = new JLabel("Enter Username or E-mail : ");
		userNameLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		userNameLbl.setBounds(158, 269, 98, 14);
		panel.add(userNameLbl);
		eMailTxFld = new JTextField();
		eMailTxFld .setBounds(295, 268, 175, 30);
		panel.add(eMailTxFld );
		eMailTxFld .setColumns(10);
		
		
		JButton submitBttn = new JButton("Submit");
		submitBttn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		submitBttn.setBounds(320, 559, 120, 35);
		panel.add(submitBttn);

		JButton cancelBttn = new JButton("Cancel");
		cancelBttn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cancelBttn.setBounds(335, 603, 90, 25);
		panel.add(cancelBttn);
		
		//listens to buttons
		submitBttn.setActionCommand("Password");
		submitBttn.addActionListener(this);
		cancelBttn.setActionCommand("Home");
		cancelBttn.addActionListener(this);	
	}
	
	//listens to buttons and gives info to fireEvent
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand()=="Home")
    		fireEvent(e.getActionCommand());
    	else if(e.getActionCommand()=="Password"){
    		checkFields();
    		if(forgotInputOK==true){
	    		try{
		    		//TODO put in error checking 
		    		//http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html <-popup message
		    		//popup message saying book added 
		    		JOptionPane.showMessageDialog(BookCheckWindow.frame, eMailTxFld.getText()+" E-mail Sent. Click Cancel to Log-In");
		    		forgotInputOK=false;
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
		forgotInputOK=false;
		ResultSet user=DBConnect.queryDB("email", "users", "email='"+eMailTxFld.getText()+"'");
		try{
			while(user.next()){
				String checkuser=user.getString("email"); //get user
				System.out.println(checkuser);
				forgotInputOK=true;
			}
		}
		catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	if(!eMailTxFld.getText().contains("@")||!eMailTxFld.getText().contains(".com"))
    		JOptionPane.showMessageDialog(BookCheckWindow.frame, "Must be an Email");
		else{
			JOptionPane.showMessageDialog(BookCheckWindow.frame, "Wrong Email");
			forgotInputOK=false;
		}
	}
}
