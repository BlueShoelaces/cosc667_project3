package cosc667_project3.narr.kmeans;

import java.io.File;
import java.io.IOException;

public class NarrKmeansDriver {

	public static void main(String[] args) throws IOException {
		
		// Set delimiter to / or \ according to operating system
		final String DELIMITER = File.separator;
				
		String root = DELIMITER + "Resources";
		
		String inputFile = root + DELIMITER + "Part1" + DELIMITER + "Input" + DELIMITER + "file1";
		String outputFile = root + DELIMITER + "Part1" + DELIMITER + "Output" + DELIMITER + "output1.txt";

		NarrKmeans clustering = new NarrKmeans();

		clustering.load(inputFile);

		clustering.setParameters(2, 4539);

		clustering.cluster();

		clustering.display(outputFile);
	}

}
