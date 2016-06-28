/**
 * 
 */
package edu.bonn.AML.GoldStandardGenerator.aml;

/**
 * @author omar
 *
 */
public class RoleClass extends GenericElement {

	public static int minimum;
	public static int child;

	@Override
	void processElement() {
		nestedElement(minimum, child, "RoleClass", "RoleClassLib");

		// TODO Auto-generated method stub

	}
}
