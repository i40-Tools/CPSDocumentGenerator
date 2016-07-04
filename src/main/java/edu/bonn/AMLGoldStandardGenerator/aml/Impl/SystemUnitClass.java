package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.SystemUnitFamilyType;

/**
 * @author omar
 *
 */
public class SystemUnitClass extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setAttribute = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setExternalInterface = -1;
	public static int setInternalElement = -1;
	public static int setInternalLink = -1;
	public static int setRevision = -1;
	public static int setRoleRequirement = -1;
	public static int setSupportedRoleClass = -1;
	public static int setVersion = -1;

	public static ArrayList<SystemUnitFamilyType> setObject() {
		// TODO Auto-generated method stub
		setNoOfElements();
		ArrayList<SystemUnitFamilyType> systemUnit = new ArrayList<SystemUnitFamilyType>();
		for (int j = 0; j < minimum; j++) {

			systemUnit.add(factory.createSystemUnitFamilyType());

			setValues(systemUnit.get(j), j);
		}

		return systemUnit;

	}

	private static void setValues(SystemUnitFamilyType type, int i) {
		type.setID("11-31-31-31-1-1-" + i);
		type.setName("System Unit class Name " + i);
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
		type.getAttribute().addAll(Attribute.setValue());
		type.getExternalInterface().addAll(ExternalInterfaceType.setObject());
		type.getInternalLink().addAll(InternalLinkImp.setObject());
		type.getSupportedRoleClass().addAll(SupportedRoleClassImp.setObject());
		type.getInternalElement().addAll(InternalElement.setObject());

	}

	private static void setNoOfElements() {

		if (setAdditionalInformation != -1) {
			AdditionalInformation.minimum = setAdditionalInformation;
		}
		if (setAttribute != -1) {
			Attribute.minimum = setAttribute;
		}
		if (setCopyright != -1)
			Copyright.minimum = setCopyright;

		if (setDescription != -1)
			Description.minimum = setDescription;

		if (setExternalInterface != -1) {
			ExternalInterfaceType.minimum = setExternalInterface;
		}
		if (setInternalElement != -1) {
			InternalElement.minimum = setInternalElement;
		}

		if (setInternalLink != -1) {
			InternalLinkImp.minimum = setInternalLink;
		}

		if (setRevision != -1) {
			RevisionImp.minimum = setRevision;
		}

		if (setRoleRequirement != -1) {
			RoleRequirement.minimum = setRoleRequirement;
		}
		if (setSupportedRoleClass != -1) {
			SupportedRoleClassImp.minimum = setSupportedRoleClass;
		}
		if (setVersion != -1)
			Version.minimum = setVersion;

	}

}
