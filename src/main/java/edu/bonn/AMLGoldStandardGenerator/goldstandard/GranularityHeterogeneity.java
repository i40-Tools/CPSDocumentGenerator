package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * @author omar This Class provides provides method for generation of
 *         Granularity heterogeneity of AutomationML(ML) files
 */
public class GranularityHeterogeneity {

	private Element element;
	private Document doc;
	int mod;
	XmlParser xml;

	public GranularityHeterogeneity(Document doc, int mod) {
		this.doc = doc;
		this.mod = mod + 1;
		xml = new XmlParser();
	}

	/**
	 * This method is used for granulrity partitions data accordingly to the
	 * following conditions based on probability one of two conditions would be
	 * true: if child nodes are greater than 3 if child nodes are greater than 1
	 * if child nodes are greater than 5. Mod value is used to partition data so
	 * that both files have different partition. Partition is currently done on
	 * Element nodes.
	 * 
	 * @param element
	 * @param doc
	 * @param mod
	 * @return
	 */
	Document granularityGenerator() {

		// three probabilities for number of childs

		// already based on uniform distribution
		double probability = new Random().nextDouble();

		if (probability <= 0.03) {

			// partitions for node having atleast two child
			return xmlPartition(2);
		}

		if (probability > 0.03 && probability <= 0.06) {

			// partitions for node having atleast 3 child
			return xmlPartition(3);

		} else {
			// partitions for node having atleast six child
			return xmlPartition(6);
		}

	}

	/**
	 * Partitions the data into two files, ignoring ecl@ss semantics , its based
	 * on probability and does new partition based on number of childs.
	 * partition variable represent the minimum number of childs a node must
	 * have to qualify for partition
	 * 
	 * @param partition
	 * @return
	 */
	Document xmlPartition(int partition) {

		// main loop gets the root attribute
		NodeList baseElmntLst = doc.getElementsByTagName("*");
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			element = (Element) baseElmntLst.item(k);

			NodeList childNodes = element.getChildNodes();
			int count = new XmlParser().getChildCount(childNodes);

			// split the nodes
			if (count >= partition && !doc.getDocumentElement().equals(element)) {
				for (int i = 0; i < childNodes.getLength(); i++) {

					// mod differentiates data between two files.
					if (i % mod != 0) {
						if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
							if (xml.ignoreAttributes(childNodes.item(i).getAttributes())) {
								Node nNode = childNodes.item(i);

								// removes nodes for partition
								nNode.getParentNode().removeChild(nNode);

								// removes blank space after remove
								doc.normalize();
							}
						}
					}
				}
			}
		}
		return doc;

	}

}
