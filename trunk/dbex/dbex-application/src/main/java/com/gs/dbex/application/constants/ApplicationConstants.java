/**
 * 
 */
package com.gs.dbex.application.constants;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sabuj
 *
 */
public interface ApplicationConstants {

	int CANCEL_OPTION = -1001;
	int APPLY_OPTION = 1001; 
	
	String IMAGE_LOCATION = "/images/";
	
	String DEFAULT_APP_DATA_DIR = "./application-data/";
	
	String PRIVATE_DATA_DIR = "private_data/";
	
	String IMAGE_PATH = "/images/";
	String EDITOR_IMAGE_PATH = "/images/com/gs/dbex/sql/editor/";
	String QUERY_IMAGE_PATH = "/images/query/";
	String DATA_DIR = "./data/";
	String CONNECTION_DATA_FILE = DATA_DIR
			+ "connection/oracle_connections.xml";
	String SYNTAX_DATA_FILE = DATA_DIR
			+ "syntax/sql-syntax-style.xml";
	
	String CONN_PROPERTIES_MAPPING_FILE = "com/gs/oracle/castor/connection_properties-mapping.xml";
	String SQL_SYNTAX_MAPPING_FILE = "com/gs/oracle/castor/sql-syntax-style-mapping.xml";

	List<String> SQL_KEYWORD_LIST = new ArrayList<String>();

	String SQL_KEYWORD = "ALL, ALTER, AND, ANY, ARRAY, ARROW, AS, ASC, AT,"
			+ "BEGIN, BETWEEN, BY,CASE, CHECK, CLUSTERS, CLUSTER, COLAUTH, COLUMNS, " +
					"COMPRESS, CONNECT, CRASH, CREATE, CURRENT,DECIMAL, DECLARE, " +
					"DEFAULT, DELETE, DESC, DISTINCT, DROP,ELSE, END, EXCEPTION, " +
					"EXCLUSIVE, EXISTS,FETCH, FORM, FOR, FROM,GOTO, GRANT, GROUP," +
					"HAVING,IDENTIFIED, IF, IN, INDEXES, INDEX, INSERT, INTERSECT, " +
					"INTO, IS,LIKE, LOCK,MINUS, MODE,NOCOMPRESS, NOT, NOWAIT, NULL,OF, " +
					"ON, OPTION, OR, ORDER,OVERLAPS,PRIOR, PROCEDURE, PUBLIC,RANGE, " +
					"RECORD, RESOURCE, REVOKE,SELECT, SHARE, SIZE, SQL, START, SUBTYPE," +
					"TABAUTH, TABLE, THEN, TO, TYPE,UNION, UNIQUE, UPDATE, USE,VALUES, " +
					"VIEW, VIEWS,WHEN, WHERE, WITH";
	
	// constants for dependency
	Color COLUMN_NAMES_BG_COLOR = new Color(232, 242, 254);
	Color COLUMN_NAMES_FG_COLOR = Color.BLACK;
	Color TABLE_BORDER_COLOR = new Color(106,140,203);
	Color TABLE_HEADER_BG_COLOR = new Color(69, 117, 205);
	Color TABLE_HEADER_FG_COLOR = Color.BLACK;
	Color TABLE_LEFT_MARGIN_BG_COLOR = new Color(171, 200, 246);
	Color TABLE_LEFT_MARGIN_FG_COLOR = Color.BLACK;
	Color TABLE_DEPENDENCY_LONE_COLOR = new Color(6, 118, 96);
	Color IMPORTED_RELATION_LINE_COLOR = new Color(0, 166, 80);
	Color EXPORTED_RELATION_LINE_COLOR = new Color(0, 80, 166);
	
	int SCALE_100_PERCENT = 100;
	int TABLE_LEFT_MARGIN_WIDTH = 20;
	
	String[] LOGICAL_OPERATORS = {"","AND", "OR", "NOT"};
	String[] SQL_CONDITION_OPERATORS = {"IN", "LIKE", "=", "<", ">", "<>", "NOT IN", "BETWEEN"};
	
	String INSERT_DATE_FORMAT = "dd-MMM-yy hh.mm.ss.SSS aaa";
	String SQL_DATE_FORMAT = "'DD-MON-RR HH.MI.SS.FF AM'";
	String SQL_DATE_FUNCTION = "to_timestamp";
	
	String FORE_GROUND = "FG";
	String BACK_GROUND = "BG";
	
	String CONTENT_TEXT = "Content of ";
	
	String ORACLE_DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	String ORACLE_CONNECTION_URL_PATTERN = "jdbc:oracle:thin:<userName>/<password>@<hostName>:<portNumber>:<SID>";
	String MSSQL_DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String MSSQL_CONNECTION_URL_PATTERN = "jdbc:microsoft:sqlserver://<hostName>:<portNumber>;databaseName=<databaseName>;selectMethod=<selectMethod>";
	String MYSQL_DRIVER_NAME = "com.mysql.jdbc.Driver";
	String MYSQL_CONNECTION_URL_PATTERN = "jdbc:mysql://<hostName>:<portNumber>/<databaseName>user=<userName>&password=<password>";
	
	
}
