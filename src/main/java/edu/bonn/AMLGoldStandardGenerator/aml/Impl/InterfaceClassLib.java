package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class InterfaceClassLib extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setInterfaceClassAttribute = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib> setObject() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib> interfaceClass = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib>();
		for (int j = 0; j < minimum; j++) {

			interfaceClass.add(factory.createCAEXFileInterfaceClassLib());

			setValues(interfaceClass.get(j), j);
		}

		return interfaceClass;

	}

	private static void setValues(edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib type, int i) {
		type.setID("141929fsafa-34-5-5-" + i);
		type.setName("interface class Lib Name " + i);
		type.getInterfaceClass().addAll(InterfaceClass.setObject());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
	}

	private static void setNoOfElements() {

		if (setAdditionalInformation != -1)
			AdditionalInformation.minimum = setAdditionalInformation;

		else if (AdditionalInformation.minimum != 0)
			AdditionalInformation.minimum = 1;

		if (setCopyright != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Copyright.minimum = setCopyright;

		else if (Copyright.minimum != 0)
			Copyright.minimum = 1;

		if (setDescription != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Description.minimum = setDescription;

		else if (Description.minimum != 0)
			Description.minimum = 1;

		if (setRevision != -1)
			RevisionImp.minimum = setRevision;

		else if (RevisionImp.minimum != 0)
			RevisionImp.minimum = 1;

		if (setVersion != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Version.minimum = setVersion;

		else if (Version.minimum != 0)
			Version.minimum = 1;

		if (setInterfaceClassAttribute != -1)
			InterfaceClass.setAttribute = setInterfaceClassAttribute;

		else if (Attribute.minimum != 0)
			Attribute.minimum = 1;

	}

}
