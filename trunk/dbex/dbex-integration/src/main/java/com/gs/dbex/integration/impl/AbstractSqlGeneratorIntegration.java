/* ******************************************************************************
 * 	
 * 	Name	: AbstractSqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.AbstractSqlGeneratorIntegration
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

package com.gs.dbex.integration.impl;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.SqlGeneratorIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public abstract class AbstractSqlGeneratorIntegration implements SqlGeneratorIntegration {

	/**
	 * 
	 */
	public AbstractSqlGeneratorIntegration() {
		// TODO Auto-generated constructor stub
	}

}
