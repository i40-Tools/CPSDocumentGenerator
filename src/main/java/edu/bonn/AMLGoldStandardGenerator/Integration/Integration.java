package edu.bonn.AMLGoldStandardGenerator.Integration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Integration {

	public static void main(String[] args) {

		try (BufferedReader br = new BufferedReader(new FileReader("c:\\output.txt"))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			System.out.println(everything);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
