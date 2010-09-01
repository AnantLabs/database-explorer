package com.gs.dbex.core.metadata.enums;

/**
 * @author Sabuj Das
 * 
 */
public final class MysqlMetadataEnums {

	public static enum INFORMATION_SCHEMA_TABLES{
		CHARACTER_SETS,
		COLLATIONS,
		COLLATION_CHARACTER_SET_APPLICABILITY,
		COLUMNS,
		COLUMN_PRIVILEGES,
		ENGINES,
		EVENTS,
		FILES,
		GLOBAL_STATUS,
		GLOBAL_VARIABLES,
		KEY_COLUMN_USAGE,
		PARTITIONS,
		PLUGINS,
		PROCESSLIST,
		PROFILING,
		REFERENTIAL_CONSTRAINTS,
		ROUTINES,
		SCHEMATA,
		SCHEMA_PRIVILEGES,
		SESSION_STATUS,
		SESSION_VARIABLES,
		STATISTICS,
		TABLES,
		TABLE_CONSTRAINTS,
		TABLE_PRIVILEGES,
		TRIGGERS,
		USER_PRIVILEGES,
		VIEWS;
	}
	
	static final class INFORMATION_SCHEMA {
		
		static final class CHARACTER_SETS {
		}

		static final class COLLATIONS {
		}

		static final class COLLATION_CHARACTER_SET_APPLICABILITY {
		}

		static final class COLUMNS {
			public static final String TABLE_CATALOG = "TABLE_CATALOG";
			public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String COLUMN_NAME = "COLUMN_NAME";
			public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
			public static final String COLUMN_DEFAULT = "COLUMN_DEFAULT";
			public static final String IS_NULLABLE = "IS_NULLABLE";
			public static final String DATA_TYPE = "DATA_TYPE";
			public static final String CHARACTER_MAXIMUM_LENGTH = "CHARACTER_MAXIMUM_LENGTH";
			public static final String CHARACTER_OCTET_LENGTH = "CHARACTER_OCTET_LENGTH";
			public static final String NUMERIC_PRECISION = "NUMERIC_PRECISION";
			public static final String NUMERIC_SCALE = "NUMERIC_SCALE";
			public static final String CHARACTER_SET_NAME = "CHARACTER_SET_NAME";
			public static final String COLLATION_NAME = "COLLATION_NAME";
			public static final String COLUMN_TYPE = "COLUMN_TYPE";
			public static final String COLUMN_KEY = "COLUMN_KEY";
			public static final String EXTRA = "EXTRA";
			public static final String PRIVILEGES = "PRIVILEGES";
			public static final String COLUMN_COMMENT = "COLUMN_COMMENT";
		}

		static final class COLUMN_PRIVILEGES {
		}

		static final class ENGINES {
		}

		static final class EVENTS {
		}

		static final class FILES {
		}

		static final class GLOBAL_STATUS {
		}

		static final class GLOBAL_VARIABLES {
		}

		static final class KEY_COLUMN_USAGE {
		}

		static final class PARTITIONS {
		}

		static final class PLUGINS {
		}

		static final class PROCESSLIST {
		}

		static final class PROFILING {
		}

		static final class REFERENTIAL_CONSTRAINTS {
		}

		static final class ROUTINES {
		}

		static final class SCHEMATA {
		}

		static final class SCHEMA_PRIVILEGES {
		}

		static final class SESSION_STATUS {
		}

		static final class SESSION_VARIABLES {
		}

		static final class STATISTICS {
		}

		static final class TABLES {
			public static final String TABLE_CATALOG = "TABLE_CATALOG";
			public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String TABLE_TYPE = "TABLE_TYPE";
			public static final String ENGINE = "ENGINE";
			public static final String VERSION = "VERSION";
			public static final String ROW_FORMAT = "ROW_FORMAT";
			public static final String TABLE_ROWS = "TABLE_ROWS";
			public static final String AVG_ROW_LENGTH = "AVG_ROW_LENGTH";
			public static final String DATA_LENGTH = "DATA_LENGTH";
			public static final String MAX_DATA_LENGTH = "MAX_DATA_LENGTH";
			public static final String INDEX_LENGTH = "INDEX_LENGTH";
			public static final String DATA_FREE = "DATA_FREE";
			public static final String AUTO_INCREMENT = "AUTO_INCREMENT";
			public static final String CREATE_TIME = "CREATE_TIME";
			public static final String UPDATE_TIME = "UPDATE_TIME";
			public static final String CHECK_TIME = "CHECK_TIME";
			public static final String TABLE_COLLATION = "TABLE_COLLATION";
			public static final String CHECKSUM = "CHECKSUM";
			public static final String CREATE_OPTIONS = "CREATE_OPTIONS";
			public static final String TABLE_COMMENT = "TABLE_COMMENT";
		}

		static final class TABLE_CONSTRAINTS {
		}

		static final class TABLE_PRIVILEGES {
		}

		static final class TRIGGERS {
		}

		static final class USER_PRIVILEGES {
		}

		static final class VIEWS {
		}

	}

	public static void main(String[] args) {
		System.out.println(INFORMATION_SCHEMA.TABLES.AUTO_INCREMENT);
	}
}
