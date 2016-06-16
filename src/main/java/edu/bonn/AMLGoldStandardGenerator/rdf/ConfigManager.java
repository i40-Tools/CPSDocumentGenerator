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

import org.apache.commons.io.IOUtils;

import com.hp.hpl.jena.n3.turtle.TurtleParseException;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
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
	private static Resource subject;

	public static ArrayList<RDFNode> literals, predicates;
	ArrayList<Resource> subjects;
	private static Model model;
	private ArrayList<String> heterogeneityID;
	private ArrayList<String> outputPath;

	private ArrayList<String> inputPath;
	private ArrayList<String> inputName;
	private ArrayList<String> integratedFile;
	private ArrayList<String> goldStandard;

	/**
	 * @return the integratedFile
	 */
	public ArrayList<String> getIntegratedFile() {
		return integratedFile;
	}

	/**
	 * @return the goldStandard
	 */
	public ArrayList<String> getGoldStandard() {
		return goldStandard;
	}

	boolean flag;

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

			// initializing files
			File configFile = new File(filePath);

			literals = new ArrayList<RDFNode>();

			predicates = new ArrayList<RDFNode>();

			subjects = new ArrayList<Resource>();

			outputPath = new ArrayList<String>();

			inputPath = new ArrayList<String>();

			inputName = new ArrayList<String>();

			heterogeneityID = new ArrayList<String>();

			if (configFile.isFile() == false) {
				System.out.println("Please especify the configuration file");
				System.exit(0);
			}

			model = ModelFactory.createDefaultModel();
			InputStream inputStream = FileManager.get().open(configFile.getPath());

			// parses an InputStream assuming RDF in Turtle format
			model.read(new InputStreamReader(inputStream), null, "TURTLE");

			// gets only the subjects so we can get every triple seperately
			StmtIterator iterator = model.listStatements();

			while (iterator.hasNext()) {

				com.hp.hpl.jena.rdf.model.Statement stmt = iterator.nextStatement();

				subject = stmt.getSubject();
				if (!subjects.contains(subject)) {
					subjects.add(subject);
				}

			}

			// for every subject we gets it triple
			for (int i = 0; i < subjects.size(); i++) {

				String key = null;

				// predicates and literals for only one subject.
				ArrayList<Object> triplePredicates = new ArrayList<>();
				ArrayList<Object> tripleLiterals = new ArrayList<>();

				// reading the subject predicate and object
				StmtIterator stmts = model.listStatements(subjects.get(i), null, (RDFNode) null);

				// goes through its statements
				while (stmts.hasNext()) {

					com.hp.hpl.jena.rdf.model.Statement stmt = stmts.next();
					predicate = stmt.getPredicate();

					predicates.add(predicate);

					triplePredicates.add(predicate);

					literal = stmt.getObject();

					literals.add(literal);

					tripleLiterals.add(literal);

				}

				// our main loop depends on number heterogenity seperated by ","
				for (int j = 0; j < triplePredicates.size(); j++) {

					String uri = getUri(triplePredicates, j);

					if (triplePredicates.get(j).toString().equals(uri + "hasHeterogeneity")) {

						key = tripleLiterals.get(j).toString();
						key = key.replaceAll(uri, "");
						heterogeneityID.add(key);

					}

				}

				// for every heterogenity identified we takes its input and
				// output path
				addValues(triplePredicates, tripleLiterals, "hasOutputPath", heterogeneityID, outputPath);
				addValues(triplePredicates, tripleLiterals, "hasInputPath", heterogeneityID, inputPath);
				addValues(triplePredicates, tripleLiterals, "filename", heterogeneityID, inputName);

			}
			// sets rest of values.
			setValues();

		} catch (TurtleParseException e) {

			if (flag == true) {
				System.exit(0);
			}
			// if exception occurs it means files path is not formatted.
			// formats file paths.
			flag = true;
			formatFilePath(filePath);
			// reruns the function after fixing exception.
			loadConfig(filePath);

		} catch (NullPointerException e) {
			System.out.println("Some issue with configuration file , Please check its fields ");

		}

	}

	/**
	 * This methods sets the values read from RDF configuration file.Due to
	 * duplicate keys ArrayList is used to get all values. This methods gets all
	 * the file paths and sets its values.
	 */

	void setValues() {

		// takes Type of heterogeneity.
		goldStandard = new ArrayList<String>();
		integratedFile = new ArrayList<String>();

		// loops through all predicates and matches its values.
		for (int i = 0; i < predicates.size(); i++) {

			String uri = getUri(predicates, i);

			if (predicates.get(i).toString().equals(uri + "hasResults")) {
				integratedFile.add(literals.get(i).toString());
			}

			if (predicates.get(i).toString().equals(uri + "hasGoldStandard")) {
				goldStandard.add(literals.get(i).toString());
			}
		}

	}

	/**
	 * This function add's input and output path for every heterogeneity
	 * identified. If there are
	 * 
	 * @param predicate
	 * @param literal
	 * @param match
	 * @param key
	 * @param path
	 * @return
	 */

	void addValues(ArrayList<Object> predicate, ArrayList<Object> literal, String match, ArrayList<String> key,
			ArrayList<String> path) {
		for (int j = 0; j < predicate.size(); j++) {
			String uri = getUri(predicate, j);
			if (predicate.get(j).toString().equals(uri + match)) {

				int k = 0;
				while (k < key.size()) {
					path.add(literal.get(j).toString());
					k++;
				}
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

			xml = xml.replaceAll("[\\\\]+", "%");
			xml = xml.replace("%", "\\");
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

	/**
	 * This method returns the uri of current predicate or literal for matching
	 * 
	 * @param predicates
	 * @param index
	 * @return
	 */
	String getUri(ArrayList<?> predicates, int index) {

		String uri = predicates.get(index).toString();

		// removes everything after #
		uri = uri.replaceAll("([#])[A-Za-z0-9 ]+", "#");
		return uri;
	}

	/*
	 * Provides getters for all the fields.*
	 * 
	 * @return the outputName
	 */

	/**
	 * @return the heterogeneityID
	 */
	public ArrayList<String> getHeterogeneityID() {
		return heterogeneityID;
	}

	/**
	 * @return the outputPath
	 */
	public ArrayList<String> getOutputPath() {
		return outputPath;
	}

	/**
	 * @return the inputPath
	 */
	public ArrayList<String> getInputPath() {
		return inputPath;
	}

	/**
	 * @return the inputName
	 */
	public ArrayList<String> getInputName() {
		return inputName;
	}

}
