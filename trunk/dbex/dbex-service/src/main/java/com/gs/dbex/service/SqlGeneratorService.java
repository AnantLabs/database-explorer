/* ******************************************************************************
 * 	
 * 	Name	: SqlGeneratorService.java
 * 	Type	: com.gs.dbex.service.SqlGeneratorService
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

package com.gs.dbex.service;

import com.gs.dbex.common.enums.DatabaseTypeEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.sql.SqlQuery;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public interface SqlGeneratorService {

	public SqlQuery generateRenameTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, String newName) throws DbexException;

	public SqlQuery generateRenameTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String newName)
			throws DbexException;

	public SqlQuery generateDropTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table) throws DbexException;

	public SqlQuery generateDropTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName) throws DbexException;

	public SqlQuery generateTruncateTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, boolean reuseStorage) throws DbexException;

	public SqlQuery generateTruncateTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, boolean reuseStorage)
			throws DbexException;

	public SqlQuery generateCommentTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, String comment) throws DbexException;

	public SqlQuery generateCommentTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String comment)
			throws DbexException;

	public SqlQuery generateAlterTableSql(DatabaseTypeEnum databaseTypeEnum,
			Table table) throws DbexException;

	public SqlQuery generateAlterTableSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName) throws DbexException;

	public SqlQuery generateRenameColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column, String newName) throws DbexException;

	public SqlQuery generateRenameColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName,
			String newName) throws DbexException;

	public SqlQuery generateDropColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column) throws DbexException;

	public SqlQuery generateDropColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName)
			throws DbexException;

	public SqlQuery generateCommentColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column, String comment) throws DbexException;

	public SqlQuery generateCommentColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String columnName,
			String comment) throws DbexException;

	public SqlQuery generateAlterColumnSql(DatabaseTypeEnum databaseTypeEnum,
			Table table, Column column) throws DbexException;

	public SqlQuery generateAlterColumnSql(DatabaseTypeEnum databaseTypeEnum,
			String schemaName, String tableName, String ColumnName)
			throws DbexException;
}
