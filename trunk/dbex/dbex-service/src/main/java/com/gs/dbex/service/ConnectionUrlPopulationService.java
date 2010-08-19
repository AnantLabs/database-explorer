/**
 * 
 */
package com.gs.dbex.service;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public interface ConnectionUrlPopulationService {

	public String populateConnectionURL(ConnectionProperties connectionProperties) throws DbexException;
	
	public ConnectionProperties populateConnectionProperties(String url) throws DbexException;
	
}
