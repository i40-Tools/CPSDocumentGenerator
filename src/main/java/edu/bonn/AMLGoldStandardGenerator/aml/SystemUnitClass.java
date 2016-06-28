/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml;

/**
 * @author omar
 *
 */
public class SystemUnitClass extends GenericElement {

	public static int minimum;
	public static int child;

	@Override
	void processElement() {
		nestedElement(minimum, child, "SystemUnitClass", "SystemUnitClassLib");

		// TODO Auto-generated method stub

	}

}
