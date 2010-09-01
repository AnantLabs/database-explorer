/**
 * 
 */
package com.gs.dbex.core.mysql;


/**
 * @author Sabuj.das
 * 
 */
public final class MysqlMetaQueryConstants {

	public static final String GET_ALL_TABLES_QUERY = "SELECT * FROM INFORMATION_SCHEMA.TABLES";
	public static final String GET_ALL_COLUMNS_FOR_TABLE_QUERY = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

	public static final String GET_TABLE_QUERY = "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=? AND TABLE_NAME=?";

	
}
