package cosc667_project3.narr.kmeans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NarrKmeans {

	private class NarrRecord {
		private double[] attributes;

		private NarrRecord(double[] attributes) {
			this.attributes = attributes;
		}
	}

	private int numberRecords;
	private int numberAttributes;
	private int numberClusters;

	private ArrayList<NarrRecord> records;
	private ArrayList<NarrRecord> centroids;
	private int[] clusters;
	private Random random;

	public NarrKmeans() {
		this.numberRecords = 0;
		this.numberAttributes = 0;
		this.numberClusters = 0;

		this.records = null;
		this.centroids = null;
		this.clusters = null;
		this.random = null;
	}

	public void load(String inputFile) throws IOException {
		Scanner inFile = new Scanner(new File(inputFile));

		this.numberRecords = inFile.nextInt();
		this.numberAttributes = inFile.nextInt();

		this.records = new ArrayList<NarrRecord>();

		for (int i = 0; i < this.numberRecords; i++) {
			double[] attributes = new double[this.numberAttributes];
			for (int j = 0; j < this.numberAttributes; j++) {
				attributes[j] = inFile.nextDouble();

				NarrRecord record = new NarrRecord(attributes);

				this.records.add(record);
			}

			inFile.close();
		}
	}

}
