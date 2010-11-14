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

	public static DatabaseConfiguration convertVoToModel(
			DatabaseConfigurationVO databaseConfigurationVO) {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertVoToModel()");
		}
		if(null == databaseConfigurationVO)
			return null;
		DatabaseConfiguration databaseConfigurationModel = new DatabaseConfiguration();
		Long propId = databaseConfigurationVO.getConnectionPropId();
		if(null != propId){
			databaseConfigurationModel.setConnectionPropId((propId.longValue()==0L) ? null : propId);
		} else{
			databaseConfigurationModel.setConnectionPropId(null);
		}
		Long cfgId = databaseConfigurationVO.getConfigurationId();
		if(null != cfgId){
			databaseConfigurationModel.setConfigurationId((cfgId.longValue()==0L) ? null : cfgId);
		} else{
			databaseConfigurationModel.setConfigurationId(null);
		}
		databaseConfigurationModel.setDriverClassName(databaseConfigurationVO.getDriverClassName());
		databaseConfigurationModel.setHostName(databaseConfigurationVO.getHostName());
		databaseConfigurationModel.setPassword(databaseConfigurationVO.getPassword());
		databaseConfigurationModel.setPortNumber(databaseConfigurationVO.getPortNumber());
		databaseConfigurationModel.setSavePassword(databaseConfigurationVO.isSavePassword());
		databaseConfigurationModel.setSchemaName(databaseConfigurationVO.getSchemaName());
		databaseConfigurationModel.setSidServiceName(databaseConfigurationVO.getSidServiceName());
		databaseConfigurationModel.setStorageType(databaseConfigurationVO.getStorageType());
		databaseConfigurationModel.setUserName(databaseConfigurationVO.getUserName());
		databaseConfigurationModel.setVersionNumber(0);
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertVoToModel()");
		}
		return databaseConfigurationModel;
	}

}
