package bookCheck1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class SuperClass extends JPanel {
	//creates QR spreadsheet
	public void qrPrint(String title,String id, Image printPic){
		String idLabel="Book ID: "+id;
		int pageWidth=1700, pageHeight=2200;
		BufferedImage buffPic = null;
		Font font1 = new Font(Font.SANS_SERIF, Font.BOLD, 40);
		Font font2 = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
		FontMetrics fm1 = getFontMetrics(font1);
		FontMetrics fm2 = getFontMetrics(font2);
		String desktop = System.getProperty("user.home")+"\\Desktop\\";
		
		
		if(Globals.printWidth==0&&Globals.printHeight==0){
			buffPic = new BufferedImage(pageWidth, pageHeight,BufferedImage.TYPE_INT_RGB);
			Globals.printCount++;
			Globals.fileName="QRSheet"+Globals.printCount+".png";
			
		}
		else{			
			try {buffPic= ImageIO.read(new File(desktop+Globals.fileName));
			} catch (IOException e) {e.printStackTrace();}
		}
		Image scalePic = printPic.getScaledInstance(350,350,Image.SCALE_SMOOTH);
    
		Graphics2D bGr = buffPic.createGraphics();
		bGr.setColor(Color.WHITE);
		if(Globals.printWidth==0&&Globals.printHeight==0){bGr.fillRect(0, 0, buffPic.getWidth(), buffPic.getHeight());}
		bGr.drawImage(scalePic, Globals.printWidth, Globals.printHeight, null);
		bGr.dispose();

		Graphics g = buffPic.getGraphics();
		g.setColor(Color.BLACK);
		g.setFont(font1);
		int xPos = (scalePic.getWidth(null) - fm1.stringWidth(idLabel)) / 2+Globals.printWidth;
		g.drawString(idLabel, xPos,Globals.printHeight+40);
		if(fm1.stringWidth(title)>300){
			g.setFont(font2);
			System.out.println("too big");
			String divTitle= title.substring(0, 16);
			xPos = (scalePic.getWidth(null) - fm2.stringWidth(divTitle)) / 2+Globals.printWidth;
			g.drawString(divTitle, xPos, scalePic.getHeight(null)-30+Globals.printHeight);
			divTitle=title.substring(16, title.length());
			xPos = (scalePic.getWidth(null) - fm2.stringWidth(divTitle)) / 2+Globals.printWidth;
			g.drawString(divTitle, xPos, scalePic.getHeight(null)+Globals.printHeight);
		}
		else{	
			xPos = (scalePic.getWidth(null) - fm1.stringWidth(title)) / 2+Globals.printWidth;
			g.drawString(title, xPos, scalePic.getHeight(null)+Globals.printHeight);
		}
		g.dispose();
		try {ImageIO.write(buffPic, "png", new File(desktop+Globals.fileName));
		} catch (IOException e) {e.printStackTrace();}
		
		Globals.printWidth+=450;
		if(Globals.printWidth>=pageWidth){
			Globals.printHeight+=370;
			Globals.printWidth=0;
			if(Globals.printHeight>=pageHeight){
				Globals.printWidth=0;
				Globals.printHeight=0;
			}
		}
	}	
}