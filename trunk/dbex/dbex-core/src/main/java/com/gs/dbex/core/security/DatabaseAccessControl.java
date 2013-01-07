/**
 * 
 */
package com.gs.dbex.core.security;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gs.dbex.common.enums.PrivilegeMetaDataEnum;
import com.gs.dbex.model.accesscontrol.Authorization;
import com.gs.dbex.model.accesscontrol.ColumnAccessPrivilege;
import com.gs.dbex.model.accesscontrol.DatabaseAccessPrivilege;
import com.gs.dbex.model.accesscontrol.TableAccessPrivilege;
import com.gs.utils.text.StringUtil;


/**
 * @author Sabuj Das | sabuj.das@gmail.com
 *
 */
public class DatabaseAccessControl {

	public Authorization grabDatabaseAuthorization(Connection connection, String schemaName) throws SQLException{
		if(connection == null || !StringUtil.hasValidContent(schemaName))
			return null;
		Authorization authorization = new Authorization();	
		
		DatabaseMetaData databaseMetaData = connection.getMetaData();
		if(databaseMetaData != null){
			ResultSet tableRs = databaseMetaData.getTables("", schemaName, "%", new String[] {"TABLE"});
			while(tableRs.next()){
				String tableName = tableRs.getString("TABLE_NAME");
				if(tableName.startsWith("BIN$"))
					continue;
				
				ResultSet tablePrvRs = databaseMetaData.getTablePrivileges(null, schemaName, tableName);
				List<TableAccessPrivilege> tablePrivilegeList = new ArrayList<TableAccessPrivilege>();
				while(tablePrvRs.next()){
					TableAccessPrivilege tap = new TableAccessPrivilege();
					tap.setTableName(tableName);
					DatabaseAccessPrivilege dap = new DatabaseAccessPrivilege();
					dap.setCatalogName(tablePrvRs.getString(PrivilegeMetaDataEnum.TABLE_CAT.getCode()));
					dap.setSchemaName(tablePrvRs.getString(PrivilegeMetaDataEnum.TABLE_SCHEM.getCode()));
					dap.setGrantor(tablePrvRs.getString(PrivilegeMetaDataEnum.GRANTOR.getCode()));
					dap.setGrantee(tablePrvRs.getString(PrivilegeMetaDataEnum.GRANTEE.getCode()));
					dap.setPrivilegeName(tablePrvRs.getString(PrivilegeMetaDataEnum.PRIVILEGE.getCode()));
					String grantable = tablePrvRs.getString(PrivilegeMetaDataEnum.IS_GRANTABLE.getCode());
					if("YES".equalsIgnoreCase(grantable)){
						dap.setIsGrantable(true);
					}else{
						dap.setIsGrantable(false);
					}
					tap.setDatabaseAccessPrivilege(dap);
					tablePrivilegeList.add(tap);
				}
				if(tablePrvRs != null){
					tablePrvRs.close();
				}
				ResultSet columnRs = databaseMetaData.getColumns("", schemaName, tableName, "%");
				while(columnRs.next()){
					String columnName = columnRs.getString("COLUMN_NAME");
					ResultSet colPrevRs = databaseMetaData.getColumnPrivileges(null, schemaName, tableName, columnName);
					List<ColumnAccessPrivilege> capList = new ArrayList<ColumnAccessPrivilege>();
					while(colPrevRs.next()){
						ColumnAccessPrivilege cap = new ColumnAccessPrivilege();
						cap.setTableName(tableName);
						cap.setColumnName(columnName);
						DatabaseAccessPrivilege dap = new DatabaseAccessPrivilege();
						dap.setCatalogName(colPrevRs.getString(PrivilegeMetaDataEnum.TABLE_CAT.getCode()));
						dap.setSchemaName(colPrevRs.getString(PrivilegeMetaDataEnum.TABLE_SCHEM.getCode()));
						dap.setGrantor(colPrevRs.getString(PrivilegeMetaDataEnum.GRANTOR.getCode()));
						dap.setGrantee(colPrevRs.getString(PrivilegeMetaDataEnum.GRANTEE.getCode()));
						dap.setPrivilegeName(colPrevRs.getString(PrivilegeMetaDataEnum.PRIVILEGE.getCode()));
						String grantable = colPrevRs.getString(PrivilegeMetaDataEnum.IS_GRANTABLE.getCode());
						if("YES".equalsIgnoreCase(grantable)){
							dap.setIsGrantable(true);
						}else{
							dap.setIsGrantable(false);
						}
						cap.setDatabaseAccessPrivilege(dap);
						capList.add(cap);
					}
					if(colPrevRs != null){
						colPrevRs.close();
					}
					authorization.getColumnPrivilegeListMap().put(schemaName+"."+tableName+"."+columnName, capList);
				}
				if(columnRs != null){
					columnRs.close();
				}
				authorization.getTablePrivilegeListMap().put(schemaName+"."+tableName, tablePrivilegeList);
			}
			if(tableRs != null){
				tableRs.close();
			}
			
		}
		
		return authorization;
	}
}
