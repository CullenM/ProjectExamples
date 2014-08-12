package bookCheck1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class DBConnect {
	public static java.sql.Connection con;
	private static java.sql.Statement stat;	
	
	//connect to DB
	public static Exception connectDB(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			//don't forget to allow remote host access
			con = DriverManager.getConnection("jdbc:mysql://gator4134.hostgator.com/dia_bookcheck","dia","BookCheck948");
			//conn = DriverManager.getConnection("jdbc:mysql://HOST/DATABASE","DB USER","PASS");
			stat=con.createStatement();
			return null;
		}catch(Exception ex){
			System.out.println("Error: "+ex);
			return ex;
		}
	}
	//get stuff from the DB
	public static ResultSet queryDB(String select, String from, String where) {
		ResultSet rslt = null;
		
		//I think I dont need this if()else() anymore but Im not sure
		/*if(where==null){
			try{
				String query="SELECT "+select+" FROM "+from;
				rslt=stat.executeQuery(query);
			}catch(Exception ex){
					System.out.println("Error: "+ex);
			}		
		}*/
		//else{
			try{
				String query="SELECT "+select+" FROM "+from+" WHERE "+where;
				rslt=stat.executeQuery(query);
			}catch(Exception ex){
					System.out.println("Error: "+ex);
			}	
		//}
		return rslt;
	}
	//put stuff into DB
	public static String insertDB(String table, String values){
		String insert="INSERT INTO "+table+" VALUES "+values;
		String result=execute(insert);
		
		if(result.contains("Error:")){
			System.out.println(result);
			if(result.contains("Communications link failure" )){
				Globals.connect.connectDB();
				result=insertDB(table,values);
			}
		}
		else {System.out.println(values+" inserted into "+table);}
		return result;
	}
	//change stuff in DB
	public static String updateDB(String table, String set, String where){
		String update="UPDATE "+table+" SET "+set+" WHERE "+where;
		String result=execute(update);		
		if(result.contains("Error:")){System.out.println(result);}
		else {System.out.println(table+" updated");}
		return result;
	}
	//remove stuff from DB
	public static void deleteFromDB(String table, String where){
		String delete="DELETE FROM "+table+" WHERE "+where;
		String result=execute(delete);		
		if(result.contains("Error:")){System.out.println(result);}
		else {System.out.println(where+" was deleted from "+table);}
	}
	//actually perform action for insert, update, and delete
	public static String execute(String sql){
		java.sql.PreparedStatement prep;
		ResultSet rslt;
		int keyNum=0;
		String key=null;
		try {
			prep = con.prepareStatement(sql ,Statement.RETURN_GENERATED_KEYS);
			prep.executeUpdate();
			rslt = prep.getGeneratedKeys();
			if(rslt.next() && rslt != null){keyNum=rslt.getInt(1);}
			//System.out.println(key);
			key=Integer.toString(keyNum);
			return key;
		} catch (SQLException ex) {
			return ("Error: "+ex);
		}
	}
	public static String pStmt(String stmt,String filePath){
    	java.sql.PreparedStatement pState=null;    	
    	File file=new File(filePath); //image path
    	FileInputStream fis = null;
    	long lenx=file.length();
    	byte [] b=new byte[(int)lenx];
    	int count=1, keyNum = 0;
    	String key=null;
    	
		try {fis = new FileInputStream(file);} catch (FileNotFoundException e2) {e2.printStackTrace();}    	 
    	try {fis.read(b);} catch (IOException e2) {e2.printStackTrace();}
    	
    	try {pState=con.prepareStatement(stmt,Statement.RETURN_GENERATED_KEYS);} 
    	catch (SQLException e1) {System.out.println("Error: "+e1);}  
    	
    	char[] find=stmt.toCharArray();
    	for(int i=0;i<stmt.length();i++){
    		if(find[i]=='?'){
    			if(find[i-1]!='/'){
    				try {pState.setBytes(count,b);} catch (SQLException e2) {e2.printStackTrace();}
    				count++;
    			}
    		}
    	}
		try {pState.executeUpdate();
			ResultSet rslt = pState.getGeneratedKeys();
			if(rslt.next() && rslt != null){keyNum=rslt.getInt(1);}
			key=Integer.toString(keyNum);
			return key;
		} catch (SQLException ex) {
			System.out.println(ex);
			return ("Error: "+ex);}
		
	}
}