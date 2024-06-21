package com.MRP.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetFile {
	private final static String basePath = "src/main/resources/";
	
	
	//Loading Properties for getting the Values from Files
	public static Properties loadProperty(String fileName) throws IOException {		
		Properties prop = new Properties();
		InputStream input = new FileInputStream(basePath+fileName);
		prop.load(input);		
		return prop;
	}

	
	//Loading a file
	public static File getFile(String fileName) {
		File file = new File(basePath+fileName);
		return file;
	}
}
