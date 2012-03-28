/* ******************************************************************************
 * 	
 * 	Name	: TableDataExportWorker.java
 * 	Type	: com.gs.dbex.application.task.TableDataExportWorker
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

package com.gs.dbex.application.task;

import javax.swing.SwingWorker;

import com.gs.dbex.common.enums.TableDataExportTypeEnum;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.gs.dbex.service.TableDataExportService;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class TableDataExportWorker extends SwingWorker<Void, ConnectionProperties> implements WorkerTaskConstants{

	private Table databaseTable;
	private ConnectionProperties connectionProperties;
	private TableDataExportTypeEnum dataExportTypeEnum;
	private String exportSql;
	private String outputFileName;
	
	/**
	 * 
	 */
	public TableDataExportWorker(ConnectionProperties connectionProperties, Table table) {
		this.connectionProperties = connectionProperties;
		this.databaseTable = table;
	}
	
	public TableDataExportTypeEnum getDataExportTypeEnum() {
		return dataExportTypeEnum;
	}

	public void setDataExportTypeEnum(TableDataExportTypeEnum dataExportTypeEnum) {
		this.dataExportTypeEnum = dataExportTypeEnum;
	}

	public String getExportSql() {
		return exportSql;
	}

	public void setExportSql(String exportSql) {
		this.exportSql = exportSql;
	}

	public Table getDatabaseTable() {
		return databaseTable;
	}

	public void setDatabaseTable(Table databaseTable) {
		this.databaseTable = databaseTable;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}

	protected Void doInBackground() throws Exception {
		firePropertyChange(TASK_STATUS_START, null, "Started the data export process...");
		
		TableDataExportService tableDataExportService = DbexServiceBeanFactory.getBeanFactory()
				.getTableDataExportService();
		if(null == tableDataExportService){
			firePropertyChange(TASK_STATUS_FAILED, null, "Internal system error !!!");
			return null;
		}
		
		try {
			boolean b = tableDataExportService.exportData(
					databaseTable.getSchemaName(), 
					databaseTable.getModelName(), 
					dataExportTypeEnum, 
					outputFileName, 
					exportSql, 
					connectionProperties);
			if(!b){
				firePropertyChange(TASK_STATUS_FAILED, null, "Internal system error !!!");
				return null;
			}
		} catch (Exception e) {
			firePropertyChange(TASK_STATUS_FAILED, null, e.getMessage());
			return null;
		}
		firePropertyChange(TASK_STATUS_DONE, null, "Connection Successful !!!");
		return null;
	}
	
	public void stop() {
		cancel(true);
		firePropertyChange(TASK_STATUS_ABORT, null, null);
	}
}
