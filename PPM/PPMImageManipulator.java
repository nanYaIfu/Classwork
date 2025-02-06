/* Ifunanya Okafor
 * Jack Khusayan
 * Course Number: CS2013
 * Section Number: Lecture-03 & Lab-04
 * Description: The following driver class "takes the name of a PPM image file,
 * asks the user what they want to do with the image: turn it to sepia, greyscale,
 * or negative, then asks the user for name of the manipulated file, and saves the new
 * image with a user chosen file name and exits.
 *
 * Other Comments: We made modifications to this class, so please use this one for testing.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class PPMImageManipulator {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter relative path of a PPM file: ");
		String filepath = input.nextLine().trim();
		
		try {
			PPMImage img = new PPMImage(filepath);
			
			System.out.println("How would you like to manipulate this file?");
			System.out.println("\t[G] Convert to grayscale.");
			System.out.println("\t[S] Convert it sepia.");
			System.out.println("\t[N] Convert it to its negative.");
			System.out.println("\t[W] Convert it to warm.");
			System.out.println("\t[K] Convert it to cool.");
			System.out.println("\t[H] Shrink it to 100x100?");
			System.out.println("\t[B] Censor the image.");
			System.out.println("\t[C] Copy without altering.");
			
			char operation = input.nextLine().charAt(0);
			operation = Character.toUpperCase(operation);
			
			switch (operation) {
			case 'G': img.grayscale(); break;
			case 'S': img.sepia(); break;
			case 'N': img.negative(); break;
			case 'W': img.warm(); break;
			case 'K': img.cool(); break;
			case 'H': img.shrink100(); break;
			case 'B': img.censor(); break;
			case 'C': /* do nothing... */ break;
			default: 
				System.out.println(operation + " is not a recognized command.");
				System.out.println("Exiting.");
				System.exit(1);
			}
			
			System.out.print("Enter filepath for the manipulated image: ");
			filepath = input.nextLine().trim();
			img.writeFile(filepath);
			
			System.out.println("Thank you for using PPMImageManipulator!");
			
		} catch (FileNotFoundException e) {
			System.out.println(filepath + " does not exist.");
		} catch (IOException e) {
			System.out.println("Could not complete request.");
			e.printStackTrace();
		}	
	}
}
