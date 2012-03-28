/* ******************************************************************************
 * 	
 * 	Name	: TableDataExportIntegration.java
 * 	Type	: com.gs.dbex.integration.TableDataExportIntegration
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

package com.gs.dbex.integration;

import java.io.File;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public interface TableDataExportIntegration {

	public boolean exportToInsertStatement(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile) throws DbexException;
	
	public boolean exportToCSV(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile) throws DbexException;
	
	public boolean exportToTEXT(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile, char separator) throws DbexException;
	
	public boolean exportToHTML(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile) throws DbexException;
	
	public boolean exportToXML(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile) throws DbexException;
	
	public boolean exportToExcel(String schemaName, String tableName, ConnectionProperties connectionProperties, String exportQuery, File outputFile) throws DbexException;
	
}
