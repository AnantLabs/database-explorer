/**
 * 
 */
package com.gs.dbex.historyMgr.data;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.historyMgr.ApplicationDataHistoryMgr;
import com.gs.dbex.integration.xmlbeans.ConnectionPropertiesBodGenerator;
import com.gs.dbex.integration.xmlbeans.ConnectionPropertiesXmlTransformer;
import com.gs.dbex.integration.xmlbeans.DriverManagerXmlTransformer;
import com.gs.dbex.integration.xmlbeans.StyleConfigurationXmlTransformer;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.cfg.JdbcDriverConfiguration;
import com.gs.dbex.model.syntax.StyleConfiguration;
import com.gs.utils.io.FileRWUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class ApplicationDataHistoryMgrImpl implements ApplicationDataHistoryMgr {

	private static final Logger logger = Logger.getLogger(ApplicationDataHistoryMgrImpl.class);
	private static DbexCommonContext dbexCommonContext = DbexCommonContext.getInstance();
	
	private ConnectionPropertiesBodGenerator connectionPropertiesBodGenerator;
	private ConnectionPropertiesXmlTransformer connectionPropertiesXmlTransformer;
	private DriverManagerXmlTransformer driverManagerXmlTransformer;
	private StyleConfigurationXmlTransformer styleConfigurationXmlTransformer;
	
	public ApplicationDataHistoryMgrImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public StyleConfigurationXmlTransformer getStyleConfigurationXmlTransformer() {
		return styleConfigurationXmlTransformer;
	}

	public void setStyleConfigurationXmlTransformer(
			StyleConfigurationXmlTransformer styleConfigurationXmlTransformer) {
		this.styleConfigurationXmlTransformer = styleConfigurationXmlTransformer;
	}

	public ConnectionPropertiesBodGenerator getConnectionPropertiesBodGenerator() {
		return connectionPropertiesBodGenerator;
	}

	public void setConnectionPropertiesBodGenerator(
			ConnectionPropertiesBodGenerator connectionPropertiesBodGenerator) {
		this.connectionPropertiesBodGenerator = connectionPropertiesBodGenerator;
	}

	public ConnectionPropertiesXmlTransformer getConnectionPropertiesXmlTransformer() {
		return connectionPropertiesXmlTransformer;
	}

	public void setConnectionPropertiesXmlTransformer(
			ConnectionPropertiesXmlTransformer connectionPropertiesXmlTransformer) {
		this.connectionPropertiesXmlTransformer = connectionPropertiesXmlTransformer;
	}

	public DriverManagerXmlTransformer getDriverManagerXmlTransformer() {
		return driverManagerXmlTransformer;
	}

	public void setDriverManagerXmlTransformer(
			DriverManagerXmlTransformer driverManagerXmlTransformer) {
		this.driverManagerXmlTransformer = driverManagerXmlTransformer;
	}



	/**
	 * This API Loads all the ConnectionProperties into a List from predefined file.
	 */
	public List<ConnectionProperties> loadAllConnectionProperties() {
		return loadAllConnectionProperties(dbexCommonContext.getConnectionConfigFileName());
	}
	
	/**
	 * This API Loads all the ConnectionProperties into a List from a given file.
	 */
	public List<ConnectionProperties> loadAllConnectionProperties(String fileName) {
		if(!StringUtil.hasValidContent(fileName))
			return null;
		String xmlText = FileRWUtil.getContents(new File(fileName));
		return getConnectionPropertiesXmlTransformer().readAllConnectionProperties(xmlText);
	}

	/**
	 * Reads a ConnectionProperties by connection name.
	 */
	public ConnectionProperties getConnectionProperties(String connectionName) {
		return null;
	}
	
	/**
	 * 
	 */
	public boolean saveAllConnectionProperties(
			List<ConnectionProperties> connectionPropertiesList) {
		return saveAllConnectionProperties(connectionPropertiesList, dbexCommonContext.getConnectionConfigFileName());
	}
	
	/**
	 * 
	 */
	public boolean saveAllConnectionProperties(
			List<ConnectionProperties> connectionPropertiesList, String fileName) {
		if(!StringUtil.hasValidContent(fileName) || connectionPropertiesList == null)
			return false;
		String xmlText = getConnectionPropertiesBodGenerator().createConnectionPropertiesXmlText(connectionPropertiesList);
		FileRWUtil.writeAsText(fileName, xmlText);
		return true;
	}

	public List<JdbcDriverConfiguration> getAllJdbcDriverConfiguration() {
		return getAllJdbcDriverConfiguration(dbexCommonContext.getDriverManagerFileName());
	}

	public List<JdbcDriverConfiguration> getAllJdbcDriverConfiguration(
			String fileName) {
		if(!StringUtil.hasValidContent(fileName))
			return null;
		String xmlText = FileRWUtil.getContents(new File(fileName));
		return getDriverManagerXmlTransformer().getAllJdbcDriverConfiguration(xmlText);
	}

	public boolean saveAllJdbcDriverConfiguration(
			List<JdbcDriverConfiguration> driverConfigurations) {
		return saveAllJdbcDriverConfiguration(driverConfigurations, dbexCommonContext.getDriverManagerFileName());
	}

	public boolean saveAllJdbcDriverConfiguration(
			List<JdbcDriverConfiguration> driverConfigurations, String fileName) {
		String xmlText = getDriverManagerXmlTransformer().saveDrivermanagerDetails(driverConfigurations);
		FileRWUtil.writeAsText(fileName, xmlText);
		return true;
	}



	@Override
	public StyleConfiguration getStyleConfiguration(String styleConfigFileName)
			throws DbexException {
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getStyleConfiguration() with fileName:= " + styleConfigFileName);
		}
		if(!StringUtil.hasValidContent(styleConfigFileName)){
			styleConfigFileName = dbexCommonContext.getDefaultSyntaxStyleFileName();
		}
		String xmlText = FileRWUtil.getContents(new File(styleConfigFileName));
		if(!StringUtil.hasValidContent(xmlText)){
			styleConfigFileName = dbexCommonContext.getDefaultSyntaxStyleFileName();
			xmlText = FileRWUtil.getContents(new File(styleConfigFileName));
		}
		if(logger.isDebugEnabled()){
			logger.debug("Read from fileName:= " + styleConfigFileName);
			logger.debug("File Content := ****[ " + xmlText + " ]****");
		}
		return getStyleConfigurationXmlTransformer().getStyleConfiguration(xmlText);
	}
	
	@Override
	public Boolean saveStyleConfiguration(
			StyleConfiguration styleConfiguration, String styleConfigFileName)
			throws DbexException {
		if(!StringUtil.hasValidContent(styleConfigFileName)){
			styleConfigFileName = dbexCommonContext.getDefaultSyntaxStyleFileName();
		}
		String xmlText = getStyleConfigurationXmlTransformer().generateStyleConfigurationXML(styleConfiguration);
		if(!StringUtil.hasValidContent(xmlText)){
			return false;
		}
		return FileRWUtil.writeAsText(styleConfigFileName, xmlText);
	}
}
