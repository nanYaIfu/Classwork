package text_File;
import java.io.File;
import java.util.Scanner;

public class ReadData {

	public static void main(String[] args) {

		try {
			// Make a file instance
			File file = new java.io.File("thisTestWriteOutput.txt");

			// Make Scanner for file
			Scanner inputthing = new Scanner(file);

			// Read data from file

			while (inputthing.hasNext()) {
				String firstName = inputthing.next();
				String mi = inputthing.next();
				String lastName = inputthing.next();
				int score = inputthing.nextInt();

				System.out.println(firstName + " " + mi + " " + lastName + " " + score);

			}
			// Close the file
			inputthing.close();

		} catch (Exception e) {
			System.err.println(e);
			System.out.println("It broke...");
		} finally {

		System.out.println("Ok anyway, I can still continue with my program");

	}

}
}
