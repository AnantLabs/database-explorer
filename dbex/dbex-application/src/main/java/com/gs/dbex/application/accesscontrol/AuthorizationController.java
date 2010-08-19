/**
 * 
 */
package com.gs.dbex.application.accesscontrol;

import java.sql.Connection;
import java.sql.SQLException;

import com.gs.dbex.core.security.DatabaseAccessControl;
import com.gs.dbex.model.accesscontrol.Authorization;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.JdbcUtil;
import com.gs.utils.text.StringUtil;



/**
 * @author Sabuj Das
 *
 */
public class AuthorizationController {

	private static AuthorizationController instance;
	
	private Authorization authorization;
	private ConnectionProperties connectionProperties;
	private String schemaName;
	
	private AuthorizationController() {
		
	}
	
	public static AuthorizationController getInstance(){
		if(instance == null)
			instance = new AuthorizationController();
		return instance;
	}
	
	public void loadAuthorization(){
		if(connectionProperties == null
				|| !StringUtil.hasValidContent(schemaName)){
			authorization = null;
			return;
		}
		DatabaseAccessControl accessControl = new DatabaseAccessControl();
		Connection connection = null;
		try {
			connection = connectionProperties.getDataSource().getConnection();
			authorization = accessControl.grabDatabaseAuthorization(connection, schemaName);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(connection);
		}
	}
	
	public <T> boolean hasAccess(T t, String permissionName){
		if(authorization == null){
			return true;
		}
		if(t instanceof Table){
			return hasAccessForTable((Table) t, permissionName);
		}else if(t instanceof Column){
			return hasAccessForColumn((Column) t, permissionName);
		}
		return true;
	}
	
	private boolean hasAccessForTable(Table t, String permissionName){
		return true;
	}
	
	private boolean hasAccessForColumn(Column c, String permissionName){
		return true;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	
	
}
