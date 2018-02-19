package com.wissen.utility;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
	public static Properties PROPERTIES;
	public static InputStream inputStream=null;

	static{
		try {
			inputStream=new FileInputStream("properties/configuration.properties");
			PROPERTIES = new Properties();
			PROPERTIES.load(inputStream);		
		} 
		catch (Exception e) {			
			e.printStackTrace();
		} 
		
	}
}
