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
 * This class generate schematic heterogeneity.
 * 
 * @author omar
 *
 */
public class SchematicHeterogeneity extends Heterogeneity {

	private Node nodeGrandParent;

	public SchematicHeterogeneity(Document doc, int mod) {

		super(doc, mod);

	}

	/**
	 * Genrates the schema heterogeneity by giving a different variant of
	 * schema.
	 * 
	 * @return
	 */
	public Document schematicGenerator() {
		Node parent = null;
		// skips the first file.
		if (mod < 2)
			return doc;
		ArrayList<Element> elements = new ArrayList<Element>();
		// main loop gets root node
		NodeList baseElmntLst = doc.getElementsByTagName("InternalLink");

		// gets all InternalLink elements
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			elements.add((Element) baseElmntLst.item(k));
		}

		// for ever InternalLink Element add it to corresponding ID.
		for (int k = 0; k < elements.size(); k++) {
			element = elements.get(k);

			// we need to add heterogeneity at InternalLink
			String Id = element.getAttribute("RefPartnerSideB");

			// only need to do remove Parent Node Once.

			if (parent == null || !element.getParentNode().isEqualNode(parent)) {
				parent = element.getParentNode();
				NodeList child = element.getParentNode().getChildNodes();
				Node nodeParent = element.getParentNode();
				nodeGrandParent = nodeParent.getParentNode();

				// we add all the data after InternalLink Parent.
				for (int i = 1; i < child.getLength(); i++) {
					if (child.item(i).hasChildNodes()) {
						nodeGrandParent.appendChild(child.item(i));
					}
				}

				// now removes the Parent
				nodeGrandParent.removeChild(nodeParent);
			}
			getAllChildNodes(nodeGrandParent, Id, element);

		}
		return doc;
	}

	/**
	 * Adds internal link element to the matching ID
	 * 
	 * @param node
	 * @param Id
	 * @param element
	 */
	private static void getAllChildNodes(Node node, String Id, Element element) {
		NodeList childNodes = node.getChildNodes();
		int length = childNodes.getLength();

		// loops through all child nodes
		for (int i = 0; i < length; i++) {
			Node childNode = childNodes.item(i);
			if (childNode instanceof Element) {
				if (childNode.hasChildNodes()) {
					Element el = (Element) childNode;
					// compares the connection ID
					if (el.hasAttribute("ID")) {
						if (Id.contains(el.getAttribute("ID"))) {
							// if matches adds the ID
							el.appendChild(element);
						}

						// recursively go through all childs
						getAllChildNodes(childNode, Id, element);
					}
				}
			}
		}
	}

}
