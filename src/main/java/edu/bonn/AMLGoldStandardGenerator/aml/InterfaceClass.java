/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml;

/**
 * @author omar
 *
 */
public class InterfaceClass extends GenericElement {

	public static int minimum;
	public static int child;

	@Override
	void processElement() {
		nestedElement(minimum, child, "InterfaceClass", "InterfaceClassLib");

		// TODO Auto-generated method stub

	}

}
