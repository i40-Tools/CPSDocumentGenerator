package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.AttributeType;

/**
 * @author omar
 *
 */
public class Attribute extends GenericElement {

	public static int minimum = 1;
	public static int setCopyright = -1;
	public static int setRefSemantic = -1;
	public static int setVersion = -1;
	public static int setRevision = -1;
	public static int setDescription = -1;
	public static int setAdditionalInformation = -1;
	public static int setConstraint = -1;

	public static ArrayList<AttributeType> setValue() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<AttributeType> attribute = new ArrayList<AttributeType>();
		for (int j = 0; j < minimum; j++) {

			attribute.add(GenerateAML.factory.createAttributeType());

			
			setAttributeValues(attribute.get(j), j);
		}

		return attribute;

	}

	public static void setAttributeValues(AttributeType type, int i) {

		type.setID(getID());
		type.setName(getName());		
		type.setUnit("xs:anyUnit");
		type.setAttributeDataType("xs:datatype");
		type.getRefSemantic().addAll(RefSemantic.setObject());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
		type.getConstraint().addAll(AttributeValueRequirements.setValue());

		// Object value = "Value";
		// type.setValue(value);
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
		if (setConstraint != -1)
			AttributeValueRequirements.minimum = setConstraint;

	}

}
