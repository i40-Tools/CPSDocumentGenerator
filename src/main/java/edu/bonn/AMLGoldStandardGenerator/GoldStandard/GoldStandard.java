package edu.bonn.AMLGoldStandardGenerator.GoldStandard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;
import main.ReadFiles;


public class GoldStandard extends ReadFiles {

	public GoldStandard() {

	}

	/**
	 * Adds a better turtle format for the obtained RDF files
	 * 
	 * @throws IOException
	 */
	public void addGoldStandard() throws IOException {

		ArrayList<String> duplicateCheck=new ArrayList<String>();
		PrintWriter GoldStandard = new PrintWriter(
				FileManager.getFilePath() + "PSL/test/GoldStandard.txt");
		LinkedHashSet<String> goldStandardList = new LinkedHashSet<String>();
		for (File file : files) {
			if (file.getName().equals("seed.ttl")) {
				InputStream inputStream = com.hp.hpl.jena.util.FileManager.get().open(file.getAbsolutePath());
				Model model = null;
				model = ModelFactory.createDefaultModel();

				model.read(new InputStreamReader(inputStream), null, "TURTLE");
				StmtIterator iterator = model.listStatements();

				while (iterator.hasNext()) {
					Statement stmt = iterator.nextStatement();
					subject = stmt.getSubject();
					predicate = stmt.getPredicate();
					object = stmt.getObject();

					if (predicate.asNode().getLocalName().equals("hasAttributeName")
							|| predicate.asNode().getLocalName()
									.equals("hasCorrespondingAttributePath")
							|| predicate.asNode().getLocalName().equals("refBaseClassPath")
							|| predicate.asNode().getLocalName().equals("hasFileName")
							|| predicate.asNode().getLocalName().equals("identifier")) {
						if (!object.asLiteral().toString()
								.equals("eClassIRDI^^http://www.w3.org/2001/XMLSchema#string")
								&& !object.asLiteral().toString()
										.equals("eClassClassificationClass^^http://www.w3.org/2001/XMLSchema#string")
								&& !object.asLiteral().toString().equals(
										"eClassVersion^^http://www.w3.org/2001/XMLSchema#string")) {
							goldStandardList.add("aml1:" + object.asLiteral().toString() + "\t"
									+ "aml2:" + object.asLiteral().toString() + "\t" + "1");
						}
					}
				}

				for (String val : goldStandardList) {
					if(!duplicateCheck.contains(val)){
						duplicateCheck.add(val);
					// remove annotation to make it a literal value
					GoldStandard.println(val);
					}
				}
				GoldStandard.close();

			}
		}
	}

	/**
	 * Adds a better turtle format for the obtained RDF files
	 * 
	 * @throws IOException
	 */
	public void convertSimilar() throws IOException {
		ArrayList<String> aml1List = new ArrayList<String>();
		ArrayList<String> aml2List = new ArrayList<String>();
		ArrayList<String> aml1negList = new ArrayList<String>();
		ArrayList<String> aml2negList = new ArrayList<String>();
		ArrayList<String> aml1Values = new ArrayList<String>();
		ArrayList<String> aml2Values = new ArrayList<String>();
		ArrayList<String> aml1negValues = new ArrayList<String>();
		ArrayList<String> aml2negValues = new ArrayList<String>();
		ArrayList<String> duplicateCheck = new ArrayList<String>();

		try (BufferedReader br = new BufferedReader(
				new FileReader(new File(FileManager.getFilePath() + "PSL/test/similar.txt")))) {
			String line;
			while ((line = br.readLine()) != null) {
					String values[] = line.split(",");
					if (line.contains("truth:1")) {
						aml1List.add(values[0].replaceAll("aml1:", ""));
						aml2List.add(values[1].replaceAll("aml2:", ""));

					} else {
						aml1negList.add(values[0].replaceAll("aml1:", ""));
						aml2negList.add(values[1].replaceAll("aml2:", ""));
					}
			}
		}

		PrintWriter similar = new PrintWriter(FileManager.getFilePath() + "PSL/test/similar.txt");

		for (File file : files) {
			InputStream inputStream = com.hp.hpl.jena.util.FileManager.get().open(file.getAbsolutePath());
			model = ModelFactory.createDefaultModel();

			model.read(new InputStreamReader(inputStream), null, "TURTLE");

			if (file.getName().equals("plfile0.ttl")) {

				addAmlValues(aml1List, aml1Values, "aml1:", "hasAttributeName");
				addAmlValues(aml1List, aml1Values, "aml1:", "refBaseClassPath");
				addAmlValues(aml1List, aml1Values, "aml1:", "identifier");
				addAmlValues(aml1List, aml1Values, "aml1:", "hasCorrespondingAttributePath");

				addAmlValues(aml1negList, aml1negValues, "aml1:", "hasAttributeName");
				addAmlValues(aml1negList, aml1negValues, "aml1:", "refBaseClassPath");
				addAmlValues(aml1negList, aml1negValues, "aml1:", "identifier");
				addAmlValues(aml1negList, aml1negValues, "aml1:", "hasCorrespondingAttributePath");

			}

			if (file.getName().equals("plfile1.ttl")) {
				addAmlValues(aml2List, aml2Values, "aml2:", "hasAttributeName");
				addAmlValues(aml2List, aml2Values, "aml2:", "refBaseClassPath");
				addAmlValues(aml2List, aml2Values, "aml2:", "identifier");
				addAmlValues(aml2List, aml2Values, "aml2:", "hasCorrespondingAttributePath");

				addAmlValues(aml2negList, aml2negValues, "aml2:", "hasAttributeName");
				addAmlValues(aml2negList, aml2negValues, "aml2:", "refBaseClassPath");
				addAmlValues(aml2negList, aml2negValues, "aml2:", "identifier");
				addAmlValues(aml2negList, aml2negValues, "aml2:", "hasCorrespondingAttributePath");
			}
		}

		for (int j = 0; j < aml1Values.size(); j++) {
			if (!aml1Values.get(j)
					.equals("aml1:eClassIRDI^^http://www.w3.org/2001/XMLSchema#string")
					&& !aml1Values.get(j)
							.equals("aml1:eClassClassificationClass^^http://www.w3.org/2001/XMLSchema#string")
					&& !aml1Values.get(j).equals(
							"aml1:eClassVersion^^http://www.w3.org/2001/XMLSchema#string")) {

				if (!duplicateCheck
						.contains(aml1Values.get(j) + "\t" + aml2Values.get(j) + "\t" + "1")) {
					duplicateCheck.add(aml1Values.get(j) + "\t" + aml2Values.get(j) + "\t" + "1");

					similar.println(aml1Values.get(j) + "\t" + aml2Values.get(j) + "\t" + "1");
				}
			}
		}

		for (int j = 0; j < aml1negValues.size(); j++) {
			if (!aml1negValues.get(j)
					.equals("aml1:eClassIRDI^^http://www.w3.org/2001/XMLSchema#string")
					|| !aml1negValues.get(j)
							.equals("aml1:eClassClassificationClass^^http://www.w3.org/2001/XMLSchema#string")
					|| !aml1negValues.get(j).equals(
							"aml1:eClassVersion^^http://www.w3.org/2001/XMLSchema#string")) {
				if (!duplicateCheck
						.contains(aml1negValues.get(j) + "\t" + aml2negValues.get(j) + "\t" + "0")
						&& !duplicateCheck.contains(
								aml1negValues.get(j) + "\t" + aml2negValues.get(j) + "\t" + "1")) {
					duplicateCheck
							.add(aml1negValues.get(j) + "\t" + aml2negValues.get(j) + "\t" + "0");

					similar.println(
							aml1negValues.get(j) + "\t" + aml2negValues.get(j) + "\t" + "0");
				}
			}
		}
		similar.close();
	}

}
