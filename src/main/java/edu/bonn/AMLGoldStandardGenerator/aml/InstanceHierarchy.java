/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml;

import org.w3c.dom.Node;

/**
 * @author omar
 *
 */
public class InstanceHierarchy extends GenericElement {

	public static int minimumInstanceGenerated;
	public static int instanceChild;

	@Override
	void processElement() {
		Node parent = docInput.getDocumentElement();
		genericElement(minimumInstanceGenerated, instanceChild, "InstanceHierarchy", parent);

	}

}
