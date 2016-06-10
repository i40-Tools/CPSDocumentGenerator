package edu.bonn.AMLGoldStandardGenerator.schemas;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author omar This Class provides XSD schema Validation against generated
 *         AutomationML(ML) files
 */
public class XSDValidator {

	protected String schemaFilePath = "CAEX_ClassModel_V2.15.xsd"; // XSD schema
	protected String xmlFilePath;
	final static Logger logger = LoggerFactory.getLogger(XSDValidator.class);

	/**
	 * 
	 * @param xmlFilePath
	 */
	public XSDValidator(String xmlFilePath) {

		this.xmlFilePath = xmlFilePath;
	}

	/**
	 * Validates the XSD for Automation ML files.
	 * 
	 * @return
	 */
	public boolean schemaValidate() {

		Source schemaPath = new StreamSource(
				new File(getClass().getClassLoader().getResource(schemaFilePath).getPath()));
		Source xmlPath = new StreamSource(new File(xmlFilePath));

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = schemaFactory.newSchema(schemaPath);
			Validator xsdValidator = schema.newValidator();

			xsdValidator.validate(xmlPath);
			System.out.println(xmlPath.getSystemId() + " is valid generated file");
		} catch (SAXException | IOException e) {

			System.out.println(xmlPath.getSystemId() + " is NOT valid generated file");
			System.out.println("Because: " + e.getLocalizedMessage());
			return false;
		}

		return true;

	}

}