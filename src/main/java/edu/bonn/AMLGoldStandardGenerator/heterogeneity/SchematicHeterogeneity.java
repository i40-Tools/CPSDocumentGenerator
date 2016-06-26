/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.heterogeneity;

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

		// skips the first file.
		if (mod < 2)
			return doc;

		// main loop gets root node
		NodeList baseElmntLst = doc.getElementsByTagName("*");
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			element = (Element) baseElmntLst.item(k);

			// we need to add heterogeneity at InternalLink
			if (element.getNodeName().trim().toString().equals("InternalLink")) {

				NodeList child = element.getParentNode().getChildNodes();
				Node node = element.getParentNode();
				Node node2 = node.getParentNode();

				// we add all the data after InternalLink Parent.
				for (int i = 1; i < child.getLength(); i++) {
					node2.appendChild(child.item(i));
				}

				// now we remove the orignal parent.
				node2.removeChild(node);

				// now adding the internal link to the previous sibling.
				element.getPreviousSibling().appendChild(element);

				// removing extra childs.
				node2.removeChild(node2.getLastChild());
			}
		}

		return doc;
	}

}
