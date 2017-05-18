
package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import java.io.File;
import java.util.ArrayList;

/**
 * Reads the RDF files and convert them to Datalog or to PSL facts
 * 
 * @author Irlan 28.06.2016
 */
public class ReadFiles {

	private ArrayList<File> files;

	int number = 0;
	public ReadFiles(){
		files = new ArrayList<File>();
	}


	/**
	 * Read the RDF files of a given path
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public ArrayList<File> readFiles(String path, String type, String type2, String type3)
			throws Exception {
		
		File originalFilesFolder = new File(path);
		if (originalFilesFolder.isDirectory()) {
			for (File amlFile : originalFilesFolder.listFiles()) {
				if (amlFile.isFile()
						&& (amlFile.getName().endsWith(type) || amlFile.getName().endsWith(type2)
								|| amlFile.getName().endsWith(type3))) {
					if (amlFile.getName().endsWith(".aml")) {
						String name = amlFile.getName().replace(".aml", "");
						if (name.endsWith("0") || name.endsWith("1") || name.equals("seed")) {
							files.add(amlFile);
						}
					}

					else if (amlFile.getName().endsWith(".opcua")) {
						String name = amlFile.getName().replace(".opcua", "");
						if (name.endsWith("0") || name.endsWith("1")) {
							files.add(amlFile);
						}
					}

					else if (amlFile.getName().endsWith(".xml")) {
						files.add(amlFile);
					}

					else {
						files.add(amlFile);
					}
				}
			}
		} else {
			System.out.println("Error in the directory that you provided");
			System.exit(0);
		}

		return files;
	}

}