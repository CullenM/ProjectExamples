package bookCheck1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
 
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
 
public class QRgenerator {
	public QRgenerator(){
		//System.out.println("QR is working?");
	}
	public static void generate(String message){
		//System.out.println(message);
		ByteArrayOutputStream out = QRCode.from(message).to(ImageType.PNG).stream();
		 
		try { 
			FileOutputStream fout = new FileOutputStream(new File("./QR.PNG")); 
			fout.write(out.toByteArray()); 
			fout.flush();
			fout.close();
 
		} catch (FileNotFoundException e) {
			// Do Logging
			e.printStackTrace();
		} catch (IOException e) {
			// Do Logging
			e.printStackTrace();
		}
	}
}