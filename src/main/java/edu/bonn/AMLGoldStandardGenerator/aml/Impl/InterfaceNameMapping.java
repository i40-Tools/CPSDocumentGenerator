/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;

/**
 * @author omar
 *
 */
public class InterfaceNameMapping extends GenericElement {

	public static int minimum = 1;
	public static int setCopyright = -1;
	public static int setVersion = -1;
	public static int setRevision = -1;
	public static int setDescription = -1;
	public static int setAdditionalInformation = -1;

	public static ArrayList<edu.bonn.AMLGolStandardGenerator.aml.MappingType.InterfaceNameMapping> setValue() {
		// TODO Auto-generated method stub
		setNoOfElements();

		ArrayList<edu.bonn.AMLGolStandardGenerator.aml.MappingType.InterfaceNameMapping> attribute = new ArrayList<edu.bonn.AMLGolStandardGenerator.aml.MappingType.InterfaceNameMapping>();
		for (int j = 0; j < minimum; j++) {

			attribute.add(GenerateAML.factory.createMappingTypeInterfaceNameMapping());
			setAttributeValues(attribute.get(j), j);
		}

		return attribute;

	}

	public static void setAttributeValues(edu.bonn.AMLGolStandardGenerator.aml.MappingType.InterfaceNameMapping interfaceNameMapping, int i) {

		interfaceNameMapping.setCopyright(Copyright.setSingleObject());
		interfaceNameMapping.setDescription(Description.setSingleObject());
		interfaceNameMapping.setVersion(Version.setSingleObject());
		interfaceNameMapping.setSystemUnitInterfaceName("System Unit interface class" + i);
		interfaceNameMapping.setRoleInterfaceName("Role interface Name" + i);

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
