package edu.bonn.amlgoldstandardgenerator.goldstandard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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

import edu.bonn.amlgoldstandardgenerator.schemas.XSDValidator;
import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

/**
 * @author omar This Class Automatically Generate Gold Standard Files to check
 *         for different types of heterogeneity in AutomationML(ML) files
 */

public class AMLGoldStandardGenerator {

	final static Logger logger = LoggerFactory.getLogger(AMLGoldStandardGenerator.class);

	/**
	 * This method Takes input file and perform heterogenity.Using the RDF
	 * configuration file we identify which heterogeneity needs to be called.
	 * 
	 * @param inputFile
	 * @param mod
	 * @return
	 */
	void heterogeneityGenerator(String inputFile, int... mod) {

		String outputFile = null;
		String directory = null;

		int i = 1;

		// reads the input from rdf configuration for type of heterogeneity
		for (int input : mod) {

			// Twice loop because for integration we need two files.
			while (i <= 2) {

				// Initialized input file to read its nodes and elements.
				Document doc = initInput(inputFile);

				// getting all elements. * represents starts from base.
				NodeList baseElmntLst = doc.getElementsByTagName("*");
				for (int k = 0; k < baseElmntLst.getLength(); k++) {
					Element baseElmnt = (Element) baseElmntLst.item(k);

					// calls the type of heterogeneity function
					switch (input) {

					// calls granularity heterogeneity generator
					case 1:
						doc = new GranularityHeterogeneity(baseElmnt, doc, i).granularityGenerator();
						// formats the output file
						outputFile = inputFile.replace(".aml", "") + "-granularity-" + i + ".aml";
						directory = "Granularity";

					case 2:
					case 3:

					}
				}
				// outputs the modified XML data to file.
				try {

					formatXML(doc, outputFile, directory);

					// validates XML Schema
					if (!new XSDValidator(directory + "\\" + outputFile).schemaValidate()) {
						logger.error("Schema did not Validated");
						break;
					}

				} catch (TransformerFactoryConfigurationError | TransformerException | IOException
						| ParsingException e) {
					// TODO Auto-generated catch block
					logger.error("Failed " + e.getLocalizedMessage());
					e.printStackTrace();
				}
				// renames the CAEXFile name attribute wiht new files
				renameFiles(inputFile, outputFile, directory);
				i++;
			}
		}
	}

	/**
	 * This function initialise the input file for data heterogeneity. It Uses
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
			doc = dBuilder
					.parse(new FileInputStream(new File(getClass().getClassLoader().getResource(inputFile).getPath())));
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
		StreamResult result = new StreamResult(
				new File(System.getProperty("user.dir") + "//" + directory + "//" + file));

		// outputs result
		transformer.transform(source, result);

		// reads output for formatting
		FileInputStream res = new FileInputStream(
				new File(System.getProperty("user.dir") + "//" + directory + "//" + file));
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		String xml = IOUtils.toString(res);

		// formatting the XML
		Serializer serializer = new Serializer(out1);
		serializer.setIndent(4); // or whatever you like
		serializer.write(new Builder().build(xml.toString(), ""));

		FileWriter output = new FileWriter((new File(System.getProperty("user.dir") + "//" + directory + "//" + file)));
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
			InputStream res = new FileInputStream(
					new File(System.getProperty("user.dir") + "//" + directory + "//" + outputFile));
			xmlString = IOUtils.toString(res);
			output = new FileWriter((System.getProperty("user.dir") + "//" + directory + "//" + outputFile));
			// renames files accordingly to new file
			xmlString = xmlString.replaceAll(inputFile, outputFile);
			output.write(xmlString);
			output.close();
		} catch (IOException | NullPointerException e) {
			logger.error("Error File not Found " + inputFile);
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// give input file name and heterogeneity mode
		// 1- Granularity
		// 2- Schema
		new AMLGoldStandardGenerator().heterogeneityGenerator("Integration.aml", 1, 2, 3, 4, 30, 40, 50);

	}
}