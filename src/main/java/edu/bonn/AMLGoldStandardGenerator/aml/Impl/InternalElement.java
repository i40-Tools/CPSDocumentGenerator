/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

import edu.bonn.AMLGoldStandardGenerator.aml.InternalElementType;

/**
 * @author omar
 *
 */
public class InternalElement extends GenericElement {

	public static int minimum = 1;
	public static int setAdditionalInformation = -1;
	public static int setAttribute = -1;
	public static int setCopyright = -1;
	public static int setDescription = -1;
	public static int setExternalInterface = -1;
	public static int setInternalElementNested = -1;
	public static int setInternalLink = -1;
	public static int setMappingObject = -1;
	public static int setRefSemantic = -1;
	public static int setRevision = -1;
	public static int setRoleRequirement = -1;
	public static int setSupportedRoleClass = -1;
	public static int setVersion = -1;

	public static ArrayList<InternalElementType> setObject() {
		// TODO Auto-generated method stub
		// setNoOfElements();
		setNoOfElements();
		ArrayList<InternalElementType> internal = new ArrayList<InternalElementType>();
		for (int i = 0; i < minimum; i++) {
			internal.add(GenerateAML.factory.createInternalElementType());
			setValues(internal.get(i), i);
		}

		return internal;

	}

	private static void setValues(InternalElementType internal, int i) {
		// TODO Auto-generated method stub
		internal.setID("0000-" + i);
		internal.setName("AML Object-" + i);
		internal.setRefBaseSystemUnitPath("RefbasePath-" + i);
		internal.getAttribute().addAll(Attribute.setValue());
		internal.getExternalInterface().addAll(ExternalInterfaceType.setObject());
		internal.getInternalLink().addAll(InternalLinkImp.setObject());
		internal.getSupportedRoleClass().addAll(SupportedRoleClassImp.setObject());
		internal.setCopyright(Copyright.setSingleObject());
		internal.setDescription(Description.setSingleObject());
		internal.setVersion(Version.setSingleObject());
		internal.getInternalElement().addAll(InternalElementNested.setObject());
		internal.setRoleRequirements(RoleRequirement.setSingleObject());
		internal.setMappingObject(MappingObject.setSingleObject());
		internal.setChangeMode(factory.createCAEXBasicObject().getChangeMode());

	}

	static void setNoOfElements() {
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
		if (setInternalElementNested != -1) {
			InternalElementNested.minimum = setInternalElementNested;
		}

		if (setInternalLink != -1) {
			InternalLinkImp.minimum = setInternalLink;
		}
		if (setMappingObject != -1) {
			MappingObject.minimum = setMappingObject;
		}
		if (setRefSemantic != -1) {
			edu.bonn.AMLGoldStandardGenerator.aml.Impl.RefSemantic.minimum = setRefSemantic;
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
