package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.bonn.AMLGoldStandardGenerator.aml.CAEXFile;
import edu.bonn.AMLGoldStandardGenerator.aml.ObjectFactory;

/**
 * @author omar This class generates Automation ML files based on JAXB
 *         configuration
 * 
 **/



public class GenerateAML {

	/**
	 * @return the caex
	 */
	public CAEXFile getCaex() {
		return caex;
	}


	/**
	 * @return the marshaller
	 */
	public Marshaller getMarshaller() {
		return marshaller;
	}

	public static ObjectFactory factory = new ObjectFactory();	
	public CAEXFile caex;
	Marshaller marshaller;
	
	public void generate(String inputPath, String outputPath) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("edu.bonn.AMLGoldStandardGenerator.aml");
			marshaller = jaxbContext.createMarshaller();
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			caex = (CAEXFile) unmarshaller.unmarshal(new File(inputPath));

			caex.getInstanceHierarchy().addAll(InstanceHierarchy.setValue());
			caex.getExternalReference()
					.addAll(edu.bonn.AMLGoldStandardGenerator.aml.Impl.ExternalReference.setObject());
			caex.getRoleClassLib().addAll(RoleClassLib.setObject());
			caex.getInterfaceClassLib()
					.addAll(edu.bonn.AMLGoldStandardGenerator.aml.Impl.InterfaceClassLib.setObject());
			caex.getSystemUnitClassLib().addAll(SystemUnitClassLib.setObject());

			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		//	marshaller.marshal(caex, new File(outputPath));
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
