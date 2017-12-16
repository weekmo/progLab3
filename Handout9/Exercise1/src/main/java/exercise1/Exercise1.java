package exercise1;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageConverter;

/**
 * A class to solve Handout9 - exercise1, the exercise is about Reading images from an input folder
 * and write it to the output folder in either the format of the input file or defined by the user.
 * There are three arguments have to be submitted:
 * 		1- Input folder: --input-folder or simply -i
 * 		2- Output folder: --output-folder or simply -o
 * 		3- Image(s) format (optional): --format or simply -f
 * 
 * Example: -i data -o new-folder -f png
 * or	  : -i data -o new-folder
 * 
 * @author laplace
 *
 */
public class Exercise1 {

	/**
	 * Main function
	 */
	public static void main(String[] args) {

		// Create and initialise command line objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		/*
		 * There are three command line parameters:
		 * 		input-folder : A folder contain all images that we need to process.
		 * 		output-folder: A folder where we need to save output images.
		 * 		format		 : A format for output images.
		 * Example: -i data -o new-folder -f png
		 */
		
		//setup options
		Option inputFolder = new Option("i", "input-folder", true, "Input folder contains images!");
		inputFolder.setRequired(true);
		options.addOption(inputFolder);

		Option outputFolder = new Option("o", "output-folder", true, "Output folder to save output images!");
		outputFolder.setRequired(true);
		options.addOption(outputFolder);

		Option format = new Option("f", "format", true, "Image format for output image(s)");
		options.addOption(format);

		//Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise1 - Reading images from a folder", options);
			System.exit(1);
			return;
		}

		// Read values from command line
		String inFolderName = cmd.getOptionValue("input-folder");
		String outFolderName = cmd.getOptionValue("output-folder");
		String formatValue = cmd.getOptionValue("format");
		
		//Check image format sent by user
		if(formatValue!=null&&!isSupported(formatValue)) {
			System.err.println("Sorry, image format '"+formatValue+"' is not supported. Try again");
			System.exit(1);
		}
		//Get all files in input folders
		File[] files = new File(inFolderName).listFiles();
		File outFolder =new File(outFolderName);
		outFolder.mkdirs();
		/*
		 * Read all files and filter images by extensions and
		 * save them to output folder
		 */
		String imageName;
		for(int i=0;i<files.length;i++) {
			imageName=files[i].getName();
			if (files[i].isFile() && isSupported(FilenameUtils.getExtension(imageName))){ 
				if(formatValue==null) {
					try {
						FileUtils.copyFileToDirectory(new File(inFolderName+"/"+imageName), outFolder);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else {
					imageSaver(new ImagePlus(inFolderName+"/"+imageName), formatValue, outFolderName);
				}
		    }
		}
		System.out.println("Images saved in: "+outFolder);
	}
	
	/**
	 * A function to filter images, if the file extension is image extension,
	 * it will return true, otherwise it will return false. 
	 * @param fileExtension
	 * @return boolean
	 */
	public static boolean isSupported(String fileExtension) {
		String[] formats=new String[]
				{"tiff","tif","gif","jpeg","jpg","png","bmp","pgm","pbm","ppm","raw","fits","lut"};
		for(String extension:formats) {
			if(fileExtension.equalsIgnoreCase(extension)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A function to save image depends on the type
	 * submitted by the user as an argument
	 * 
	 * @param img
	 * @param outFolder
	 */
	public static void imageSaver(ImagePlus img,String format,String outFolder) {
		FileSaver saver = new FileSaver(img);
		String imagePath=outFolder+"/"+img.getShortTitle()+"."+format;
		ImageConverter ic;
		if(format.equalsIgnoreCase("tif") || 
				format.equalsIgnoreCase("tiff")) {
			saver.saveAsTiff(imagePath);
		}
		else if(format.equalsIgnoreCase("gif")) {
			ic=new ImageConverter(img);
			if(FilenameUtils.getExtension(img.getTitle()).equalsIgnoreCase("gif"))
			{
				System.err.println(img.getTitle()+" converted to black/white");
			}
			try {
				ic.convertRGBtoIndexedColor(img.getBitDepth());
				img.updateImage();
			}catch(IllegalArgumentException e) {
				ic.convertToGray8();
				img.updateImage();
				System.err.println(img.getTitle()+" converted to Gray 8-bit");
			}
			saver.saveAsGif(imagePath);
			
		}
		else if(format.equalsIgnoreCase("jpg") || 
				format.equalsIgnoreCase("jpeg")) {
			saver.saveAsJpeg(imagePath);
		}
		else if(format.equalsIgnoreCase("bmp")) {
			saver.saveAsBmp(imagePath);
		}
		else if(format.equalsIgnoreCase("pgm") || 
				format.equalsIgnoreCase("pbm") || 
				format.equalsIgnoreCase("ppm")) {
			saver.saveAsPgm(imagePath);
		}
		else if(format.equalsIgnoreCase("png")) {
			saver.saveAsPng(imagePath);
		}
		else if(format.equalsIgnoreCase("fits")) {
			ic=new ImageConverter(img);
			ic.convertToGray8();
			img.updateImage();
			saver.saveAsFits(imagePath);
		}
		else if(format.equalsIgnoreCase("lut")) {
			saver.saveAsLut(imagePath);
		}
		else {
			System.err.println("Sorry, Unsoppurted format!");
		}
	}
}
