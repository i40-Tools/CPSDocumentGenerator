/**
 * 
 */
package edu.bonn.AMLGoldStandardGenerator.heterogeneity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.bonn.AMLGoldStandardGenerator.aml.Impl.GenerateAML;
import edu.bonn.AMLGoldStandardGenerator.aml.util.AMLConfigManager;
import edu.bonn.AMLGoldStandardGenerator.xml.XmlParser;
import main.CPSDocumentGenerator;

/**
 * This is top level class for Heterogeneity generation, used to generate
 * heterogeneities.
 * 
 * @author omar
 *
 */

public class Heterogeneity extends CPSDocumentGenerator {

	private String fileName;
	protected Element element;
	protected Document doc;
	protected int mod;
	protected XmlParser xml;

	public Heterogeneity(Document doc, int mod) {
		this.doc = doc;
		this.mod = mod + 1;
		xml = new XmlParser();
	}

	/**
	 * This function generates heterogeneities based on the initial seed.
	 * This can only work if the seeds contains heterogeneous data.
	 * 
	 * @param inputFiles
	 * @param mod
	 */
	void heterogeneityGenerator(ArrayList<String> inputFiles, ArrayList<String> mod) {
		String outputFile = null;
		String directory = null;
		int i = 0;
		// reads the input from rdf configuration for type of heterogeneity
		for (int m = 0; m < mod.size(); m++) {

			if (inputName.get(m) != null)
				fileName = inputName.get(m).replace(".aml", "");
			else
				fileName = "default" + m;

			String input = mod.get(m).toString();
			String inputFile = inputFiles.get(m).toString();

			// Twice loop because for integration we need two files.
			int j = 0;
			while (i < 2) {

				// Initialized input file to read its nodes and elements.
				Document doc = new XmlParser().initInput(inputFile);

				// calls the type of heterogeneity function
				j = m;
				directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\";
				String fileHetro = null;

				while (j < mod.size() && inputFile.equals(inputFiles.get(j).toString())) {
					if (fileHetro == null) {
						fileHetro = mod.get(j).toString();

					} else {
						fileHetro += mod.get(j).toString();
					}
					switch (mod.get(j).toString()) {
					// calls granularity heterogeneity generator
					case "M2":
						doc = new GranularityHeterogeneity(doc, i).granularityGenerator();

						// formats the output file
						outputFile = fileName + "-" + fileHetro + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory += mod.get(j).toString() + "-";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						break;

					case "M1":

						doc = new DataTypeHeterogeneity(doc, i).dataTypeGenerator();

						// formats the output file
						outputFile = fileName + "-" + fileHetro + "-" + i + ".aml";
						if (outputPath.get(m) != null) {

							directory += mod.get(j).toString() + "-";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}

						break;

					case "M1.2":

						doc = new DataTypeHeterogeneity(doc, i).dataTypeGenerator();
						// formats the output file
						outputFile = fileName + "-" + "DataTypeTransoformation" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {

							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M1-ValueTransformation" + "\\\\" + "M1.1";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}

						break;

					// calls schematic heterogeneity generator
					case "M3":
						doc = new SchematicHeterogeneity(doc, i).schematicGenerator();

						// formats the output file
						outputFile = fileName + "-" + "Schematic" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M3-Schematic";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						doc = new XmlParser().sortXML(doc, outputFile, directory);

						break;

					// calls Grouping/Aggregation heterogeneity generator

					case "M6":
						doc = new GroupingHeterogeneity(doc, i).groupingGenerator();

						// formats the output file
						outputFile = fileName + "-" + "Grouping" + "-" + i + ".aml";
						if (outputPath.get(m) != null) {
							directory = outputPath.get(m) + "\\\\output\\\\" + fileName + "\\\\"
									+ "M6-Grouping";
						} else {
							System.out.println("output Path not found using default c:\\output");
							directory = "c:\\output";
						}
						break;

					}
					j++;
				}
				// outputs the modified XML data to file and validates
				new XmlParser().formatXML(doc, outputFile, directory);

				validateSchema(outputFile, directory);

				// renames the CAEXFile name attribute wiht new files
				renameFiles(inputName.get(m), outputFile, directory);

				String enlargeFile = outputFile.replace(".aml", "-enlarge.aml");

				// calls the generator configuration
				AMLConfigManager.loadConfigurationUniform();
				// calls the generator
				new GenerateAML().generate(inputFile, directory + "//" + enlargeFile);

				// validates the files
				validateSchema(enlargeFile, directory + "//");

				// rename enlarged files
				renameFiles(inputName.get(m), enlargeFile, directory);

				// make it look pretty.
				new XmlParser().formatXML(new XmlParser().initInput(directory + "//" + enlargeFile),
						enlargeFile, directory);

				i++;
			}

			// saves input file to output folder.
			new XmlParser().formatXML(new XmlParser().initInput(inputFile), inputName.get(m),
					directory);
			m = j - 1;
			i = 0;
		}

	}

	/**
	 * @param inputFile
	 * @param outputfile
	 * @param directory
	 *            Renames final XML files, in the CAEXFilename tag and changes
	 *            its names accordingly.
	 */
	void renameFiles(String inputFile, String outputFile, String directory) {
		FileWriter output;
		String xmlString = null;

		try {
			InputStream res = new FileInputStream(new File(directory + "//" + outputFile));
			xmlString = IOUtils.toString(res);
			output = new FileWriter((directory + "//" + outputFile));

			// renames files accordingly to new file
			xmlString = xmlString.replaceAll(inputFile, outputFile);
			output.write(xmlString);
			output.close();
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
		}
	}

}
