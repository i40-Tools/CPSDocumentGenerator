package edu.bonn.AMLGoldStandardGenerator.GoldStandard;

import java.io.File;
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

import main.ReadFiles;


public class GoldStandard extends ReadFiles {

	public GoldStandard() {

	}

	/**
	 * Adds a better turtle format for the obtained RDF files
	 * 
	 * @throws IOException
	 */
	public void addGoldStandard(String path) throws IOException {

		ArrayList<String> duplicateCheck=new ArrayList<String>();
		PrintWriter GoldStandard = new PrintWriter(
				path + "PSL/test/GoldStandard.txt");
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


}
