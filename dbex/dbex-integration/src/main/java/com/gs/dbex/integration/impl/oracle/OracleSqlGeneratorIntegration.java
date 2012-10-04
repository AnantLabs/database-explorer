/* ******************************************************************************
 * 	
 * 	Name	: OracleSqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.oracle.OracleSqlGeneratorIntegration
 * 
 * 	Created	: Mar 25, 2012
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

package com.gs.dbex.integration.impl.oracle;

import java.util.Map;

import com.gs.dbex.integration.helper.oracle.OracleSqlGeneratorHelper;
import com.gs.dbex.integration.impl.AbstractSqlGeneratorIntegration;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class OracleSqlGeneratorIntegration extends AbstractSqlGeneratorIntegration {

	private OracleSqlGeneratorHelper oracleSqlGeneratorHelper;
	
	/**
	 * 
	 */
	public OracleSqlGeneratorIntegration() {
		// TODO Auto-generated constructor stub
	}

	public OracleSqlGeneratorHelper getOracleSqlGeneratorHelper() {
		return oracleSqlGeneratorHelper;
	}

	public void setOracleSqlGeneratorHelper(
			OracleSqlGeneratorHelper oracleSqlGeneratorHelper) {
		this.oracleSqlGeneratorHelper = oracleSqlGeneratorHelper;
	}

	@Override
	public String populateInsertValues(Map<String, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	
	
}
