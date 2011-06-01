package com.gs.dbex.integration;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.dependency.TableDependency;

public interface DependencyIntegration {

	public TableDependency generateTableDependency(ConnectionProperties connectionProperties, String tableName) throws DbexException ;
	
}
