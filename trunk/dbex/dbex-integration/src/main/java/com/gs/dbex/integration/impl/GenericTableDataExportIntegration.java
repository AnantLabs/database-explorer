/* ******************************************************************************
 * 	
 * 	Name	: GenericTableDataExportIntegration.java
 * 	Type	: com.gs.dbex.integration.impl.GenericTableDataExportIntegration
 * 
 * 	Created	: Mar 24, 2012
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

import java.io.File;
import java.sql.SQLException;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class GenericTableDataExportIntegration extends
		AbstractTableDataExportIntegration {

	@Override
	public boolean exportToInsertStatement(String schemaName, String tableName,
			ConnectionProperties connectionProperties, String exportQuery,
			File outputFile) throws DbexException {
		throw new DbexException("The target database is unknown.");
	}

	@Override
	public String getInsertStringFromRawObject(Object rawObject, int columnType)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
