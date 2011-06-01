package com.gs.dbex.service;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.dependency.TableDependency;

public interface DependencyService {

	public TableDependency generateTableDependency(ConnectionProperties connectionProperties, String tableName) throws DbexException; 
	
}
