package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class ExternalReference extends GenericElement {
	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference> setObject() {
		// TODO Auto-generated method stub
		setNoOfElements();

		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference> external = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference>();
		for (int j = 0; j < minimum; j++) {

			external.add(factory.createCAEXFileExternalReference());

			setAttributeValues(external.get(j), j);
		}

		return external;

	}

	private static void setAttributeValues(edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference type,
			int i) {
		type.setAlias(getName());
		type.setPath(getName());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());

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
	}

}
