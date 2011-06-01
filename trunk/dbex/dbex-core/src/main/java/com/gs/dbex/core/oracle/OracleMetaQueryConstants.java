/**
 * 
 */
package com.gs.dbex.core.oracle;

/**
 * @author Sabuj Das
 * 
 */
public final class OracleMetaQueryConstants {

	private static OracleMetaQueryConstants instance;
	
	private OracleMetaQueryConstants(){}
	
	
	public synchronized static OracleMetaQueryConstants getInstance() {
		if(null == instance){
			instance = new OracleMetaQueryConstants();
		}
		return instance;
	}

	private String availableSchemaSql;
	private String allTableSql;
	private String allColumnsSql;
	private String allConstraintsForTableSql;
	private String allConstraintsByTypeForTableSql;
	private String allPkForTableSql;
	private String allImportedKeyForTableSql;
	private String allExportedKeyForTableSql;


	public static final String AVAILABLE_SCHEMA_SQL = 
		"SELECT DISTINCT OWNER FROM ALL_CATALOG";

	public static final String ALL_TABLES_SQL = 
		"SELECT OWNER, TABLE_NAME, NUM_ROWS, TEMPORARY, DROPPED FROM ALL_TABLES WHERE OWNER = ?";

	public static final String ALL_COLUMNS_SQL = 
		"SELECT T.OWNER, T.TABLE_NAME, T.COLUMN_NAME, T.DATA_TYPE, " 
		+ "T.DATA_LENGTH, T.DATA_PRECISION, T.DATA_SCALE, T.NULLABLE, " 
		+ "T.COLUMN_ID, T.DEFAULT_LENGTH, T.DATA_DEFAULT, T.CHARACTER_SET_NAME, "
		+ " P.COMMENTS"  
		+ " FROM ALL_TAB_COLUMNS T, ALL_COL_COMMENTS P " 
		+ " WHERE "
		+ " T.OWNER=? " 
		+ " AND T.TABLE_NAME=? "
		+ " AND T.OWNER = P.OWNER " 
		+ " AND T.TABLE_NAME =P.TABLE_NAME " 
		+ " AND T.COLUMN_NAME = P.COLUMN_NAME ";

	public static final String GET_CONSTRAINT_COLUMNS_SQL = 
		"SELECT COLS.TABLE_NAME, COLS.COLUMN_NAME, CONS.STATUS, CONS.OWNER"
		+ " FROM ALL_CONSTRAINTS CONS, ALL_CONS_COLUMNS COLS"
		+ " WHERE "
		+ " CONS.CONSTRAINT_NAME = COLS.CONSTRAINT_NAME"
		+ " AND CONS.OWNER = COLS.OWNER"
		+ " AND CONS.TABLE_NAME = COLS.TABLE_NAME"
		+ " AND COLS.OWNER=?"
		+ " AND COLS.TABLE_NAME=?"
		+ " AND CONS.CONSTRAINT_TYPE = ?"
		+ " ORDER BY COLS.TABLE_NAME";

	/* --------------- */
	
	public String getAvailableSchemaSql() {
		return availableSchemaSql;
	}

	public void setAvailableSchemaSql(String availableSchemaSql) {
		this.availableSchemaSql = availableSchemaSql;
	}

	public String getAllTableSql() {
		return allTableSql;
	}

	public void setAllTableSql(String allTableSql) {
		this.allTableSql = allTableSql;
	}

	public String getAllColumnsSql() {
		return allColumnsSql;
	}

	public void setAllColumnsSql(String allColumnsSql) {
		this.allColumnsSql = allColumnsSql;
	}

	public String getAllConstraintsForTableSql() {
		return allConstraintsForTableSql;
	}

	public void setAllConstraintsForTableSql(String allConstraintsForTableSql) {
		this.allConstraintsForTableSql = allConstraintsForTableSql;
	}

	public String getAllPkForTableSql() {
		return allPkForTableSql;
	}

	public void setAllPkForTableSql(String allPkForTableSql) {
		this.allPkForTableSql = allPkForTableSql;
	}

	public String getAllImportedKeyForTableSql() {
		return allImportedKeyForTableSql;
	}

	public void setAllImportedKeyForTableSql(String allImportedKeyForTableSql) {
		this.allImportedKeyForTableSql = allImportedKeyForTableSql;
	}

	public String getAllExportedKeyForTableSql() {
		return allExportedKeyForTableSql;
	}

	public void setAllExportedKeyForTableSql(String allExportedKeyForTableSql) {
		this.allExportedKeyForTableSql = allExportedKeyForTableSql;
	}


	public String getAllConstraintsByTypeForTableSql() {
		return allConstraintsByTypeForTableSql;
	}


	public void setAllConstraintsByTypeForTableSql(
			String allConstraintsByTypeForTableSql) {
		this.allConstraintsByTypeForTableSql = allConstraintsByTypeForTableSql;
	}
	
	
	
	
}
