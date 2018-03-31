package edu.bonn.AMLGoldStandardGenerator.aml.util;

import java.util.Random;

import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Attribute;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeEclass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeNameMapping;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.AttributeValueRequirements;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Copyright;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.Description;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InstanceHierarchy;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.InternalElement;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.MappingObject;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClass;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.Impl.RoleClassNested;
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

	static int getPoissonDistribution(String range) {

		if (!range.contains("-")) {
			return Integer.parseInt(range);
		}
		String[] a = range.split("-");
		int mean = (Integer.parseInt(a[0]) + Integer.parseInt(a[1])) / 2;
				
			  double L = Math.exp(-mean);
			  double p = 1.0;
			  int k = 0;

			  do {
			    k++;
			    p *= Math.random();
			  } while (p > L);

			  return k - 1;
				
//		RandomEngine engine = new DRand();
//		Poisson poisson = new Poisson(mean, engine);
//		int poissonObs = poisson.nextInt();
//		
//		return poissonObs;
	}
	
	static int getUniformDistribution(String range){
		if (!range.contains("-")) {
			return Integer.parseInt(range);
		}
		String[] a = range.split("-");
		
		Random r = new Random();
		int mySample = r.nextInt(Integer.parseInt(a[1]));
		
		return mySample;
	}
	
	
	static int getNormalDistribution(String range){
		if (!range.contains("-")) {
			return Integer.parseInt(range);
		}
		String[] a = range.split("-");
		
		Random r = new Random();
		double mySample = r.nextGaussian() * Double.parseDouble(a[0]) + 
				                             Double.parseDouble(a[1]);
		double d = mySample;
		Long L = Math.round(d);
		int toReturn = Integer.valueOf(L.intValue());
		System.out.println(toReturn + " Value");
		return toReturn;
	}

	/**
	 * Default configuration is every element occurrence = 1;
	 */
	public static void loadConfigurationNormal() {
		Copyright.minimum = getNormalDistribution(FileManager.Copyright());
		Description.minimum = getNormalDistribution(FileManager.Description());
		Version.minimum = getNormalDistribution(FileManager.Version());
		AttributeNameMapping.minimum = getNormalDistribution(FileManager.AttributeNameMapping());
		AttributeValueRequirements.minimum = getNormalDistribution(
				FileManager.AttributeValueRequirements()); // disables optional

		// Attribute option
		Attribute.setConstraint = getNormalDistribution(FileManager.AttributesetConstraint());
		MappingObject.minimum = getNormalDistribution(FileManager.MappingObject());
		Attribute.minimum = getNormalDistribution(FileManager.Attribute());

		// usage by static values
		InternalElement.setAttribute = getNormalDistribution(
				FileManager.InternalElementsetAttribute());

		// attribute.
		InstanceHierarchy.minimum = getNormalDistribution(FileManager.InstanceHierarchy());

		// generates 2 instancehierarchy
		InstanceHierarchy.setInternalElement = getNormalDistribution(
				FileManager.InstanceHierarchysetInternalElement());

		// generates 2 internal Element
		InstanceHierarchy.setInternalElementNested = getNormalDistribution(
				FileManager.InstanceHierarchysetInternalElementNested());
		// sets nesting
		InstanceHierarchy.setInternalRoleRequirement = getNormalDistribution(
				FileManager.InstanceHierarchysetInternalRoleRequirement());

		InterfaceClassLib.minimum = getNormalDistribution(FileManager.InterfaceClassLib());
		;
		InterfaceClass.minimum = getNormalDistribution(FileManager.InterfaceClass());
		;
		InterfaceClass.setInterfaceClassNested = getNormalDistribution(
				FileManager.InterfaceClasssetInterfaceClassNested());

		// InterfaceClass.setAttribute = 0; // overrides global value.

		RoleClassLib.minimum = getNormalDistribution(FileManager.RoleClassLib());
		RoleClass.minimum = getNormalDistribution(FileManager.RoleClass());
		RoleClass.setAttribute = getNormalDistribution(FileManager.RoleClassSetAttribute());
		RoleClass.setExternalInterface = getNormalDistribution(
				FileManager.RoleClassSetExternalInterface());

		RoleClassNested.minimum = getNormalDistribution(FileManager.RoleClassNested());

		SystemUnitClassLib.minimum = getNormalDistribution(FileManager.SystemUnitClassLib());
		SystemUnitClass.minimum = getNormalDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setAttribute = getNormalDistribution(
				FileManager.SystemUnitClassSetAttribute());
		SystemUnitClass.setInternalElement = getNormalDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getNormalDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
		ExternalReference.minimum=getNormalDistribution(
				FileManager.SetExternalReference());
	}
	
	/**
	 * Default configuration is every element occurrence = 1;
	 */
	public static void loadConfigurationUniform() {
		// optional Attributes
		Copyright.minimum = getUniformDistribution(FileManager.Copyright());
		Description.minimum = getUniformDistribution(FileManager.Description());
		Version.minimum = getUniformDistribution(FileManager.Version());
		AttributeNameMapping.minimum = getUniformDistribution(FileManager.AttributeNameMapping());
		AttributeValueRequirements.minimum = getUniformDistribution(
				FileManager.AttributeValueRequirements()); // disables optional

		// Attribute option
		Attribute.setConstraint = getUniformDistribution(FileManager.AttributesetConstraint());
		MappingObject.minimum = getUniformDistribution(FileManager.MappingObject());
		Attribute.minimum = getUniformDistribution(FileManager.Attribute());

		// usage by static values
		InternalElement.setAttribute = getUniformDistribution(
				FileManager.InternalElementsetAttribute());

		// attribute.
		InstanceHierarchy.minimum = getUniformDistribution(FileManager.InstanceHierarchy());

		// generates 2 instancehierarchy
		InstanceHierarchy.setInternalElement = getUniformDistribution(
				FileManager.InstanceHierarchysetInternalElement());

		// generates 2 internal Element
		InstanceHierarchy.setInternalElementNested = getUniformDistribution(
				FileManager.InstanceHierarchysetInternalElementNested());
		// sets nesting
		InstanceHierarchy.setInternalRoleRequirement = getUniformDistribution(
				FileManager.InstanceHierarchysetInternalRoleRequirement());

		InterfaceClassLib.minimum = getUniformDistribution(FileManager.InterfaceClassLib());
		;
		InterfaceClass.minimum = getUniformDistribution(FileManager.InterfaceClass());
		;
		InterfaceClass.setInterfaceClassNested = getUniformDistribution(
				FileManager.InterfaceClasssetInterfaceClassNested());

		// InterfaceClass.setAttribute = 0; // overrides global value.

		RoleClassLib.minimum = getUniformDistribution(FileManager.RoleClassLib());
		RoleClass.minimum = getUniformDistribution(FileManager.RoleClass());
		RoleClass.setAttribute = getUniformDistribution(FileManager.RoleClassSetAttribute());
		RoleClass.setExternalInterface = getUniformDistribution(
				FileManager.RoleClassSetExternalInterface());
		RoleClassNested.minimum = getUniformDistribution(FileManager.RoleClassNested());
		
		SystemUnitClassLib.minimum = getUniformDistribution(FileManager.SystemUnitClassLib());
		SystemUnitClass.minimum = getUniformDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setInternalElement = getUniformDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getUniformDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
		SystemUnitClass.setAttribute = getUniformDistribution(
				FileManager.SystemUnitClassSetAttribute());
		ExternalReference.minimum=getNormalDistribution(
				FileManager.SetExternalReference());
	}

	
	public static void loadConfigurationPoisson() {
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
		AttributeEclass.minimum = getPoissonDistribution(FileManager.Attribute());
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
		RoleClass.minimum = getPoissonDistribution(FileManager.RoleClass());
		RoleClass.setAttribute = getPoissonDistribution(FileManager.RoleClassSetAttribute());
		RoleClass.setEclassAttribute = getPoissonDistribution(FileManager.RoleClassSetEclassAttribute());
		RoleClass.setExternalInterface = getPoissonDistribution(
				FileManager.RoleClassSetExternalInterface());

		RoleClassNested.minimum = getPoissonDistribution(FileManager.RoleClassNested());

		SystemUnitClassLib.minimum = getPoissonDistribution(FileManager.SystemUnitClassLib());
		SystemUnitClass.minimum = getPoissonDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setAttribute = getPoissonDistribution(
				FileManager.SystemUnitClassSetAttribute());
		SystemUnitClass.setInternalElement = getPoissonDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getPoissonDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
		ExternalReference.minimum=getNormalDistribution(
				FileManager.SetExternalReference());
	}

}
