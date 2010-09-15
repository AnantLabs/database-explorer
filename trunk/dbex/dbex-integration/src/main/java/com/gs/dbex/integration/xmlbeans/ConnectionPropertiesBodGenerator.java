/**
 * 
 */
package com.gs.dbex.integration.xmlbeans;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.dbex.bod.dbcfg.ConnectionDocument.Connection;
import com.gs.dbex.bod.dbcfg.ConnectionUrlDocument.ConnectionUrl;
import com.gs.dbex.bod.dbcfg.ConnectionsDocument;
import com.gs.dbex.bod.dbcfg.ConnectionsDocument.Connections;
import com.gs.dbex.bod.dbcfg.DatabaseConfigurationDocument.DatabaseConfiguration;
import com.gs.dbex.bod.dbcfg.StorageTypeDocument;
import com.gs.dbex.bod.dbcfg.StorageTypeEnum;
import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.common.enums.DatabaseStorageTypeEnum;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class ConnectionPropertiesBodGenerator {

	private static Logger logger = Logger.getLogger(ConnectionPropertiesBodGenerator.class);
	
	private static final String XML_TXT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	public String createConnectionPropertiesXmlText(List<ConnectionProperties> propsList){
		if(logger.isDebugEnabled()){
			logger.debug("createConnectionPropertiesXmlText with { " + propsList + " }");
		}
		if(propsList == null){
			return "";
		}
		
		ConnectionsDocument connectionsDocument = ConnectionsDocument.Factory.newInstance();
		Connections connections = connectionsDocument.addNewConnections();
		for (ConnectionProperties properties : propsList) {
			createConnectionProperties(connections, properties);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Generated XML { \n" + connectionsDocument.toString() + " }");
		}
		return XML_TXT + "\n" + connectionsDocument.toString();
	}

	private void createConnectionProperties(Connections connections,
			ConnectionProperties properties) {
		if(null == properties)
			return;
		if(logger.isDebugEnabled()){
			logger.debug("createConnectionProperties with { " + properties.getConnectionName() + " }");
		}
		Connection connection = connections.addNewConnection();
		connection.setName(properties.getConnectionName());
		connection.setDatabaseType(properties.getDatabaseType());
		connection.setDisplayOrder(new BigInteger(
				(properties.getDisplayOrder()!= null ? properties.getDisplayOrder().toString() : "0")));
		DatabaseConfiguration databaseConfiguration = connection.addNewDatabaseConfiguration();
		createDatabaseConfiguration(databaseConfiguration, properties.getDatabaseConfiguration());
		ConnectionUrl connectionUrl = connection.addNewConnectionUrl();
		connectionUrl.setProtocol("");
		connectionUrl.setUrl(properties.getConnectionUrl());
	}

	private void createDatabaseConfiguration(
			DatabaseConfiguration databaseConfiguration,
			com.gs.dbex.model.cfg.DatabaseConfiguration dbCfg) {
		if(dbCfg == null)
			return;
		databaseConfiguration.setDriverClassName(dbCfg.getDriverClassName());
		databaseConfiguration.setHostName(dbCfg.getHostName());
		databaseConfiguration.setPassword(dbCfg.getPassword());
		databaseConfiguration.setPortNumber(new BigInteger(
				(dbCfg.getPortNumber()!=null ? dbCfg.getPortNumber().toString() : "" + DbexCommonContext.getInstance().getDefaultPortNumber())));
		databaseConfiguration.setSavePassword(dbCfg.isSavePassword());
		databaseConfiguration.setSchemaName(dbCfg.getSchemaName());
		databaseConfiguration.setSidServiceName(dbCfg.getSidServiceName());
		databaseConfiguration.setUserName(dbCfg.getUserName());
		if(StringUtil.hasValidContent(dbCfg.getStorageType())){
			DatabaseStorageTypeEnum.getTypeByDesc(dbCfg.getStorageType());
			if(DatabaseStorageTypeEnum.CATALOG_STORAGE.getCode().equals(dbCfg.getStorageType()))
				databaseConfiguration.setStorageType(StorageTypeEnum.CATALOG);
			else if(DatabaseStorageTypeEnum.SCHEMA_STORAGE.getCode().equals(dbCfg.getStorageType()))
				databaseConfiguration.setStorageType(StorageTypeEnum.SCHEMA);
			else
				databaseConfiguration.setStorageType(StorageTypeEnum.CATALOG);
		}
		
	}
	
	
}
