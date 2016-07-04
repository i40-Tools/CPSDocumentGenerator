package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXBasicObject;

/**
 * @author omar
 *
 */
public class Copyright extends GenericElement {

	public static int minimum = 1;

	public static CAEXBasicObject.Copyright setSingleObject() {
		// TODO Auto-generated method stub
		if (minimum != 0) {

			edu.bonn.AMLGoldStandardGenerator.aml.CAEXBasicObject.Copyright copy = factory
					.createCAEXBasicObjectCopyright();
			copy.setValue("Eis 2016");
			return copy;

		} else
			return null;

	}

}
