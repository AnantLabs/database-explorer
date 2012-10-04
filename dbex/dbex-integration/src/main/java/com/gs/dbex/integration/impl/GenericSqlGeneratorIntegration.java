/* ******************************************************************************
 * 	
 * 	Name	: GenericSqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.GenericSqlGeneratorIntegration
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

package com.gs.dbex.integration.impl;

import java.util.Map;

import com.gs.dbex.integration.helper.GenericSqlGeneratorHelper;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class GenericSqlGeneratorIntegration extends
		AbstractSqlGeneratorIntegration {

	private GenericSqlGeneratorHelper genericSqlGeneratorHelper;
	
	/**
	 * 
	 */
	public GenericSqlGeneratorIntegration() {
		// TODO Auto-generated constructor stub
	}

	public GenericSqlGeneratorHelper getGenericSqlGeneratorHelper() {
		return genericSqlGeneratorHelper;
	}

	public void setGenericSqlGeneratorHelper(
			GenericSqlGeneratorHelper genericSqlGeneratorHelper) {
		this.genericSqlGeneratorHelper = genericSqlGeneratorHelper;
	}

	@Override
	public String populateInsertValues(Map<String, Object> values) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
