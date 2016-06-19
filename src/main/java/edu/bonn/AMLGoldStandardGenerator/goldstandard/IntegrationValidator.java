package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import edu.bonn.AMLGoldStandardGenerator.rdf.ConfigManager;

/**
 * This class measures precision and recall of the integration results with the
 * gold standard.
 * 
 * @author omar
 *
 */
public class IntegrationValidator {

	private static ArrayList<String> goldStandards;
	private static ArrayList<String> integratedFiles;
	private RelevanceCollection rc;

	public IntegrationValidator() {

		ConfigManager con = ConfigManager.getInstance();
		goldStandards = con.getGoldStandard();
		integratedFiles = con.getIntegratedFile();

	}

	/**
	 * checks validation of the integration files and returns precision and
	 * recall.
	 */
	void validate() {
		for (int i = 0; i < goldStandards.size(); i++) {

			File goldStandard = new File(goldStandards.get(i));

			if (!goldStandard.exists()) {

				System.out.println("File not Found " + goldStandard.getAbsolutePath());
				System.exit(0);
			}

			File[] listOfFiles = getFiles(integratedFiles.get(i));

			if (listOfFiles == null) {

				System.out.println("Folder not found " + integratedFiles.get(i));
				System.exit(0);
			}

			for (int j = 0; j < listOfFiles.length; j++) {
				try {
					if (listOfFiles[j] == null) {

						System.out.println("File not Found " + listOfFiles[j].getPath());
						System.exit(0);
					}

					if (FileUtils.contentEquals(goldStandard, listOfFiles[j])) {

						System.out.println(listOfFiles[j].getName() + " is identical to " + goldStandard.getName());
					}

					rc = new RelevanceCollection();
					rc.processRelevanceCollection(goldStandard.getAbsolutePath(), listOfFiles[j].getAbsolutePath());
					getPrecisionRecall(listOfFiles[j]);

				} catch (IOException e) {
					System.out.println("file not found" + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * Displays precision and recall
	 * 
	 * @param file
	 */
	void getPrecisionRecall(File file) {
		System.out.println("Precision and Recal for File :" + file.getName());
		System.out.println("Recall is:=" + getRecall());
		System.out.println("Precision is:=" + getPrecision());

	}

	/**
	 * measure precision
	 * 
	 * precision = number of relevant texts retrieved /total number of texts
	 * retrieved
	 * 
	 * @return
	 */

	double getPrecision() {
		// true positive denotes relevant text found
		double tp = rc.getRelevantTextFound();

		// false positives, denotes texts that were marked relevant when they
		// were not.
		double fp = rc.getTotalTextFound() - tp;

		double precision = tp / (tp + fp);

		return precision;
	}

	/**
	 * measures recall recall = number of relevant texts retrieved /total number
	 * of relevant texts
	 * 
	 * @return
	 */

	double getRecall() {

		// true positive
		double tp = rc.getRelevantTextFound();

		// denotes texts that were not marked relevant when they should have
		// been.
		double fn = rc.getRelevantText() - tp;

		// calculates recall
		double recall = tp / (tp + fn);

		return recall;
	}

	/**
	 * calculates harmonic mean
	 * 
	 * @return
	 */
	double fScore() {

		double fScore = 2 * getPrecision() * getRecall() / (getPrecision() + getRecall());
		return fScore;
	}

	/**
	 * gets files
	 * 
	 * @param path
	 * @return
	 */
	File[] getFiles(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;

	}

}
