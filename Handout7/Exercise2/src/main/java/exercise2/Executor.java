package exercise2;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;
import ij.plugin.RGBStackMerge;
import ij.process.ByteProcessor;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

public class Executor {

	public static void main(String[] args) {
		/*
		ImagePlus imgPlus=new ImagePlus("data/ecoli/set1/feature_0.tif");
		//imgPlus.show();
		ImageProcessor imgProcessor = imgPlus.getProcessor();
		imgProcessor.resize(5);
		FileSaver fs = new FileSaver(imgPlus);
		//fs.saveAsJpeg();
		imgPlus.show();
		/************************/
		/*
		ImagePlus img = new ImagePlus("test.jpg", new ByteProcessor(800,600));
		ImagePlus img2=IJ.createImage("test.jpg", "RGB white", 800, 600, 1);
		FileSaver fs = new FileSaver(img2);
		fs.save();
		img.flush();
		img2.flush();
		*/
		ImagePlus img1=new ImagePlus("data/20P1_POS0002_D_1UL.tif");
		ImagePlus img2=new ImagePlus("data/20P1_POS0002_D_1UL.tif");
		ImagePlus[] images = {img1,img2};
		ImagePlus img3 = RGBStackMerge.mergeChannels(images, false);
		/*
		ImageConverter conv =new ImageConverter(img3);
		conv.convertToRGB();
		img3.updateImage();
		*/
		ImageConverter ic = new ImageConverter(img3);
		ic.convertToGray8();
		img3.updateImage();
		IJ.setAutoThreshold(img3, "Minimum");
		IJ.run(img1,"Convert to Mask","Black Background");
		Prefs.blackBackground=true;
	}

}
