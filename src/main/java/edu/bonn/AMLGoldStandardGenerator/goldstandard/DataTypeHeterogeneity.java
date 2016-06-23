package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * This class is used for generating Data Type heterogeneity
 * 
 * @author omar
 *
 */

public class DataTypeHeterogeneity {

	private Element element;
	private Document doc;
	private int mod;
	XmlParser xml;

	public DataTypeHeterogeneity(Document doc, int mod) {
		this.doc = doc;
		this.mod = mod + 1;
		xml = new XmlParser();
	}

	Document dataTypeGenerator() {

		// main loop gets root node
		NodeList baseElmntLst = doc.getElementsByTagName("*");
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			element = (Element) baseElmntLst.item(k);

			// we need to add heterogeneity at Data Type Attribute
			if (element.hasAttribute("AttributeDataType")) {

				// takes the type of data type
				String value = element.getAttribute("AttributeDataType");

				// gets its child nodes to check if it can be converted into
				// other data types
				NodeList childNodes = element.getChildNodes();

				// loop through child nodes
				for (int i = 0; i < childNodes.getLength(); i++) {

					if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {

						// ignore if its eclass
						if (xml.ignoreAttributes(childNodes.item(i).getParentNode().getAttributes())) {

							// checks if has value
							if (childNodes.item(i).getFirstChild() != null) {

								switch (value) {

								case "xs:float": {
									// do nothing let if be float

									if (mod > 1) {

										break;
									}

									// convert it to integer
									setInteger(childNodes.item(i));

									break;
								}

								case "xs:double": {

									if (mod > 1) {

										// make it float
										setFloat(childNodes.item(i));
										break;

									}

									// make it integer
									setInteger(childNodes.item(i));
									break;

								}

								case "xs:string": {
									if (mod > 1) {
										setFloat(childNodes.item(i));
										break;

									}

									setInteger(childNodes.item(i));
									break;

								}

								case "xs:integer": {
									if (mod > 1) {
										setFloat(childNodes.item(i));
										break;

									}

									break;
								}
								}
							}
						}
					}
				}
			}
		}
		return doc;

	}

	/**
	 * converts the value to integer
	 * 
	 * @param n
	 */
	void setInteger(Node n) {

		try {
			// check if its numeric value
			double temp = Double.parseDouble(n.getFirstChild().getNodeValue().trim().toString());
			int value = (int) temp;

			// gets first child and set its value
			n.getFirstChild().setNodeValue(String.valueOf(value));
			element.setAttribute("AttributeDataType", "xs:integer");

		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * converts the value to float
	 * 
	 * @param n
	 */
	void setFloat(Node n) {

		try {
			float temp = Float.parseFloat(n.getFirstChild().getNodeValue().trim().toString());
			System.out.println(temp);

			// gets first child and set its value
			n.getFirstChild().setNodeValue(String.valueOf(temp));
			element.setAttribute("AttributeDataType", "xs:float");

		} catch (Exception e) {
			e.getMessage();
		}

	}

	/**
	 * Converts to double data type
	 * 
	 * @param n
	 */
	void setDouble(Node n) {

		try {
			double temp = Double.parseDouble(n.getFirstChild().getNodeValue().trim().toString());
			n.getFirstChild().setNodeValue(String.valueOf(temp));
			element.setAttribute("AttributeDataType", "xs:double");

		} catch (Exception e) {
			e.getMessage();
		}

	}

}