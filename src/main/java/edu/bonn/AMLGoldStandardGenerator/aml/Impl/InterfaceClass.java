package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class InterfaceClass extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setAttribute = -1;
	public static int setInterfaceClassNested = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.InterfaceFamilyType> setObject() {
		// TODO Auto-generated method stub

		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.InterfaceFamilyType> interfaceClass = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.InterfaceFamilyType>();

		setNoOfElements();
		for (int j = 0; j < minimum; j++) {

			interfaceClass.add(factory.createInterfaceFamilyType());

			setValues(interfaceClass.get(j), j);
		}

		return interfaceClass;

	}

	private static void setValues(edu.bonn.AMLGoldStandardGenerator.aml.InterfaceFamilyType type, int i) {
		type.setID(getID());
		type.setName(getName());
		type.setRefBaseClassPath(getName());
		type.getAttribute().addAll(Attribute.setValue());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
		type.getInterfaceClass().addAll(InterfaceClassNested.setObject());

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

		if (setInterfaceClassNested != -1)
			InterfaceClassNested.minimum = setInterfaceClassNested;

		if (setAttribute != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Attribute.minimum = setAttribute;

	}

}
