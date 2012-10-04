/* ******************************************************************************
 * 	
 * 	Name	: SqlServerSqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.mssql.SqlServerSqlGeneratorIntegration
 * 
 * 	Created	: Jun 21, 2012
 * 	
 * 	Author	: Sabuj Das [ mailto::sabuj.das@gmail.com ]
 * 
 * -----------------------------------------------------------------------------*
 * 																				*
 * Copyright © Sabuj Das 2010 All Rights Reserved. 								*
 * <br/>No part of this document may be reproduced without written 				*
 * consent from the author.														*
 * 																				*
 ****************************************************************************** */

package com.gs.dbex.integration.impl.mssql;

import java.util.Map;

import com.gs.dbex.integration.helper.mssql.SqlServerSqlGeneratorHelper;
import com.gs.dbex.integration.impl.AbstractSqlGeneratorIntegration;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class SqlServerSqlGeneratorIntegration extends
		AbstractSqlGeneratorIntegration {

	private SqlServerSqlGeneratorHelper sqlServerSqlGeneratorHelper;
	
	/**
	 * 
	 */
	public SqlServerSqlGeneratorIntegration() {
		// TODO Auto-generated constructor stub
	}

	public SqlServerSqlGeneratorHelper getSqlServerSqlGeneratorHelper() {
		return sqlServerSqlGeneratorHelper;
	}

	public void setSqlServerSqlGeneratorHelper(
			SqlServerSqlGeneratorHelper sqlServerSqlGeneratorHelper) {
		this.sqlServerSqlGeneratorHelper = sqlServerSqlGeneratorHelper;
	}

	@Override
	public String populateInsertValues(Map<String, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
