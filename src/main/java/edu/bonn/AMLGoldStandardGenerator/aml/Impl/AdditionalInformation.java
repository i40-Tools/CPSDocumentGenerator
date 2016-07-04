package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXBasicObject;

/**
 * @author omar
 *
 */
public class AdditionalInformation extends GenericElement {

	public static int minimum = 1;

	public static ArrayList<CAEXBasicObject> setObject() {
		// TODO Auto-generated method stub

		ArrayList<CAEXBasicObject> additional = new ArrayList<CAEXBasicObject>();
		for (int j = 0; j < minimum; j++) {

			additional.add(factory.createCAEXBasicObject());

		}
		return additional;

	}

}
