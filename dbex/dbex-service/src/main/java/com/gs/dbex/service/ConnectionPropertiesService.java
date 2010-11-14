/**
 * 
 */
package com.gs.dbex.service;

import java.util.List;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj Das
 *
 */
public interface ConnectionPropertiesService {

	public ConnectionProperties saveConnectionProperties(ConnectionProperties connectionProperties) throws DbexException;
	
	public List<ConnectionProperties> saveAllConnectionProperties(List<ConnectionProperties> connectionProperties) throws DbexException;
	
}
