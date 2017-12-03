package exercise3;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.plugin.RGBStackMerge;
import ij.process.ImageConverter;

public class Executor {

	public static void main(String[] args) {
		ImagePlus img1=new ImagePlus("data/20P1_POS0002_D_1UL.tif");
		ImageConverter imgConverter1 = new ImageConverter(img1);
		imgConverter1.convertToGray8();
		
		ImagePlus img2=new ImagePlus("data/20P1_POS0002_F_2UL.tif");
		ImageConverter imgConverter2 = new ImageConverter(img2);
		imgConverter2.convertToGray8();
		
		ImagePlus[] images = {img1,img2};
		ImagePlus img3 = RGBStackMerge.mergeChannels(images, false);
		FileSaver fileSaver = new FileSaver(img3);
		fileSaver.saveAsPng("data/dualchannel.png");
		fileSaver.saveAsTiff("data/dualchannel.tif");
	}

}
