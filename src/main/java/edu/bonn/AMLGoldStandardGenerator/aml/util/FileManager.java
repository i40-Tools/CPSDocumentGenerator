/**
 * @Copyright EIS University of Bonn
 */

package edu.bonn.AMLGoldStandardGenerator.aml.util;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

/**
 * The aim of this class is to load the RDF configuration file to this program
 * containing all the input data
 * 
 * @class ConfigManager
 * @Version = 1.0
 * @Date 4/21/2016
 * @author Irlan
 **/

public class FileManager {

	private static FileManager manager;
	static Properties prop;
	private static RDFNode literal;
	private static RDFNode predicate;
	private static ArrayList<RDFNode> literals, predicates;
	private static Model model;
	public static String filePath;
	public static int hetCount=0;

	public final static String HET_NAMESPACE = "http://vocab.cs.uni-bonn.de/het#";
	public final static String URI_NAMESPACE = "http://uri4uri.net/vocab.html/#";
	public final static String ONTO_NAMESPACE = "http://www.semanticweb.org/ontologies/2008/11/"
			+ "OntologySecurity.owl#";
	public final static String STO_NAMESPACE = "https://w3id.org/i40/sto#";
	public final static String AML_NAMESPACE = "http://vocab.cs.uni-bonn.de/aml#";

	/**
	 * Get the instance of manager
	 * 
	 * @return manager
	 */
	public static FileManager getInstance() {

		if (manager == null) {
			manager = new FileManager();
		}
		return manager;
	}

	/**
	 * This method load the Configuration file parameters
	 */
	public static Properties loadConfig() {
		prop = new Properties();
		String dir = System.getProperty("user.dir");
		File configFile = new File(dir + "/config.ttl");

		if (configFile.isFile() == false) {
			System.out.println("Please especify the configuration file" + "(config.ttl)");
			System.exit(0);
		}

		if (configFile.length() == 0) {
			System.out.println("The configuration file (config.ttl) is empty");
			System.exit(0);
		}

		model = ModelFactory.createDefaultModel();
		InputStream inputStream = org.apache.jena.util.FileManager.get().open(configFile.getPath());
		model.read(new InputStreamReader(inputStream), null, "TURTLE");
		// parses an InputStream assuming RDF in Turtle format

		literals = new ArrayList<RDFNode>();
		predicates = new ArrayList<RDFNode>();

		StmtIterator iterator = model.listStatements();

		while (iterator.hasNext()) {

			Statement stmt = iterator.nextStatement();

			predicate = stmt.getPredicate();
			predicates.add(predicate);

			literal = stmt.getLiteral();
			literals.add(literal);

		}

		for (int i = 0; i < predicates.size(); ++i) {
			for (int j = 0; j < literals.size(); ++j) {
				String key = predicates.get(j).toString();
				String value = literals.get(j).toString();
				prop.setProperty(key, value);
			}
		}

		return prop;
	}

	/**
	 * Get the general file path where all the files are located
	 * 
	 * @return
	 */
	public static String getFilePath() {
		
		if(filePath!=null){
			return filePath;
		}
		filePath = loadConfig().getProperty(URI_NAMESPACE + "path");
		return filePath;
	}

	/**
	 * Get the general file path where all the files are located
	 * 
	 * @return
	 */
	public static String getOntoURIPath() {
		String filePath = loadConfig().getProperty(URI_NAMESPACE + "URI");
		return filePath;
	}


	public static String getTestDataPath() {
		String filePath = loadConfig().getProperty(URI_NAMESPACE + "testDataPath");
		return filePath;
	}

	/**
	 * Reads the configuration regarding the existence of a training set or not
	 * 
	 * @return true or false
	 */
	public static String getExecutionMethod() {
		String filePath = loadConfig().getProperty(ONTO_NAMESPACE + "Training");
		return filePath;
	}

	/**
	 * Reads the configuration to check whether the ontological predicates will
	 * be used or not
	 * 
	 * @return true or false
	 */
	public static String getOntoPredicates() {
		String filePath = loadConfig().getProperty(ONTO_NAMESPACE + "ontoPredicates");
		return filePath;
	}

	/**
	 * Get the general file path where all the test data files are located
	 * 
	 * @return
	 */
	public static String getTrainDataPath() {
		String filePath = loadConfig().getProperty(URI_NAMESPACE + "trainDataPath");
		return filePath;
	}

	public static String getStandard() {
		String standard = loadConfig().getProperty(STO_NAMESPACE + "Standard");
		return standard;
	}

	public static String Version() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "Version");
		return value;
	}

	public static String Description() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "Description");
		return value;
	}

	public static String Copyright() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "Copyright");
		return value;
	}

	public static String AttributeNameMapping() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "AttributeNameMapping");
		return value;
	}

	public static String AttributeValueRequirements() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "AttributeValueRequirements");
		return value;
	}

	public static String AttributesetConstraint() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "AttributesetConstraint");
		return value;
	}

	public static String MappingObject() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "MappingObject");
		return value;
	}

	public static String Attribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "Attribute");
		return value;
	}

	public static String InstanceHierarchy() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "InstanceHierarchy");
		return value;
	}

	public static String InstanceHierarchysetInternalElement() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "InstanceHierarchysetInternalElement");
		return value;
	}

	public static String InternalElementsetAttribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "InternalElementsetAttribute");
		return value;
	}

	public static String InterfaceClassLib() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "InterfaceClassLib");
		return value;
	}

	public static String InterfaceClass() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "InterfaceClass");
		
		return value;
	}

	public static String InterfaceClasssetAttribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "InterfaceClasssetAttribute");
System.out.println("helo"+value);
		return value;
	}

	public static String RoleClassLib() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClassLib");
		return value;
	}

	public static String RoleClassNested() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClassNested");
		return value;
	}

	public static String SystemUnitClass() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "SystemUnitClass");
		return value;
	}

	public static String SystemUnitClasssetInternalElement() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "SystemUnitClasssetInternalElement");
		return value;
	}

	public static String SystemUnitClasssetSystemUnitClassNested() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "SystemUnitClasssetSystemUnitClassNested");
		return value;
	}

	public static String InstanceHierarchysetInternalElementNested() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "InstanceHierarchysetInternalElementNested");
		return value;
	}

	public static String InstanceHierarchysetInternalRoleRequirement() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "InstanceHierarchysetInternalRoleRequirement");
		return value;
	}
	public static String InterfaceClasssetInterfaceClassNested() {
		String value = loadConfig()
				.getProperty(AML_NAMESPACE + "InterfaceClasssetInterfaceClassNested");
		return value;
	}

	public static String SystemUnitClassLib() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "SystemUnitClassLib");
		return value;
	}
	

	public static String RoleClassSetAttribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClassSetAttribute");
		return value;
	}

	public static String RoleClassSetEclassAttribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClassSetEclassAttribute");
		return value;
	}

	public static String RoleClass() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClass");
		return value;
	}

	public static String SystemUnitClassSetAttribute() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "SystemUnitClassSetAttribute");
		return value;
	
	}
	
	public static String SetExternalReference() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "SetExternalReference");
		return value;
	
	}


	public static String RoleClassSetExternalInterface() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RoleClassSetExternalInterface");
		return value;
	
	}

	public static String getRandomize() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "RandomizeAllSeeds");
		return value;
	
	}
	
	
	public static String ContaminateData() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "ContaminateRoleClassAttribute");
		return value;
	
	}

	public static int getMultiHeterogeneity() {
		String value = loadConfig().getProperty(AML_NAMESPACE + "MultiHeterogeneity");
		//return Integer.parseInt(value);
		if(hetCount!=0){
			return hetCount;
		}
		return AMLConfigManager.getPoissonDistribution(value);
		
	}

	
	public static String getRoot() {
		String value = loadConfig().getProperty(URI_NAMESPACE + "root");
		return value;
	
	}

	
	

}