package com.gs.dbex.application.tree.cfg;

import javax.swing.tree.DefaultMutableTreeNode;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj Das
 *
 */
public class ConnectionPropertyTreeNode {

	private ConnectionProperties connectionProperties;

	public ConnectionPropertyTreeNode(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	
	@Override
	public String toString() {
		return (null != connectionProperties) 
			? connectionProperties.getConnectionName()
			: "";
	}
	
	public boolean expand(DefaultMutableTreeNode parent) {
		return true;
	}
	
	public String printFullData(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("Connection Properties ::\n");
		buffer.append("\tConnection Name").append("\t: ").append(connectionProperties.getConnectionName()).append("\n");
		buffer.append("\tDatabase Type").append("\t: ").append(connectionProperties.getDatabaseType()).append("\n");
		buffer.append("\tConnection URL").append("\t: ").append(connectionProperties.getConnectionUrl()).append("\n");
		buffer.append("  Database Configurations ::\n");
		buffer.append("\tHost Name").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getHostName()).append("\n");
		buffer.append("\tPort Number").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getPortNumber()).append("\n");
		buffer.append("\tUser Name").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getUserName()).append("\n");
		buffer.append("\tPassword").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getPassword()).append("\n");
		
		buffer.append("\tDriver Class").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getDriverClassName()).append("\n");
		buffer.append("\tSchema/Catalog").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getSchemaName()).append("\n");
		buffer.append("\tSID/Service").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getSidServiceName()).append("\n");
		buffer.append("\tStorage Type").append("\t: ").append(connectionProperties.getDatabaseConfiguration().getStorageType()).append("\n");
		
		return buffer.toString();
	}
}
