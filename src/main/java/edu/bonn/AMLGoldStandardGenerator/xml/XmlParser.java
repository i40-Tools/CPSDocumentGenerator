/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.xml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import nu.xom.Builder;
import nu.xom.ParsingException;
import nu.xom.Serializer;
import nu.xom.ValidityException;

/**
 * This class parses XML document and provides functionality to read nodes and
 * its attributes
 * 
 * @author omar
 *
 */

public class XmlParser {

	/**
	 * This function initializes the XML file. Then its parsed in to java
	 * objects.
	 * 
	 * @param inputFile
	 * @return
	 */
	public Document initInput(String inputFile) {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		dbFactory.setValidating(false);
		Document doc = null;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(new FileInputStream(new File(inputFile)));
			doc.getDocumentElement().normalize();

		} catch (SAXException | IOException | ParserConfigurationException e) {
			System.out.println("Error File not Found " + inputFile);
			System.out.println("Please check configuration file");
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * This function return no of child a node contains.
	 * 
	 * @param childNodes
	 * @return
	 */

	public static int getChildCount(NodeList childNodes) {

		// counting number of children
		int count = 0;

		for (int i = 0; i < childNodes.getLength(); i++) {

			// must be Element node.
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				count++;

			}
		}
		return count;
	}

	public ArrayList<Node> getChildNodes(NodeList childNodes) {

		// counting number of children
		ArrayList<Node> child = new ArrayList<Node>();
		for (int i = 0; i < childNodes.getLength(); i++) {

			// must be Element node.
			if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				child.add(childNodes.item(i));

			}
		}
		return child;
	}

	/**
	 * This Function gets Attributes for any Given Node.
	 * 
	 * @param baseElmntAttr
	 * @return
	 */

	public ArrayList<Node> getAttribute(NamedNodeMap baseElmntAttr) {
		ArrayList<Node> node = new ArrayList<Node>();
		for (int i = 0; i < baseElmntAttr.getLength(); ++i) {
			Node attr = baseElmntAttr.item(i);
			node.add(attr);
		}
		return node;
	}

	/**
	 * This functions takes input document xml and return arraylist of nodes
	 * which does have any values.
	 * 
	 * @param doc
	 * @return
	 */

	protected ArrayList<Node> getNodesWithNoValues(Document doc) {

		ArrayList<Node> node = new ArrayList<Node>();

		// reads the root node.
		NodeList baseElmntLst = doc.getElementsByTagName("*");

		// loops through every element
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			String value = getNodeValue(baseElmntLst.item(k));

			// if value is null its add to list
			if (value == null) {
				node.add(baseElmntLst.item(k));
			}
		}
		return node;

	}

	/**
	 * This function returns a pair node of Attribute Name and its values.
	 * 
	 * @param doc
	 * @return
	 */

	protected HashMap<Node, Node> getNodesWithValues(Document doc) {

		HashMap<Node, Node> result = new HashMap<Node, Node>();
		ArrayList<Node> node = new ArrayList<Node>();

		// starts from root
		NodeList baseElmntLst = doc.getElementsByTagName("*");

		// get every node
		for (int k = 0; k < baseElmntLst.getLength(); k++) {
			Node n = baseElmntLst.item(k);
			node.add(n);
		}

		// for every node gets its attribute and its value.
		for (int i = 0; i < node.size(); i++) {
			NodeList children = node.get(i).getChildNodes();
			for (int i1 = 0; i1 < children.getLength(); i1++) {
				Node textChild = children.item(i1);

				// compares if node has attribute value
				if (textChild.getNodeType() == Node.TEXT_NODE) {
					if (textChild.getNodeValue().trim().length() != 0) {

						// puts attribute and its value
						result.put(node.get(i), textChild);
					}

				}
			}
		}
		return result;
	}

	/**
	 * This function take parameter node and returns its text value if exist.
	 * 
	 * @param node
	 * @return
	 */

	protected String getNodeValue(Node node) {

		// get all of its children if exist
		NodeList children = node.getChildNodes();

		// loops through children
		for (int i1 = 0; i1 < children.getLength(); i1++) {

			// gets value
			Node textChild = children.item(i1);
			if (textChild.getNodeType() == Node.TEXT_NODE) {

				if (textChild.getNodeValue().trim().length() != 0) {

					// returns the node value
					return textChild.getNodeValue().trim();
				}
			}

		}
		return null;

	}

	/**
	 * This functions finds Element by name in a given xml file.
	 * 
	 * @param name
	 * @param doc
	 * @return
	 */
	public Element findElement(String name, Document doc) {

		// starts by root
		NodeList baseElmntLst = doc.getElementsByTagName("*");

		// loop throug all elements
		for (int k = 0; k < baseElmntLst.getLength(); k++) {

			Element baseElmnt = (Element) baseElmntLst.item(k);

			// if found return that element
			if (baseElmnt.getNodeName().equals(name)) {
				return baseElmnt;
			}
		}

		return null;
	}

	/**
	 * This functions finds Element by name in a given xml file.
	 * 
	 * @param name
	 * @param doc
	 * @return
	 */

	/**
	 * copies one node to another document.
	 * 
	 * @param name
	 * @param doc
	 * @return
	 */
	public ArrayList<Node> migrateElement(String name, Document doc, Document migrateDoc) {

		ArrayList<Node> migrateNodes = new ArrayList<Node>();

		// starts by root
		NodeList baseElmntLst = doc.getElementsByTagName("*");

		// loop throug all elements
		for (int k = 0; k < baseElmntLst.getLength(); k++) {

			Element baseElmnt = (Element) baseElmntLst.item(k);

			// if found return that element
			if (baseElmnt.getNodeName().equals(name)) {
				migrateNodes.add(migrateDoc.importNode(baseElmnt, true));
			}
		}

		return migrateNodes;
	}

	/**
	 * Adds node to Any tag with minimum childs and given number of repetition
	 * 
	 * @param node
	 * @param min
	 * @param child
	 * @param parent
	 */

	private static void removeAllChildNodes(Node node, int child) {
		NodeList childNodes = node.getChildNodes();
		int length = childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node childNode = childNodes.item(i);
			if (childNode instanceof Element) {
				if (childNode.hasChildNodes()) {
					while (childNode.hasChildNodes()) {

						if (getChildCount(childNode.getChildNodes()) <= child) {
							break;

						} else {
							childNode.removeChild(childNode.getFirstChild());

						}
					}
				}
			}

			removeAllChildNodes(childNode, child);
		}

	}

	/**
	 * Adds nodes with given parameters for generation.
	 * 
	 * @param node
	 * @param min
	 * @param child
	 * @param parent
	 * @param depth
	 */
	public void addNodes(ArrayList<Node> node, int min, int child, Node parent, int depth) {

		for (int i = 0; i < min; i++) {

			while (node.get(i).hasChildNodes()) {
				node.get(i).removeChild(node.get(i).getFirstChild());
				if (depth != -1) {
					removeAllChildNodes(node.get(i), depth);
				}
				if (getChildCount(node.get(i).getChildNodes()) <= child) {
					break;
				}
			}
			if (parent != null)
				parent.appendChild(node.get(i));
		}

	}

	/**
	 * Removes child nodes to given child value
	 * 
	 * @param node
	 * @param child
	 */
	public void removeChilds(int child, ArrayList<Node> n, String name) {

		for (int z = 0; z < n.size(); z++) {
			NodeList baseElmntLst = n.get(z).getChildNodes();

			// loop throug all elements
			for (int k = 0; k < baseElmntLst.getLength(); k++) {

				Element baseElmnt = (Element) baseElmntLst.item(k);

				for (int i = 0; i <= child; i++) {

					while (baseElmnt.hasChildNodes()) {
						if (new XmlParser().getChildCount(baseElmnt.getChildNodes()) < child) {

							break;
						}

						baseElmnt.removeChild(baseElmnt.getFirstChild());

					}
				}
			}
		}
	}

	/**
	 * This function take xml document and return all its nodes.
	 * 
	 * @param doc
	 * @return
	 */

	protected ArrayList<Node> getAllNodes(Document doc) {

		ArrayList<Node> node = new ArrayList<Node>();

		// start from root.
		NodeList baseElmntLst = doc.getElementsByTagName("*");

		for (int k = 0; k < baseElmntLst.getLength(); k++) {

			Element baseElmnt = (Element) baseElmntLst.item(k);

			node.add(baseElmnt);
		}
		return node;
	}

	/**
	 * This function takes attribute node and returs its name and value
	 * 
	 * @param n
	 * @return
	 */

	public ArrayList<String> getNodeNameValuePair(ArrayList<Node> n) {

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < n.size(); i++) {

			temp.add(n.get(i).getNodeName().trim());

			temp.add(n.get(i).getNodeValue().trim());

		}
		return temp;
	}

	/**
	 * Functions returns node names
	 * 
	 * @param n
	 * @return
	 */
	public ArrayList<String> getNodeName(ArrayList<Node> n) {

		ArrayList<String> temp = new ArrayList<String>();

		for (int i = 0; i < n.size(); i++) {

			temp.add(n.get(i).getNodeName().trim());

		}
		return temp;
	}

	/**
	 * This function gets all those nodes which doesnt have attributes.
	 * 
	 * @param n1
	 * @return
	 */
	protected ArrayList<Node> getNodesWihtNoAttributes(ArrayList<Node> n1) {

		ArrayList<Node> tempNode = new ArrayList<Node>();

		for (int i = 0; i < n1.size(); i++) {

			// checks if it has attributes
			if (!n1.get(i).hasAttributes()) {

				tempNode.add(n1.get(i));
			}
		}
		return tempNode;
	}

	/**
	 * Ignore semantic field of ecl@ss
	 * 
	 * @param baseElmntAttr
	 * @return
	 */

	public boolean ignoreAttributes(NamedNodeMap baseElmntAttr) {
		for (int i = 0; i < baseElmntAttr.getLength(); ++i) {
			Node attr = baseElmntAttr.item(i);
			if (attr.getNodeValue().equals("eClassVersion") || attr.getNodeValue().equals("eClassClassificationClass")
					|| attr.getNodeValue().equals("eClassIRDI")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Outputs the modified partition data into files.
	 * 
	 * @param doc
	 * @param file
	 * @param directory
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 * @throws IOException
	 * @throws ParsingException
	 * @throws ValidityException
	 * @throws Exception
	 */

	public void formatXML(Document doc, String file, String directory) {

		// Takes input file
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			DOMSource source = new DOMSource(doc);
			File dir = new File(directory);

			// checks for output directory and creates it
			if (!dir.exists()) {
				if (dir.mkdirs()) {
					System.out.println("Creating output directory");
				} else {
					System.out.println("cannot create output directories Please check permission");
					System.exit(0);
				}

			}
			StreamResult result = new StreamResult(new File(directory + "//" + file));

			// outputs result
			transformer.transform(source, result);

			// reads output for formatting
			FileInputStream res = new FileInputStream(new File(directory + "//" + file));
			ByteArrayOutputStream out1 = new ByteArrayOutputStream();
			String xml = IOUtils.toString(res);

			// formatting the XML
			Serializer serializer = new Serializer(out1);
			serializer.setIndent(4); // or whatever you like
			serializer.write(new Builder().build(xml.toString(), ""));

			FileWriter output = new FileWriter((new File(directory + "//" + file)));
			output.write(out1.toString());
			output.close();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParsingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param doc
	 * @param file
	 * @param directory
	 * @return
	 */

	public Document sortXML(Document doc, String file, String directory) {

		// Takes input file
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance()
					.newTransformer(new StreamSource(getClass().getClassLoader().getResourceAsStream("sort.xsl")));
			DOMSource source = new DOMSource(doc);
			File dir = new File(directory);

			// checks for output directory and creates it
			if (!dir.exists()) {
				if (dir.mkdirs()) {
					System.out.println("Creating output directory");
				} else {
					System.out.println("cannot create output directories Please check permission");
					System.exit(0);
				}

			}
			StreamResult result = new StreamResult(new File(directory + "//" + file));

			// outputs result

			transformer.transform(source, result);
			doc = initInput(directory + "//" + file);

		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doc;

	}

}