package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import Test.ModelRepair;
import edu.bonn.AMLGoldStandardGenerator.GoldStandard.GoldStandard;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.GenerateAML;
import edu.bonn.AMLGoldStandardGenerator.aml.util.AMLConfigManager;
import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;
import edu.bonn.AMLGoldStandardGenerator.rdf.ConfigManager;
import edu.bonn.AMLGoldStandardGenerator.schema.XSDValidator;

/**
 * @author omar This Class Generates Gold Standard Files to check for different
 *         types of heterogeneity in AutomationML files
 */

public class CPSDocumentGenerator {

	protected ArrayList<String> outputPath;
	protected ArrayList<String> inputPath;
	protected ArrayList<String> heterogeneityID;
	protected static ArrayList<String> inputName;
	protected static ArrayList<File> seedFiles;
	protected static ArrayList<File> seedOne;
	protected static ArrayList<File> seedTwo;
	protected static int sCount;
	protected static int num;

	/**
	 * Constructor to initialize values
	 */
	public CPSDocumentGenerator() {

	}

	/**
	 * Validates and repair the AML file if its not correct.
	 * 
	 * @param doc
	 * @param outputFile
	 * @param directory
	 */
	public void validateSchema(String outputFile, String directory) {
		// outputs the modified XML data to file.
		try {

			// validates XML Schema
			if (!new XSDValidator(directory + "\\" + outputFile).schemaValidate()) {
				System.out.println("Repairing and Rechecking");
				ModelRepair.testRoundTrip(directory + "\\" + outputFile);
				if (!new XSDValidator(directory + "\\" + outputFile).schemaValidate()) {
				}
			}
		} catch (TransformerFactoryConfigurationError | Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates the files
	 * 
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static void generateFiles(String path) throws Exception {
		ReadFiles files = new ReadFiles();
		ArrayList<File> amlFiles = files.readFiles(FileManager.getFilePath(), ".aml");
		File dir = new File(FileManager.getFilePath() + "Generated/");
		if (!dir.exists())
			dir.mkdirs();
		GenerateAML load = new GenerateAML();

		// gets generated data
		for (File file : amlFiles) {
			if (file.getName().equals("seed.aml")) {
				load.generate(file.getAbsolutePath(),
						FileManager.getFilePath() + "Generated/" + file.getName());
				break;
			}
		}

		// adds split data into other documents
		splitData(load, amlFiles);
	}

	/**
	 * Getting all the multi heterogeneites based on config.ttl We take testbesd
	 * 2 for all the multiheterogenties and store all the file names in a single
	 * array and return it.
	 * 
	 * @param num
	 * @return
	 * @throws IOException
	 */
	static ArrayList<File> getRandomizedMultiHeterogenetiesPaths(int nums) throws IOException {

		// Skips if running in bulk
		if (num == 0) {
			// randomly add testbed for multi heterogeneity 1-10 testbed
			sCount = ThreadLocalRandom.current().nextInt(1, 10);
		}
		ReadFiles files = new ReadFiles();
		
		//sCount = 4;

		// Random heterogeneties starts here
		// adds numbers of elements to list so always unique values
		// we knows maximum heterogeneties are 7
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 8; i++) {
			// makes sure its not the current heterogeneity
			list.add(new Integer(i));
		}

		// storing all multi heterogeneties files
		ArrayList<File> allFiles = new ArrayList<File>();

		PrintWriter pr = new PrintWriter(FileManager.getFilePath() + "Generated/multi.txt");
		// taking Testbeds-2 for multi heterogeneties
		try {
			for (int i = 0; i < nums; i++) {
				int randomNum = list.get(i);
				
				// testbed randomization
				System.out.println("Adding M" + randomNum + " testbed-" + sCount);

				if (randomNum == 1) {
					int random = ThreadLocalRandom.current().nextInt(1, 3);
					random = 2;
					if (random == 2) {
						allFiles = files.readFiles(FileManager.getRoot() + "M" + randomNum
								+ "/M1.1/Testbeds-" + sCount + "/", ".aml");
						allFiles = files.readFiles(FileManager.getRoot() + "M" + randomNum
								+ "/M1.2/Testbeds-" + sCount + "/", ".aml");
						pr.println("M1.1");
						pr.println("M1.2");

					} else {
						allFiles = files.readFiles(
								FileManager.getRoot() + "M" + randomNum + "/M1.2/Testbeds-3/",
								".aml");
						pr.println("M1.2");
					}

				} else {
					allFiles = files.readFiles(
							FileManager.getRoot() + "M" + randomNum + "/Testbeds-" + sCount + "/",
							".aml");
					pr.println("M" + randomNum);
				}
			}
			pr.close();

		} catch (Exception e) {
			System.out.println("Maximum heterogenities available 6");
			pr.close();
		}

		return allFiles;

	}

	/**
	 * Gets multi heterogeneity and splits them in array according to there name
	 * 
	 * @throws Exception
	 */
	public static void getMultiHeterogeneity() throws Exception {

		int num = FileManager.getMultiHeterogeneity();

		// get randomized paths
		ArrayList<File> allFiles = getRandomizedMultiHeterogenetiesPaths(num);

		// splits the paths into separate files arrays
		seedFiles = new ArrayList<File>();
		seedOne = new ArrayList<File>();
		seedTwo = new ArrayList<File>();

		for (int i = 0; i < allFiles.size(); i++) {

			if (allFiles.get(i).getName().equals("seed.aml"))
				seedFiles.add(allFiles.get(i));

			if (allFiles.get(i).getName().contains("-0.aml")
					&& !allFiles.get(i).getName().contains("enlarge"))
				seedOne.add(allFiles.get(i));

			if (allFiles.get(i).getName().contains("-1.aml")
					&& !allFiles.get(i).getName().contains("enlarge"))
				seedTwo.add(allFiles.get(i));
		}
	}

	/**
	 * @param amlFiles
	 * @throws JAXBException
	 * @throws IOException
	 * @throws Exception
	 *             Default means nothing is randomized
	 */
	static void addDefault(GenerateAML load, File file, ArrayList<File> seedFile)
			throws JAXBException, IOException {
		load.getMarshaller().marshal(load.getdefault(file.getAbsolutePath(),file),
				new File(FileManager.getFilePath() + "Generated/" + file.getName()));

		if (FileManager.getMultiHeterogeneity() != 0) {
			load.getMarshaller().marshal(load.getMulti(file.getAbsolutePath(), seedFile),
					new File(FileManager.getFilePath() + "Generated/" + file.getName()));
		}

	}

	/**
	 * Both files are randomized and added The data is split in both files.
	 * 
	 * @param load
	 * @param file
	 * @param seedFile
	 * @throws JAXBException
	 */
	static void addRandom(GenerateAML load, File file, ArrayList<File> seedFile)
			throws JAXBException {

		load.getMarshaller().marshal(load.getCaexElementsSplit(file.getAbsolutePath(),file),
				new File(FileManager.getFilePath() + "Generated/" + file.getName()));

		// for multiheterogeneity
		if (FileManager.getMultiHeterogeneity() != 0) {
			load.getMarshaller().marshal(load.getMulti(file.getAbsolutePath(), seedFile),
					new File(FileManager.getFilePath() + "Generated/" + file.getName()));
		}

	}

	/**
	 * Adds randomization to the files with multi - heterogeneties
	 * 
	 * @param load
	 * @param amlFiles
	 * @throws Exception
	 */
	public static void splitData(GenerateAML load, ArrayList<File> amlFiles) throws Exception {
		int count = 0;

		// checks if multiheterogeneity is required
		if (FileManager.getMultiHeterogeneity() != 0) {
			getMultiHeterogeneity();
		}

		for (File file : amlFiles) {
			if (file.getName().equals("seed.aml")) {
				addDefault(load, file, seedFiles);
			} else {
				// both seeds are randomized
				if (FileManager.getRandomize().equals("true")) {
					if (count % 2 == 0) {
						addRandom(load, file, seedOne);
					} else {
						addRandom(load, file, seedTwo);
					}
				}
				// only one seed is randomized
				else if (FileManager.getRandomize().equals("one")) {
					if (count % 2 == 0) {
						addDefault(load, file, seedOne);
					} else {
						addRandom(load, file, seedTwo);
					}
				} else {// none is randomized
					if (count % 2 == 0) {
						addDefault(load, file, seedOne);
					} else {
						addDefault(load, file, seedTwo);
					}
				}
			}
			count++;
		}
	}

	/**
	 * Deletes old rdf files
	 */
	public static void cleanUp() {
		File file1 = new File(FileManager.getFilePath() + "Generated/plfile0.ttl");
		File file2 = new File(FileManager.getFilePath() + "Generated/plfile1.ttl");
		file1.delete();
		file2.delete();
		file1 = new File(FileManager.getFilePath() + "plfile0.ttl");
		file2 = new File(FileManager.getFilePath() + "plfile1.ttl");
		file1.delete();
		file2.delete();
		file1 = new File(FileManager.getFilePath() + "seed.ttl");
		file2 = new File(FileManager.getFilePath() + "Generated/seed.ttl");
		file1.delete();
		file2.delete();

	}

	/**
	 * This function generates GoldStandard based on rdf files. It creates two
	 * goldStandard files one for generated files and one for original files.
	 * 
	 * @throws Exception
	 */
	public static void generateGoldStandard() throws Exception {
		// adding GoldStandard For Original files
		GoldStandard goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath(), ".aml");
		goldStandard.convert2RDF(FileManager.getFilePath());
		goldStandard.readFiles(FileManager.getFilePath(), ".ttl");

		goldStandard.addGoldStandard(FileManager.getFilePath() + "GoldStandard.txt");
		// adding Gold Standard for Generated Files
		goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath() + "Generated/", ".aml");
		goldStandard.convert2RDF(FileManager.getFilePath() + "Generated/");
		goldStandard.readFiles(FileManager.getFilePath() + "Generated/", ".ttl");

		goldStandard.addGoldStandard(FileManager.getFilePath() + "Generated/GoldStandard.txt");
		
		goldStandard.addGoldStandardContaminate(FileManager.getFilePath() + "Generated/GoldStandard.txt");
		
	}

	public static void main(String[] args) throws Exception {
		// BulkGenerate.runBulk();
		// System.exit(0);
		AMLConfigManager.loadConfigurationPoisson();
		generateFiles(FileManager.getFilePath());
		generateGoldStandard();
		System.out.println("Gold Standard Generated for Original file in " + FileManager.getFilePath() + 
				           "Generated/GoldStandard.txt");
		System.out.println("Finished Successfully");
		try {
			// ModelRepair.testRoundTrip("model.aml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}