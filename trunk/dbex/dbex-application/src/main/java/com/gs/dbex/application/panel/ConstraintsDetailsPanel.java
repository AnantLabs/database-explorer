/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.table.model.ResultSetTableModelFactory;
import com.gs.dbex.application.util.MenuBarUtil;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.design.util.DrawingUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.dbex.service.DbexServiceBeanFactory;

/**
 * @author sabuj.das
 *
 */
public class ConstraintsDetailsPanel extends JPanel {

	private JFrame parentFrame;
	private String schemaName; 
	private String tableName;
	private ConnectionProperties connectionProperties;
	
	private JButton refreshButton, addConstraintButton, editConstraintButton, dropConstraintButton;
	private JTable constraintDetailsTable;
	private JToolBar constraintToolBar;
	private ResultSetTableModelFactory resultSetTableModelFactory;
	
	public ConstraintsDetailsPanel(JFrame frame, String schemaName, String tableName,
			ConnectionProperties connectionProperties) {
		this.parentFrame = frame;
		this.schemaName = schemaName; 
		this.tableName = tableName;
		this.connectionProperties = connectionProperties;
		
		try {
			this.resultSetTableModelFactory = new ResultSetTableModelFactory(
					connectionProperties.getDataSource().getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		initComponents();
	}

	private void initComponents() {
		Icon image = null;
		GridBagConstraints gridBagConstraints = null;
		Insets insets = null;
		
		refreshButton = new JButton();
		addConstraintButton = new JButton("+");
		editConstraintButton = new JButton("E");
		dropConstraintButton = new JButton("-");
		constraintToolBar = new JToolBar();
		constraintDetailsTable = new JTable();

		setLayout(new GridBagLayout());
		
		constraintToolBar.setFloatable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "reload_green.png"));
		refreshButton.setIcon(image);
		refreshButton.setFocusable(false);
		constraintToolBar.add(refreshButton);
		constraintToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//addColumnButton.setIcon(image);
		constraintToolBar.add(addConstraintButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//editColumnButton.setIcon(image);
		constraintToolBar.add(editConstraintButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//dropColumnButton.setIcon(image);
		constraintToolBar.add(dropConstraintButton);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(constraintToolBar, gridBagConstraints);

		
		
		constraintDetailsTable.setAutoCreateRowSorter(true);
		constraintDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		constraintDetailsTable.setCellSelectionEnabled(true);
		constraintDetailsTable.setAutoscrolls(true);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		insets = new Insets(1,1,1,1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = insets;
		
		
		DatabaseMetadataService metadataService = DbexServiceBeanFactory.getBeanFactory().getDatabaseMetadataService();
		
		try {
			ResultSet resultSet = metadataService.getAllConstraints(connectionProperties, resultSetTableModelFactory.getConnection(), schemaName, tableName);
			constraintDetailsTable.setModel(resultSetTableModelFactory.getResultSetTableModel(resultSet));
			constraintDetailsTable.updateUI();
			DrawingUtil.updateTableColumnWidth(constraintDetailsTable);
		}catch(Exception e){
			e.printStackTrace();
		}
		add(new JScrollPane(constraintDetailsTable), gridBagConstraints);
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
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
