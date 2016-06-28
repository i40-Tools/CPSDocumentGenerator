package edu.bonn.AML.GoldStandardGenerator.aml;

import org.w3c.dom.Node;

/**
 * 
 * @author omar
 *
 */
public class RoleClassLib extends GenericElement {

	public static int minimumRoleGenerated;
	public static int roleChild;

	/**
	 * Generates Role class Lib
	 */

	@Override
	void processElement() {
		Node parent = docInput.getDocumentElement();

		genericElement(minimumRoleGenerated, roleChild, "RoleClassLib", parent);

	}

}
