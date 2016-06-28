package edu.bonn.AMLGoldStandardGenerator.aml;

import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Test.ModelRepair;
import edu.bonn.AMLGoldStandardGenerator.xml.XmlGenerator;
import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;

/**
 * This class Generate dummy elements of AML files and holds the configuration.
 * 
 * @author omar
 *
 */
public abstract class GenericElement {

	static String inputPath;
	static Document docInput;
	public static int minimumElementsGenerated;
	public static int elementChild;
	static int max;
	static int temp;
	static int tempChild;

	/**
	 * Every Element will have its different requirement.
	 */
	abstract void processElement();

	/**
	 * This function generates the data based on XML generation from XSD and
	 * paremeters.
	 * 
	 * @param file
	 * @param directory
	 * @param output
	 */
	public static void generate(String file, String directory, String output) {
		docInput = new XmlParser().initInput(file);
		inputPath = file;
		temp = minimumElementsGenerated;
		tempChild = elementChild;
		// gets the maximum level of data generation of all configuration and
		// generates only once.
		setMaxGeneration(minimumElementsGenerated);
		setMaxGeneration(InternalElement.minimumInternalGenerated);
		setMaxGeneration(InstanceHierarchy.minimumInstanceGenerated);
		setMaxGeneration(ExternalReference.minimumExternalGenerated);
		setMaxGeneration(InterfaceClassLib.minimumInterfaceGenerated);
		setMaxGeneration(SystemUnitClass.minimum);
		setMaxGeneration(SystemUnitClassLib.minimumSystemUnitGenerated);
		setMaxGeneration(RoleClass.minimum);
		setMaxGeneration(RoleClassLib.minimumRoleGenerated);

		// generates the max number found and call xml generator.
		new XmlGenerator().generate(max, false);

		// now for every individual we process the data from generated file.
		new InternalElement().processElement();
		new InstanceHierarchy().processElement();
		new ExternalReference().processElement();
		new InterfaceClassLib().processElement();
		new InterfaceClass().processElement();
		new SystemUnitClassLib().processElement();
		new SystemUnitClass().processElement();
		new RoleClassLib().processElement();
		new RoleClass().processElement();

		// now we have the data save it in enlarged file.
		new XmlParser().formatXML(docInput, output, directory);
		try {
			// repairs the data according to XSD ..
			ModelRepair.testRoundTrip(directory + output);

			File xmlFile = new File("temp.aml");
			xmlFile.delete();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Takes individual Element parameters and act accordingly.
	 * 
	 * @param min
	 * @param child
	 * @param tag
	 * @param parent
	 */
	public static void genericElement(int min, int child, String tag, Node parent) {

		// for over writing all the elements value if enabled.
		updateValue(min, child);

		// path of generated xml
		Document doc = new XmlParser().initInput("temp.aml");

		// migrates all the nodes from the generator.
		ArrayList<Node> node = new XmlParser().migrateElement(tag, doc, docInput);

		// relevant nodes added to the aml file.
		new XmlParser().addNodes(node, minimumElementsGenerated, child, parent);

	}

	/**
	 * Some elements can have many childs, this function defines those childs
	 * parameters.
	 * 
	 * @param min
	 * @param child
	 * @param tag
	 * @param parentTag
	 */
	public static void nestedElement(int min, int child, String tag, String parentTag) {

		updateValue(min, child);

		Document doc = new XmlParser().initInput("temp.aml");

		ArrayList<Node> node = new XmlParser().migrateElement(tag, doc, docInput);

		// under which tag the nested element can fit.
		Node parent = new XmlParser().findElement(parentTag, docInput);

		new XmlParser().addNodes(node, minimumElementsGenerated, child, parent);

	}

	/**
	 * This function updates value according to configuration. If individual
	 * element is called with parameters it updates accordingly.
	 * 
	 * @param value
	 * @param children
	 */
	static void updateValue(int value, int children) {
		if (value != 0) {
			minimumElementsGenerated = value;
			elementChild = children;
		} else if (value == 0 && temp == 0) {
			minimumElementsGenerated = value;
			elementChild = children;
		} else if (value == 0) {
			minimumElementsGenerated = temp;
			elementChild = tempChild;
		}

	}

	/**
	 * Function to generate only once from XSD schema to save memory.
	 * 
	 * @param value
	 */
	static void setMaxGeneration(int value) {

		if (value > max) {
			max = value;
		}

	}

	/**
	 * Possible configuration public static void main(String arg[]) {
	 * 
	 * GenericElement.elementChild = 1; // for enabling all elements
	 * GenericElement.minimumElementsGenerated = 1;
	 * 
	 * Individual configuration
	 * 
	 * InternalElement.minimumInternalGenerated = 2; //
	 * InternalElement.internalChild = 5; //
	 * InstanceHierarchy.minimumInstanceGenerated = 10; //
	 * InstanceHierarchy.instanceChild = 5; //
	 * ExternalReference.minimumExternalGenerated = 5; //
	 * ExternalReference.externalChild = 2; //
	 * InterfaceClassLib.minimumInterfaceGenerated = 15; //
	 * InterfaceClassLib.interfaceChild = 10; //
	 * SystemUnitClassLib.minimumSystemUnitGenerated = 10; //
	 * SystemUnitClassLib.systemChild = 10; //
	 * RoleClassLib.minimumRoleGenerated= 20; // RoleClassLib.roleChild = 10; //
	 * RoleClass.minimum = 2; // InterfaceClass.minimum = 2;
	 * 
	 * 
	 **/
}
