/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.xml;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;

import Test.ModelRepair;
import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

/**
 * This class generates AML data based on XSD models
 * 
 * @author omar
 *
 */
public class XmlGenerator {

	private FileOutputStream fs;

	/**
	 * Generates data based on XSD format.
	 * 
	 * @param value
	 * @param check
	 */
	public void generate(int value, Boolean check) {
		XSModel xsModel = new XSParser().parse("Model/CAEX_ClassModel_V2.15.xsd");
		// XSModel xsModel = new XSParser().parse("mode.xsd");

		XSInstance xsInstance = new XSInstance();

		xsInstance.minimumElementsGenerated = value;

		xsInstance.maximumElementsGenerated = value;

		// xsInstance.minimumListItemsGenerated = 5;

		// xsInstance.maximumListItemsGenerated = 5;
		xsInstance.maximumRecursionDepth = 0;
		xsInstance.generateDefaultElementValues = Boolean.TRUE;
		xsInstance.generateOptionalAttributes = Boolean.TRUE;
		xsInstance.generateFixedAttributes = Boolean.TRUE;

		xsInstance.generateDefaultAttributes = Boolean.FALSE;
		xsInstance.generateAllChoices = Boolean.TRUE;
		xsInstance.generateOptionalElements = check;

		javax.xml.namespace.QName rootElement = new javax.xml.namespace.QName("CAEXFile");

		XMLDocument sampleXml = null;
		try {
			fs = new FileOutputStream("temp.aml");
			sampleXml = new XMLDocument(new StreamResult(fs), true, 4, null);

		} catch (TransformerConfigurationException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		xsInstance.generate(xsModel, rootElement, sampleXml);

		try {
			ModelRepair.testRoundTrip("temp.aml");
			fs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 * @throws TransformerConfigurationException
	 */

}
