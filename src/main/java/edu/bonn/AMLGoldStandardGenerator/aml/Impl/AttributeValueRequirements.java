package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.AttributeValueRequirementType;
import edu.bonn.AMLGoldStandardGenerator.aml.AttributeValueRequirementType.OrdinalScaledType;

/**
 * @author omar
 *
 */
public class AttributeValueRequirements extends GenericElement {

	public static int minimum = 1;
	public static int setCopyright = -1;
	public static int setRefSemantic = -1;
	public static int setVersion = -1;
	public static int setRevision = -1;
	public static int setDescription = -1;
	public static int setAdditionalInformation = -1;

	public static ArrayList<AttributeValueRequirementType> setValue() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<AttributeValueRequirementType> attribute = new ArrayList<AttributeValueRequirementType>();
		for (int j = 0; j < minimum; j++) {

			attribute.add(GenerateAML.factory.createAttributeValueRequirementType());

			setAttributeValues(attribute.get(j), j);

		}

		return attribute;

	}

	public static void setAttributeValues(AttributeValueRequirementType type, int i) {

		type.setName(getName());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
		OrdinalScaledType ordinary = factory.createAttributeValueRequirementTypeOrdinalScaledType();
		ordinary.setRequiredMaxValue("10");
		ordinary.setRequiredMinValue("1");
		ordinary.setRequiredValue("5");
		type.setOrdinalScaledType(ordinary);

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
		if (setRefSemantic != -1)
			RefSemantic.minimum = setRefSemantic;
	}

}
