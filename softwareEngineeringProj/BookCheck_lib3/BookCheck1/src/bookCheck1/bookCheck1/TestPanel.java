package bookCheck1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;

public class TestPanel extends JPanel {

	public TestPanel() {
		gui();
		//http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html <-dialog info
	}
	public void DBTest(){
		//Database test
		
		//ResultSet query=DBConnect.queryDB("*","books","bookid=1");
		//DBConnect.insertDB("books (title,readinglvl,ISBN,cond,lang,bookset)","('Another Test','Easy','987654321','Fair','Spanish','1 of 1')");
		//DBConnect.updateDB("books", "title='Update Test', cond='Fair'", "bookid=4");
		//DBConnect.deleteFromDB("books","bookid=5");
		
		/*ResultSet query=DBConnect.queryDB("*","books","bookid=2");		
		try {
			while(query.next()){
				String title=query.getString("title");
				String lvl=query.getString("readinglvl");
				System.out.println("Title: "+title+"  Reading Level: "+lvl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}
	public void gui(){
		setLayout(null);
		
		JButton Bttn = new JButton("BUTTON");
		Bttn.setBounds(301, 277, 89, 23);
		add(Bttn);
		
		final JLabel label = new JLabel("THIS PART IS NOT DONE YET");
		label.setFont(new Font("Tahoma", Font.PLAIN, 35));
		label.setBounds(10, 56, 746, 244);
		add(label);
		
		Bttn.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e){
				label.setText("This button does not do anything of use.");
			}
		});
	}
}
