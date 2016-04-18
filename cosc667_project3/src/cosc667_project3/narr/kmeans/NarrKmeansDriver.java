package cosc667_project3.narr.kmeans;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NarrKmeansDriver {

	public static void main(String[] args) throws IOException {

		// Set delimiter to / or \ according to operating system
		final String DELIMITER = File.separator;

		String root = "Resources";

		String inputFile;
		String outputFile;

		Scanner keyboard = new Scanner(System.in);
		int numberClusters;
		int seed = 1776;

		do {
			System.out.println();
			System.out.println("Pick one:");
			System.out.println(" 1 K-means #1");
			System.out.println(" 2 K-means #2");
			System.out.println(" 3 K-means #4");
			System.out.println(" 4 EXIT");

			int menuChoice = keyboard.nextInt();
			keyboard.nextLine();

			String inputDirectoryPath = root + DELIMITER + "Part1" + DELIMITER + "Input"
					+ DELIMITER;
			String outputDirectoryPath = root + DELIMITER + "Part1" + DELIMITER + "Output"
					+ DELIMITER;
			switch (menuChoice) {

			case 1:
				inputFile = inputDirectoryPath + "testFile.txt";
				outputFile = outputDirectoryPath + "outputTestFile.txt";

				numberClusters = 2;
				seed = 4539;

				runKmeansClusteringAlgorithm(inputFile, outputFile, numberClusters, seed);

				break;

			case 2:
				inputFile = inputDirectoryPath + "file1";
				outputFile = outputDirectoryPath + "output1.txt";

				numberClusters = 3;

				runKmeansClusteringAlgorithm(inputFile, outputFile, numberClusters, seed);

				seed++;
				break;

			case 3:
				inputFile = inputDirectoryPath + "file2";
				outputFile = outputDirectoryPath + "output2.txt";

				numberClusters = 3;

				runKmeansClusteringAlgorithm(inputFile, outputFile, numberClusters, seed);

				break;

			case 4:
				keyboard.close();
				System.exit(0);
				break;

			default:
				System.out.println("Invalid option.");
				System.out.println();
				break;
			}

		} while (true);
	}

	private static NarrKmeans runKmeansClusteringAlgorithm(String inputFile, String outputFile,
			int numberClusters, int seed) throws IOException {
		NarrKmeans clustering = new NarrKmeans();

		clustering.load(inputFile);

		clustering.setParameters(numberClusters, seed);

		clustering.cluster();

		clustering.printToFileWithRecordsGroupedByCluster(outputFile);
		return clustering;
	}

}
