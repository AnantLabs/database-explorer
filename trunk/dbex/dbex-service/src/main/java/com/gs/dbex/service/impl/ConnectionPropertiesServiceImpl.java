/**
 * 
 */
package com.gs.dbex.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.historyMgr.dao.ConnectionPropertiesDao;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.ConnectionPropertiesService;

/**
 * @author Sabuj Das
 *
 */
@Transactional
public class ConnectionPropertiesServiceImpl implements
		ConnectionPropertiesService {

	
	private ConnectionPropertiesDao connectionPropertiesDao;
	
	public ConnectionPropertiesServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ConnectionPropertiesDao getConnectionPropertiesDao() {
		return connectionPropertiesDao;
	}

	public void setConnectionPropertiesDao(
			ConnectionPropertiesDao connectionPropertiesDao) {
		this.connectionPropertiesDao = connectionPropertiesDao;
	}



	@Override
	public ConnectionProperties saveConnectionProperties(
			ConnectionProperties connectionProperties) throws DbexException {
		return getConnectionPropertiesDao().saveConnectionProperties(connectionProperties);
	}

	
	@Override
	public List<ConnectionProperties> saveAllConnectionProperties(
			List<ConnectionProperties> connectionPropertiesList) throws DbexException {
		// TODO Auto-generated method stub
		return getConnectionPropertiesDao().saveAllConnectionProperties(connectionPropertiesList);
	}

}
