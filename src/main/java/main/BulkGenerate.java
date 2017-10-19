/**
 * 
 */
package main;

import edu.bonn.AMLGoldStandardGenerator.aml.util.AMLConfigManager;
import edu.bonn.AMLGoldStandardGenerator.aml.util.FileManager;

/**
 * @author omar
 *
 */
public class BulkGenerate extends AMLGoldStandardGenerator {

	/**
	 * Running generator in bulk.
	 * k represent heterogeneity.
	 * hetCount represent number of heterogeneities to include.
	 * sCount represent testbed number
	 * @throws Exception
	 */
	static void runBulk() throws Exception {
		int k = 2;
		while (k <= 2) {
			FileManager.hetCount = k - 1;
			num = FileManager.hetCount;
			sCount = 1;
			while (sCount <= 10) {
				if (k == 1) {
					FileManager.filePath = FileManager.getRoot() + "M" + k + "/" + "M1.1/Testbeds-"
							+ sCount + "/";
					// creates folders if not there
					AMLConfigManager.loadConfigurationPoisson();
					generateFiles(FileManager.getFilePath());
					generateGoldStandard();
				} else {

					FileManager.filePath = FileManager.getRoot() + "M" + k + "/Testbeds-" + sCount
							+ "/";
					AMLConfigManager.loadConfigurationPoisson();
					generateFiles(FileManager.getFilePath());
					generateGoldStandard();
					cleanUp();

				}

				System.out.println("Gold Standard Generated for Orignal and Generated Files");
				System.out.println("Finished SuccessFully");
				sCount++;
			}
			k++;
		}
	}

}
