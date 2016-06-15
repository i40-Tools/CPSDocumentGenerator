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
				} catch (IOException e) {
					System.out.println("file not found" + e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * measure precision
	 * 
	 * @return
	 */
	float getPrecision() {
		return 0;
	}

	/**
	 * measures recall
	 * 
	 * @return
	 */
	float getRecall() {
		return 0;
	}

	File[] getFiles(String path) {

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;

	}

}
