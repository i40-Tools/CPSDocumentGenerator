
package main;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;

import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;
import uni.bonn.krextor.Krextor;

/**
 * Reads the RDF files and convert them to Datalog or to PSL facts
 * 
 * @author Irlan 28.06.2016
 */
public class ReadFiles {
	public RDFNode object;
	public RDFNode predicate;
	public RDFNode subject;

	protected ArrayList<File> files;

	private LinkedHashSet<String> subjectsToWrite;
	public Model model;
	private PrintWriter documentwriter;
	int number = 0;

	public ReadFiles(){
		files = new ArrayList<File>();
	}


	/**
	 * Read the RDF files of a given path
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public ArrayList<File> readFiles(String path, String type, String type2, String type3)
			throws Exception {
		
		File originalFilesFolder = new File(path);
		if (originalFilesFolder.isDirectory()) {
			for (File amlFile : originalFilesFolder.listFiles()) {
				if (amlFile.isFile()
						&& (amlFile.getName().endsWith(type) || amlFile.getName().endsWith(type2)
								|| amlFile.getName().endsWith(type3))) {
					if (amlFile.getName().endsWith(".aml")) {
						String name = amlFile.getName().replace(".aml", "");
						if (name.endsWith("0") || name.endsWith("1") || name.equals("seed")) {
							files.add(amlFile);
						}
					}

					else if (amlFile.getName().endsWith(".opcua")) {
						String name = amlFile.getName().replace(".opcua", "");
						if (name.endsWith("0") || name.endsWith("1")) {
							files.add(amlFile);
						}
					}

					else if (amlFile.getName().endsWith(".xml")) {
						files.add(amlFile);
					}

					else {
						files.add(amlFile);
					}
				}
			}
		} else {
			System.out.println("Error in the directory that you provided");
			System.exit(0);
		}

		return files;
	}

	
	/**
	 * Converts the file to turtle format based on Krextor
	 * 
	 * @param input
	 * @param output
	 */
	public void convert2RDF() {
		int i = 0;
		Krextor krextor = new Krextor();
		for (File file : files) {			
			if (file.getName().endsWith(".aml")) {
				if (file.getName().equals("seed.aml")) {
					krextor.convertRdf(file.getAbsolutePath(), "aml", "turtle",
							FileManager.getFilePath()+"Generated/" + "seed" + ".ttl");

				} else {
					krextor.convertRdf(file.getAbsolutePath(), "aml", "turtle",
							FileManager.getFilePath()+"Generated/" + "plfile" + i + ".ttl");
				}
			} else {
				krextor.convertRdf(file.getAbsolutePath(), "opcua", "turtle",
						FileManager.getFilePath() + "plfile" + i + ".ttl");
			}
			i++;
		}
	}


	
	
	/**
	 * Adds aml Values
	 * @param amlList
	 * @param amlValue
	 * @param aml
	 * @return
	 */
	protected ArrayList<String> addAmlValues(ArrayList<?> amlList,ArrayList<String> amlValue,String aml,String predicate){	
			for(int i=0;i<amlList.size();i++){	
				StmtIterator iterator = model.listStatements();
				while (iterator.hasNext()) {
					Statement stmt = iterator.nextStatement();
					subject = stmt.getSubject();
					if(subject.asResource().getLocalName().equals(amlList.get(i))){
						
						String value=getValue(subject,predicate);					
						if(value!=null){
						amlValue.add(aml +value);
						break;
						}
				}
			 }
	     }
	return amlValue;
	}
		
	/**
     * get predicate Value
     * @param name
     * @return
     */
	String getValue(RDFNode name, String predicate) {
		String type = null;
		StmtIterator stmts = model.listStatements(name.asResource(), null, (RDFNode) null);
		while (stmts.hasNext()) {
			Statement stmte = stmts.nextStatement();

			if (stmte.getPredicate().asNode().getLocalName().toString().equals(predicate)) {
				type = stmte.getObject().asLiteral().toString();
			}
		}
		return type;
	}
	
	
}