/* ******************************************************************************
 * 	
 * 	Name	: TableDataExportServiceImpl.java
 * 	Type	: com.gs.dbex.service.impl.TableDataExportServiceImpl
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

package com.gs.dbex.service.impl;

import java.io.File;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.enums.TableDataExportTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.common.exception.ErrorCodeConstants;
import com.gs.dbex.integration.IntegrationBeanFactory;
import com.gs.dbex.integration.TableDataExportIntegration;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.QueryExecutionService;
import com.gs.dbex.service.TableDataExportService;
import com.gs.utils.io.IOUtil;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class TableDataExportServiceImpl implements TableDataExportService {


	private QueryExecutionService queryExecutionService;
	
	public TableDataExportServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public QueryExecutionService getQueryExecutionService() {
		return queryExecutionService;
	}

	public void setQueryExecutionService(QueryExecutionService queryExecutionService) {
		this.queryExecutionService = queryExecutionService;
	}

	@Override
	public boolean exportData(String schemaName, String tableName,
			TableDataExportTypeEnum exportTypeEnum, String outputFileName,
			String exportQuery, ConnectionProperties connectionProperties) throws DbexException {
		if(!StringUtil.hasValidContent(exportQuery) 
				|| !StringUtil.hasValidContent(outputFileName)
				|| exportTypeEnum == null)
			return false;
		File outputFile = null;
		
		TableDataExportIntegration integration = 
				IntegrationBeanFactory.getBeanFactory().getTableDataExportIntegration(
						DatabaseTypeEnum.getDatabaseTypeEnum(
								connectionProperties.getDatabaseType())
					);
		
		if(integration == null){
			throw new DbexException(ErrorCodeConstants.UNSUPPORTED_OPERATION);
		}
		
		if(TableDataExportTypeEnum.CSV.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.CSV.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.CSV.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToCSV(schemaName, tableName, connectionProperties, exportQuery, outputFile);
		} else if(TableDataExportTypeEnum.EXCEL.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.EXCEL.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.EXCEL.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToExcel(schemaName, tableName, connectionProperties, exportQuery, outputFile);
		} else if(TableDataExportTypeEnum.HTML.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.HTML.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.HTML.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToHTML(schemaName, tableName, connectionProperties, exportQuery, outputFile);
		} else if(TableDataExportTypeEnum.INSERT_STATEMENT.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.INSERT_STATEMENT.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.INSERT_STATEMENT.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToInsertStatement(schemaName, tableName, connectionProperties, exportQuery, outputFile);
		} else if(TableDataExportTypeEnum.SQL_LOADER.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.SQL_LOADER.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.SQL_LOADER.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			
		} else if(TableDataExportTypeEnum.TEXT.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.TEXT.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.TEXT.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToTEXT(schemaName, tableName, connectionProperties, exportQuery, outputFile, '\t');
		} else if(TableDataExportTypeEnum.XML.getCode() == exportTypeEnum.getCode()){
			if(!outputFileName.toLowerCase().endsWith(TableDataExportTypeEnum.XML.getExtension())){
				outputFileName += outputFileName + TableDataExportTypeEnum.XML.getExtension();
			}
			outputFile = IOUtil.mkfile(outputFileName);
			integration.exportToXML(schemaName, tableName, connectionProperties, exportQuery, outputFile);
		} 
		
		return false;
	}

}
