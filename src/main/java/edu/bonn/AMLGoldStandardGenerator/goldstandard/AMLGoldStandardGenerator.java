package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.bonn.AMLGoldStandardGenerator.rdf.ConfigManager;
import edu.bonn.AMLGoldStandardGenerator.schema.XSDValidator;
import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

/**
 * @author omar This Class Automatically Generate Gold Standard Files to check
 *         for different types of heterogeneity in AutomationML(ML) files
 */

public class AMLGoldStandardGenerator {

	private static HashMap<String, String> outputPath;
	private static String inputPath;
	private static HashMap<String, String> outputName;
	private static ArrayList<String> heterogeneityID;
	private static String inputName;

	final static Logger logger = LoggerFactory.getLogger(AMLGoldStandardGenerator.class);

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

		con.loadConfig("configuration.ttl");

		outputName = con.getOutputName();

		heterogeneityID = con.getHeterogeneityID();

		outputPath = con.getOutputPath();

		inputName = con.getInputName();

		inputPath = con.getInputPath();

	}

	void heterogeneityGenerator(String inputFile, ArrayList<String> mod) {

		String outputFile = null;
		String directory = null;

		int i = 1;

		// reads the input from rdf configuration for type of heterogeneity
		for (String input : mod) {

			// Twice loop because for integration we need two files.
			while (i <= 2) {

				if (inputFile == null) {
					logger.error("cannot find inputfile");
					System.exit(0);
				}

				// Initialized input file to read its nodes and elements.
				Document doc = initInput(inputFile);

				// getting all elements. * represents starts from base.
				NodeList baseElmntLst = doc.getElementsByTagName("*");
				for (int k = 0; k < baseElmntLst.getLength(); k++) {
					Element baseElmnt = (Element) baseElmntLst.item(k);

					// calls the type of heterogeneity function
					switch (input) {

					// calls granularity heterogeneity generator
					case "M2":
						doc = new GranularityHeterogeneity(baseElmnt, doc, i).granularityGenerator();

						// formats the output file
						outputFile = outputName.get(input) + "-" + i + ".aml";
						directory = outputPath.get(input);
						break;

					case "M1":

						// formats the output file
						if (outputName.get(input) == null || outputPath.get(input) == null) {
							logger.error("cannot find outputfile");
							System.exit(0);
						}
						outputFile = outputName.get(input) + "-" + i + ".aml";
						directory = outputPath.get(input);

						break;
					}
				}

				// outputs the modified XML data to file.
				try {

					formatXML(doc, outputFile, directory);

					// validates XML Schema
					if (!new XSDValidator(directory + "\\" + outputFile).schemaValidate()) {
						logger.error("Schema did not Validated");
						System.exit(0);
						break;
					}

				} catch (TransformerFactoryConfigurationError | TransformerException | IOException
						| ParsingException e) {
					// TODO Auto-generated catch block
					logger.error("Failed " + e.getLocalizedMessage());
					e.printStackTrace();
				}
				// renames the CAEXFile name attribute wiht new files
				renameFiles(inputName, outputFile, directory);
				i++;
			}
			i = 1;

			// saves input file to output folder.
			outputInputFile(directory);
		}

	}

	/**
	 * This function initialize the input file for data heterogeneity. It Uses
	 * Dom to read the input file
	 * 
	 * @param inputFile
	 * @return
	 */
	Document initInput(String inputFile) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new FileInputStream(new File(inputFile)));
			doc.getDocumentElement().normalize();

		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("Error File not Found " + inputFile);
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * Outputs the modified partition data into files.
	 * 
	 * @param doc
	 * @param file
	 * @param directory
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParsingException
	 * @throws ValidityException
	 * @throws Exception
	 */
	private void formatXML(Document doc, String file, String directory) throws TransformerFactoryConfigurationError,
			TransformerException, IOException, ValidityException, ParsingException {

		// Takes input file
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source = new DOMSource(doc);
		File dir = new File(directory);

		// checks for output directory and creates it
		if (!dir.exists()) {
			dir.mkdir();
			System.out.println("Creating output directory");
		}
		StreamResult result = new StreamResult(new File(directory + "//" + file));

		// outputs result
		transformer.transform(source, result);

		// reads output for formatting
		FileInputStream res = new FileInputStream(new File(directory + "//" + file));
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		String xml = IOUtils.toString(res);

		// formatting the XML
		Serializer serializer = new Serializer(out1);
		serializer.setIndent(4); // or whatever you like
		serializer.write(new Builder().build(xml.toString(), ""));

		FileWriter output = new FileWriter((new File(directory + "//" + file)));
		output.write(out1.toString());
		output.close();

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
	 * outputs the input file to a specified output folder.
	 * 
	 * @param directory
	 */
	private void outputInputFile(String directory) {
		// TODO Auto-generated method stub
		InputStream res;
		try {
			res = new FileInputStream(new File(inputPath));
			String xmlString = IOUtils.toString(res);
			FileWriter output = new FileWriter((directory + "//" + inputName));
			output.write(xmlString);
			output.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		// give input file name and heterogeneity mode
		// 1- Granularity
		// 2- Schema

		new AMLGoldStandardGenerator().heterogeneityGenerator(inputPath, heterogeneityID);

	}
}