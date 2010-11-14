/**
 * 
 */
package com.gs.dbex.historyMgr.dao;

import java.util.List;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj Das
 *
 */
public interface ConnectionPropertiesDao {

	public ConnectionProperties saveConnectionProperties(ConnectionProperties connectionProperties);
	
	public List<ConnectionProperties> saveAllConnectionProperties(List<ConnectionProperties> connectionPropertiesList);
	
}
