/**
 * 
 */
package com.gs.dbex.application.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.gs.utils.io.IOUtil;

/**
 * @author sabuj.das
 *
 */
public class ApplicationPropertyLoader {

	private static Logger logger = Logger.getLogger(ApplicationPropertyLoader.class);
	
	public static Properties loadProperties(String filename){
		if(logger.isDebugEnabled()){
			logger.debug("Load properties ");
		}
		InputStream inStream = null;
		Properties properties = new Properties();
		try {
			inStream = new FileInputStream(filename);
			properties.load(inStream);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally{
			IOUtil.close(inStream);
		}
		
		return properties;
	}
}
