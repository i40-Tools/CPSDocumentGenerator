/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml;

import org.w3c.dom.Node;

/**
 * @author omar
 *
 */
public class InterfaceClassLib extends GenericElement {

	public static int minimumInterfaceGenerated;
	public static int interfaceChild;

	@Override
	void processElement() {
		Node parent = docInput.getDocumentElement();

		genericElement(minimumInterfaceGenerated, interfaceChild, "InterfaceClassLib", parent);

	}

}
