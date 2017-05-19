package edu.bonn.AMLGoldStandardGenerator.aml.util;

import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
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
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClassNested;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.SystemUnitClass;
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

	static int getPoissonDistribution(String range) {

		if (!range.contains("-")) {
			return Integer.parseInt(range);
		}
		String[] a = range.split("-");
		int mean = (Integer.parseInt(a[0]) + Integer.parseInt(a[1])) / 2;
		RandomEngine engine = new DRand();
		Poisson poisson = new Poisson(mean, engine);
		int poissonObs = poisson.nextInt();

		return poissonObs;
	}

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

		// optional Attributes
		Copyright.minimum = getPoissonDistribution(FileManager.Copyright());
		Description.minimum = getPoissonDistribution(FileManager.Description());
		Version.minimum = getPoissonDistribution(FileManager.Version());
		AttributeNameMapping.minimum = getPoissonDistribution(FileManager.AttributeNameMapping());
		AttributeValueRequirements.minimum = getPoissonDistribution(
				FileManager.AttributeValueRequirements()); // disables optional

		// Attribute option
		Attribute.setConstraint = getPoissonDistribution(FileManager.AttributesetConstraint());
		MappingObject.minimum = getPoissonDistribution(FileManager.MappingObject());
		Attribute.minimum = getPoissonDistribution(FileManager.Attribute());

		// usage by static values
		InternalElement.setAttribute = getPoissonDistribution(
				FileManager.InternalElementsetAttribute());

		// attribute.
		InstanceHierarchy.minimum = getPoissonDistribution(FileManager.InstanceHierarchy());

		// generates 2 instancehierarchy
		InstanceHierarchy.setInternalElement = getPoissonDistribution(
				FileManager.InstanceHierarchysetInternalElement());

		// generates 2 internal Element
		InstanceHierarchy.setInternalElementNested = getPoissonDistribution(
				FileManager.InstanceHierarchysetInternalElementNested());
		// sets nesting
		InstanceHierarchy.setInternalRoleRequirement = getPoissonDistribution(
				FileManager.InstanceHierarchysetInternalRoleRequirement());

		InterfaceClassLib.minimum = getPoissonDistribution(FileManager.InterfaceClassLib());
		;
		InterfaceClass.minimum = getPoissonDistribution(FileManager.InterfaceClass());
		;
		InterfaceClass.setInterfaceClassNested = getPoissonDistribution(
				FileManager.InterfaceClasssetInterfaceClassNested());

		// InterfaceClass.setAttribute = 0; // overrides global value.

		RoleClassLib.minimum = getPoissonDistribution(FileManager.RoleClassLib());
		RoleClassNested.minimum = getPoissonDistribution(FileManager.RoleClassNested());
		SystemUnitClass.minimum = getPoissonDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setInternalElement = getPoissonDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getPoissonDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
	}

}
