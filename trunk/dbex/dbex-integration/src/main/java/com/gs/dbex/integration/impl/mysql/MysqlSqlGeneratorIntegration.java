/* ******************************************************************************
 * 	
 * 	Name	: MysqlSqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.mysql.MysqlSqlGeneratorIntegration
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

package com.gs.dbex.integration.impl.mysql;

import java.util.Map;

import com.gs.dbex.integration.helper.mysql.MysqlSqlGeneratorHelper;
import com.gs.dbex.integration.impl.AbstractSqlGeneratorIntegration;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class MysqlSqlGeneratorIntegration extends
		AbstractSqlGeneratorIntegration {

	private MysqlSqlGeneratorHelper mysqlSqlGeneratorHelper;
	
	/**
	 * 
	 */
	public MysqlSqlGeneratorIntegration() {
		// TODO Auto-generated constructor stub
	}

	public MysqlSqlGeneratorHelper getMysqlSqlGeneratorHelper() {
		return mysqlSqlGeneratorHelper;
	}

	public void setMysqlSqlGeneratorHelper(
			MysqlSqlGeneratorHelper mysqlSqlGeneratorHelper) {
		this.mysqlSqlGeneratorHelper = mysqlSqlGeneratorHelper;
	}

	@Override
	public String populateInsertValues(Map<String, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
