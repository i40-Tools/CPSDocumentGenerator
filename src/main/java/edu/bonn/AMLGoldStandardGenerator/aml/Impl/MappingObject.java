package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGoldStandardGenerator.aml.InternalElementType.RoleRequirements;
import edu.bonn.AMLGoldStandardGenerator.aml.MappingType;

/**
 * @author omar
 *
 */
public class MappingObject extends GenericElement {
	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setAttributeNameMapping = -1;

	public static MappingType setSingleObject() {
		// TODO Auto-generated method stub
		setNoOfElements();

		if (minimum != 0) {

			MappingType type = factory.createMappingType();
			type.setCopyright(Copyright.setSingleObject());
			type.setDescription(Description.setSingleObject());
			type.setVersion(Version.setSingleObject());
			type.getAttributeNameMapping().addAll(AttributeNameMapping.setValue());
			type.getInterfaceNameMapping().addAll(InterfaceNameMapping.setValue());
			return type;

		} else
			return null;

	}

	public static void setValues(RoleRequirements type, int i) {

		type.setRefBaseRoleClassPath(getName());

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
		if (setAttributeNameMapping != -1)
			AttributeNameMapping.minimum = setAttributeNameMapping;

	}
}
