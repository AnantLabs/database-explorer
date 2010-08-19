/**
 * 
 */
package com.gs.dbex.common;

/**
 * @author sabuj.das
 *
 */
public interface DbexCommonConstants {

	String FILE_SEPARATOR = System.getProperty("file.separator");
	String USER_HOME = System.getProperty("user.home");
	String USER_NAME = System.getProperty("user.name");
	
	String PROFILES_PATH_KEY = "EXTERNAL_DATA_PATH";
	String DEFAULT_PROFILES_PATH = "./Profiles";
	
	int MAX_SAVED_CONNECTIONS = 100;
	
	String APPLICATION_DATA_DIR = "./application-data";
	String APPLICATION_PROPERTIES_DIR_NAME = "properties";
	
	String CASTOR_MAPPING_DIR = APPLICATION_DATA_DIR + FILE_SEPARATOR + "mapping";
}
