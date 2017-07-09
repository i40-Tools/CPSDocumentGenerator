package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile;
import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.ExternalReference;
import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InterfaceClassLib;
import edu.bonn.AMLGoldStandardGenerator.aml.ObjectFactory;
import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;

/**
 * @author omar This class generates Automation ML files based on JAXB
 *         configuration
 * 
 **/

public class GenerateAML extends GenericElement{

	public static ObjectFactory factory = new ObjectFactory();
	public CAEXFile caex;
	public Marshaller marshaller;
	public Unmarshaller unmarshaller;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.InstanceHierarchy> instance;
	public ArrayList<ExternalReference> external;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.RoleClassLib> roleclassLib;
	public ArrayList<InterfaceClassLib> interfaceClassLib;
	public ArrayList<edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile.SystemUnitClassLib> systemUnitClassLib;

	
	
	
	public CAEXFile getMulti(String inputPath,ArrayList<File> amlFiles) throws JAXBException {
		//caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
		for(int i=0;i<amlFiles.size();i++){			
		CAEXFile caex2 = (CAEXFile) unmarshaller.unmarshal(new File(amlFiles.get(i).getAbsolutePath()));		
		caex.getInstanceHierarchy().addAll(caex2.getInstanceHierarchy());
		caex.getExternalReference().addAll(caex2.getExternalReference());
		caex.getRoleClassLib().addAll(caex2.getRoleClassLib());
		caex.getInterfaceClassLib().addAll(caex2.getInterfaceClassLib());
		caex.getSystemUnitClassLib().addAll(caex2.getSystemUnitClassLib());
		}
		
//		if (FileManager.ContaminateData().equals("1")) {
//			contaminateAttributeName();
//		}

		return caex;
	}

	
	
	
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
	
		if (FileManager.ContaminateData().equals("1")) {
			contaminateAttributeName();
		}
	
		return caex;
	}

	void contaminateAttributeName(){
		// contaminates Attribute Names
		for (int i = 0; i < roleclassLib.size(); i++) {
			for (int j = 0; j < roleclassLib.get(i).getRoleClass().size(); j++) {
				for (int k = 0; k < roleclassLib.get(i).getRoleClass().get(j).getAttribute()
						.size(); k++) {

					roleclassLib.get(i).getRoleClass().get(j).getAttribute().get(k)
							.setName(getNameSeed());
				}
			}
		}
		
//		for (int i = 0; i < instance.size(); i++) {
//			instance.get(i).getInternalElement().get(i).getAttribute().get(i).setName(getNameSeed());
//		}
//
//		for (int i = 0; i < interfaceClassLib.size(); i++) {
//			interfaceClassLib.get(i).getInterfaceClass().get(i).getAttribute().get(i)
//					.setName(getNameSeed());
//		}
//
//		for (int i = 0; i < systemUnitClassLib.size(); i++) {
//			systemUnitClassLib.get(i).getSystemUnitClass().get(i).getAttribute().get(i)
//					.setName(getNameSeed());
//			systemUnitClassLib.get(i).getSystemUnitClass().get(i).getInternalElement().get(i)
//					.getAttribute().get(i).setName(getNameSeed());
//		}		
//		
		
	}
	
	public CAEXFile setContaminatedData(String inputPath) throws JAXBException {
		caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
		contaminateAttributeName();
		caex.getRoleClassLib().addAll(roleclassLib);
		return caex;
	}
	
	

	public CAEXFile getCaexElementsSplit(String inputPath) throws JAXBException {

		caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
		
		if (FileManager.ContaminateData() == "1") {
			contaminateAttributeName();
		}
		ArrayList<String> name = new ArrayList<String>();
		name.add("RoleClassLib");
		name.add("InstanceHierarchy");
		name.add("ExternalReference");
		name.add("InterfaceClassLib");
		name.add("SystemUnitClassLib");

		// shuffles the arrayList
		long seed = System.nanoTime();
		Collections.shuffle(name, new Random(seed));

		// pick randomly how many elements should be selected
		int randomNum = ThreadLocalRandom.current().nextInt(2, name.size());

		// adds numbers of elements to list so always unique values
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < name.size(); i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);
		
		// randomly selects elements
		for (int i = 0; i < randomNum; i++) {

			// picks a random element
			int random = list.get(i);
			if (name.get(random).contains("Roleclass")) {
				caex.getRoleClassLib().addAll(roleclassLib);
			}
			if (name.get(random).contains("InterfaceClassLib")) {
				caex.getInterfaceClassLib().addAll(interfaceClassLib);
			}
			if (name.get(random).contains("SystemUnitClassLib")) {
				caex.getSystemUnitClassLib().addAll(systemUnitClassLib);
			}
			if (name.get(random).contains("InstanceHierarchy")) {
				caex.getInstanceHierarchy().addAll(instance);
			}
			if (name.get(random).contains("ExternalReference")) {
				caex.getExternalReference().addAll(external);
			}
		}

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
			
			instance = InstanceHierarchy.setValue();
			external = edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference.setObject();
			roleclassLib = RoleClassLib.setObject();
			interfaceClassLib = edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib
					.setObject();
			systemUnitClassLib = SystemUnitClassLib.setObject();

		
			caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));
			caex.getInstanceHierarchy().addAll(InstanceHierarchy.setValue());
			caex.getExternalReference().addAll(
					edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference.setObject());
			caex.getRoleClassLib().addAll(RoleClassLib.setObject());
			caex.getInterfaceClassLib().addAll(
					edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib.setObject());
			caex.getSystemUnitClassLib().addAll(SystemUnitClassLib.setObject());

			
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// marshaller.marshal(caex, new File(outputPath));

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
