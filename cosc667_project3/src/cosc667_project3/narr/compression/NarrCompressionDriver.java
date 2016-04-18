package cosc667_project3.narr.compression;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import cosc667_project3.narr.kmeans.NarrKmeans;

public class NarrCompressionDriver {

	public static void main(String[] args) throws IOException {

		// Set delimiter to / or \ according to operating system
		final String DELIMITER = File.separator;

		String root = "Resources";
		
		String directoryPath = root + DELIMITER + "Part3" + DELIMITER;
		
		String inputFile = directoryPath + "imagefile";
		String convertedInputFile = directoryPath + "convertedImageFile.txt";
		convertToClusterableFile(inputFile, convertedInputFile);
		
		String compressedImageFile = directoryPath + "compressedImage.txt";
		int numberClusters = 8;
		
		NarrKmeans clustering = runKmeansClusteringAlgorithm(convertedInputFile, compressedImageFile, numberClusters, 1989);
		
		String decompressedImageFile = directoryPath + "decompressedImage.txt";
		clustering.decompress(compressedImageFile, decompressedImageFile);
		
	}

	private static void convertToClusterableFile(String inputFile, String outputFile) throws IOException {

		Scanner inFile = new Scanner(new File(inputFile));
		PrintWriter outFile = new PrintWriter(new FileWriter(new File(outputFile)));
		
		int numberOfPixelPairs = 0;
		
		while (inFile.hasNext()) {
			inFile.nextInt();
			inFile.nextInt();
			numberOfPixelPairs++;
		}
		
		inFile.close();
		
		inFile = new Scanner(new File(inputFile));
		
		int numberOfAttributes = 2;
		outFile.println(numberOfPixelPairs + " " + numberOfAttributes);
		outFile.println();
		
		int firstPixelValueInPair;
		int secondPixelValueInPair;

		while (inFile.hasNext()) {
			firstPixelValueInPair = inFile.nextInt();
			secondPixelValueInPair = inFile.nextInt();
			
			outFile.println(firstPixelValueInPair + " " + secondPixelValueInPair);
		}
		
		inFile.close();
		outFile.close();
		
	}
	
	private static NarrKmeans runKmeansClusteringAlgorithm(String inputFile, String outputFile,
			int numberClusters, int seed) throws IOException {
		NarrKmeans clustering = new NarrKmeans();

		clustering.load(inputFile);

		clustering.setParameters(numberClusters, seed);

		clustering.cluster();

		clustering.printToFileWithRoundedCentroidsAndEncodedPixels(outputFile, 512);
		return clustering;
	}

}
