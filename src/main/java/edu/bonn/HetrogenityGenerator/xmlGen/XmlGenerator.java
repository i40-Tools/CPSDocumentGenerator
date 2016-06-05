package edu.bonn.HetrogenityGenerator.xmlGen;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

/**
 * @author omar
 * @todo purpose of the class
 */

public class XmlGenerator {

	final static Logger logger = LoggerFactory.getLogger(XmlGenerator.class);
	private FileWriter output;

	/**
	 * 
	 * @param inputFile
	 * @param file1
	 * @param file2
	 *            This method renames final XML files, in the CAEXFilename tag
	 *            and changes its names accordingly.
	 * 
	 * @throws FileNotFoundException
	 */
	void renameFiles(String inputFile, String file) {
		FileWriter output;
		String xmlString = null;

		try {
			InputStream res = new FileInputStream(new File(System.getProperty("user.dir") + "//output//" + file));
			xmlString = IOUtils.toString(res);
			output = new FileWriter((System.getProperty("user.dir") + "//output//" + file));
			// renames files accordingly to new file
			xmlString = xmlString.replaceAll(inputFile, file);
			output.write(xmlString);
			output.close();
		} catch (IOException | NullPointerException e) {
			logger.error("Error File not Found " + inputFile);
			e.printStackTrace();
		}
	}

	/**
	 * This method Takes input file and perform granularity heterogenity
	 * generation.First we read the XML input file, identify all nodes and gets
	 * count of the number of children. if the children are greater than 3 then
	 * we can perform partition over it.
	 * 
	 * @param inputFile
	 * @param file
	 * @param mod
	 *            for modolus so that both files dont partition same data
	 * @return
	 * @throws Exception
	 */
	void granularityGenerator(String inputFile, String file, int mod) {
		// reading orignal input file to generate hetrogenity
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setValidating(false);
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder
					.parse(new FileInputStream(new File(getClass().getClassLoader().getResource(inputFile).getPath())));
		} catch (SAXException | IOException | ParserConfigurationException e) {
			logger.error("Error File not Found " + inputFile);
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();

		// getting all elements. * represents starts from base.
		NodeList baseElmntLst = doc.getElementsByTagName("*");
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			Element baseElmnt = (Element) baseElmntLst.item(k);

			// calls partition based on element.if it has no of child greater to
			// some value
			xmlPartition(baseElmnt, doc, mod);

		}
		// outputs the modified XML data to file.
		try {
			formatXML(doc, file);
		} catch (TransformerFactoryConfigurationError | TransformerException | IOException | ParsingException e) {
			// TODO Auto-generated catch block
			logger.error("Failed " + e.getMessage());
			e.printStackTrace();
		}
		renameFiles(inputFile, file);

	}

	/**
	 * This method partitions data accordingly to the following conditions based
	 * on probability one of two conditions would be true: if child nodes are
	 * greater than 3 if child nodes are greater than 1 if child nodes are
	 * greater than 5. Mod value is used to partition data so that both files
	 * have different partition. Partition is currently done on Element nodes.
	 * 
	 * @param element
	 * @param doc
	 * @param mod
	 * @return
	 * @throws Exception
	 */
	int xmlPartition(Element element, Document doc, int mod) {
		// counting number of children
		int count = 0;
		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++) {
			// must be Element node.
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				count++;

			}
		}
		// performing partition based on probability. not implemented yet
		if (count > 3 && !doc.getDocumentElement().equals(element)) {

			for (int i = 0; i < childNodes.getLength(); i++) {
				// mod differentiates data between two files.
				if (i % mod != 0) {
					if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Node nNode = childNodes.item(i);
						// removes nodes for partition
						nNode.getParentNode().removeChild(nNode);
						// removes blank space after remove
						doc.normalize();
					}
				}
			}
		}
		return count;
	}

	/**
	 * Outputs the modified partition data into files.
	 * 
	 * @param doc
	 * @param file
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParsingException
	 * @throws ValidityException
	 * @throws Exception
	 */
	private void formatXML(Document doc, String file) throws TransformerFactoryConfigurationError, TransformerException,
			IOException, ValidityException, ParsingException {
		// Takes input file
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(System.getProperty("user.dir") + "//output//" + file));
		// outputs result
		transformer.transform(source, result);
		// reads output for formatting
		FileInputStream res = new FileInputStream(new File(System.getProperty("user.dir") + "//output//" + file));
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		String xml = IOUtils.toString(res);

		// formatting the XML
		Serializer serializer = new Serializer(out1);
		serializer.setIndent(4); // or whatever you like
		serializer.write(new Builder().build(xml.toString(), ""));

		output = new FileWriter((new File(System.getProperty("user.dir") + "//output//" + file)));
		output.write(out1.toString());
		output.close();

	}

	public static void main(String[] args) {

		new XmlGenerator().granularityGenerator("Integration.aml", "Granularity-1.aml", 2);
		new XmlGenerator().granularityGenerator("Integration.aml", "Granularity-2.aml", 3);

	}
}