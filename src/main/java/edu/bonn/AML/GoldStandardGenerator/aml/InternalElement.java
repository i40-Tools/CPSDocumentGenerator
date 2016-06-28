/**
 * 
 */
package edu.bonn.AML.GoldStandardGenerator.aml;

/**
 * @author omar
 *
 */
public class InternalElement extends GenericElement {

	public static int minimumInternalGenerated;
	public static int internalChild;

	@Override
	void processElement() {

		nestedElement(minimumInternalGenerated, internalChild, "InternalElement", "InstanceHierarchy");

	}

}
