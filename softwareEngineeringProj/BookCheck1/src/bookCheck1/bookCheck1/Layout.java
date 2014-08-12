package bookCheck1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class Layout extends JPanel{
	public void hello(){
		System.out.println("Hello");
	}
	public JPanel panelBackground(){
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBackground(Color.ORANGE);
		panel.setBounds(65, 0, 765, 700);
		panel.setLayout(null);
		//System.out.println("Hello");
		
		return panel;
		}
	public static Image getImage(String path) {
		Image image=null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {e.printStackTrace();}
		return image;
	}
	public static void resetCover(JLabel picPanel){
	    RenderedImage img = null;
		try {img = ImageIO.read(new File("./no.jpg"));
		} catch (IOException e1) {e1.printStackTrace();}
	    try {ImageIO.write(img, "jpg",new File("./cover.jpg"));
		} catch (IOException e1) {e1.printStackTrace();}
	    
		Image pic=getImage(Globals.logo);
		ImageIcon icon = new ImageIcon(pic);
		icon.getImage().flush();
		picPanel.setIcon( icon );
	}
	//I dont think the method id actualy used
    public void reformat(JPanel panel){
    	System.out.println("REFORMAT");
    	panel.removeAll();
    	panel.revalidate();
    	panel.repaint();
    }
    //remove Foreign Key: delID=id that is being deleted; fromID= id of table row 
    public static void removeFKey(String table,String column,String fromID,String delID){
    	String col="",temp="",replace="";
    	int i=0;

    	ResultSet query=DBConnect.queryDB(column, table, table+"id="+fromID);
    	try {
			while(query.next()){col=query.getString(column);}
		} catch (SQLException e) {e.printStackTrace();}
    	char[] result=col.toCharArray();
    	while(i<col.length()){
    		if(result[i]==' '){    			
    			if(temp.equals(delID)){temp="";}
    			else{
    				temp+=result[i];
    				replace+=temp;
    				temp="";}
    		}
    		else{temp+=result[i];}
    		i++;
    	}
    	DBConnect.updateDB(table, column+"='"+replace+"'", table+"id="+fromID);
    }
    public static Image takePic(String save){
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Image cover = new ImageIcon("cover.jpg").getImage();
    	VideoCapture camera=new VideoCapture(0);

		if(camera.isOpened()){
    		Mat frame = new Mat();
    	    while(true){
    	    	if (camera.read(frame)){
    	    		Highgui.imwrite(save, frame);
    	    		//System.out.println("Cover Picture was taken!");
    	    		break;
    	    	}
    	    }	
    	}
    	else{ JOptionPane.showMessageDialog(BookCheckWindow.frame, "Error: "+ "unable to find a web camera.");
    		return null;
    	}
    	camera.release();	
	    return cover;
    }
    public static Color checkDate(String date){
    	if(date.equals("9999-12-31")){return Color.BLACK;}
    	else{
    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		Calendar cal = Calendar.getInstance();
    	
    		Date today=cal.getTime();
    		cal.add(Calendar.DATE,3);
    		Date soon = cal.getTime();
    		Date bookDate = null;
			try {bookDate = dateFormat.parse(date);
			} catch (ParseException e){e.printStackTrace();}
						
    		if(today.after(bookDate)||today.equals(bookDate)){return Color.RED;}
    		else if(soon.after(bookDate)||soon.equals(bookDate)){return Color.YELLOW;}
    		else {return Color.GREEN;}  
    	}
    }
    //TODO checks text for invalid characters
    public String checkText(){
    	return null;
    }
    public static int checkInsert(String id){
		if(id.contains("Error:")){
			if(id.contains("Communications link failure")){
				DBConnect.connectDB();
				return 1;
			}
			else{
				JOptionPane.showMessageDialog(BookCheckWindow.frame,    				
				"Error: Could not be added.\n"+id,"Insert Error", JOptionPane.ERROR_MESSAGE);
				return 0;}
		}
		else{return 2;}
    }
    public void qrPrint(String title,String id, Image printPic){
    	String idLabel="Book ID: "+id;
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		FontMetrics fm = getFontMetrics(font);
		String desktop = System.getProperty("user.home")+"\\Desktop\\";
		
		Image scalePic = printPic.getScaledInstance(350,350,Image.SCALE_SMOOTH);
	    BufferedImage buffPic = new BufferedImage(1700, 2200,
	    		BufferedImage.TYPE_INT_RGB);
	    
	    Graphics2D bGr = buffPic.createGraphics();
        bGr.setColor(Color.WHITE);
        bGr.fillRect(0, 0, buffPic.getWidth(), buffPic.getHeight());
	    bGr.drawImage(scalePic, 0, 0, null);
	    bGr.dispose();

	    Graphics g = buffPic.getGraphics();
	    g.setColor(Color.BLACK);
	    g.setFont(font);
	    //fm.stringWidth(idLabel)
	    int xPos = (scalePic.getWidth(null) - fm.stringWidth(idLabel)) / 2;
		g.drawString(idLabel, xPos,40);
		xPos = (scalePic.getWidth(null) - fm.stringWidth(idLabel)) / 2;
		g.drawString(title, xPos, scalePic.getHeight(null));
		g.dispose();
		try {ImageIO.write(buffPic, "png", new File(desktop+"test.png"));
		} catch (IOException e) {e.printStackTrace();}
    }
}