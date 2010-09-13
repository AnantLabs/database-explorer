/**
 * 
 */
package com.gs.dbex.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

/**
 * @author sabuj.das
 *
 */
public final class DbexCommonContext implements DbexCommonConstants{

	private static DbexCommonContext instance;
	
	private DbexCommonContext() {
		initContext();
	}

	public static DbexCommonContext getInstance() {
		if(instance == null)
			instance = new DbexCommonContext();
		return instance;
	}
	
	/* ------------  Context Variables  ------------------------------------ */
	
	public final Map<String, String> APP_PROPERTIES_MAP = new HashMap<String, String>();
	private int defaultPortNumber = 1521;
	private String defaultHostName = "localhost";
	public ApplicationContext applicationSpringContext;
	
	/* -----------  Utility methods  --------------------------------------- */
	private void initContext() {
		APP_PROPERTIES_MAP.put(PROFILES_PATH_KEY, DEFAULT_PROFILES_PATH);
	}

	
	/* -----------  get-set methods  --------------------------------------- */
	
	public String getProfilesDirName(){
		return APP_PROPERTIES_MAP.get(PROFILES_PATH_KEY);
	}
	
	public void setDataDirName(String dir){
		APP_PROPERTIES_MAP.put(PROFILES_PATH_KEY, dir);
	}

	public String getApplicationDataDir() {
		return getUserDataPath() + FILE_SEPARATOR + "application";
	}

	public String getConnectionConfigFileName() {
		return getApplicationDataDir() + FILE_SEPARATOR + "connection-properties.xml";
	}
	
	public String getDriverManagerFileName() {
		return getApplicationDataDir() + FILE_SEPARATOR + "jdbc-driver-manager.xml";
	}

	public String getLocalHistoryPath() {
		return getUserDataPath() + FILE_SEPARATOR + "localHistory";
	}

	public String getUserDataPath() {
		return getProfilesDirName() + FILE_SEPARATOR + USER_NAME;
	}

	public String getSyntaxDataPath(){
		return getApplicationDataDir() + FILE_SEPARATOR + "syntax";
	}
	
	public String getSyntaxFileName(){
		return getSyntaxDataPath() + FILE_SEPARATOR + "sql-syntax-style.xml";
	}
	
	public int getDefaultPortNumber() {
		return defaultPortNumber;
	}

	public void setDefaultPortNumber(int defaultPortNumber) {
		this.defaultPortNumber = defaultPortNumber;
	}

	public String getDefaultHostName() {
		return defaultHostName;
	}

	public void setDefaultHostName(String defaultHostName) {
		this.defaultHostName = defaultHostName;
	}
	
	public String getApplicationPropertiesDir(){
		return APPLICATION_DATA_DIR + FILE_SEPARATOR + APPLICATION_PROPERTIES_DIR_NAME;
	}
	
	public String getErrorMsgConstFileName() {
		return getApplicationPropertiesDir() + FILE_SEPARATOR + "ErrorMessage.properties";
	}
	
	public String getDefaultSyntaxStyleFileName(){
		return APPLICATION_DATA_DIR + FILE_SEPARATOR + "syntax" + FILE_SEPARATOR + "default-syntax-style.xml";
	}
}
