package com.gs.dbex.core.metadata.enums;

/**
 * @author Sabuj Das
 * 
 */
public final class MysqlMetadataConstants {

	/**
	 * This Enum contains the table names from INFORMATION_SCHEMA.
	 * @author Sabuj.das
	 *
	 */
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
	
	public static enum CONSTRAINT_TYPE_ENUM{
		PK("PRIMARY KEY"),
		FK("FOREIGN KEY"),
		UN("UNIQUE"),
		CK("CHECK");
		
		private final String code;

		private CONSTRAINT_TYPE_ENUM(String code) {
			this.code = code;
		}

		public String getCode() {
			return code;
		}
		
		
	}
	
	/**
	 * Detailed column name constants of tables of INFORMATION_SCHEMA.
	 * @author Sabuj.das
	 *
	 */
	public static final class INFORMATION_SCHEMA {
		
		/**
		 * Column Name constants for table <code>CHARACTER_SETS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class CHARACTER_SETS {
		}

		/**
		 * Column Name constants for table <code>COLLATIONS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class COLLATIONS {
		}

		/**
		 * Column Name constants for table <code>COLLATION_CHARACTER_SET_APPLICABILITY</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class COLLATION_CHARACTER_SET_APPLICABILITY {
		}

		/**
		 * Column Name constants for table <code>COLUMNS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class COLUMNS {
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

		/**
		 * Column Name constants for table <code>COLUMN_PRIVILEGES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class COLUMN_PRIVILEGES {
		}

		/**
		 * Column Name constants for table <code>ENGINES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class ENGINES {
		}

		/**
		 * Column Name constants for table <code>EVENTS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class EVENTS {
		}

		/**
		 * Column Name constants for table <code>FILES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class FILES {
		}

		/**
		 * Column Name constants for table <code>GLOBAL_STATUS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class GLOBAL_STATUS {
		}

		/**
		 * Column Name constants for table <code>GLOBAL_VARIABLES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class GLOBAL_VARIABLES {
		}

		/**
		 * Column Name constants for table <code>KEY_COLUMN_USAGE</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class KEY_COLUMN_USAGE {
			public static final String CONSTRAINT_CATALOG = "CONSTRAINT_CATALOG";
			public static final String CONSTRAINT_SCHEMA = "CONSTRAINT_SCHEMA";
			public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
			public static final String TABLE_CATALOG = "TABLE_CATALOG";
			public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String COLUMN_NAME = "COLUMN_NAME";
			public static final String ORDINAL_POSITION = "ORDINAL_POSITION";
			public static final String POSITION_IN_UNIQUE_CONSTRAINT = "POSITION_IN_UNIQUE_CONSTRAINT";
			public static final String REFERENCED_TABLE_SCHEMA = "REFERENCED_TABLE_SCHEMA";
			public static final String REFERENCED_TABLE_NAME = "REFERENCED_TABLE_NAME";
			public static final String REFERENCED_COLUMN_NAME = "REFERENCED_COLUMN_NAME";
		}

		/**
		 * Column Name constants for table <code>PARTITIONS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class PARTITIONS {
		}

		/**
		 * Column Name constants for table <code>PLUGINS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class PLUGINS {
		}

		/**
		 * Column Name constants for table <code>PROCESSLIST</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class PROCESSLIST {
		}

		/**
		 * Column Name constants for table <code>PROFILING</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class PROFILING {
		}

		/**
		 * Column Name constants for table <code>REFERENTIAL_CONSTRAINTS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class REFERENTIAL_CONSTRAINTS {
			public static final String CONSTRAINT_CATALOG = "CONSTRAINT_CATALOG";
			public static final String CONSTRAINT_SCHEMA = "CONSTRAINT_SCHEMA";
			public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String REFERENCED_TABLE_NAME = "REFERENCED_TABLE_NAME";
			public static final String UNIQUE_CONSTRAINT_CATALOG = "UNIQUE_CONSTRAINT_CATALOG";
			public static final String UNIQUE_CONSTRAINT_SCHEMA = "UNIQUE_CONSTRAINT_SCHEMA";
			public static final String UNIQUE_CONSTRAINT_NAME = "UNIQUE_CONSTRAINT_NAME";
			public static final String MATCH_OPTION = "MATCH_OPTION";
			public static final String UPDATE_RULE = "UPDATE_RULE";
			public static final String DELETE_RULE = "DELETE_RULE";
		}

		/**
		 * Column Name constants for table <code>ROUTINES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class ROUTINES {
		}

		/**
		 * Column Name constants for table <code>SCHEMATA</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class SCHEMATA {
			public static final String CATALOG_NAME = "CATALOG_NAME";
			public static final String SCHEMA_NAME = "SCHEMA_NAME";
			public static final String DEFAULT_CHARACTER_SET_NAME = "DEFAULT_CHARACTER_SET_NAME";
			public static final String DEFAULT_COLLATION_NAME = "DEFAULT_COLLATION_NAME";
			public static final String SQL_PATH = "SQL_PATH";
		}

		/**
		 * Column Name constants for table <code>SCHEMA_PRIVILEGES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class SCHEMA_PRIVILEGES {
		}

		/**
		 * Column Name constants for table <code>SESSION_STATUS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class SESSION_STATUS {
		}

		/**
		 * Column Name constants for table <code>SESSION_VARIABLES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class SESSION_VARIABLES {
		}

		/**
		 * Column Name constants for table <code>STATISTICS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class STATISTICS {
		}

		/**
		 * Column Name constants for table <code>TABLES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class TABLES {
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

		/**
		 * Column Name constants for table <code>TABLE_CONSTRAINTS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class TABLE_CONSTRAINTS {
			public static final String CONSTRAINT_CATALOG = "CONSTRAINT_CATALOG";
			public static final String CONSTRAINT_SCHEMA = "CONSTRAINT_SCHEMA";
			public static final String CONSTRAINT_NAME = "CONSTRAINT_NAME";
			public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String CONSTRAINT_TYPE = "CONSTRAINT_TYPE";
		}

		/**
		 * Column Name constants for table <code>TABLE_PRIVILEGES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class TABLE_PRIVILEGES {
			public static final String TABLE_CATALOG = "TABLE_CATALOG";
			public static final String TABLE_SCHEMA = "TABLE_SCHEMA";
			public static final String TABLE_NAME = "TABLE_NAME";
			public static final String GRANTEE = "GRANTEE";
			public static final String PRIVILEGE_TYPE = "PRIVILEGE_TYPE";
			public static final String IS_GRANTABLE = "IS_GRANTABLE";
		}

		/**
		 * Column Name constants for table <code>TRIGGERS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class TRIGGERS {
		}

		/**
		 * Column Name constants for table <code>USER_PRIVILEGES</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class USER_PRIVILEGES {
		}

		/**
		 * Column Name constants for table <code>VIEWS</code>
		 * @author Sabuj.das
		 *
		 */
		public static final class VIEWS {
		}

	}

	public static void main(String[] args) {
		System.out.println(INFORMATION_SCHEMA.TABLES.AUTO_INCREMENT);
	}
}
