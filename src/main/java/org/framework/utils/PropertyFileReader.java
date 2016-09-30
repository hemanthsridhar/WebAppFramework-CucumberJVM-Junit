package org.framework.utils;

import java.io.FileReader;
import java.util.Properties;


public class PropertyFileReader {

	/*
	 * @author Hemanth.Sridhar
	 */
	
	
	public String propertiesReader(String Filename, String key) {	
		try
		{
		FileReader reader = new FileReader(Filename);
		Properties properties = new Properties();
		properties.load(reader);
		String s =  properties.getProperty(key);
		return s;	
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}