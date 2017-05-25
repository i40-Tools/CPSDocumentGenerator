package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerFactoryConfigurationError;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author omar This Class Automatically Generate Gold Standard Files to check
 *         for different types of heterogeneity in AutomationML(ML) files
 */

public class AMLGoldStandardGenerator {

	private ArrayList<String> outputPath;
	private ArrayList<String> inputPath;
	private ArrayList<String> heterogeneityID;

	private static ArrayList<String> inputName;

	final static Logger logger = LoggerFactory.getLogger(AMLGoldStandardGenerator.class);
	private String fileName;

	/**
	 * This method Takes input file and perform heterogenity.Using the RDF
	 * configuration file we identify which heterogeneity needs to be called.
	 * 
	 * @param inputFile
	 * @param mod
	 * @return
	 */

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

				if (inputFile == null) {
					logger.error("cannot find inputfile");
					logger.error("Please check configuration file");
					System.exit(0);
				}

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
	 * 
	 * @param inputFile
	 * @param outputfile
	 * @param directory
	 *            This method renames final XML files, in the CAEXFilename tag
	 *            and changes its names accordingly.
	 * 
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
			logger.error("Error File not Found " + inputFile);
			e.printStackTrace();
		}
	}

	/**
	 * Validates and repair the Aml file if its not correct.
	 * 
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
					logger.error("Schema did not Validated for " + outputFile);
				}
			}

		} catch (TransformerFactoryConfigurationError | Exception e) {
			// TODO Auto-generated catch block
			logger.error("Failed " + e.getLocalizedMessage());
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
	static void generateFiles() throws Exception {
		ReadFiles files = new ReadFiles();
		ArrayList<File> amlFiles = files.readFiles(FileManager.getFilePath(), ".aml", ".opcua",
				".xml");
		File dir = new File(FileManager.getFilePath() + "Generated/");
		if (!dir.exists())
			dir.mkdirs();
		GenerateAML load = new GenerateAML();

		// gets generated data
		for (File file : amlFiles) {
			if(file.getName().equals("seed.aml")){
				load.generate(file.getAbsolutePath(),
						FileManager.getFilePath() + "Generated/" + file.getName());
				break;
			}
		}

		// adds split data into other documents
		splitData(load, amlFiles);
	}

	/**
	 * 
	 * @param amlFiles
	 * @throws JAXBException 
	 */
	
		
	public static void splitData(GenerateAML load, ArrayList<File> amlFiles) throws JAXBException{
		int count = 0;
		for (File file : amlFiles) {
			if (file.getName().equals("seed.aml")) {
				load.getMarshaller().marshal(load.getdefault(file.getAbsolutePath()),
						new File(FileManager.getFilePath() + "Generated/" + file.getName()));
			} else {
				if (FileManager.getRandomize().equals("true")) {
					// both seeds are randomized

					if (count % 2 == 0) {

						load.getMarshaller().marshal(
								load.getCaexElementsSplit(file.getAbsolutePath()), new File(
										FileManager.getFilePath() + "Generated/" + file.getName()));
					} else {

						load.getMarshaller().marshal(
								load.getCaexElementsSplit(file.getAbsolutePath()), new File(
										FileManager.getFilePath() + "Generated/" + file.getName()));
					}
				}

				// only one seed is randomized
				else {
					if (count % 2 == 0) {

						load.getMarshaller().marshal(
								load.getCaexElementsSplit(file.getAbsolutePath()), new File(
										FileManager.getFilePath() + "Generated/" + file.getName()));
					} else {

						load.getMarshaller().marshal(load.getdefault(file.getAbsolutePath()),
								new File(
										FileManager.getFilePath() + "Generated/" + file.getName()));
					}

				}

			}

			count++;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public static void generateGoldStandard() throws Exception {
		// add GoldStandard For Orignal files
		GoldStandard goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath(), ".aml", ".opcua", ".xml");
		goldStandard.convert2RDF(FileManager.getFilePath());
		goldStandard.readFiles(FileManager.getFilePath(), ".ttl", ".rdf", ".owl");
		FileManager.createDataPath(FileManager.getFilePath());// creates folders if not there
		goldStandard.addGoldStandard(FileManager.getFilePath());

		// adding Gold Standard for Generated Files
		goldStandard = new GoldStandard();
		goldStandard.readFiles(FileManager.getFilePath()+"Generated/", ".aml", ".opcua", ".xml");
		goldStandard.convert2RDF(FileManager.getFilePath()+"Generated/");
		goldStandard.readFiles(FileManager.getFilePath()+"Generated/", ".ttl", ".rdf", ".owl");
		FileManager.createDataPath(FileManager.getFilePath()+"Generated/");// creates folders if not there
		goldStandard.addGoldStandard(FileManager.getFilePath()+"Generated/");
	}

	public static void main(String[] args) throws Exception {
		// give input file name and heterogeneity mode
		// 1- Granularity
		// 2- Schema

		// calls the generator configuration
		AMLConfigManager.loadConfigurationUniform();

		generateFiles();
		generateGoldStandard();
		System.out.println("Gold Standard Generated for Orignal and Generated Files");
		System.out.println("Finished SuccessFully");

		// AMLGoldStandardGenerator goldStandard = new
		// AMLGoldStandardGenerator();
		// System.out.println("Generating Files Please wait....");
		// goldStandard.heterogeneityGenerator(goldStandard.inputPath,
		// goldStandard.heterogeneityID);
		// new IntegrationValidator().validate();
		try {
			// ModelRepair.testRoundTrip("model.aml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}