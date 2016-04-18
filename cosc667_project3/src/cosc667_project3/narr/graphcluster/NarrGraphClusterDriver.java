package cosc667_project3.narr.graphcluster;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class NarrGraphClusterDriver {

	// TODO: This was just copied from KmeansDriver! FIX IT

	public static void main(String[] args) throws IOException {

		// Set delimiter to / or \ according to operating system
		final String DELIMITER = File.separator;

		String root = "Resources";

		String inputFile;
		String outputFile;

		Scanner keyboard = new Scanner(System.in);

		do {
			System.out.println();
			System.out.println("Pick one:");
			System.out.println(" 1 Graph #1");
			System.out.println(" 2 Graph #2");
			System.out.println(" 3 Graph #4");
			System.out.println(" 4 EXIT");

			int menuChoice = keyboard.nextInt();
			keyboard.nextLine();

			String inputDirectoryPath = root + DELIMITER + "Part2" + DELIMITER + "Input"
					+ DELIMITER;
			String outputDirectoryPath = root + DELIMITER + "Part2" + DELIMITER + "Output"
					+ DELIMITER;

			NarrGraph clustering;
			double delta = 3.0;
			int numberClusters;

			switch (menuChoice) {

			case 1:
				inputFile = inputDirectoryPath + "testFile.txt";
				outputFile = outputDirectoryPath + "outputTestFile.txt";

				clustering = runGraphClusteringAlgorithm(inputFile, outputFile, delta);
				numberClusters = clustering.getNumberClusters();

				System.out.println();
				System.out.println(numberClusters + " clusters");

				break;

			case 2:
				inputFile = inputDirectoryPath + "file3";
				outputFile = outputDirectoryPath + "output1.txt";

				clustering = runGraphClusteringAlgorithm(inputFile, outputFile, delta);
				numberClusters = clustering.getNumberClusters();

				System.out.println();
				System.out.println(numberClusters + " clusters");

				break;

			case 3:
				inputFile = inputDirectoryPath + "file4";
				outputFile = outputDirectoryPath + "output2.txt";

				runGraphClusteringAlgorithm(inputFile, outputFile, delta);

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

	private static NarrGraph runGraphClusteringAlgorithm(String inputFile, String outputFile,
			double delta) throws IOException {
		NarrGraph clustering = new NarrGraph();

		clustering.load(inputFile);

		clustering.setParameters(delta);

		clustering.cluster();

		clustering.printToFileWithRecordsGroupedByCluster(outputFile);

		return clustering;
	}
}
