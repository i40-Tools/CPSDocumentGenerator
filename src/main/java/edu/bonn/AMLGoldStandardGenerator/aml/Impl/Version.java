/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGolStandardGenerator.aml.CAEXBasicObject;

/**
 * @author omar
 *
 */
public class Version extends GenericElement {

	public static int minimum = 1;

	public static CAEXBasicObject.Version setSingleObject() {
		// TODO Auto-generated method stub
		if (minimum != 0) {

			edu.bonn.AMLGolStandardGenerator.aml.CAEXBasicObject.Version version = factory.createCAEXBasicObjectVersion();
			version.setValue("Eis 2016 version 1.0");
			return version;

		} else
			return null;

	}

	public static void setValues(CAEXBasicObject.Version type, int i) {

		type.setValue("version-" + i);
	}

}
