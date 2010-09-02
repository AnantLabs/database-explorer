/**
 * 
 */
package com.gs.dbex.core.mysql;

import com.gs.dbex.core.metadata.enums.MysqlMetadataConstants;
import com.gs.dbex.core.metadata.enums.MysqlMetadataConstants.INFORMATION_SCHEMA_TABLES;

/**
 * @author Sabuj.das
 * 
 */
public final class MysqlMetaQueryConstants {

	public static final String GET_ALL_TABLES_QUERY = "SELECT * FROM INFORMATION_SCHEMA.TABLES";
	public static final String GET_ALL_COLUMNS_FOR_TABLE_QUERY = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

	public static final String GET_TABLE_QUERY = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

	public static final String GET_TABLE_NAMES_SQL = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA LIKE ? AND TABLE_NAME LIKE ?";
	public static final String GET_TABLE_SQL = 
		"SELECT " 
		+ MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.TABLE_CATALOG + ", "
		+ MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.TABLE_SCHEMA + ", "
		+ MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.TABLE_NAME + ", "
		+ MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.COLUMN_NAME + ", "
		+ MysqlMetadataConstants.INFORMATION_SCHEMA.COLUMNS.TABLE_CATALOG + ", "
		+ "FROM INFORMATION_SCHEMA.TABLES T, INFORMATION_SCHEMA.COLUMNS C " +
		"WHERE T.TABLE_SCHEMA LIKE ? AND T.TABLE_NAME LIKE ? AND T.TABLE_NAME = C.TABLE_NAME AND T.TABLE_SCHEMA = C.TABLE_SCHEMA";

}
