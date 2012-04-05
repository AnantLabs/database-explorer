/* ******************************************************************************
 * 	
 * 	Name	: SqlGeneratorServiceImpl.java
 * 	Type	: com.gs.dbex.service.impl.SqlGeneratorServiceImpl
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

package com.gs.dbex.service.impl;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;
import com.gs.dbex.service.SqlGeneratorService;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class SqlGeneratorServiceImpl implements SqlGeneratorService {

	/**
	 * 
	 */
	public SqlGeneratorServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public SqlQuery generateRenameTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, String newName) throws DbexException {
		return generateRenameTableSql(databaseTypeEnum, table.getSchemaName(), table.getModelName(), newName);
	}

	@Override
	public SqlQuery generateRenameTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String newName)
			throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" RENAME TO ")
			.append(newName);
		return new SqlQuery(queryBuffer.toString()); 
	}

	@Override
	public SqlQuery generateDropTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table) throws DbexException {
		return generateDropTableSql(databaseTypeEnum,
				table.getSchemaName(), table.getModelName());
	}

	@Override
	public SqlQuery generateDropTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName) throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("DROP TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName);
			
		return new SqlQuery(queryBuffer.toString()); 
	}

	@Override
	public SqlQuery generateTruncateTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, boolean reuseStorage) throws DbexException {
		return generateTruncateTableSql(databaseTypeEnum, table.getSchemaName(), 
				table.getModelName(), reuseStorage);
	}

	@Override
	public SqlQuery generateTruncateTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, boolean reuseStorage)
			throws DbexException {
		StringBuffer queryBuffer = new StringBuffer("TRUNCATE TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append((!reuseStorage) ? " DROP STORAGE " : " REUSE STORAGE ");
			
		return new SqlQuery(queryBuffer.toString()); 
	}

	@Override
	public SqlQuery generateCommentTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, String comment) throws DbexException {
		return generateCommentTableSql(databaseTypeEnum, table.getSchemaName(), 
				table.getModelName(), comment);
	}

	@Override
	public SqlQuery generateCommentTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String comment)
			throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("COMMENT ON TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" IS ")
			.append('\'')
			.append(comment)
			.append('\'');
			
		return new SqlQuery(queryBuffer.toString());
	}

	@Override
	public SqlQuery generateAlterTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlQuery generateAlterTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlQuery generateRenameColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column, String newName) throws DbexException {
		return generateRenameColumnSql(databaseTypeEnum, table.getSchemaName(), 
				table.getModelName(), column.getModelName(), newName);
	}

	@Override
	public SqlQuery generateRenameColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName,
			String newName) throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(columnName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" RENAME COLUMN ")
			.append(columnName)
			.append(" TO ")
			.append(newName);
		return new SqlQuery(queryBuffer.toString());
	}

	@Override
	public SqlQuery generateDropColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column) throws DbexException {
		return generateDropColumnSql(databaseTypeEnum, table.getSchemaName(), 
				table.getModelName(), column.getModelName());
	}

	@Override
	public SqlQuery generateDropColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName)
			throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(columnName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" DROP COLUMN ")
			.append(columnName);
		return new SqlQuery(queryBuffer.toString());
	}

	@Override
	public SqlQuery generateCommentColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column, String comment) throws DbexException {
		return generateCommentColumnSql(databaseTypeEnum, table.getSchemaName(), 
				table.getModelName(), column.getModelName(), comment);
	}

	@Override
	public SqlQuery generateCommentColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName,
			String comment) throws DbexException {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(columnName)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("COMMENT ON COLUMN ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append('.')
			.append(columnName)
			.append(" IS ")
			.append('\'')
			.append(comment)
			.append('\'');
		return new SqlQuery(queryBuffer.toString());
	}

	@Override
	public SqlQuery generateAlterColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column) throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlQuery generateAlterColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String ColumnName)
			throws DbexException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SqlQuery generateCopyTableSql(DatabaseTypeEnum databaseTypeEnum,
			String sourceSchema, String sourceTable,
			String destinationSchema, String destinationTable, boolean copyData)
			throws DbexException {
		if(!StringUtil.hasValidContent(sourceSchema)
				|| !StringUtil.hasValidContent(sourceTable)
				|| !StringUtil.hasValidContent(destinationSchema)
				|| !StringUtil.hasValidContent(destinationTable)){
			return null;
		}
		StringBuffer queryBuffer = new StringBuffer("CREATE TABLE ");
		queryBuffer
			.append(destinationSchema)
			.append('.')
			.append(destinationTable)
			.append(" AS SELECT * FROM ")
			.append(sourceSchema)
			.append('.')
			.append(sourceTable)
			.append(" WHERE '1' = ")
			.append((copyData) ? "\'1\'" : "\'\'");
		return new SqlQuery(queryBuffer.toString());
	}

		
}
