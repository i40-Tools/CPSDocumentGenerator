package edu.bonn.AMLGoldStandardGenerator.aml.util;

import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Attribute;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeNameMapping;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeValueRequirements;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Copyright;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Description;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InstanceHierarchy;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InternalElement;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.MappingObject;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.SystemUnitClass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.SystemUnitClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Version;

/**
 * 
 * @author omar
 *
 */
public class AMLConfigManager {

	/**
	 * This method provides configuration setting for generating Automation ML
	 * files. ALL the Elements can set the inidividual properties using the set
	 * method. By default every element is set to occurance of 1. 0 means the
	 * element is disabled.
	 **/

	public static void loadConfiguration() {
		// TODO Auto-generated method stub
		/** individual properties could be set like this **/

		// Default configuration is every element occurance =1;
		// Use ClassName .set<attribute>= no of value
		// InternalElement.minimum = 2;
		// InternalElement.setAttribute = 2;
		// InstanceHierarchy.minimum = 2;
		// InstanceHierarchy.setInternalElement = 2;
		// ExternalReference.minimum = 1;
		// InterfaceClassLib.minimum= 10;
		// SystemUnitClassLib.minimum= 10;
		// RoleClassLib.minimumR= 20;
		// RoleClass.minimum = 2;
		// InterfaceClass.minimum = 2;

		// configuration can be used by object or directly calling static
		// functions
		InternalElement internal = new InternalElement();
		InstanceHierarchy instance = new InstanceHierarchy();
		InterfaceClassLib interfaceClassLib = new InterfaceClassLib();
		InterfaceClass interfaceClass = new InterfaceClass();
		RoleClassLib roleClassLib = new RoleClassLib();
		RoleClass roleClass = new RoleClass();
		SystemUnitClassLib systemLib = new SystemUnitClassLib();
		SystemUnitClass systemUnit = new SystemUnitClass();

		Copyright.minimum = 0; // disables optional attribute
		Description.minimum = 0; // disables optional attribute
		Version.minimum = 0; // disables optional attribute
		AttributeNameMapping.minimum = 0; // disables optional attribute
		AttributeValueRequirements.minimum = 0; // disables optional
		Attribute.setConstraint = 0; // disables optional attribute globaly
		MappingObject.minimum = 0; // disables optional attribute

		// usage by objects optioanl
		internal.setAttribute = 2;

		instance.minimum = 1;
		instance.setInternalElement = 1;
		instance.setInternalElementNested = 1;
		instance.setInternalRoleRequirement = 0;

		interfaceClassLib.minimum = 2;
		interfaceClass.minimum = 2;
		interfaceClass.setAttribute = 0;

		roleClassLib.minimum = 2;
		roleClass.minimum = 2;
		roleClass.setAttribute = 1;

		SystemUnitClassLib.minimum = 1;
		systemUnit.setExternalInterface = 2;

		// usage by static values

		InternalElement.setAttribute = 2; // Every Internal Element will be 2
											// attribute.
		InstanceHierarchy.minimum = 1; // generates 2 instancehierarchy
		InstanceHierarchy.setInternalElement = 1; // generates 2 internal
													// Element
		InstanceHierarchy.setInternalElementNested = 1; // sets nesting
		InstanceHierarchy.setInternalRoleRequirement = 0; // disables

		InterfaceClassLib.minimum = 2;
		InterfaceClass.minimum = 2;
		InterfaceClass.setAttribute = 0; // overrides global value.

		RoleClassLib.minimum = 2;
		edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClass.setAttribute = 2;

		SystemUnitClass.minimum = 2;
		SystemUnitClass.minimum = 1;
		SystemUnitClass.setInternalElement = 0; // disables
	}

}
