/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj
 *
 */
public enum SupportedDatabaseEnum {

	ORACLE_8i ("ORACLE_8i", "Oracle 8i", 0),
	ORACLE_9i ("ORACLE_9i", "Oracle 9i", 1),
	ORACLE_10g ("ORACLE_10g", "Oracle 10g", 2),
	MY_SQL("MY_SQL", "MySql", 3),
	SQL_SERVER_2005 ("SQL_SERVER_2005", "SQL Server 2005", 4);
	
	
	private String code;
	private String name;
	private int displayOrder;
	
	private SupportedDatabaseEnum(String code, String name, int displayOrder) {
		this.code = code;
		this.name = name;
		this.displayOrder = displayOrder;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}
	
	
}
