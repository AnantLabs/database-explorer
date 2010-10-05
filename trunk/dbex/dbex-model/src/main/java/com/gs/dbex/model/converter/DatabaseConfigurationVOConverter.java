/**
 * 
 */
package com.gs.dbex.model.converter;

import org.apache.log4j.Logger;

import com.gs.dbex.model.cfg.DatabaseConfiguration;
import com.gs.dbex.model.vo.cfg.DatabaseConfigurationVO;

/**
 * @author Sabuj Das
 *
 */
public class DatabaseConfigurationVOConverter {

	private static final Logger logger = Logger.getLogger(DatabaseConfigurationVOConverter.class);
	
	public static DatabaseConfigurationVO convertModelToVO(
			DatabaseConfiguration databaseConfigurationModel) {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertModelToVO()");
		}
		if(null == databaseConfigurationModel)
			return null;
		DatabaseConfigurationVO databaseConfigurationVO = new DatabaseConfigurationVO();
		databaseConfigurationVO.setConfigurationId(databaseConfigurationModel.getConfigurationId());
		databaseConfigurationVO.setConnectionPropId(databaseConfigurationModel.getConnectionPropId());
		databaseConfigurationVO.setDriverClassName(databaseConfigurationModel.getDriverClassName());
		databaseConfigurationVO.setHostName(databaseConfigurationModel.getHostName());
		databaseConfigurationVO.setPassword(databaseConfigurationModel.getPassword());
		databaseConfigurationVO.setPortNumber(databaseConfigurationModel.getPortNumber());
		databaseConfigurationVO.setSavePassword(databaseConfigurationModel.isSavePassword());
		databaseConfigurationVO.setSchemaName(databaseConfigurationModel.getSchemaName());
		databaseConfigurationVO.setSidServiceName(databaseConfigurationModel.getSidServiceName());
		databaseConfigurationVO.setStorageType(databaseConfigurationModel.getStorageType());
		databaseConfigurationVO.setUserName(databaseConfigurationModel.getUserName());
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertModelToVO()");
		}
		return databaseConfigurationVO;
	}

}
