/**
 * 
 */
package com.gs.dbex.integration.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Constraint;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class GenericDatabaseMetadataIntegrationImpl extends
		DatabaseMetadataIntegrationImpl {

	private static Logger logger = Logger.getLogger(GenericDatabaseMetadataIntegrationImpl.class);
	
	@Override
	public Set<String> getAvailableSchemaNames(
			ConnectionProperties connectionProperties, ReadDepthEnum readDepthEnum) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Database readDatabase(ConnectionProperties connectionProperties,
			ReadDepthEnum readDepthEnum) throws DbexException {
		logger.debug("START:: Reading Full database.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		Database database = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			OracleDbGrabber dbGrabber = new OracleDbGrabber();
			database = dbGrabber.grabDatabaseBySchema(connectionProperties, "", readDepthEnum);
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		logger.debug("END:: Reading Full database.");
		return database;
	}

	
	public Schema readSchema(ConnectionProperties connectionProperties,
			String schemaName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Table readTable(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) {
		// TODO Auto-generated method stub
		return null;
	}


	public Set<Constraint> getAllConstraints(ConnectionProperties connectionProperties,
			String schemaName, String tableName, ReadDepthEnum readDepthEnum) throws DbexException {
		
		return null;
	}

	@Override
	public List<String> getSystemFunctions(
			ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("START:: get system functions from DB.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		String systemFunctions = "";
		List<String> systemFunctionList = new ArrayList<String>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(null != connection){
				DatabaseMetaData metaData = connection.getMetaData();
				if(null != metaData){
					systemFunctions = metaData.getSystemFunctions();
				}
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		
		if(StringUtil.hasValidContent(systemFunctions)){
			StringTokenizer tokenizer = new StringTokenizer(systemFunctions, ",");
			while(tokenizer.hasMoreElements()){
				String token = tokenizer.nextToken();
				if(StringUtil.hasValidContent(token)){
					systemFunctionList.add(token);
				}
			}
		}
		
		logger.debug("END:: get system functions from DB.");
		return systemFunctionList;
	}

	@Override
	public List<String> getNumericFunctions(
			ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("START:: get Numeric functions from DB.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		String systemFunctions = "";
		List<String> systemFunctionList = new ArrayList<String>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(null != connection){
				DatabaseMetaData metaData = connection.getMetaData();
				if(null != metaData){
					systemFunctions = metaData.getNumericFunctions();
				}
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		
		if(StringUtil.hasValidContent(systemFunctions)){
			StringTokenizer tokenizer = new StringTokenizer(systemFunctions, ",");
			while(tokenizer.hasMoreElements()){
				String token = tokenizer.nextToken();
				if(StringUtil.hasValidContent(token)){
					systemFunctionList.add(token);
				}
			}
		}
		
		logger.debug("END:: get Numeric functions from DB.");
		return systemFunctionList;
	}

	@Override
	public List<String> getStringFunctions(
			ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("START:: get String functions from DB.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		String systemFunctions = "";
		List<String> systemFunctionList = new ArrayList<String>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(null != connection){
				DatabaseMetaData metaData = connection.getMetaData();
				if(null != metaData){
					systemFunctions = metaData.getStringFunctions();
				}
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		
		if(StringUtil.hasValidContent(systemFunctions)){
			StringTokenizer tokenizer = new StringTokenizer(systemFunctions, ",");
			while(tokenizer.hasMoreElements()){
				String token = tokenizer.nextToken();
				if(StringUtil.hasValidContent(token)){
					systemFunctionList.add(token);
				}
			}
		}
		
		logger.debug("END:: get String functions from DB.");
		return systemFunctionList;
	}

	@Override
	public List<String> getTimeDateFunctions(
			ConnectionProperties connectionProperties) throws DbexException {
		logger.debug("START:: get TimeDate functions from DB.");
		if(connectionProperties == null){
			throw new DbexException(ErrorCodeConstants.CANNOT_CONNECT_DB);
		}
		Connection connection = null; 
		String functions = "";
		List<String> functionList = new ArrayList<String>();
		try {
			connection = connectionProperties.getDataSource().getConnection();
			if(null != connection){
				DatabaseMetaData metaData = connection.getMetaData();
				if(null != metaData){
					functions = metaData.getTimeDateFunctions();
				}
			}
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DbexException(null, e.getMessage());
		} finally {
			JdbcUtil.close(connection);
		}
		
		if(StringUtil.hasValidContent(functions)){
			StringTokenizer tokenizer = new StringTokenizer(functions, ",");
			while(tokenizer.hasMoreElements()){
				String token = tokenizer.nextToken();
				if(StringUtil.hasValidContent(token)){
					functionList.add(token);
				}
			}
		}
		
		logger.debug("END:: get TimeDate functions from DB.");
		return functionList;
	}

	
}
