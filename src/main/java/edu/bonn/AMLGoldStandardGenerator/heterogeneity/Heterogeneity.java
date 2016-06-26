/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.heterogeneity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * This is top level class for Heterogeneity generation, used to initialize
 * constructor.
 * 
 * @author omar
 *
 */
public class Heterogeneity {

	protected Element element;
	protected Document doc;
	protected int mod;
	protected XmlParser xml;

	public Heterogeneity(Document doc, int mod) {
		this.doc = doc;
		this.mod = mod + 1;
		xml = new XmlParser();
	}

}
