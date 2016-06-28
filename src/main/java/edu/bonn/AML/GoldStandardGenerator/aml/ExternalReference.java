/**
 * 
 */
package edu.bonn.AML.GoldStandardGenerator.aml;

import org.w3c.dom.Node;

/**
 * @author omar
 *
 */
public class ExternalReference extends GenericElement {
	public static int minimumExternalGenerated;
	public static int externalChild;

	@Override
	void processElement() {
		Node parent = docInput.getDocumentElement();

		genericElement(minimumExternalGenerated, externalChild, "ExternalReference", parent);
		// TODO Auto-generated method stub

	}

}
