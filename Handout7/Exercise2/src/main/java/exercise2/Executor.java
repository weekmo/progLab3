package exercise2;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

/**
 * Executor class has main function to start the application<br/>
 * it contain solution for Handout7-exercise2
 * 
 * @author laplace
 */
public class Executor {

	public static void main(String[] args) {

		// Read image set1/phase.tif and save it as png
		ImagePlus img = new ImagePlus("data/ecoli/set1/phase.tif");
		FileSaver fileSaver = new FileSaver(img);
		// System.out.println("set1/phase.tif size (before resize):
		// "+Arrays.toString(img.getDimensions()));
		System.out.println(
				"set1/phase.tif size (before resize): " + img.getDimensions()[0] + " x " + img.getDimensions()[1]);
		fileSaver.saveAsPng("data/ecoli/set1/phase.png");

		// Resize image set1/phase.tif and save resized image as png
		ImageProcessor imgProcessor = img.getProcessor();
		img = new ImagePlus("phaseHalfSize1", imgProcessor.resize(img.getWidth() / 2));
		System.out.println("set1/phaseHalfSize.png size (after resize): " + img.getDimensions()[0] + " x "
				+ img.getDimensions()[1]);
		fileSaver = new FileSaver(img);
		fileSaver.saveAsPng("data/ecoli/set1/phaseHalfSize.png");

		// Read image set2/phase.tif and save it as png
		img = new ImagePlus("data/ecoli/set2/phase.tif");
		fileSaver = new FileSaver(img);
		System.out.println(
				"\nset2/phase.tif size (before resize): " + img.getDimensions()[0] + " x " + img.getDimensions()[1]);
		fileSaver.saveAsPng("data/ecoli/set2/phase.png");

		// Resize image set2/phase.tif and save resized image as png
		imgProcessor = img.getProcessor();
		img = new ImagePlus("phaseHalfSize2", imgProcessor.resize(img.getWidth() / 2));
		fileSaver = new FileSaver(img);
		System.out.println("set2/phaseHalfSize.png size (after resize): " + img.getDimensions()[0] + " x "
				+ img.getDimensions()[1]);
		fileSaver.saveAsPng("data/ecoli/set2/phaseHalfSize.png");

	}

}
