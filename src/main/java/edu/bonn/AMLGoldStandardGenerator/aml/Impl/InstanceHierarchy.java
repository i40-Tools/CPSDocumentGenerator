package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * 
 * @author omar
 *
 */
public class InstanceHierarchy extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setRevision = -1;
	public static int setVersion = -1;
	public static int setInternalElement = -1;
	public static int setInternalElementNested = -1;
	public static int setInternalExternalInterface = -1;
	public static int setInternalInternalLink = -1;
	public static int setInternalMappingObject = -1;
	public static int setInternalRefSemantic = -1;
	public static int setInternalRoleRequirement = -1;
	public static int setInternalSupportedRoleClass = -1;
	public static int setInternalAttribute = -1;

	public static ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy> setValue() {
		// TODO Auto-generated method stub
		setNoOfElements();

		ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy> instance = new ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy>();

		for (int j = 0; j < minimum; j++) {

			instance.add(GenerateAML.factory.createCAEXFileInstanceHierarchy());

			setValues(instance.get(j), j);

		}

		return instance;

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

		if (setInternalElement != -1)
			InternalElement.minimum = setInternalElement;

		else if (InternalElement.minimum != 0)
			InternalElement.minimum = 1;

		if (setInternalElementNested != -1)
			InternalElement.setInternalElementNested = setInternalElementNested;

		if (setInternalAttribute != -1) {
			InternalElement.setAttribute = setInternalAttribute;

		}

		else if (Attribute.minimum != 0)
			Attribute.minimum = 1;

		if (setInternalExternalInterface != -1) {
			InternalElement.setExternalInterface = setInternalExternalInterface;
		} else if (ExternalInterface.minimum != 0) {
			ExternalInterface.minimum = 1;
		}

		if (setInternalInternalLink != -1) {
			InternalElement.setInternalLink = setInternalInternalLink;
		} else if (InternalLinkImp.minimum != 0)
			InternalLinkImp.minimum = 1;

		if (setInternalMappingObject != -1) {
			InternalElement.setMappingObject = setInternalMappingObject;
		}

		else if (MappingObject.minimum != 0) {
			MappingObject.minimum = 1;
		}

		if (setInternalRefSemantic != -1) {
			InternalElement.setRefSemantic = setInternalRefSemantic;
		}

		else if (RefSemantic.minimum != 0) {
			RefSemantic.minimum = 1;
		}

		if (setInternalRoleRequirement != -1) {
			InternalElement.setRoleRequirement = setInternalRoleRequirement;
		}

		else if (RoleRequirement.minimum != 0) {
			RoleRequirement.minimum = 1;
		}

		if (setInternalSupportedRoleClass != -1) {
			InternalElement.setSupportedRoleClass = setInternalSupportedRoleClass;
		}

		else if (SupportedRoleClassImp.minimum != 0) {
			SupportedRoleClassImp.minimum = 1;
		}
	}

	public static void setValues(edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy type, int i) {

		type.setID("0000-" + i);
		type.setName("Instance Name-" + i);
		type.getInternalElement().addAll(InternalElement.setObject());
		type.setCopyright(Copyright.setSingleObject());
		type.setDescription(Description.setSingleObject());
		type.setVersion(Version.setSingleObject());

	}

}
