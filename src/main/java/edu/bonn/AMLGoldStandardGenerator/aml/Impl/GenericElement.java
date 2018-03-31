package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.ArrayList;
import java.util.Random;

import edu.bonn.AMLGoldStandardGenerator.aml.ObjectFactory;

/**
 * @author omar
 *
 */
public class GenericElement {

	public static ObjectFactory factory = new ObjectFactory();
	int count=0;
	public static ArrayList GoldStandardValue=new ArrayList<String>();
	public static ArrayList GoldStandardValue2=new ArrayList<String>();
	// protected static ArrayList<AttributeType> attribute;
	// generate random id
	static String getID(){
		
		// Generate random id, for example 283952-V8M32
		char[] chars = "0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((20000000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		sb.append("-");
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		sb.append("-");
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}

		sb.append("-");

		for (int i = 0; i < 12; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		
		return sb.toString();
	}

	
	static String getName(){
		// Generate random id, for example 283952-V8M32
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((1 + rnd.nextInt(900000)) + "-");

		for (int i = 0; i < 6; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}


		return sb.toString();
	}

	static String geteClassClassificationClass(){
		// Generate random id, for example 283952-V8M32
		char[] chars = "01234567".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((1 + rnd.nextInt(900000)));

		for (int i = 0; i < 8; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}


		return sb.toString();
	}

	static String geteClassIRDI(){
		
		// Generate random id, for example 283952-V8M32
		char[] chars = "0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		sb.append("-");
		for (int i = 0; i < 1; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		sb.append("---BASIC_1_1#");
		for (int i = 0; i < 2; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}

		sb.append("-");
		chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		for (int i = 0; i < 3; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		chars = "0123456789".toCharArray();
		for (int i = 0; i < 3; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		
		sb.append("#");
		for (int i = 0; i < 3; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		return sb.toString();
	}

	
	
	static String getNameSeed(){
		// Generate random id, for example 283952-V8M32
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((1 + rnd.nextInt(900000)) + "-");

		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}


		return sb.toString();
	}
	
	static String getRef(){
		// Generate random id, for example 283952-V8M32
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder((10000000 + rnd.nextInt(900000)) + "-");
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}
		sb.append("-");
		for (int i = 0; i < 4; i++){
		    sb.append(chars[rnd.nextInt(chars.length)]);
		}


		return sb.toString();
	}
	
	 int getCount(){
		return count++;
	}
	
	
}
