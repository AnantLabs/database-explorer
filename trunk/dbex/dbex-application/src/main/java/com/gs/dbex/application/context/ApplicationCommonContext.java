/**
 * 
 */
package com.gs.dbex.application.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * A singleton class to hold the runtime context
 * 
 * @author sabuj
 *
 */
public class ApplicationCommonContext{

	private static final String DRIVER_MGR_DIR = "driver-manager/";
	private static final String JDBC_DRIVER_DIR = DRIVER_MGR_DIR + "jdbc-driver/";
	private static final String DRIVER_MGR_DATA_FILE = "jdbc-driver-manager.xml";
	private static final String USER_DATA_DIR = "user_data/";
	private static final String APP_CONF_DIR = "app-conf/";

	private static ApplicationCommonContext instance;
	
	private ApplicationCommonContext(){}

	public static ApplicationCommonContext getInstance() {
		if(instance == null)
			instance = new ApplicationCommonContext();
		
		return instance;
	}
	
	protected void finalize() throws Throwable {
		resetContext();
		super.finalize();
	}
	
	public static void resetContext(){
		instance = new ApplicationCommonContext();
	}
	
	/* -----  Context Constants   ------------------------------------------ */
	private final String errorMsgConstFileName = ApplicationConstants.DEFAULT_APP_DATA_DIR
		+ "properties/ErrorMessage.properties";
	private final Properties errorMessageProperties = ApplicationPropertyLoader.loadProperties(getErrorMsgConstFileName());
	private final Map<String, ConnectionProperties> connectionPropertiesMap = new HashMap<String, ConnectionProperties>();
	
	
	/* -----  Context Variables   ------------------------------------------ */
	
	
	private String applicationDataDirName = ApplicationConstants.DEFAULT_APP_DATA_DIR;
	private String driverLoaderLastAccessedDirName = "./";
	private String driverManagerDir = getApplicationDataDirName() + DRIVER_MGR_DIR;
	private String jdbcDriverDir = getApplicationDataDirName() + JDBC_DRIVER_DIR;
	private String driverMgrDataFileName = getDriverManagerDir() + DRIVER_MGR_DATA_FILE;
	
	
	
	/* -----  get/set methods for Context Variables  ----------------------- */
	
	public String getApplicationDataDirName() {
		return applicationDataDirName;
	}

	public void setApplicationDataDirName(String applicationDataDirName) {
		this.applicationDataDirName = applicationDataDirName;
	}

	public String getDriverManagerDir() {
		return driverManagerDir;
	}

	public String getJdbcDriverDir() {
		return jdbcDriverDir;
	}

	public String getDriverMgrDataFileName() {
		return driverMgrDataFileName;
	}

	public String getDriverLoaderLastAccessedDirName() {
		return driverLoaderLastAccessedDirName;
	}

	public void setDriverLoaderLastAccessedDirName(
			String driverLoaderLastAccessedDirName) {
		this.driverLoaderLastAccessedDirName = driverLoaderLastAccessedDirName;
	}

	public String getErrorMsgConstFileName() {
		return errorMsgConstFileName;
	}


	public Properties getErrorMessageProperties() {
		return errorMessageProperties;
	}

	public Map<String, ConnectionProperties> getConnectionPropertiesMap() {
		return connectionPropertiesMap;
	}



}
