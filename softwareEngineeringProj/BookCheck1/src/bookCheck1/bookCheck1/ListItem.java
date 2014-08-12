package bookCheck1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ListItem extends JPanel implements ActionListener{
	//String id;
	//String table;
	private JButton viewBttn;
	private JButton delBttn;
	private boolean firstPass=true;
	private static BookCheckWindow bookCW;
	//private ViewPanel viewP;

	public JPanel listGui(String[] listItem, String Mode,Blob blobPic){
		Image scaledpic = null;
		final String table=listItem[5];
		final String id=listItem[4];
		
		JPanel item= new JPanel();
		GroupLayout layout=new GroupLayout(item);
		item.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
	
		item.setBorder(new LineBorder(new Color(0, 0, 0), 5));
		item.setPreferredSize(new Dimension(875, 105));
		Font font=new Font("Tahoma", Font.BOLD, 15);
		
		//picture stuff
		if(blobPic==null){			
			try {scaledpic = ImageIO.read(new File(Globals.noPic)).getScaledInstance(75,75,Image.SCALE_SMOOTH);
			} catch (IOException e1) {e1.printStackTrace();}
		}
		else{
		byte[] bytePic = null;
		try {bytePic = blobPic.getBytes(1, (int) blobPic.length());
		} catch (SQLException e2) {e2.printStackTrace();}
		Image pic = null;
		try {pic = ImageIO.read(new ByteArrayInputStream(bytePic));}
		catch (IOException e1) {e1.printStackTrace();}		
		scaledpic = pic.getScaledInstance(75,75,Image.SCALE_SMOOTH);
		//JLabel picture = new JLabel(new ImageIcon(scaledpic));
		//picture.setPreferredSize(new Dimension(75,75));
		//item.add(picture);
		}
		JLabel picture = new JLabel(new ImageIcon(scaledpic));
		picture.setPreferredSize(new Dimension(75,75));
		item.add(picture);
		//ImageIcon icon = new ImageIcon(pic);
		//icon.getImage().flush();
		//picPanel.setIcon( icon );
	
		JLabel line1aLbl = new JLabel(listItem[0]);
		line1aLbl.setFont(font);
		line1aLbl.setForeground(Color.BLACK);
		item.add(line1aLbl);
		JLabel line1bLbl = new JLabel(listItem[1]);
		line1bLbl.setFont(font);
		line1bLbl.setForeground(Color.BLACK);
		item.add(line1bLbl);		
		JLabel rLvlLbl =new JLabel(listItem[2]);
		rLvlLbl.setFont(font);
		rLvlLbl.setForeground(Color.BLACK);
		item.add(rLvlLbl);		
		JLabel checkedLbl=new JLabel(listItem[3]);
		if(table=="books"){
			Color color=Layout.checkDate(listItem[3]);
			if(color==Color.BLACK){checkedLbl.setVisible(false);}
			else{
				checkedLbl.setBackground(color);
				checkedLbl.setOpaque(true);
			}
		}
		else{checkedLbl.setText("");}
		checkedLbl.setForeground(Color.BLACK);
		checkedLbl.setFont(font);
		item.add(checkedLbl);
		 
		viewBttn=new JButton("View");
		item.add(viewBttn);
		delBttn=new JButton("Delete");
		item.add(delBttn);
		
		viewBttn.addActionListener(new ActionListener(){			
			@Override
			public void actionPerformed(ActionEvent e){
				//if(firstPass==true){firstPass=false;}
				//else{ViewPanel.reformat();}
				ViewPanel.reformat();
				bookCW.viewP.create(id,table);
			}
		});
		
		viewBttn.setActionCommand(viewBttn.getText());
		viewBttn.addActionListener(this);
		delBttn.setActionCommand(delBttn.getText());
		delBttn.addActionListener(this);
		
		//TODO Make a better layout. add checkin/out button to books
		layout.setHorizontalGroup(layout.createSequentialGroup()
			.addGap(10)
		    .addComponent(picture)
		    .addGap(10)
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(line1aLbl)			                
		                .addComponent(rLvlLbl)
		                .addComponent(checkedLbl))
		            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		                .addComponent(line1bLbl))))			                
		     .addGroup(layout.createSequentialGroup()
		    		 .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        .addComponent(viewBttn)
		        .addComponent(delBttn)))
				);
		layout.setVerticalGroup(layout.createSequentialGroup()
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
		    .addGap(10)
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    		.addComponent(picture)
		    .addGroup(layout.createSequentialGroup()			    		
		    			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)	
		    					.addComponent(line1aLbl)
		    					.addComponent(line1bLbl)
		    					.addComponent(viewBttn))
		    			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    					.addComponent(rLvlLbl))
		    			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		    					.addComponent(checkedLbl)
		    					.addComponent(delBttn))))
			);
	
		layout.linkSize(SwingConstants.HORIZONTAL, picture,viewBttn,delBttn);
		layout.linkSize(SwingConstants.VERTICAL, picture);
		return item;
	}
	//listens to buttons
    public void actionPerformed(ActionEvent e) {
    	bookCW.chckOut1P.actionPerformed(e);
    }
}