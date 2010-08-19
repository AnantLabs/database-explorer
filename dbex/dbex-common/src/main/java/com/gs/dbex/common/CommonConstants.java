/**
 * 
 */
package com.gs.dbex.common;

import com.gs.dbex.common.enums.DatabaseTypeEnum;

/**
 * @author Sabuj
 *
 */
public final class CommonConstants {

	public static final String[] DATABASE_TYPES = {
		DatabaseTypeEnum.ORACLE.getDescription(),
		DatabaseTypeEnum.MYSQL.getDescription(),
		DatabaseTypeEnum.MSSQL_2005.getDescription(),
		DatabaseTypeEnum.OTHER.getDescription()
	};
	
	
	
}
