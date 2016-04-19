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

		Scanner keyboard = new Scanner(System.in);
		String root = "Resources";
		String directoryPath = root + DELIMITER + "Part3" + DELIMITER;

		int dimension = 512;

		System.out.println("Directory: " + directoryPath);
		System.out.print("Image file: ");
		String imageFileName = keyboard.nextLine();

		String inputFile = directoryPath + imageFileName;
		String convertedInputFile = directoryPath + "converted_" + imageFileName + ".txt";

		convertToClusterableFile(inputFile, convertedInputFile);

		String compressedImageFile = directoryPath + "compressed_" + imageFileName + ".txt";

		System.out.print("Number of clusters: ");
		int numberClusters = keyboard.nextInt();
		keyboard.nextLine();
		System.out.println();

		NarrKmeans clustering = runKmeansClusteringAlgorithm(convertedInputFile,
				compressedImageFile, numberClusters, dimension, 1989);

		String decompressedImageFile = directoryPath + "decompressed_" + imageFileName + ".txt";
		clustering.decompress(compressedImageFile, decompressedImageFile);

		double compressionRatio = calculateCompressionRatio(dimension, numberClusters);

		System.out.println("Clusters used: " + numberClusters);
		System.out.println("Image dimensions: " + dimension + " x " + dimension);
		System.out.printf("Compression ratio: %.2f", compressionRatio);

		keyboard.close();

	}

	private static double calculateCompressionRatio(int dimension, int numberClusters) {
		int bitsPerPixel = 8;
		double bitsInOriginalImage = dimension * dimension * bitsPerPixel;
		double bitsInCompressedImage = dimension * (dimension / 2.0) * bitsPerPixel
				+ numberClusters * 2 * bitsPerPixel;
		return bitsInCompressedImage / bitsInOriginalImage;
	}

	private static void convertToClusterableFile(String inputFile, String outputFile)
			throws IOException {

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
			int numberClusters, int dimension, int seed) throws IOException {

		NarrKmeans clustering = new NarrKmeans();

		clustering.load(inputFile);
		clustering.setParameters(numberClusters, seed);
		clustering.cluster();

		clustering.printToFileWithRoundedCentroidsAndEncodedPixels(outputFile, dimension);

		return clustering;
	}

}
