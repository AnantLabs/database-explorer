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
	
	

	
	
}
