package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXBasicObject;

/**
 * @author omar
 *
 */
public class Description extends GenericElement {
	public static int minimum = 1;

	public static CAEXBasicObject.Description setSingleObject() {
		// TODO Auto-generated method stub
		if (minimum != 0) {

			edu.bonn.AMLGoldStandardGenerator.aml.CAEXBasicObject.Description desc = factory
					.createCAEXBasicObjectDescription();
			desc.setValue(getName());
			return desc;

		} else
			return null;

	}

}
