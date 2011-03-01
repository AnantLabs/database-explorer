/**
 * 
 */
package com.gs.dbex.application.panel;



import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import org.apache.log4j.Logger;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.table.model.ColumnDetailsTableModel;
import com.gs.dbex.application.util.MenuBarUtil;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.design.util.DesignUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.dbex.service.DbexServiceBeanFactory;

/**
 * @author sabuj.das
 *
 */
public class ColumnDetailsPanel extends JPanel implements ActionListener,
		ApplicationConstants {

	private static final Logger logger = Logger.getLogger(ColumnDetailsPanel.class);
	private JFrame parentFrame;
	private JButton refreshButton, addColumnButton, editColumnButton, dropColumnButton;
	private JTable columDetailsTable;
	private JToolBar columnToolBar;
	private String tableName,schemaName;
	private ConnectionProperties connectionProperties;
	private Table databaseTable;
	
	public ColumnDetailsPanel(JFrame parent, Table table, 
			ConnectionProperties connectionProperties) {
		if(logger.isDebugEnabled()){
			logger.debug("Populating column details for Table : " + 
					table.getSchemaName() + "." + table.getModelName());
		}
		databaseTable = table;
		this.parentFrame = parent;
		this.tableName = table.getModelName();
		this.schemaName = table.getSchemaName();
		this.connectionProperties = connectionProperties;
		initComponents();
		showColumns(table);
	}
	
	public ColumnDetailsPanel(JFrame parent, String schemaName, String tableName, 
			ConnectionProperties connectionProperties) {
		if(logger.isDebugEnabled()){
			logger.debug("Populating column details for Table : " + 
					schemaName + "." +tableName );
		}
		this.parentFrame = parent;
		this.tableName = tableName;
		this.schemaName = schemaName;
		this.connectionProperties = connectionProperties;
		initComponents();
		showColumns(null);
	}
	
	private void showColumns(Table table) {
		DatabaseMetadataService metadataService = DbexServiceBeanFactory.getBeanFactory()
			.getDatabaseMetadataService();
		if(null == table){
			try {
				table = metadataService.getTableDetails(getConnectionProperties(), schemaName, tableName);
			} catch(Exception e){
				e.printStackTrace();
			}finally{
				
			}
		}
		if(null == table)
			return;
		List<Column> columnList = new ArrayList<Column>();
		if(table != null){
			if(columnList != null){
				columnList = table.getColumnlist();
			}
		}
		columDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columDetailsTable.setCellSelectionEnabled(true);
		columDetailsTable.setModel(new ColumnDetailsTableModel(columnList));
		DesignUtil.updateTableColumnWidth(columDetailsTable);
	}

	private void initComponents() {
		Icon image = null;
		GridBagConstraints gridBagConstraints = null;
		Insets insets = null;
		
		refreshButton = new JButton();
		addColumnButton = new JButton("+");
		editColumnButton = new JButton("E");
		dropColumnButton = new JButton("-");
		columnToolBar = new JToolBar();
		columDetailsTable = new JTable();

		setLayout(new GridBagLayout());
		
		columnToolBar.setFloatable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(IMAGE_PATH
						+ "reload_green.png"));
		refreshButton.setIcon(image);
		refreshButton.addActionListener(this);
		refreshButton.setFocusable(false);
		columnToolBar.add(refreshButton);
		columnToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(IMAGE_PATH
						+ "execution_obj.gif"));
		//addColumnButton.setIcon(image);
		columnToolBar.add(addColumnButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(IMAGE_PATH
						+ "execution_obj.gif"));
		//editColumnButton.setIcon(image);
		columnToolBar.add(editColumnButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(IMAGE_PATH
						+ "execution_obj.gif"));
		//dropColumnButton.setIcon(image);
		columnToolBar.add(dropColumnButton);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(columnToolBar, gridBagConstraints);

		
		
		columDetailsTable.setAutoCreateRowSorter(true);
		columDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		columDetailsTable.setAutoscrolls(true);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		insets = new Insets(1,1,1,1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = insets;
		
		
		
		
		add(new JScrollPane(columDetailsTable), gridBagConstraints);
		
	}

	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(refreshButton)){
			showColumns(getDatabaseTable());
		}
	}

	
	
	public Table getDatabaseTable() {
		return databaseTable;
	}

	public void setDatabaseTable(Table databaseTable) {
		this.databaseTable = databaseTable;
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

}
