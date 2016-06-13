package edu.bonn.AMLGoldStandardGenerator.rdf;

/**
 * @Copyright EIS University of Bonn
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

/**
 * The aim of this class is to load the RDF configuration file to this program
 * containing all the input data
 * 
 * @class ConfigManager
 * @Version = 1.0
 * @Date 4/21/2016
 * @author Irlan
 **/

public class ConfigManager {

	private static ConfigManager manager;
	private static RDFNode literal;
	private static RDFNode predicate;
	public static ArrayList<RDFNode> literals, predicates;
	private static Model model;
	private HashMap<String, String> outputName;
	private ArrayList<String> heterogeneityID;
	private HashMap<String, String> outputPath;
	private String inputPath;
	private String inputName;

	public final static String HET_NAMESPACE = "http://iais.fraunhofer.de/aml#";

	public static ConfigManager getInstance() {

		if (manager == null) {
			manager = new ConfigManager();

		}
		return manager;
	}

	/**
	 * This method load the Configuration file parameters
	 **/
	public void loadConfig(String filePath) {
		try {

			File configFile = new File(filePath);

			if (configFile.isFile() == false) {
				System.out.println("Please especify the configuration file");
				System.exit(0);
			}

			model = ModelFactory.createDefaultModel();
			InputStream inputStream = FileManager.get().open(configFile.getPath());

			// parses an InputStream assuming RDF in Turtle format
			model.read(new InputStreamReader(inputStream), null, "TURTLE");

			literals = new ArrayList<RDFNode>();
			predicates = new ArrayList<RDFNode>();

			StmtIterator iterator = model.listStatements();

			while (iterator.hasNext()) {

				com.hp.hpl.jena.rdf.model.Statement stmt = iterator.nextStatement();

				predicate = stmt.getPredicate();
				predicates.add(predicate);
				literal = stmt.getObject();
				literals.add(literal);

			}
			// sets all values.
			setValues();

		} catch (Exception e) {

			// if exception occurs it means files path is not formatted.
			// formats file paths.
			formatFilePath(filePath);

			// reruns the function after fixing exception.
			loadConfig(filePath);
		}

	}

	/**
	 * This methods sets the values read from RDF configuration file.Due to
	 * duplicate keys ArrayList is used to get all values. This methods gets all
	 * the file paths and sets its values.
	 */
	void setValues() {

		// takes Output fileName
		outputName = new HashMap<String, String>();

		// takes Type of heterogeneity.
		heterogeneityID = new ArrayList<String>();

		// tell outputFolder.
		outputPath = new HashMap<String, String>();

		// holds value of output path for key pair.
		String filePath = null;

		// holds heterogeneity id for key pair.
		String id = null;

		// loops through all predicates and matches its values.
		for (int i = 0; i < predicates.size(); i++) {

			// This makes pair of ID and output Name.
			if (predicates.get(i).toString().equals(HET_NAMESPACE + "hasName")) {

				// checks if id value came first or not.
				if (id == null) {
					System.out.println("failed output came first");
					System.exit(0);
				}

				// puts the associated key pair value with id,outputName.
				outputName.put(id, literals.get(i).toString());

			}

			// This makes pair of ID and output path.
			if (predicates.get(i).toString().equals(HET_NAMESPACE + "hasID")) {

				// checks if output Path came first or not.
				heterogeneityID.add(literals.get(i).toString());
				if (filePath == null) {
					System.out.println("failed ID came first");
					System.exit(0);
				}

				// This makes pair of ID and output path.
				outputPath.put(literals.get(i).toString(), filePath);

				// keeps id value for other pair.
				id = literals.get(i).toString();

			}
			// sets OutputPath
			if (predicates.get(i).toString().equals(HET_NAMESPACE + "hasOutputPath")) {
				filePath = literals.get(i).toString();

			}

			// sets InputPath
			if (predicates.get(i).toString().equals(HET_NAMESPACE + "hasInputPath")) {
				inputPath = literals.get(i).toString();
			}

			// sets Input File name
			if (predicates.get(i).toString().equals("http://www.ebu.ch/metadata/ontologies/ebucore/ebucore#filename")) {
				inputName = literals.get(i).toString();
			}

		}

	}

	/**
	 * This method formats the file path in configuartion file c:/example.aml to
	 * c://example.aml. Otherwise lexical error occurs.
	 * 
	 * @param fileName
	 */

	static void formatFilePath(String fileName) {
		try {
			FileInputStream res = new FileInputStream(new File(fileName));
			String xml = IOUtils.toString(res);

			// replace '/' with //
			xml = xml.replace("\\", "\\\\");
			FileWriter output = new FileWriter((new File(fileName)));
			System.out.println(xml);
			output.write(xml.toString());
			output.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * Provides getters for all the fields.*
	 * 
	 * @return the outputName
	 */
	public HashMap<String, String> getOutputName() {
		return outputName;
	}

	/**
	 * @return the heterogeneityID
	 */
	public ArrayList<String> getHeterogeneityID() {
		return heterogeneityID;
	}

	/**
	 * @return the outputPath
	 */
	public HashMap<String, String> getOutputPath() {
		return outputPath;
	}

	/**
	 * @return the inputPath
	 */
	public String getInputPath() {
		return inputPath;
	}

	/**
	 * @return the inputName
	 */
	public String getInputName() {
		return inputName;
	}

}
