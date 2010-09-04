package com.gs.dbex.application.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Connection;
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
import com.gs.dbex.model.cfg.ConnectionProperties;

public class IndexDetailsPanel extends JPanel {

	private String schemaName; 
	private String tableName;
	private ConnectionProperties connectionProperties;
	private JFrame parentFrame;
	private JButton refreshButton, editIndexButton;
	private JTable indexDetailsTable;
	private JToolBar indexToolBar;
	private ResultSetTableModelFactory resultSetTableModelFactory;
	
	public IndexDetailsPanel(JFrame frame, String schemaName, String tableName,
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
		editIndexButton = new JButton("E");
		indexToolBar = new JToolBar();
		indexDetailsTable = new JTable();

		setLayout(new GridBagLayout());
		
		indexToolBar.setFloatable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "reload_green.png"));
		refreshButton.setIcon(image);
		refreshButton.setFocusable(false);
		indexToolBar.add(refreshButton);
		indexToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//addColumnButton.setIcon(image);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//editColumnButton.setIcon(image);
		indexToolBar.add(editIndexButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//dropColumnButton.setIcon(image);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(indexToolBar, gridBagConstraints);

		
		
		indexDetailsTable.setAutoCreateRowSorter(true);
		indexDetailsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		indexDetailsTable.setCellSelectionEnabled(true);
		indexDetailsTable.setAutoscrolls(true);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		insets = new Insets(1,1,1,1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = insets;
		
		
		OracleDbGrabber dbGrabber = new OracleDbGrabber();
		Connection conn = null;
		try {
			String query = "select * from all_indexes where owner='"+
				schemaName +"' and TABLE_NAME = '" + tableName +"'";
			
			//indexDetailsTable.setModel(resultSetTableModelFactory.getResultSetTableModel(query));
			
		/*} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ 
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		add(new JScrollPane(indexDetailsTable), gridBagConstraints);
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
