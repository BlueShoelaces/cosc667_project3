package cosc667_project3.narr.graphcluster;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class NarrGraph {

	private class NarrRecord {
		private double[] attributes;

		private NarrRecord(double[] attributes) {
			this.attributes = attributes;
		}
	}

	private int numberRecords;
	private int numberAttributes;
	private double delta;
	private int numberClusters;

	private ArrayList<NarrRecord> records;
	private int[] clusters;
	private int[][] matrix;

	public NarrGraph() {
		this.numberRecords = 0;
		this.numberAttributes = 0;
		this.delta = 0.0;
		this.numberClusters = 0;

		this.records = null;
		this.matrix = null;
		this.clusters = null;
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
			}

			NarrRecord record = new NarrRecord(attributes);

			this.records.add(record);
		}

		inFile.close();
	}

	public void setParameters(double delta) {
		this.delta = delta;
	}

	public void cluster() {
		this.createMatrix();

		this.initializeClusters();

		int index = 0;

		int clusterName = 0;

		while (index < this.numberRecords) {
			if (this.clusters[index] == -1) {
				assignCluster(index, clusterName);
				clusterName++;
			}

			index++;
		}

		this.numberClusters = this.countClusters();
	}

	private void initializeClusters() {
		this.clusters = new int[this.numberRecords];

		for (int i = 0; i < this.numberRecords; i++) {
			this.clusters[i] = -1;
		}
	}

	private void assignCluster(int clusterIndex, int clusterName) {
		this.clusters[clusterIndex] = clusterName;

		LinkedList<Integer> list = new LinkedList<Integer>();

		list.addLast(clusterIndex);

		while (!list.isEmpty()) {
			int i = list.removeFirst();

			for (int j = 0; j < this.numberRecords; j++) {
				if (this.matrix[i][j] == 1 && this.clusters[j] == -1) {
					this.clusters[j] = clusterName;
					list.addLast(j);
				}
			}
		}
	}

	private int countClusters() {

		ArrayList<Integer> uniqueClusters = new ArrayList<Integer>();

		for (int recordNumber = 0; recordNumber < this.numberRecords; recordNumber++) {
			if (!uniqueClusters.contains(this.clusters[recordNumber])) {
				uniqueClusters.add(this.clusters[recordNumber]);
			}
		}

		return uniqueClusters.size();
	}

	private void createMatrix() {
		this.matrix = new int[this.numberRecords][this.numberRecords];

		for (int i = 0; i < this.numberRecords; i++) {
			for (int j = 0; j < this.numberRecords; j++) {
				this.matrix[i][j] = this.areNeighbors(this.records.get(i), this.records.get(j));
			}
		}
	}

	private int areNeighbors(NarrRecord firstRecord, NarrRecord secondRecord) {
		double distance = 0;

		for (int i = 0; i < firstRecord.attributes.length; i++) {
			distance += Math.pow((firstRecord.attributes[i] - secondRecord.attributes[i]), 2);
		}

		distance = Math.sqrt(distance);

		if (distance <= this.delta) {
			return 1;
		} else {
			return 0;
		}
	}

	public void printToFileWithRecordsGroupedByCluster(String outputFile) throws IOException {

		PrintWriter outFile = new PrintWriter(new FileWriter(outputFile));

		for (int clusterNumber = 0; clusterNumber < this.numberClusters; clusterNumber++) {
			outFile.println("Cluster " + (clusterNumber + 1));
			for (int recordNumber = 0; recordNumber < this.numberRecords; recordNumber++) {
				if (this.clusters[recordNumber] == clusterNumber) {
					printRecordAttributes(this.records.get(recordNumber), outFile);
				}
			}
			outFile.println();
		}

		outFile.close();
	}

	private void printRecordAttributes(NarrRecord record, PrintStream outputStream) {
		for (int attributeNumber = 0; attributeNumber < this.numberAttributes; attributeNumber++) {
			outputStream.printf("%.2f ", record.attributes[attributeNumber]);
		}
		outputStream.println();
	}

	private void printRecordAttributes(NarrRecord record, PrintWriter outputWriter) {
		for (int attributeNumber = 0; attributeNumber < this.numberAttributes; attributeNumber++) {
			outputWriter.printf("%.2f ", record.attributes[attributeNumber]);
		}
		outputWriter.println();
	}

	public int getNumberClusters() {
		return this.numberClusters;
	}
}
