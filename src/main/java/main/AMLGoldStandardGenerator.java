package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import Test.ModelRepair;
import edu.bonn.AMLGoldStandardGenerator.GoldStandard.GoldStandard;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.GenerateAML;
import edu.bonn.AMLGoldStandardGenerator.aml.util.AMLConfigManager;
import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;
import edu.bonn.AMLGoldStandardGenerator.heterogeneity.DataTypeHeterogeneity;
import edu.bonn.AMLGoldStandardGenerator.heterogeneity.GranularityHeterogeneity;
import edu.bonn.AMLGoldStandardGenerator.heterogeneity.GroupingHeterogeneity;
import edu.bonn.AMLGoldStandardGenerator.heterogeneity.SchematicHeterogeneity;
import edu.bonn.AMLGoldStandardGenerator.rdf.ConfigManager;
import edu.bonn.AMLGoldStandardGenerator.schema.XSDValidator;
import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * @author omar 
 * This Class Generates Gold Standard Files to check
 * for different types of heterogeneity in AutomationML files
 */

public class AMLGoldStandardGenerator {

	private ArrayList<String> outputPath;
	private ArrayList<String> inputPath;
	private ArrayList<String> heterogeneityID;

	private static ArrayList<String> inputName;
	private static ArrayList<File> seedFiles;
	private static ArrayList<File> seedOne;
	private static ArrayList<File> seedTwo;
	private static int sCount;
	private static int num;

	private String fileName;

	/**
	 * Constructor to initialize values
	 */
	public AMLGoldStandardGenerator() {

		ConfigManager con = ConfigManager.getInstance();
		con.loadConfig("conf.ttl");
		heterogeneityID = con.getHeterogeneityID();
		outputPath = con.getOutputPath();
		inputName = con.getInputName();
		inputPath = con.getInputPath();
	}
	
	/**
	 * TODO to write comments
	 * @param inputFiles
	 * @param mod TODO - what is mod?
	 */
	void heterogeneityGenerator(ArrayList<String> inputFiles, ArrayList<String> mod) {

		String outputFile = null;
		String directory = null;

		int i = 0;

		// reads the input from rdf configuration for type of heterogeneity
		for (int m = 0; m < mod.size(); m++) {

			if (inputName.get(m) != null)
				fileName = inputName.get(m).replace(".aml", "");
			else
				fileName = "default" + m;

			String input = mod.get(m).toString();
			String inputFile = inputFiles.get(m).toString();

			// Twice loop because for integration we need two files.
			int j = 0;
			while (i < 2) {

				// Initialized input file to read its nodes and elements.
				Document doc = new XmlParser().initInput(inputFile);

				// calls the type of heterogeneity function
				j = m;
				directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\";
				String fileHetro = null;

				while (j < mod.size() && inputFile.equals(inputFiles.get(j).toString())) {
					if (fileHetro == null) {
						fileHetro = mod.get(j).toString();

					} else {
						fileHetro += mod.get(j).toString();
					}
					switch (mod.get(j).toString()) {
					// calls granularity heterogeneity generator
					case "M2":
						doc = new GranularityHeterogeneity(doc, i).granularityGenerator();

						// formats the output file
						outputFile = fileName + "-" + fileHetro + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory += mod.get(j).toString() + "-";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						break;

					case "M1":

						doc = new DataTypeHeterogeneity(doc, i).dataTypeGenerator();

						// formats the output file
						outputFile = fileName + "-" + fileHetro + "-" + i + ".aml";
						if (outputPath.get(m) != null) {

							directory += mod.get(j).toString() + "-";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}

						break;

					case "M1.2":

						doc = new DataTypeHeterogeneity(doc, i).dataTypeGenerator();
						// formats the output file
						outputFile = fileName + "-" + "DataTypeTransoformation" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {

							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M1-ValueTransformation" + "\\\\" + "M1.1";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}

						break;

					// calls schematic heterogeneity generator
					case "M3":
						doc = new SchematicHeterogeneity(doc, i).schematicGenerator();

						// formats the output file
						outputFile = fileName + "-" + "Schematic" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M3-Schematic";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						doc = new XmlParser().sortXML(doc, outputFile, directory);

						break;

					// calls Grouping/Aggregation heterogeneity generator

					case "M6":
						doc = new GroupingHeterogeneity(doc, i).groupingGenerator();

						// formats the output file
						outputFile = fileName + "-" + "Grouping" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M6-Grouping";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						break;

					}
					j++;
				}
				// outputs the modified XML data to file and validates
				new XmlParser().formatXML(doc, outputFile, directory);

				validateSchema(outputFile, directory);

				// renames the CAEXFile name attribute wiht new files
				renameFiles(inputName.get(m), outputFile, directory);

				String enlargeFile = outputFile.replace(".aml", "-enlarge.aml");

				// calls the generator configuration
				AMLConfigManager.loadConfigurationUniform();
				// calls the generator
				new GenerateAML().generate(inputFile, directory + "//" + enlargeFile);

				// validates the files
				validateSchema(enlargeFile, directory + "//");

				// rename enlarged files
				renameFiles(inputName.get(m), enlargeFile, directory);

				// make it look pretty.
				new XmlParser().formatXML(new XmlParser().initInput(directory + "//" + enlargeFile),
						enlargeFile, directory);

				i++;
			}

			// saves input file to output folder.
			new XmlParser().formatXML(new XmlParser().initInput(inputFile), inputName.get(m),
					directory);
			m = j - 1;
			i = 0;
		}

	}

	/**
	 * @param inputFile
	 * @param outputfile
	 * @param directory
	 * Renames final XML files, in the CAEXFilename tag and changes its names accordingly.
	 */
	void renameFiles(String inputFile, String outputFile, String directory) {
		FileWriter output;
		String xmlString = null;

		try {
			InputStream res = new FileInputStream(new File(directory + "//" + outputFile));
			xmlString = IOUtils.toString(res);
			output = new FileWriter((directory + "//" + outputFile));

			// renames files accordingly to new file
			xmlString = xmlString.replaceAll(inputFile, outputFile);
			output.write(xmlString);
			output.close();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Validates and repair the AML file if its not correct.
	 * @param doc
	 * @param outputFile
	 * @param directory
	 */
	void validateSchema(String outputFile, String directory) {
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
	 * @param args
	 * @return
	 * @throws Exception
	 */
	static void generateFiles(String path) throws Exception {
		ReadFiles files = new ReadFiles();
		ArrayList<File> amlFiles = files.readFiles(FileManager.getFilePath(), ".aml", ".opcua",
				".xml");
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
	 * Getting all the multi heterogeneites based on config.ttl
	 * We take testbesd 2 for all the multiheterogenties and store all the file names in
	 * a single array and return it.
	 * @param num
	 * @return
	 * @throws IOException
	 */
	static ArrayList<File> getRandomizedMultiHeterogenetiesPaths(int nums) throws IOException {

		ReadFiles files = new ReadFiles();

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

		PrintWriter pr = new PrintWriter(
				FileManager.getFilePath() + "Generated/PSL/test/Precision/multi.txt");
		// taking Testbeds-2 for multi heterogeneties 
		try {
			for (int i = 0; i < num; i++) {
				int randomNum = list.get(i);

				// testbed randomization
				int seedRand = ThreadLocalRandom.current().nextInt(1, 10 + 1);
				System.out.println("M" + randomNum);

				if (randomNum == 1) {
					int random = ThreadLocalRandom.current().nextInt(1, 3);
					random = 2;
					if (random == 2) {
						allFiles = files.readFiles(FileManager.getRoot() + "M" + randomNum
								+ "/M1.1/Testbeds-" + sCount + "/", ".aml", ".opcua", ".xml");
						allFiles = files.readFiles(FileManager.getRoot() + "M" + randomNum
								+ "/M1.2/Testbeds-" + sCount + "/", ".aml", ".opcua", ".xml");
						pr.println("M1.1");
						pr.println("M1.2");

					} else {
						allFiles = files.readFiles(
								FileManager.getRoot() + "M" + randomNum + "/M1.2/Testbeds-3/",
								".aml", ".opcua", ".xml");
						pr.println("M1.2");
					}

				} else {
					allFiles = files.readFiles(
							FileManager.getRoot() + "M" + randomNum + "/Testbeds-" + sCount + "/",
							".aml", ".opcua", ".xml");
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
	 * @throws Exception
	 */
	public static void getMultiHeterogeneity() throws Exception {

		int num = FileManager.getMultiHeterogeneity();

		// get randomized paths
		ArrayList<File> allFiles = getRandomizedMultiHeterogenetiesPaths(num);

		// splits the paths into seperate files arrays
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
	 * Default means nothing is randomized
	 */
	static void addDefault(GenerateAML load, File file, ArrayList<File> seedFile)
			throws JAXBException, IOException {
		load.getMarshaller().marshal(load.getdefault(file.getAbsolutePath()),
				new File(FileManager.getFilePath() + "Generated/" + file.getName()));

		if (FileManager.getMultiHeterogeneity() != 0) {
			load.getMarshaller().marshal(load.getMulti(file.getAbsolutePath(), seedFile),
					new File(FileManager.getFilePath() + "Generated/" + file.getName()));
		}

	}

	/**
	 * Both files are randomized and added The data is splitted in both files.
	 * @param load
	 * @param file
	 * @param seedFile
	 * @throws JAXBException
	 */
	static void addRandom(GenerateAML load, File file, ArrayList<File> seedFile)
			throws JAXBException {

		load.getMarshaller().marshal(load.getCaexElementsSplit(file.getAbsolutePath()),
				new File(FileManager.getFilePath() + "Generated/" + file.getName()));

		// for multi heterogeneties
		if (FileManager.getMultiHeterogeneity() != 0) {
			load.getMarshaller().marshal(load.getMulti(file.getAbsolutePath(), seedFile),
					new File(FileManager.getFilePath() + "Generated/" + file.getName()));
		}

	}

	/**
	 * Adds randomization to the files with multi - heterogeneties
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
				}else {// none is randomized
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
	 * TODO write comment
	 * @throws Exception
	 */
	public static void generateGoldStandard() throws Exception {
		// add GoldStandard For Orignal files
		GoldStandard goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath(), ".aml", ".opcua", ".xml");
		goldStandard.convert2RDF(FileManager.getFilePath());
		goldStandard.readFiles(FileManager.getFilePath(), ".ttl", ".rdf", ".owl");

		// creates folders if not there
		FileManager.createDataPath(FileManager.getFilePath());

		goldStandard.addGoldStandard(FileManager.getFilePath() + "Alligator/GoldStandard.txt");
		goldStandard.addGoldStandard(FileManager.getFilePath() + "Silk/GoldStandard.txt");
		goldStandard.addGoldStandard(FileManager.getFilePath() + "Edoal/GoldStandard.txt");
		goldStandard.addGoldStandard(FileManager.getFilePath() + "PSL/test/GoldStandard.txt");
		// adding Gold Standard for Generated Files
		goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath() + "Generated/", ".aml", ".opcua", ".xml");
		goldStandard.convert2RDF(FileManager.getFilePath() + "Generated/");
		goldStandard.readFiles(FileManager.getFilePath() + "Generated/", ".ttl", ".rdf", ".owl");

		// creates folders if not there
		FileManager.createDataPath(FileManager.getFilePath() + "Generated/");

		goldStandard.addGoldStandard(
				FileManager.getFilePath() + "Generated/Alligator/GoldStandard.txt");
		goldStandard
				.addGoldStandard(FileManager.getFilePath() + "Generated/PSL/test/GoldStandard.txt");
		goldStandard.addGoldStandard(
				FileManager.getFilePath() + "Generated/Edoal/GoldStandard.txt");
		goldStandard
				.addGoldStandard(FileManager.getFilePath() + "Generated/Silk/GoldStandard.txt");


	}

	/**
	 * Running generator in bulk
	 * 
	 * @throws Exception
	 */
	static void runBulk() throws Exception {

		int k = 2;
		while (k <= 7) {
			FileManager.hetCount = k-1;
			num = FileManager.hetCount;
			sCount = 1;
			while (sCount <= 10) {
				if (k == 1) {
					FileManager.filePath = FileManager.getRoot() + "M" + k + "/" + "M1.1/Testbeds-"
							+ sCount + "/";
					// creates folders if not there
					FileManager.createDataPath(FileManager.getFilePath() + "Generated/");

					AMLConfigManager.loadConfigurationPoisson();
					generateFiles(FileManager.getFilePath());
					generateGoldStandard();
				} else {

					FileManager.filePath = FileManager.getRoot() + "M" + k + "/Testbeds-" + sCount
							+ "/";
					// creates folders if not there
					FileManager.createDataPath(FileManager.getFilePath() + "Generated/");

					AMLConfigManager.loadConfigurationPoisson();
					generateFiles(FileManager.getFilePath());
					generateGoldStandard();
					cleanUp();

				}

				System.out.println("Gold Standard Generated for Orignal and Generated Files");
				System.out.println("Finished SuccessFully");
				sCount++;
			}
			k++;
		}
	}

	/**
	 * Give input file name and heterogeneity mode
	 * 1- Granularity, 2- Schema 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		runBulk();
		System.exit(0);
		FileManager.createDataPath(FileManager.getFilePath() + "Generated/");
		AMLConfigManager.loadConfigurationPoisson();
		generateFiles(FileManager.getFilePath());
		generateGoldStandard();
		System.out.println("Gold Standard Generated for Orignal and Generated Files");
		System.out.println("Finished SuccessFully");
		try {
			// ModelRepair.testRoundTrip("model.aml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}