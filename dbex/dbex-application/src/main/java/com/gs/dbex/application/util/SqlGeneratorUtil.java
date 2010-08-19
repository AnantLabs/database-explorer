package com.gs.dbex.application.util;

import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 *
 */
public class SqlGeneratorUtil {

	/*private static final String RENAME_TABLE_PART_1 = "ALTER TABLE <> RENAME TO <>";
	private static final String RENAME_TABLE_PART_2 = "";
*/	
	public static String generateTableRenameSQL(String schemaName, String oldName, String newName){
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(oldName)){
			return "";
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(oldName)
			.append(" RENAME TO ")
			.append(newName);
		return queryBuffer.toString(); 
	}
	
	public static String generateDropTableSQL(String schemaName, String tableName){
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)){
			return "";
		}
		StringBuffer queryBuffer = new StringBuffer("DROP TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName);
			
		return queryBuffer.toString(); 
	}

	public static String generateCommentTableSQL(String schemaName,
			String tableName, String comment) {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)){
			return "";
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
			
		return queryBuffer.toString();
	}
	
	public static String generateCopyTableSql(String sourceSchema, String sourceTable,
			String destinationSchema, String destinationTable, boolean copyData){
		if(!StringUtil.hasValidContent(sourceSchema)
				|| !StringUtil.hasValidContent(sourceTable)
				|| !StringUtil.hasValidContent(destinationSchema)
				|| !StringUtil.hasValidContent(destinationTable)){
			return "";
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
		return queryBuffer.toString();
	}

	public static String generateTruncateTableSQL(String schemaName,
			String tableName, boolean drop) {
		StringBuffer queryBuffer = new StringBuffer("TRUNCATE TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append((drop) ? " DROP STORAGE " : " REUSE STORAGE ");
			
		return queryBuffer.toString();
	}

	public static String generateColumnRenameSQL(String schemaName,
			String tableName, String oldColumnName, String newColumnName) {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(oldColumnName)){
			return "";
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" RENAME COLUMN ")
			.append(oldColumnName)
			.append(" TO ")
			.append(newColumnName);
		return queryBuffer.toString();
	}

	public static String generateDropColumnSQL(String schemaName,
			String tableName, String columnName) {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(columnName)){
			return "";
		}
		StringBuffer queryBuffer = new StringBuffer("ALTER TABLE ");
		queryBuffer
			.append(schemaName)
			.append('.')
			.append(tableName)
			.append(" DROP COLUMN ")
			.append(columnName);
			
		return queryBuffer.toString();
	}

	public static String generateCommentColumnSQL(String schemaName,
			String tableName, String columnName, String comment) {
		if(!StringUtil.hasValidContent(schemaName)
				|| !StringUtil.hasValidContent(tableName)
				|| !StringUtil.hasValidContent(columnName)){
			return "";
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
			
		return queryBuffer.toString();
	}
}
