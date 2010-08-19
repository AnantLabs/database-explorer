package com.gs.dbex.integration.xmlbeans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import noNamespace.ConnectionDocument.Connection;
import noNamespace.ConnectionUrlDocument.ConnectionUrl;
import noNamespace.ConnectionsDocument;
import noNamespace.impl.ConnectionsDocumentImpl;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.cfg.DatabaseConfiguration;
import com.gs.utils.io.FileRWUtil;
import com.gs.utils.text.StringUtil;

public class ConnectionPropertiesXmlTransformer {

	private static Logger logger = Logger.getLogger(ConnectionPropertiesXmlTransformer.class);
	
	
	
	public List<ConnectionProperties> readAllConnectionProperties(String xmlText){
		if(logger.isDebugEnabled()){
			logger.debug("readAllConnectionProperties from XML");
		}
		List<ConnectionProperties> list = new ArrayList<ConnectionProperties>();
		if(!StringUtil.hasValidContent(xmlText)){
			return list;
		}
		ConnectionsDocument connectionsDocument = null;
		XmlOptions xmloptions = new XmlOptions();
		xmloptions.setDocumentType(XmlBeans.typeForClass(ConnectionsDocumentImpl.class));
		try {
			connectionsDocument = ConnectionsDocument.Factory.parse(xmlText, xmloptions);
			if(connectionsDocument != null){
				ConnectionsDocument.Connections connections = connectionsDocument.getConnections();
				if(null != connections){
					 Connection[] connectionArray = connections.getConnectionArray();
					 if(null != connectionArray && connectionArray.length > 0){
						 
						 for(Connection connection : connectionArray){
							 if(connection != null){
								 ConnectionProperties connectionProperties = new ConnectionProperties();
								 connectionProperties.setConnectionName(connection.getName());
								 ConnectionUrl connectionUrl = connection.getConnectionUrl();
								 if(connectionUrl != null){
									 connectionProperties.setConnectionUrl(connectionUrl.getUrl());
								 }
								 connectionProperties.setDatabaseType(connection.getDatabaseType());
								 connectionProperties.setDisplayOrder((null != connection.getDisplayOrder() ? connection.getDisplayOrder().intValue() : 0));
								 if(connection.getDatabaseConfiguration() != null){
									 connectionProperties.setDatabaseConfiguration(populateDatabaseConfiguration(connection.getDatabaseConfiguration()));
								 }
								 list.add(connectionProperties);
							 }
						 }
					 }
				}
			}
		} catch (XmlException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	private DatabaseConfiguration populateDatabaseConfiguration(noNamespace.DatabaseConfigurationDocument.DatabaseConfiguration databaseConfiguration) {
		DatabaseConfiguration cfg = new DatabaseConfiguration();
		cfg.setDriverClassName(databaseConfiguration.getDriverClassName());
		cfg.setHostName(databaseConfiguration.getHostName());
		cfg.setPassword(databaseConfiguration.getPassword());
		cfg.setPortNumber((null != databaseConfiguration.getPortNumber() ? databaseConfiguration.getPortNumber().intValue() : 0));
		cfg.setSavePassword(databaseConfiguration.getSavePassword());
		cfg.setSchemaName(databaseConfiguration.getSchemaName());
		cfg.setSidServiceName(databaseConfiguration.getSidServiceName());
		if(databaseConfiguration.getStorageType() != null)
			cfg.setStorageType(databaseConfiguration.getStorageType().toString());
		cfg.setUserName(databaseConfiguration.getUserName());
		return cfg;
	}


}
