package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.SystemUnitClassType;

/**
 * @author omar
 *
 */
public class InternalLinkImp extends GenericElement {

	public static int minimum = 1;

	public static ArrayList<SystemUnitClassType.InternalLink> setObject() {
		// TODO Auto-generated method stub

		ArrayList<SystemUnitClassType.InternalLink> external = new ArrayList<SystemUnitClassType.InternalLink>();

		for (int j = 0; j < minimum; j++) {

			external.add(factory.createSystemUnitClassTypeInternalLink());

			setValues(external.get(j), j);
		}

		return external;

	}

	public static void setValues(SystemUnitClassType.InternalLink type, int i) {
		type.setID(getID());
		type.setName(getName());
		type.setRefPartnerSideA("connection A" + getID());
		type.setRefPartnerSideB("connection B" + getID());

	}

}
