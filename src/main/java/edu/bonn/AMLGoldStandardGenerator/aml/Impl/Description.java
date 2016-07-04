/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGolStandardGenerator.aml.CAEXBasicObject;

/**
 * @author omar
 *
 */
public class Description extends GenericElement {
	public static int minimum = 1;

	public static CAEXBasicObject.Description setSingleObject() {
		// TODO Auto-generated method stub
		if (minimum != 0) {

			edu.bonn.AMLGolStandardGenerator.aml.CAEXBasicObject.Description desc = factory.createCAEXBasicObjectDescription();
			desc.setValue("Eis 2016");
			return desc;

		} else
			return null;

	}

}
