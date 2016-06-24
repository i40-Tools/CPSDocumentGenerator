/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.xml;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.apache.xerces.xs.XSModel;

import jlibs.xml.sax.XMLDocument;
import jlibs.xml.xsd.XSInstance;
import jlibs.xml.xsd.XSParser;

/**
 * @author omar
 *
 */
public class XmlGenerator {

	/**
	 * @param args
	 * @throws TransformerConfigurationException
	 */
	public static void main(String[] args) throws TransformerConfigurationException {
		// TODO Auto-generated method stub
		XSModel xsModel = new XSParser().parse("CAEX_ClassModel_V2.15.xsd");
		XSInstance xsInstance = new XSInstance();

		xsInstance.minimumElementsGenerated = 2;

		xsInstance.maximumElementsGenerated = 2;

		xsInstance.minimumListItemsGenerated = 2;

		xsInstance.maximumListItemsGenerated = 2;
		xsInstance.maximumRecursionDepth = 2;
		xsInstance.generateOptionalAttributes = Boolean.FALSE;
		xsInstance.generateFixedAttributes = Boolean.FALSE;

		xsInstance.generateDefaultAttributes = Boolean.FALSE;
		xsInstance.generateAllChoices = Boolean.FALSE;

		xsInstance.generateOptionalElements = Boolean.FALSE;

		javax.xml.namespace.QName rootElement = new javax.xml.namespace.QName("CAEXFile");

		XMLDocument sampleXml = new XMLDocument(new StreamResult(System.out), true, 1, null);

		xsInstance.generate(xsModel, rootElement, sampleXml);

	}

}
