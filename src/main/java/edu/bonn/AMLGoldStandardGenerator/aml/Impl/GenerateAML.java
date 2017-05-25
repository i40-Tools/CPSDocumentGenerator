package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile;
import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference;
import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.ObjectFactory;

/**
 * @author omar This class generates Automation ML files based on JAXB
 *         configuration
 * 
 **/

public class GenerateAML {

	public static ObjectFactory factory = new ObjectFactory();
	public CAEXFile caex;
	public Marshaller marshaller;
	public Unmarshaller unmarshaller;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy> instance;
	public ArrayList<ExternalReference> external;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.RoleClassLib> roleclassLib;
	public ArrayList<InterfaceClassLib> interfaceClassLib;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.SystemUnitClassLib> systemUnitClassLib;

	/**
	 * @return the caex
	 * @throws JAXBException
	 */
	public CAEXFile getdefault(String inputPath) throws JAXBException {
		caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
		caex.getInstanceHierarchy().addAll(instance);
		caex.getExternalReference().addAll(external);
		caex.getRoleClassLib().addAll(roleclassLib);
		caex.getInterfaceClassLib().addAll(interfaceClassLib);
		caex.getSystemUnitClassLib().addAll(systemUnitClassLib);
		return caex;
	}

	public CAEXFile getCaexElementsSplit(String inputPath) throws JAXBException {
		caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
		caex.getRoleClassLib().addAll(roleclassLib);
		caex.getInterfaceClassLib().addAll(interfaceClassLib);
		caex.getSystemUnitClassLib().addAll(systemUnitClassLib);
		return caex;
	}	

	public CAEXFile getCaex() {
		return caex;
	}

	/**
	 * @return the marshaller
	 */
	public Marshaller getMarshaller() {
		return marshaller;
	}

	public void generate(String inputPath, String outputPath) {
		try {
			JAXBContext jaxbContext = JAXBContext
					.newInstance("edu.bonn.AMLGoldStandardGenerator.aml");
			marshaller = jaxbContext.createMarshaller();
			unmarshaller = jaxbContext.createUnmarshaller();
			caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
			caex.getInstanceHierarchy().addAll(InstanceHierarchy.setValue());
			caex.getExternalReference().addAll(
					edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference.setObject());
			caex.getRoleClassLib().addAll(RoleClassLib.setObject());
			caex.getInterfaceClassLib().addAll(
					edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib.setObject());
			caex.getSystemUnitClassLib().addAll(SystemUnitClassLib.setObject());

			instance = InstanceHierarchy.setValue();
			external = edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference.setObject();
			roleclassLib = RoleClassLib.setObject();
			interfaceClassLib = edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib
					.setObject();
			systemUnitClassLib = SystemUnitClassLib.setObject();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshaller.marshal(caex, new File(outputPath));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
