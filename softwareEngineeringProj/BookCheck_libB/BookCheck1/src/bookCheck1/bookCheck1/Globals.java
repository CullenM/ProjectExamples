package bookCheck1;

public class Globals {
	static DBConnect connect;	
	public static String userid="39";
	public static final String logo="./DIApawprint.jpg";
	public static final String noPic="./no.jpg";
	public static String[] studList={"First Name","Last Name","Parents","Street","City","Zip Code","Group"};
	public static String[] bookList={"Title","Reading Level", "ISBN","Condition","Language","Set","Due Date"};
	static int printWidth=0, printHeight=0, printCount=0;
	static String fileName=null;
}
