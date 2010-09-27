/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.graph.TableDependencyPanel;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;

/**
 * @author sabuj.das
 * 
 */
public class TableDetailsPanel extends JPanel implements ActionListener,
		ApplicationConstants {

	private Table databaseTable;
	
	private JFrame parentFrame;
	private JTabbedPane tableDetailsTabbedPane;
	private String tableName;
	private String schemaName;
	private ConnectionProperties connectionProperties;
	

	public TableDetailsPanel(JFrame parent, Table table, ConnectionProperties connectionProperties) {
		this.parentFrame = parent;
		this.databaseTable = table;
		this.schemaName = table.getSchemaName();
		this.tableName = table.getModelName();
		this.connectionProperties = connectionProperties;
		initComponents();
	}


	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}


	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}


	public Table getDatabaseTable() {
		return databaseTable;
	}


	public void setDatabaseTable(Table databaseTable) {
		this.databaseTable = databaseTable;
	}


	private void initComponents() {
		tableDetailsTabbedPane = new JTabbedPane();
		tableDetailsTabbedPane.setFocusable(false);
		tableDetailsTabbedPane.addTab("Data", new ImageIcon(
				TableDetailsPanel.class
						.getResource(ApplicationConstants.IMAGE_PATH
								+ "table_data.gif")), 
				new TableDataPanel(getParentFrame(), getDatabaseTable(), getConnectionProperties()));
		tableDetailsTabbedPane.addTab("Columns",new ImageIcon(
				TableDetailsPanel.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "columngroup.gif")), 
				new ColumnDetailsPanel(getParentFrame(),getDatabaseTable(), getConnectionProperties()));
		tableDetailsTabbedPane.addTab("Constraints",new ImageIcon(
				TableDetailsPanel.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "constraint.gif")), 
				new ConstraintsDetailsPanel(getParentFrame(),getSchemaName(), getTableName(), getConnectionProperties()));
		tableDetailsTabbedPane.addTab("Dependencies",new ImageIcon(
				TableDetailsPanel.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "dependency.gif")), 
				new TableDependencyPanel(getParentFrame(),getSchemaName(), getTableName(), getConnectionProperties()));
		tableDetailsTabbedPane.addTab("Indexes",new ImageIcon(
				TableDetailsPanel.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "index.gif")), 
				new IndexDetailsPanel(getParentFrame(),getSchemaName(), getTableName(), getConnectionProperties()));
		tableDetailsTabbedPane.addTab("DDL", new ImageIcon(
				TableDetailsPanel.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "generate_ddl.gif")),
				new DDLGenerationPanel(getParentFrame(),getTableName(), getConnectionProperties()));

		setLayout(new BorderLayout());

		add(tableDetailsTabbedPane, BorderLayout.CENTER);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public JTabbedPane getTableDetailsTabbedPane() {
		return tableDetailsTabbedPane;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
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
