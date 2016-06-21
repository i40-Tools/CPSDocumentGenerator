/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.rdf;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.util.FileManager;

/**
 * @author omar
 *
 */
public class EclassParser {

	/**
	 * @param args
	 */

	private void eClassQuery() {
		// Array of nodes which will store all the classes of RDF graph.
		String output = null;
		// Getting the final RDF and reading all its graph to extract classes.
		Model modelY = FileManager.get()
				.loadModel(getClass().getClassLoader().getResource("eclass_514en.owl").getPath());

		Dataset dataset = DatasetFactory.create();
		dataset.setDefaultModel(modelY);
		dataset.addNamedModel("http://www.example.com/eclass#" + "eclass", modelY);
		String queryString = null;

		// Query to read all the graph values
		try (InputStream res = (EclassParser.class.getResourceAsStream("/eclass.rq"))) {
			try {
				queryString = IOUtils.toString(res);
			} catch (IOException e) {
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		org.apache.jena.query.Query query = QueryFactory.create(queryString);

		try (QueryExecution qexec = QueryExecutionFactory.create(query, dataset)) {
			PrintWriter out = new PrintWriter(new FileWriter("c:\\filename.txt"));
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();

				// Gets all the values of graph in variable
				RDFNode nPredicate = soln.get("predicate"); // Gets predicate
				RDFNode nObject = soln.get("object"); // Gets Object
				RDFNode nSubject = soln.get("subject"); // Gets predicate

				// Gets all rdf classes in array through rdf:type
				output = "[" + nSubject.toString() + " ] " + "[" + nPredicate.toString() + " ] " + "["
						+ nObject.toString() + " ]";

				out.println(output);

			}
			out.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new EclassParser().eClassQuery();

	}

}
