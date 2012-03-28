/* ******************************************************************************
 * 	
 * 	Name	: TableDataExportService.java
 * 	Type	: com.gs.dbex.service.TableDataExportService
 * 
 * 	Created	: Mar 18, 2012
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

package com.gs.dbex.service;

import com.gs.dbex.common.enums.TableDataExportTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public interface TableDataExportService {

	String BEAN_NAME = "tableDataExportService";
	
	public boolean exportData(String schemaName, String tableName, TableDataExportTypeEnum exportTypeEnum,
			String outputFileName, String exportQuery, ConnectionProperties connectionProperties) throws DbexException;
	
}
