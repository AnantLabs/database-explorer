/**
 * 
 */
package com.gs.dbex.application.iframe;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import oracle.jdbc.pool.OracleDataSource;

import org.apache.log4j.Logger;
import org.omg.CORBA.portable.ApplicationException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.panel.DatabaseDirectoryPanel;
import com.gs.dbex.application.panel.SqlQueryPanel;
import com.gs.dbex.application.tab.ButtonTabComponent;
import com.gs.dbex.application.tree.DatabaseDirectoryTree;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.core.GenericDataSource;
import com.gs.dbex.model.DatabaseReservedWordsUtil;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.common.ConnectionBasedReservedWords;
import com.gs.dbex.model.common.DatabaseReservedWords;
import com.gs.dbex.model.db.Database;
import com.gs.dbex.model.db.Schema;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DbexServiceBeanFactory;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * @author sabuj.das
 * 
 */
public class DatabaseViewerInternalFrame extends JInternalFrame implements
		WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6258515577551371086L;

	private static final Logger logger = Logger
			.getLogger(DatabaseViewerInternalFrame.class);

	private JFrame parentFrame;

	private ConnectionProperties connectionProperties;

	private JSplitPane outterSplitPane, innerSplitPane;
	private JPanel mainPanel;
	private JTabbedPane dbDetailsTabbedPane;

	private JTabbedPane dbViewerTabbedPane;

	private List<String> schemaNameList = new ArrayList<String>();
	private List<String> tableNameList = new ArrayList<String>();

	public DatabaseViewerInternalFrame(JFrame parent,
			ConnectionProperties connectionProperties) {
		DatabaseReservedWordsUtil util = DatabaseReservedWordsUtil.getInstance();
		if(util.getConnectionBasedReservedWords(connectionProperties.getConnectionName()) == null){
			ConnectionBasedReservedWords connectionBasedReservedWords = new ConnectionBasedReservedWords();
			connectionBasedReservedWords.setConnectionName(connectionProperties.getConnectionName());
			connectionBasedReservedWords.setConnectionProperties(connectionProperties);
			util.addConnectionBasedReservedWords(connectionProperties.getConnectionName(), connectionBasedReservedWords);
		}
		
		setName(getClass().getCanonicalName());

		parentFrame = parent;
		this.connectionProperties = connectionProperties;
		Database database = getDataBaseInformation(ReadDepthEnum.SHALLOW);
		initComponents(database);
	}

	private Database getDataBaseInformation(ReadDepthEnum readDepth) {
		logger.info("STRT: Reading Database. " + readDepth.getCode());
		Database db = null;
		if (connectionProperties != null) {
			try {
				db = DbexServiceBeanFactory
						.getBeanFactory()
						.getDatabaseMetadataService()
						.getDatabaseDetails(connectionProperties,
								ReadDepthEnum.SHALLOW);
				if (null != db) {
					db.setModelName(connectionProperties.getConnectionName());
				}
			} catch (DbexException e) {
				logger.error(e);
			}
		}
		logger.info("DONE: Reading Database.");
		if (db != null) {
			List<Schema> schemaList = db.getSchemaList();
			for (Schema schema : schemaList) {
				schemaNameList.add(schema.getModelName());
				ApplicationConstants.SQL_KEYWORD_LIST.add(schema.getModelName()
						.toLowerCase());
				List<Table> tablelList = schema.getTableList();
				for (Table table : tablelList) {
					tableNameList.add(table.getModelName());
					ApplicationConstants.SQL_KEYWORD_LIST.add(table
							.getModelName().toLowerCase());
				}
			}
		}
		return db;
	}

	/**
	 * @return the schemaNameList
	 */
	public List<String> getSchemaNameList() {
		return schemaNameList;
	}

	/**
	 * @return the tableNameList
	 */
	public List<String> getTableNameList() {
		return tableNameList;
	}

	public void refreshConnectionScheduler(
			ConnectionProperties connectionProperties) {

	}

	public void refreshConnection(ConnectionProperties connectionProperties) {

	}

	private void initComponents(Database database) {
		if (connectionProperties != null)
			setTitle((connectionProperties.getDatabaseConfiguration()
					.getUserName() + " @ " + connectionProperties
					.getDatabaseConfiguration().getHostName()).toUpperCase());

		setLocation(0, 0);
		setSize(600, 450);
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		outterSplitPane = new JSplitPane();
		outterSplitPane.setDividerLocation(150);
		outterSplitPane.setContinuousLayout(true);
		outterSplitPane.setOneTouchExpandable(true);
		outterSplitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);

		DatabaseDirectoryPanel directoryPanel = new DatabaseDirectoryPanel(
				getParentFrame(), new DatabaseDirectoryTree(
						getConnectionProperties(), database),
				getConnectionProperties());
		directoryPanel.setParentComponent(this);
		outterSplitPane.setLeftComponent(directoryPanel);
		mainPanel.add(outterSplitPane, BorderLayout.CENTER);

		dbDetailsTabbedPane = new JTabbedPane();
		dbDetailsTabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		SqlQueryPanel panel = new SqlQueryPanel((JComponent) getParent(),
				getConnectionProperties());
		panel.setParentFrame(parentFrame);

		dbDetailsTabbedPane.addTab("SQL", panel);
		int n = dbDetailsTabbedPane.getTabCount();
		dbDetailsTabbedPane.setTabComponentAt(
				n - 1,
				new ButtonTabComponent(dbDetailsTabbedPane, new ImageIcon(
						DatabaseViewerInternalFrame.class
								.getResource(ApplicationConstants.IMAGE_PATH
										+ "executesql.gif"))));
		dbDetailsTabbedPane.setSelectedIndex(n - 1);
		outterSplitPane.setRightComponent(dbDetailsTabbedPane);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// pack();
	}

	public JTabbedPane getDbDetailsTabbedPane() {
		return dbDetailsTabbedPane;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public void setConnectionProperties(
			ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JSplitPane getOutterSplitPane() {
		return outterSplitPane;
	}

	public void setOutterSplitPane(JSplitPane outterSplitPane) {
		this.outterSplitPane = outterSplitPane;
	}

	public JSplitPane getInnerSplitPane() {
		return innerSplitPane;
	}

	public void setInnerSplitPane(JSplitPane innerSplitPane) {
		this.innerSplitPane = innerSplitPane;
	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		closeWindow();
	}

	public void closeWindow() {
		if(connectionProperties.getDataSource() != null){
			logger.info("Closing Datasource"); 
			try {
				if(connectionProperties.getDataSource() instanceof OracleDataSource){
					OracleDataSource dataSource = (OracleDataSource) connectionProperties.getDataSource(); 
					dataSource.close();
				}
				else if(connectionProperties.getDataSource() instanceof MysqlDataSource){
					MysqlDataSource dataSource = (MysqlDataSource) connectionProperties.getDataSource();
					dataSource = null;
				}
				else if(connectionProperties.getDataSource() instanceof SQLServerDataSource){
					SQLServerDataSource dataSource = (SQLServerDataSource) connectionProperties.getDataSource();
					dataSource = null;
				}
				else if(connectionProperties.getDataSource() instanceof GenericDataSource){
					GenericDataSource dataSource = (GenericDataSource) connectionProperties.getDataSource();
					dataSource = null;
				} 
				else {
					connectionProperties.setDataSource(null);
				}
			}catch (SQLException e) {
				DisplayUtils.displayMessage(getParentFrame(), e.getMessage(),DisplayTypeEnum.ERROR);
			}
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
}
