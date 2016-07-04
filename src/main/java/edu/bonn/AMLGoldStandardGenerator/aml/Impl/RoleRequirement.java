/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import edu.bonn.AMLGolStandardGenerator.aml.InternalElementType.RoleRequirements;

/**
 * @author omar
 *
 */
public class RoleRequirement extends GenericElement {
	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setExternalInterface = -1;

	public static RoleRequirements setSingleObject() {
		// TODO Auto-generated method stub
		setNoOfElements();

		if (minimum != 0) {

			RoleRequirements type = factory.createInternalElementTypeRoleRequirements();
			type.setRefBaseRoleClassPath("RebaseRoleClassPath-");
			type.setCopyright(Copyright.setSingleObject());
			type.setDescription(Description.setSingleObject());
			type.setVersion(Version.setSingleObject());
			type.getExternalInterface().addAll(ExternalInterfaceType.setObject());
			return type;

		} else
			return null;

	}

	public static void setValues(RoleRequirements type, int i) {

		type.setRefBaseRoleClassPath("RebaseRoleClassPath-" + i);

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

		if (setExternalInterface != -1)
			ExternalInterfaceType.minimum = setExternalInterface;

	}

}
