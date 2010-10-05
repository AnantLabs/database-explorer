package com.gs.dbex.model.converter;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;

/**
 * @author Sabuj Das
 *
 */
public class ConnectionPropertiesVOConverter {

	private static final Logger logger = Logger.getLogger(ConnectionPropertiesVOConverter.class);
	
	public static ConnectionPropertiesVO convertModelToVO(ConnectionProperties connectionPropertiesModel){
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertModelToVO()");
		}
		if(null == connectionPropertiesModel)
			return null;
		ConnectionPropertiesVO connectionPropertiesVO = new ConnectionPropertiesVO();
		connectionPropertiesVO.setConnectionPropId(connectionPropertiesModel.getConnectionPropId());
		connectionPropertiesVO.setUserId(connectionPropertiesModel.getUserId());
		connectionPropertiesVO.setConnectionName(connectionPropertiesModel.getConnectionName());
		connectionPropertiesVO.setConnectionUrl(connectionPropertiesModel.getConnectionUrl());
		connectionPropertiesVO.setDatabaseType(connectionPropertiesModel.getDatabaseType());
		connectionPropertiesVO.setDisplayOrder(connectionPropertiesModel.getDisplayOrder());
		connectionPropertiesVO.setDatabaseConfiguration(DatabaseConfigurationVOConverter.convertModelToVO(connectionPropertiesModel.getDatabaseConfiguration()));
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertModelToVO()");
		}
		return connectionPropertiesVO;
	}
	
	public static Set<ConnectionPropertiesVO> convertModelsToVOs(Set<ConnectionProperties> connectionPropertiesModels){
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertModelsToVOs()");
		}
		if(null == connectionPropertiesModels)
			return null;
		Set<ConnectionPropertiesVO> connectionPropertiesVOs = new HashSet<ConnectionPropertiesVO>();
		for (ConnectionProperties connectionProperties : connectionPropertiesModels) {
			connectionPropertiesVOs.add(convertModelToVO(connectionProperties));
		}
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertModelsToVOs()");
		}
		return connectionPropertiesVOs;
	}
}
