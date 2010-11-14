package com.gs.dbex.model.converter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.gs.dbex.common.DbexCommonContext;
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
	
	public static Set<ConnectionPropertiesVO> convertModelsToVOSet(Set<ConnectionProperties> connectionPropertiesModels){
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
	
	public static List<ConnectionPropertiesVO> convertModelsToVOList(List<ConnectionProperties> connectionPropertiesModels){
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertModelsToVOList()");
		}
		if(null == connectionPropertiesModels)
			return null;
		List<ConnectionPropertiesVO> connectionPropertiesVOs = new ArrayList<ConnectionPropertiesVO>();
		for (ConnectionProperties connectionProperties : connectionPropertiesModels) {
			connectionPropertiesVOs.add(convertModelToVO(connectionProperties));
		}
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertModelsToVOList()");
		}
		return connectionPropertiesVOs;
	}
	
	public static List<ConnectionProperties> convertVOsToModelList(List<ConnectionPropertiesVO> connectionPropertiesVos){
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertVOsToModelList()");
		}
		if(null == connectionPropertiesVos)
			return null;
		List<ConnectionProperties> connectionPropertiesModels = new ArrayList<ConnectionProperties>();
		for (ConnectionPropertiesVO connectionProperties : connectionPropertiesVos) {
			connectionPropertiesModels.add(convertVoToModel(connectionProperties));
		}
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertVOsToModelList()");
		}
		return connectionPropertiesModels;
	}

	public static ConnectionProperties convertVoToModel(
			ConnectionPropertiesVO connectionPropertiesVO) {
		if(logger.isDebugEnabled()){
			logger.debug("ENTER::- convertVoToModel()");
		}
		if(null == connectionPropertiesVO){
			return null;
		}
		ConnectionProperties connectionPropertiesModel = new ConnectionProperties();
		Long propId = connectionPropertiesVO.getConnectionPropId();
		if(null != propId){
			connectionPropertiesModel.setConnectionPropId((propId.longValue()==0L) ? null : propId);
		} else{
			connectionPropertiesModel.setConnectionPropId(null);
		}
		Long userId = connectionPropertiesVO.getUserId();
		if(null != userId){
			connectionPropertiesModel.setUserId((userId.longValue()==0L) ? null : userId);
		} else{
			connectionPropertiesModel.setUserId(DbexCommonContext.loggedInUserID);
		}
		connectionPropertiesModel.setConnectionName(connectionPropertiesVO.getConnectionName());
		connectionPropertiesModel.setConnectionUrl(connectionPropertiesVO.getConnectionUrl());
		connectionPropertiesModel.setDatabaseType(connectionPropertiesVO.getDatabaseType());
		connectionPropertiesModel.setDisplayOrder(connectionPropertiesVO.getDisplayOrder());
		connectionPropertiesModel.setDatabaseConfiguration(
				DatabaseConfigurationVOConverter.convertVoToModel(connectionPropertiesVO.getDatabaseConfiguration()));
		connectionPropertiesModel.setVersionNumber(0);
		if(logger.isDebugEnabled()){
			logger.debug("EXIT::- convertVoToModel()");
		}
		return connectionPropertiesModel;
	}
}
