package text_File;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class WriteData {

	public static void main(String[] args) throws FileNotFoundException{
		//the above method could potentially have an error
		String pathString = "thisTestWriteOutput.txt";
		
		try { 
			File aFile = new File (pathString);
			
			PrintWriter prtout = new PrintWriter(aFile); 
			
			prtout.println("Hello World!");
			
			prtout.flush();
			prtout.close();
			
			System.out.println("made it this far");
			
		} catch (Exception e) {
			//if something breaks catch it and print out what went wrong
			System.err.println(e);
		}

	}

}
