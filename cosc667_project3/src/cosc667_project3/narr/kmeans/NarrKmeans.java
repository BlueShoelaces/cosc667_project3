package cosc667_project3.narr.kmeans;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

		}
		inFile.close();
	}

	public void setParameters(int numberClusters, int seed) {
		this.numberClusters = numberClusters;

		this.random = new Random(seed);
	}

	public void cluster() {
		this.initializeClusters();

		this.initializeCentroids();

		boolean stopCondition = false;

		while (!stopCondition) {
			int clusterChanges = this.assignClusters();

			this.updateCentroids();

			stopCondition = clusterChanges == 0;
		}
	}

	private void updateCentroids() {
		ArrayList<NarrRecord> clusterSum = new ArrayList<NarrRecord>();

		for (int i = 0; i < this.numberClusters; i++) {
			double[] attributes = new double[this.numberAttributes];
			for (int j = 0; j < this.numberAttributes; j++) {
				attributes[j] = 0;
			}

			clusterSum.add(new NarrRecord(attributes));
		}

		int[] clusterSizes = new int[this.numberClusters];

		for (int i = 0; i < this.numberClusters; i++) {
			clusterSizes[i] = 0;
		}

		for (int i = 0; i < this.numberRecords; i++) {
			int cluster = this.clusters[i];

			NarrRecord sum = this.sum(clusterSum.get(cluster), this.records.get(i));
			clusterSum.set(cluster, sum);

			clusterSizes[cluster] += 1;
		}

		for (int i = 0; i < this.numberClusters; i++) {
			NarrRecord average = this.scale(clusterSum.get(i), 1.0 / clusterSizes[i]);

			this.centroids.set(i, average);
		}
	}

	private NarrRecord scale(NarrRecord record, double scalar) {
		double[] scaledAttributes = new double[record.attributes.length];

		for (int i = 0; i < record.attributes.length; i++) {
			scaledAttributes[i] = scalar * record.attributes[i];
		}

		return new NarrRecord(scaledAttributes);
	}

	private NarrRecord sum(NarrRecord firstRecord, NarrRecord secondRecord) {
		double[] sumsOfAttributes = new double[firstRecord.attributes.length];

		for (int i = 0; i < firstRecord.attributes.length; i++) {
			sumsOfAttributes[i] = firstRecord.attributes[i] + secondRecord.attributes[i];
		}

		return new NarrRecord(sumsOfAttributes);
	}

	private int assignClusters() {
		int clusterChanges = 0;

		for (int i = 0; i < this.numberRecords; i++) {
			NarrRecord record = this.records.get(i);

			double minDistance = this.distance(record, this.centroids.get(0));
			int minIndex = 0;

			for (int j = 0; j < this.numberClusters; j++) {
				double distance = this.distance(record, this.centroids.get(j));

				if (distance < minDistance) {
					minDistance = distance;
					minIndex = j;
				}
			}

			if (this.clusters[i] != minIndex) {
				this.clusters[i] = minIndex;
				clusterChanges++;
			}
		}

		return clusterChanges;
	}

	private double distance(NarrRecord firstRecord, NarrRecord secondRecord) {
		double sum = 0;

		for (int i = 0; i < firstRecord.attributes.length; i++) {
			sum += Math.pow((firstRecord.attributes[i] - secondRecord.attributes[i]), 2);
		}

		return sum;
	}

	private void initializeCentroids() {
		this.centroids = new ArrayList<NarrRecord>();

		for (int i = 0; i < this.numberClusters; i++) {
			int index = this.random.nextInt(this.numberRecords);

			this.centroids.add(this.records.get(index));
		}
	}

	private void initializeClusters() {
		this.clusters = new int[this.numberRecords];

		for (int i = 0; i < this.numberRecords; i++) {
			this.clusters[i] = -1;
		}
	}

	public void display(String outputFile) throws IOException {
		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int i = 0; i < this.numberRecords; i++) {
			for (int j = 0; j < this.numberAttributes; j++) {
				outFile.print(this.records.get(i).attributes[j] + " ");
			}

			outFile.println(this.clusters[i] + 1);
		}

		outFile.close();
	}

}
