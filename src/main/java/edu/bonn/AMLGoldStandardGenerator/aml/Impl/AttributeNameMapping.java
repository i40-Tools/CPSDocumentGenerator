package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class AttributeNameMapping extends GenericElement {

	public static int minimum = 1;
	public static int setCopyright = -1;
	public static int setVersion = -1;
	public static int setRevision = -1;
	public static int setDescription = -1;
	public static int setAdditionalInformation = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.MappingType.AttributeNameMapping> setValue() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.MappingType.AttributeNameMapping> attribute = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.MappingType.AttributeNameMapping>();
		for (int j = 0; j < minimum; j++) {

			attribute.add(GenerateAML.factory.createMappingTypeAttributeNameMapping());
			setAttributeValues(attribute.get(j), j);
		}

		return attribute;

	}

	public static void setAttributeValues(edu.bonn.AMLGoldStandardGenerator.aml.MappingType.AttributeNameMapping type,
			int i) {

		type.setRoleAttributeName(getName());
		type.setSystemUnitAttributeName(getName());
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
