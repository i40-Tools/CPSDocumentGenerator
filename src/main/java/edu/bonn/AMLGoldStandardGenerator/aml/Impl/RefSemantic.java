package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGolStandardGenerator.aml.AttributeType;

/**
 * 
 * @author omar
 *
 */
public class RefSemantic {

	public static int minimum = 1;
	public static int setCopyright = -1;
	public static int setVersion = -1;
	public static int setRevision = -1;
	public static int setDescription = -1;
	public static int setAdditionalInformation = -1;

	public static ArrayList<AttributeType.RefSemantic> setObject() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<AttributeType.RefSemantic> refSemantic = new ArrayList<>();
		for (int j = 0; j < minimum; j++) {

			refSemantic.add(GenerateAML.factory.createAttributeTypeRefSemantic());

			setValues(refSemantic.get(j), j);
		}
		return refSemantic;
	}

	public static void setValues(AttributeType.RefSemantic type, int j) {
		// TODO Auto-generated method stub

		type.setCorrespondingAttributePath("eclass-default");
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
