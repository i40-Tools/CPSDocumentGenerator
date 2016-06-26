/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.heterogeneity;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class generated Grouping/Aggreation heterogeneity.
 * 
 * @author omar
 *
 */
public class GroupingHeterogeneity extends Heterogeneity {

	public GroupingHeterogeneity(Document doc, int mod) {

		super(doc, mod);

	}

	/**
	 * Generates heterogeneity
	 * 
	 * @return
	 */
	public Document groupingGenerator() {

		// first file is ignored and created as it is.
		if (mod < 2) {
			return doc;
		}

		// array list to hold elements that are to be removed.
		ArrayList<Node> nodes = new ArrayList<Node>();

		// main loop gets root node
		NodeList baseElmntLst = doc.getElementsByTagName("*");
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			element = (Element) baseElmntLst.item(k);

			// we need to add heterogeneity Name attribute
			if (element.hasAttribute("Name")) {
				String value = element.getAttribute("Name");

				// if its a Group add heterogeneity
				if (value.contains("Group")) {
					nodes.add(element);
				}

			}
		}

		// removes the elements that were marked group
		for (int i = 0; i < nodes.size(); i++) {
			nodes.get(i).getParentNode().removeChild(nodes.get(i));
		}

		return doc;
	}
}
