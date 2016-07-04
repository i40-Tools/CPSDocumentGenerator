package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.InterfaceClassType;

/**
 * 
 * @author omar
 *
 */
public class ExternalInterfaceType extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setAttribute = -1;

	public static ArrayList<InterfaceClassType> setObject() {
		// TODO Auto-generated method stub

		ArrayList<InterfaceClassType> external = new ArrayList<InterfaceClassType>();
		setNoOfElements();
		for (int j = 0; j < minimum; j++) {

			external.add(factory.createInterfaceClassType());

			setValues(external.get(j), j);
		}

		// setOptionalAttribute(external, version, copy, desc);
		return external;

	}

	public static void setValues(InterfaceClassType type, int i) {
		type.setID("0000-1--1" + i);
		type.setName("External Interface" + i);
		type.setRefBaseClassPath("RefBasePath" + i);
		type.getAttribute().addAll(Attribute.setValue());

	}

	private static void setNoOfElements() {

		if (setAdditionalInformation != -1)
			AdditionalInformation.minimum = setAdditionalInformation;

		if (setCopyright != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Copyright.minimum = setCopyright;

		if (setDescription != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Description.minimum = setDescription;

		if (setRevision != -1)
			RevisionImp.minimum = setRevision;

		if (setVersion != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Version.minimum = setVersion;

		if (setAttribute != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Attribute.minimum = setAttribute;

	}

}
