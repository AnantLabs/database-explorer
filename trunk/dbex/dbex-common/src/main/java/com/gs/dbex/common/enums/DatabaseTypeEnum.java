/**
 * 
 */
package com.gs.dbex.common.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sabuj.das
 *
 */
public enum DatabaseTypeEnum {

	OTHER("OTHER", "Other Database"),
	ORACLE("ORACLE", "Oracle"),
	MYSQL("MYSQL", "Mysql"),
	MSSQL_2005("MSSQL_2005", "MS SQL Server 2005");
	
	
	private String code;
	private String description;
	private DatabaseTypeEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}
	public String getCode() {
		return code;
	}
	public String getDescription() {
		return description;
	}
	
	public static DatabaseTypeEnum getDatabaseTypeEnum(String type){
		if(ORACLE.getCode().equals(type)){
			return ORACLE;
		}
		if(MYSQL.getCode().equals(type)){
			return MYSQL;
		}
		if(MSSQL_2005.getCode().equals(type)){
			return MSSQL_2005;
		}
		return OTHER;
	}
	
	public static DatabaseTypeEnum getDatabaseTypeEnumByName(String name){
		if(ORACLE.getDescription().equals(name)){
			return ORACLE;
		}
		if(MYSQL.getDescription().equals(name)){
			return MYSQL;
		}
		if(MSSQL_2005.getDescription().equals(name)){
			return MSSQL_2005;
		}
		return OTHER;
	}
	
	public static List<DatabaseTypeEnum> toList(){
		List<DatabaseTypeEnum> l = new ArrayList<DatabaseTypeEnum>();
		l.add(ORACLE);
		l.add(MYSQL);
		l.add(MSSQL_2005);
		l.add(OTHER);
		return l;
	}
	
	public static List<String> getCodes(){
		List<String> l = new ArrayList<String>();
		l.add(ORACLE.getCode());
		l.add(MYSQL.getCode());
		l.add(MSSQL_2005.getCode());
		l.add(OTHER.getCode());
		return l;
	}
	
	public static List<String> getNamse(){
		List<String> l = new ArrayList<String>();
		l.add(ORACLE.getDescription());
		l.add(MYSQL.getDescription());
		l.add(MSSQL_2005.getDescription());
		l.add(OTHER.getDescription());
		return l;
	}
}
