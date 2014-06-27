package bookCheck1;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BookCheckWindow extends JFrame {	
	private static TestPanel testP;
	private static LoginPanel loginP;
	private static AddBookPanel addBkP;
	public static CheckOutPanel1 chckOut1P;
	private static AddStdntPanel addStdntP;
	public static ViewPanel viewP;
	private static CreateUser createUsr;
	private static ForgotLogin forgotLog;
	public static CheckOutDialog chckOD;
	public static JFrame frame;
	
	//private static QRgenerator qrGen;	
	//private static DBConnect connect;
	
	public static void main(String[] args){
		Globals.connect=new DBConnect();	
		
		Exception conn=Globals.connect.connectDB();
		if(conn==null){
			createFrame();
		}
		else{
			JOptionPane.showMessageDialog(BookCheckWindow.frame,
					"Error: Could not connect to the Database.\n Check internet connection.\n"+conn,"Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static void createFrame(){	
		frame=new JFrame("Book Check");
		createLogin();
		
		//set up frame
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(900,700);
		frame.setLocation(275,25);
		frame.getContentPane().add(loginP);
	}
	public static void createLogin(){
		loginP=new LoginPanel();
		createUsr=new CreateUser();
		forgotLog=new ForgotLogin();
		
		
		loginP.addDetailListener(new PanelListener(){
			public void eventHappen(String event) {
				if(event=="Login"){frame.setContentPane(chckOut1P);}
				else if(event=="Register"){frame.setContentPane(createUsr);}
				else if(event=="Forgot"){frame.setContentPane(forgotLog);}
				frame.setVisible(true);
			}
		});	
		createUsr.addDetailListener(new PanelListener(){
			public void eventHappen(String event) {
				if(event=="Home"){frame.setContentPane(loginP);}
				frame.setVisible(true);
			}
		});
		forgotLog.addDetailListener(new PanelListener(){
			public void eventHappen(String event) {
				if(event=="Home"){frame.setContentPane(loginP);}
				frame.setVisible(true);
			}
		});
	}
	public static void createRest(){
		
		addStdntP=new AddStdntPanel();
		addBkP=new AddBookPanel();
		chckOut1P=new CheckOutPanel1();
		chckOD=new CheckOutDialog();
		viewP=new ViewPanel();		
		
		listeners(frame);
	}
	//Switches from one panel to another panel through listeners
	public static void listeners(final JFrame frame){
		//boolean studModified=false;
	
		
		chckOut1P.addDetailListener(new PanelListener(){
			public void eventHappen(String event){
				System.out.println(event);
				switch(event){
				case "Add Student":frame.setContentPane(addStdntP);
				frame.setVisible(true);
					break;
				case "Add Book":frame.setContentPane(addBkP);
				frame.setVisible(true);
					break;
				case "Exit":frame.dispose();
					break;
				case "View":frame.setContentPane(viewP);
				frame.setVisible(true);
					break;
				default: frame.setContentPane(testP);
				frame.setVisible(true);
					break;
				}
			}
		});
		addStdntP.addDetailListener(new PanelListener(){
			public void eventHappen(String event) {
				if(addStdntP.addStdMod==true){chckOut1P.getStuds();}
				addStdntP.addStdMod=false;
				frame.setContentPane(chckOut1P);
				//TODO reset pic
				addStdntP.clear();
				frame.setVisible(true);	
			}

		});	
		addBkP.addDetailListener(new PanelListener(){
			public void eventHappen(String event) {
				if(addBkP.addBkMod==true){chckOut1P.getBooks();}
				addBkP.addBkMod=false;
				frame.setContentPane(chckOut1P);
				//TODO reset pic; do this in sudents too
				addBkP.clear();
				frame.setVisible(true);				
			}
		});	
		viewP.addDetailListener(new PanelListener(){
			public void eventHappen(String event){
				if(viewP.viewMod==true){
					if(event=="students"){chckOut1P.getStuds();}
					else{chckOut1P.getBooks();}
				}
				frame.setContentPane(chckOut1P);
				frame.setVisible(true);
			}
		});
	}
	//TODO close DB connection and reset pics
	public void exit(){
		
	}
}

