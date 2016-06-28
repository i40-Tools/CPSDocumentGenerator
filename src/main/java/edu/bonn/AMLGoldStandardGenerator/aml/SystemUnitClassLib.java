/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml;

import org.w3c.dom.Node;

/**
 * @author omar
 *
 */
public class SystemUnitClassLib extends GenericElement {

	public static int minimumSystemUnitGenerated;
	public static int systemChild;

	@Override
	void processElement() {
		Node parent = docInput.getDocumentElement();

		genericElement(minimumSystemUnitGenerated, systemChild, "SystemUnitClassLib", parent);

	}

}
