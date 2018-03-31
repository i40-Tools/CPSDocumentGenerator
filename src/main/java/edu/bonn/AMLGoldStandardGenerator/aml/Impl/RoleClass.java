package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class RoleClass extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setAttribute = -1;
	public static int setEclassAttribute = -1;
	public static int setExternalInterface = -1;
	public static int setRoleClassNested = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.RoleFamilyType> setObject() {
		// TODO Auto-generated method stub

		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.RoleFamilyType> roleClass = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.RoleFamilyType>();
		setNoOfElements();

		for (int j = 0; j < minimum; j++) {

			roleClass.add(factory.createRoleFamilyType());

			setValues(roleClass.get(j), j);
		}
		return roleClass;

	}

	private static void setValues(edu.bonn.AMLGoldStandardGenerator.aml.RoleFamilyType type, int i) {
		//type.setID(getID());
		type.setName("Role class Name " + getName());
		//type.setRefBaseClassPath("Ref base path" + getName());
		type.getAttribute().addAll(Attribute.setValue());
		type.getAttribute().addAll(AttributeEclass.setValue());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());
		type.getExternalInterface().addAll(ExternalInterface.setObject());
		type.getRoleClass().addAll(RoleClassNested.setObject());

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

		if (setRoleClassNested != -1) {
			RoleClassNested.minimum = setRoleClassNested;
		}

		if (setRevision != -1)
			RevisionImp.minimum = setRevision;

		else if (RevisionImp.minimum != 0)
			RevisionImp.minimum = 1;

		if (setVersion != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Version.minimum = setVersion;

		else if (Version.minimum != 0)
			Version.minimum = 1;

		if (setAttribute != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.Attribute.minimum = setAttribute;

		else if (Attribute.minimum != 0)
			Attribute.minimum = 1;

		if (setEclassAttribute != -1)
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeEclass.minimum = 4;

		else if (AttributeEclass.minimum != 0)
			AttributeEclass.minimum = 1;

		if (setExternalInterface != -1)
			ExternalInterface.minimum=setExternalInterface;
	}

}
