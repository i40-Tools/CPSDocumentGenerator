package edu.bonn.AMLGoldStandardGenerator.goldstandard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * This class collects the relevant text from goldStandard and generated file.
 * 
 * @author omar
 *
 */

public class RelevanceCollection extends XmlParser {

	protected int relevantTextFound;
	protected int relevantText;
	protected int totalTextFound;
	HashMap<String, Integer> occurances;

	public RelevanceCollection() {
	}

	/**
	 * @return the relevantTextFound
	 */
	public int getRelevantTextFound() {
		return relevantTextFound;
	}

	/**
	 * @return the relevantText
	 */
	public int getRelevantText() {
		return relevantText;
	}

	/**
	 * @return the totalTextFound
	 */
	public int getTotalTextFound() {
		return totalTextFound;
	}

	/**
	 * sets the Relevant text
	 * 
	 * @param doc
	 */
	void setRelevantText(Document doc) {

		relevantText = getAllNodes(doc).size();

	}

	/**
	 * Finds all occurances count for not keeping duplicate nodes as relevant.
	 * 
	 * @param doc
	 * @return
	 */

	HashMap<String, Integer> getOccurunces(Document doc) {
		ArrayList<String> nodes = getNodeName(getAllNodes(doc));
		occurances = new HashMap<String, Integer>();
		for (int i = 0; i < nodes.size(); i++) {
			occurances.put(nodes.get(i), Collections.frequency(nodes, nodes.get(i)));
		}
		return occurances;
	}

	/**
	 * Sets the total Text
	 * 
	 * @param doc
	 */
	void setTotalText(Document doc) {

		totalTextFound = getAllNodes(doc).size();

	}

	/**
	 * This functions process the gold standard document and generated result
	 * document and find its relevant text. This function first starts with file
	 * paths.Then it counts the number of counts the nodes has in gold standard
	 * so that one node should not be mark relevant if its a duplicate. Then it
	 * calculate relevant and non relevant text on nodes with attributes. Then
	 * it calculates relevance for nodes with no attributes. And lastly it get
	 * relevant text count for Elements which have values.
	 * 
	 * @param goldStandardFilePath
	 * @param resultPath
	 */

	void processRelevanceCollection(String goldStandardFilePath, String resultPath) {

		// gets gold standard path
		Document goldStandard = initInput(goldStandardFilePath);

		// gets generated file path
		Document generated = initInput(resultPath);

		getOccurunces(goldStandard);

		// compares nodes with Attributes
		checkNodeWithAttribute(goldStandard, generated);

		// compares nodes with No Attributes
		checkNodesWithNoAttr(goldStandard, generated);

		// compares nodes with Values
		checkNodesWithValues(goldStandard, generated);

		setRelevantText(goldStandard);

		setTotalText(generated);

	}

	/**
	 * This function checks attributes of a node if they are equal or not.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */

	boolean checkAttribute(Node n1, Node n2) {

		ArrayList<Node> goldStandard = getAttribute(n2.getAttributes());

		ArrayList<Node> generated = getAttribute(n1.getAttributes());

		// loops through every Gold standard element Attribute
		for (int i = 0; i < generated.size(); i++) {

			for (int j = 0; j < goldStandard.size(); j++) {

				// compare if the names are equal
				if (!checkNodeName(generated.get(i), goldStandard.get(j))) {

					// compare if its value is equal or not
					if (!checkNodeValue(generated.get(i), goldStandard.get(j))) {

						return false;
					}
					return false;
				}

			}
		}

		return true;
	}

	/**
	 * This function checks if two nodes values are equal or not.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */

	boolean checkNodeValue(Node n1, Node n2) {
		if (getNodeValue(n1).trim().equals(getNodeValue(n2).trim())) {
			return true;
		}
		return false;
	}

	/**
	 * For every node that is checked , it should also be semantically correct.
	 * So its parents nodes are also checked and they should match to be
	 * qualified as relevant text.
	 * 
	 * @param goldStandard
	 * @param generated
	 * @return
	 */

	boolean checkParent(Node goldStandard, Node generated) {

		// loops until there are no more parents.
		while (goldStandard.getParentNode() != null && generated.getParentNode() != null) {

			// compares parents name
			if (goldStandard.getParentNode().getNodeName().equals(generated.getParentNode().getNodeName())) {

				// if equal puts next parent
				goldStandard = goldStandard.getParentNode();

				generated = generated.getParentNode();

			} else {

				return false;
			}
		}

		return true;
	}

	/**
	 * This functions checks if two Node names are equal or not.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */

	boolean checkNodeName(Node n1, Node n2) {

		if (n1.getNodeName().trim().equals(n2.getNodeName().trim())) {

			return true;
		}
		return false;
	}

	/**
	 * This converts Nodes with attributes Array into Single string and compares
	 * if its equal or not. if its not equal whole node is regarded as not
	 * relevant.
	 * 
	 * @param n1
	 * @param n2
	 * @return
	 */

	boolean checkNodeArray(ArrayList<Node> n1, ArrayList<Node> n2) {

		// converts attributes to String value
		ArrayList<String> goldStandard = getNodeNameValuePair(n1);
		ArrayList<String> generated = getNodeNameValuePair(n2);

		// compares if those values are inside Gold standard nodes or not.
		for (int j = 0; j < generated.size(); j++) {

			// ignores FileName
			if (generated.get(j).equals("FileName"))
				return true;

			if (!goldStandard.contains(generated.get(j))) {

				return false;

			}

		}

		// Nodes with Attributes empty are ignored.
		if (generated.size() < 1) {

			return false;
		}

		return true;
	}

	/**
	 * This function checks if the node is duplicated in the result. if its a
	 * duplicated it should not be relevant
	 * 
	 * @param n
	 * @return
	 */
	boolean checkDuplicate(Node n) {

		Integer count = occurances.get(n.getNodeName().trim());
		if (count > 0) {
			count--;
			occurances.put(n.getNodeName().trim(), count);
			return true;
		}
		return false;
	}

	/**
	 * This function checkNodes with values, compare them and also check its
	 * parents for semantic correctness.
	 * 
	 * @param goldStandardFile
	 * @param generatedFile
	 */
	void checkNodesWithValues(Document goldStandardFile, Document generatedFile) {
		// gets nodes and its attributes in key pair.
		HashMap<Node, Node> generatedPair = getNodesWithValues(generatedFile);
		HashMap<Node, Node> goldStandardPair = getNodesWithValues(goldStandardFile);

		// seperate keys from pair values.
		ArrayList<Node> goldStandard = new ArrayList<Node>();
		ArrayList<Node> generated = new ArrayList<Node>();

		// gets all keys of goldStandard
		for (int i = 0; i < goldStandardPair.size(); i++) {
			Node n1 = (Node) goldStandardPair.keySet().toArray()[i];
			goldStandard.add(n1);
		}

		// gets all keys of generated file
		for (int i = 0; i < generatedPair.size(); i++) {
			Node n1 = (Node) generatedPair.keySet().toArray()[i];
			generated.add(n1);
		}

		// loops through all generated nodes keys
		for (int i = 0; i < generated.size(); i++) {
			for (int j = 0; j < goldStandard.size(); j++) {

				// compares node name
				if (checkNodeName(generated.get(i), goldStandard.get(j))) {

					// compares parent
					if (checkParent(generated.get(i), goldStandard.get(j))) {

						// finally compares value
						if (checkNodeValue(generated.get(i), goldStandard.get(j))) {

							// System.out.println(generated.get(i).getNodeName().trim());

							// checks its not a duplicate.
							if (checkDuplicate(generated.get(i))) {

								// if everything matches its relevant.

								relevantTextFound++;
							}

						}
					}
				}
			}
		}

	}

	/**
	 * This function check nodes which does not have attributes or have empty
	 * attributes
	 * 
	 * @param goldStandardPath
	 * @param generatedPath
	 */

	void checkNodesWithNoAttr(Document goldStandardPath, Document generatedPath) {
		ArrayList<Node> goldStandardNodes = getNodesWithNoValues(goldStandardPath);
		ArrayList<Node> generatedNodes = getNodesWithNoValues(generatedPath);

		// filters the nodes with no Attributes
		ArrayList<Node> goldStandard = getNodesWihtNoAttributes(goldStandardNodes);
		ArrayList<Node> generated = getNodesWihtNoAttributes(generatedNodes);

		// System.out.println(origNodesNoAttributes);
		// System.out.println(modifiedNodesNoAttributes);

		// loops and compare
		for (int i = 0; i < generated.size(); i++) {
			for (int j = 0; j < goldStandard.size(); j++) {

				// compares the node name
				if (generated.toString().contains(goldStandard.get(j).toString())) {

					// compares if its the node that is compared
					if (generated.get(i).toString().equals(goldStandard.get(j).toString())) {

						// compares its parent for semantic
						if (checkParent(generated.get(i), goldStandard.get(j))) {

							// compares if duplicate
							if (checkDuplicate(generated.get(i))) {

								// its a relevant text
								relevantTextFound++;

								// System.out.println(generated.get(i).getNodeName().trim());
							}
						}
					}
				}
			}
		}

	}

	/**
	 * This function checkNodes with Attributes, compare them and also check its
	 * parents for semantic correctness. For every node its all attributes are
	 * stored in to one array and passed to function to check as a whole if all
	 * the attributes matches or not
	 * 
	 * 
	 * @param goldStandardPath
	 * @param generatedPath
	 */
	void checkNodeWithAttribute(Document goldStandardPath, Document generatedPath) {

		// holds all the nodes
		ArrayList<Node> goldStandard = getNodesWithNoValues(goldStandardPath);
		ArrayList<Node> generated = getNodesWithNoValues(generatedPath);

		// for every node loop and gets its attributes in array
		for (int z = 0; z < generated.size(); z++) {
			for (int k = 0; k < goldStandard.size(); k++) {

				// stores attributes
				ArrayList<Node> goldStandardAttr = getAttribute(generated.get(z).getAttributes());
				ArrayList<Node> generatedAttr = getAttribute(goldStandard.get(k).getAttributes());

				// compares if attributes of current node is equal
				if (checkNodeArray(goldStandardAttr, generatedAttr)) {

					// checks its owner node of those attribute
					if (checkNodeName(generated.get(z), goldStandard.get(k))) {

						// check its parent for semantic correctness
						if (checkParent(generated.get(z), goldStandard.get(k))) {

							// compares if duplicate
							if (checkDuplicate(generated.get(z))) {

								// if found its relevant
								relevantTextFound++;

							} // System.out.println(generated.get(z).getNodeName());
						}
					}
				}

			}
		}

	}
}
