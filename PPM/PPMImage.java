/* Ifunanya Okafor
 * Jack Khusayan
 * Course Number: CS2013
 * Section Number: Lecture-03 & Lab-04
 * Description: The following class can "read a PPM image,
 * alter its pixel data to change how the image looks, and
 * write the changed image to a new file", as originally intended.
 * We successfully added grayscale, sepia, and negative methods.
 * The grayscale method alters the pixel data in the raster array
 * to make it look grey, while the sepia also alters the data but
 * to make the image have a reddish-brown tone (sepia). Finally,
 * the negative method inverts the RGB values in the image array.
 *
 * Other Comments: We also added two additional methods: one that
 * censors a given PPM image and one that shrinks a given PPM image
 * down to 100x100, which are named censor and shrink100 respectively.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PPMImage {
	public static final String MAGIC_NUMBER = "P6";
	private String magicNumber;
	private int width;
	private int height;
	private int maxColorValue;
	private char[] raster;

	public PPMImage(String imageFileName) throws IOException {
		if (!imageFileName.endsWith(".ppm")) {
			throw new IOException("Input filename must end with .ppm; "
					+ "got " + imageFileName);
		}
		readImage(imageFileName);
	}

	private void readImage(String imageFileName) throws FileNotFoundException,
			IOException {
		try {
			FileInputStream fis =
					new FileInputStream(new File(imageFileName));

			// read magic number
			magicNumber = ""
					+ (char) fis.read()
					+ (char) fis.read();

			if (!magicNumber.equals(MAGIC_NUMBER)) {
				throw new IOException("expected " + MAGIC_NUMBER + ", got"
						+ magicNumber + " as magic number");
			}

			// omit line feed after magic number
			fis.skip(1);

			width = readNum(fis);
			height = readNum(fis);
			maxColorValue = readNum(fis);

			raster = new char[width * height * 3];
			for (int i = 0; i < raster.length; i++) {
				raster[i] = (char) fis.read();
			}

			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public void writeFile(String outputImageFileName) throws FileNotFoundException,
			IOException {

		// TO DO: Should also accept
		if (!outputImageFileName.endsWith(".ppm")) {
			throw new IOException("Output filename must end with .ppm; "
					+ "got " + outputImageFileName);
		}

		try {
			FileOutputStream fos =
					new FileOutputStream(outputImageFileName);

			// write magic number
			fos.write(magicNumber.charAt(0)); // write 'P'
			fos.write(magicNumber.charAt(1)); // write '6'

			// write newline
			fos.write('\n');

			// write width, height, maxColorValue
			writeNum(fos, width);
			fos.write(' ');
			writeNum(fos, height);
			fos.write('\n');
			writeNum(fos, maxColorValue);
			fos.write('\n');

			// write raster data
			for (int i = 0; i < raster.length; i++) {
				fos.write(Math.min(raster[i], maxColorValue));
			}

			fos.flush();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void writeNum(FileOutputStream fos, int num) throws IOException {
		String digits = Integer.toString(num);

		for (int i = 0; i < digits.length(); i++) {
			fos.write(digits.charAt(i));
		}
	}

	private int readNum(FileInputStream fis) throws IOException {
		StringBuilder digits = new StringBuilder();
		//String s = "";
		char d;

		do {
			d = (char) fis.read();
			digits.append(d);
			//s += d;
		} while (!Character.isWhitespace((int) d));

		return Integer.parseInt(digits.toString().trim());
	}

	public void warm() {
		for (int i = 0; i < raster.length; i += 3) {
			//R = raster[i]
			//G = raster[i+1]
			//B = raster[i+2]

			raster[i] *= 1.5;
			raster[i + 1] *= 1;
			raster[i + 2] *= 1;

		}
	}

	public void cool() {
		for (int i = 0; i < raster.length; i += 3) {
			raster[i] *= (3 / 5);
			raster[i + 2] *= 3.5;

		}
	}

	public void grayscale() {
		for (int i = 0; i < raster.length; i += 3) {
			raster[i] = (char) ((raster[i] * 0.299) + (raster[i + 1] * 0.587) + (raster[i + 2] * 0.114));
			raster[i + 1] = (char) ((raster[i] * 0.299) + (raster[i + 1] * 0.587) + (raster[i + 2] * 0.114));
			raster[i + 2] = (char) ((raster[i] * 0.299) + (raster[i + 1] * 0.587) + (raster[i + 2] * 0.114));

		}
	}

	public void sepia() {
		for (int i = 0; i < raster.length; i += 3) {
			raster[i] = (char) ((raster[i] * .393) + (raster[i + 1] * .769) + (raster[i + 2] * .189));
			raster[i + 1] = (char) ((raster[i] * .349) + (raster[i + 1] * .686) + (raster[i + 2] * .168));
			raster[i + 2] = (char) ((raster[i] * .272) + (raster[i + 1] * .534) + (raster[i + 2] * .131));

			for (int j = 0; j < 3; j++) {
				if (raster[i + j] > 255) {
					raster[i + j] = 255;
				}
			}
		}
	}

	public void negative() {
		for (int i = 0; i < raster.length; i += 3) {
			raster[i] = (char) (255 - raster[i]);
			raster[i + 1] = (char) (255 - raster[i]);
			raster[i + 2] = (char) (255 - raster[i]);

		}
	}

	public void censor() {
		int censorIntensity = 175;

		for (int i = 0; i < raster.length; i += (censorIntensity * 3)) {
			char censorBlockR = raster[i];
			char censorBlockG = raster[i + 1];
			char censorBlockB = raster[i + 2];

			for (int j = i; j < (censorIntensity * 3) + i && j < raster.length; j += 3) {
				raster[j] = censorBlockR;
				raster[j + 1] = censorBlockG;
				raster[j + 2] = censorBlockB;
			}

		}
	}

	public void shrink100() {

		double ratio = (double) width / height;
		int newWidth = 100;
		int newHeight = (int) (100 / ratio);
		double heightScaler = (double) newHeight / height;
		double widthScaler = (double) newWidth / width;

		char[] shrunkRaster = new char[newHeight * newWidth * 3];

		for (int y = 0; y < newHeight; y++) {
			for (int x = 0; x < newWidth; x++) {

				int origX = (int) (x / widthScaler);
				int origY = (int) (y / heightScaler);
				int origIndex = (origY * width + origX) * 3;
				int newIndex = (y * newWidth + x) * 3;


				shrunkRaster[newIndex] = raster[origIndex];
				shrunkRaster[newIndex + 1] = raster[origIndex + 1];
				shrunkRaster[newIndex + 2] = raster[origIndex + 2];
			}
		}


		this.width = newWidth;
		this.height = newHeight;
		this.raster = shrunkRaster;
	}

}




