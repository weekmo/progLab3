package exercise2;

import java.util.Arrays;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

public class Executor {

	public static void main(String[] args) {
		
		ImagePlus img=new ImagePlus("data/ecoli/set1/phase.tif");
		FileSaver fileSaver = new FileSaver(img);
		System.out.println(Arrays.toString(img.getDimensions()));
		fileSaver.saveAsPng("data/ecoli/set1/phase.png");
		
		ImageProcessor imgProcessor = img.getProcessor();
		img=new ImagePlus("phaseHalfSize", imgProcessor.resize(img.getWidth()/2));
		System.out.println(Arrays.toString(img.getDimensions()));
		fileSaver = new FileSaver(img);
		fileSaver.saveAsPng("data/ecoli/set1/phaseHalfSize.png");
		
		
		img=new ImagePlus("data/ecoli/set2/phase.tif");
		fileSaver = new FileSaver(img);
		System.out.println(Arrays.toString(img.getDimensions()));
		fileSaver.saveAsPng("data/ecoli/set2/phase.png");

		imgProcessor = img.getProcessor();
		img=new ImagePlus("phaseHalfSize", imgProcessor.resize(img.getWidth()/2));
		fileSaver = new FileSaver(img);
		System.out.println(Arrays.toString(img.getDimensions()));
		fileSaver.saveAsPng("data/ecoli/set2/phaseHalfSize.png");
		
	}

}
