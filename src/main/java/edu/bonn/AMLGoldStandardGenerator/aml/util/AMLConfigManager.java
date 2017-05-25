package edu.bonn.AMLGoldStandardGenerator.aml.util;

import java.util.Random;

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
		
		System.out.println(poissonObs + " Poisson");
		return poissonObs;
	}
	
	static int getUniformDistribution(String range){
		if (!range.contains("-")) {
			return Integer.parseInt(range);
		}
		String[] a = range.split("-");
		
		Random r = new Random();
		int mySample = r.nextInt(Integer.parseInt(a[1]));
		
		System.out.println(mySample + " Value");
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
		RoleClassNested.minimum = getUniformDistribution(FileManager.RoleClassNested());
		SystemUnitClass.minimum = getUniformDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setInternalElement = getUniformDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getUniformDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
	}

	
	public static void loadConfigurationNormal() {
		// optional Attributes
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
		RoleClassNested.minimum = getNormalDistribution(FileManager.RoleClassNested());
		SystemUnitClass.minimum = getNormalDistribution(FileManager.SystemUnitClass());
		SystemUnitClass.setInternalElement = getNormalDistribution(
				FileManager.SystemUnitClasssetInternalElement());
		SystemUnitClass.setSystemUnitClassNested = getNormalDistribution(
				FileManager.SystemUnitClasssetSystemUnitClassNested());
	}

}
