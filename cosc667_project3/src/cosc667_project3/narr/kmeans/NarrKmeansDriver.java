package cosc667_project3.narr.kmeans;

import java.io.IOException;

public class NarrKmeansDriver {

	public static void main(String[] args) throws IOException {
		String inputFile = "inputFile";
		String outputFile = "outputFile";

		NarrKmeans clustering = new NarrKmeans();

		clustering.load(inputFile);

		clustering.setParameters(2, 4539);

		clustering.cluster();

		clustering.display(outputFile);
	}

}
