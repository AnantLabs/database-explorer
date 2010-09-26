/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author Sabuj Das
 *
 */
public final class OracleMetadataConstants {

	public static final class AVAILABLE_SCHEMA_SQL_META_DATA{
		public static final String OWNER = "OWNER";
	}
	
	public static final class ALL_TABLES_SQL_META_DATA{
		public static final String OWNER = "OWNER";
		public static final String TABLE_NAME = "TABLE_NAME";
		public static final String NUM_ROWS = "NUM_ROWS";
		public static final String TEMPORARY = "TEMPORARY";
		public static final String DROPPED = "DROPPED";
	}
	
	public static final class ALL_COLUMNS_SQL_META_DATA{
		public static final String OWNER = "OWNER";
		public static final String TABLE_NAME = "TABLE_NAME";
		public static final String COLUMN_NAME = "COLUMN_NAME";
		public static final String DATA_TYPE = "DATA_TYPE";
		public static final String DATA_LENGTH = "DATA_LENGTH";
		public static final String DATA_PRECISION = "DATA_PRECISION";
		public static final String DATA_SCALE = "DATA_SCALE";
		public static final String NULLABLE = "NULLABLE";
		public static final String COLUMN_ID = "COLUMN_ID";
		public static final String DEFAULT_LENGTH = "DEFAULT_LENGTH";
		public static final String DATA_DEFAULT = "DATA_DEFAULT";
		public static final String CHARACTER_SET_NAME = "CHARACTER_SET_NAME";
		public static final String COMMENTS = "COMMENTS";
	}
	
	public static final class GET_CONSTRAINT_COLUMNS_SQL_META_DATA{
		public static final String OWNER = "OWNER";
		public static final String TABLE_NAME = "TABLE_NAME";
		public static final String COLUMN_NAME = "COLUMN_NAME";
		public static final String STATUS = "STATUS";
		public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
	}
}
