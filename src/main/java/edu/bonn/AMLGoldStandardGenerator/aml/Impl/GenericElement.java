package edu.bonn.AMLGoldStandardGenerator.aml.Impl;

import java.util.Random;

import edu.bonn.AMLGoldStandardGenerator.aml.ObjectFactory;

/**
 * @author omar
 *
 */
public class GenericElement {

	public static ObjectFactory factory = new ObjectFactory();
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
	
	
	
}
