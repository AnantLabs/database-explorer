/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import oracle.sql.ROWID;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.dlg.QuickEditDialog;
import com.gs.dbex.application.dlg.ResultFilterDialog;
import com.gs.dbex.application.table.model.ResultSetTableModelFactory;
import com.gs.dbex.application.util.DisplayTypeEnum;
import com.gs.dbex.application.util.DisplayUtils;
import com.gs.dbex.application.util.MenuBarUtil;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.model.vo.QuickEditVO;

/**
 * @author sabuj.das
 *
 */
public class TableContentPanel extends JPanel implements ActionListener, MouseListener{
	private String schemaName; 
	private String tableName;
	private ConnectionProperties connectionProperties;
	private Table databaseTable;
	private JButton refreshButton, addRecordButton, editRecordButton, deleteRecordButton,
		filterDataButton;
	private JTable sampleContentTable;
	private JToolBar contentToolBar;
	private ResultSetTableModelFactory resultSetTableModelFactory;
	private String queryString;
	private String currentFilter = "";
	private JFrame parentFrame;
	private String []selectedColumns;
	
	
	
	public TableContentPanel(String schemaName, String tableName,
			ConnectionProperties connectionProperties, Table table) {
		this(schemaName, tableName, connectionProperties, table, null);
	}
	
	public TableContentPanel(String schemaName, String tableName,
			ConnectionProperties connectionProperties, Table table, String []selectedColumns) {
		this.schemaName = schemaName; 
		this.tableName = tableName;
		this.connectionProperties = connectionProperties;
		this.databaseTable = table;
		this.selectedColumns = selectedColumns;
		try {
			this.resultSetTableModelFactory = new ResultSetTableModelFactory(
					connectionProperties.getDataSource().getConnection());
		} catch (SQLException e) {
			DisplayUtils.displayMessage(null, e.getMessage(), DisplayTypeEnum.ERROR);
		}
		String comaSeparatedColumns = " ";
		if(null != selectedColumns && selectedColumns.length > 0){
			for(int i=0; i<selectedColumns.length; i++){
				comaSeparatedColumns += selectedColumns[i];
				if(i != selectedColumns.length-1){
					comaSeparatedColumns += ", ";
				}
			}
			comaSeparatedColumns += " ";
		} else {
			comaSeparatedColumns = " * ";
		}
		
		queryString = "SELECT "+ comaSeparatedColumns +" FROM " + schemaName + "." + tableName.toUpperCase();
		initComponents();
		
		showContent(queryString);
	}

	private void showContent(String query) {
		OracleDbGrabber dbGrabber = new OracleDbGrabber();
		Connection conn = null;
		try {
			sampleContentTable.setModel(resultSetTableModelFactory.getResultSetTableModel(query));
		}catch(Exception e){
			DisplayUtils.displayMessage(null, e.getMessage(), DisplayTypeEnum.ERROR);
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
		
	}

	private void initComponents() {
		Icon image = null;
		GridBagConstraints gridBagConstraints = null;
		Insets insets = null;
		
		refreshButton = new JButton();
		addRecordButton = new JButton();
		editRecordButton = new JButton();
		deleteRecordButton = new JButton();
		filterDataButton = new JButton();
		contentToolBar = new JToolBar();
		sampleContentTable = new JTable();

		setLayout(new GridBagLayout());
		
		refreshButton.setToolTipText("Refresh");
		addRecordButton.setToolTipText("Add Record");
		editRecordButton.setToolTipText("Edit Record");
		deleteRecordButton.setToolTipText("Delete Record");
		filterDataButton.setToolTipText("Apply Filter");
		
		refreshButton.addActionListener(this);
		addRecordButton.addActionListener(this);
		editRecordButton.addActionListener(this);
		deleteRecordButton.addActionListener(this);
		filterDataButton.addActionListener(this);
		
		contentToolBar.setFloatable(false);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "reload_green.png"));
		refreshButton.setIcon(image);
		refreshButton.setFocusable(false);
		contentToolBar.add(refreshButton);
		contentToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "add_plus.png"));
		addRecordButton.setIcon(image);
		addRecordButton.setFocusable(false);
		contentToolBar.add(addRecordButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "editor_area.gif"));
		editRecordButton.setIcon(image);
		editRecordButton.setFocusable(false);
		contentToolBar.add(editRecordButton);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "delete_edit.gif"));
		deleteRecordButton.setIcon(image);
		deleteRecordButton.setFocusable(false);
		contentToolBar.add(deleteRecordButton);
		contentToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "systemfilterpool.gif"));
		filterDataButton.setIcon(image);
		filterDataButton.setFocusable(false);
		contentToolBar.add(filterDataButton);
		
		contentToolBar.add(new JToolBar.Separator());
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//addColumnButton.setIcon(image);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//editColumnButton.setIcon(image);
		image = new ImageIcon(MenuBarUtil.class
				.getResource(ApplicationConstants.IMAGE_PATH
						+ "execution_obj.gif"));
		//dropColumnButton.setIcon(image);
		
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(contentToolBar, gridBagConstraints);

		
		
		sampleContentTable.setAutoCreateRowSorter(true);
		sampleContentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		sampleContentTable.setCellSelectionEnabled(true);
		sampleContentTable.setAutoscrolls(true);
		sampleContentTable.addMouseListener(this);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		insets = new Insets(1,1,1,1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = insets;
		
		add(new JScrollPane(sampleContentTable), gridBagConstraints);
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

	public Table getDatabaseTable() {
		return databaseTable;
	}

	public void setDatabaseTable(Table databaseTable) {
		this.databaseTable = databaseTable;
	}

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public String[] getSelectedColumns() {
		return selectedColumns;
	}

	public void setSelectedColumns(String[] selectedColumns) {
		this.selectedColumns = selectedColumns;
	}

	
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource().equals(refreshButton)){
			showContent(queryString);
		} else if(evt.getSource().equals(filterDataButton)){
			applyFilter();
		} 
	}

	private void applyFilter() {
		ResultFilterDialog filterDialog = new ResultFilterDialog(null, true, connectionProperties.getConnectionName());
		filterDialog.setFilterQuery(currentFilter);
		filterDialog.setInputQuery(queryString);
		filterDialog.setAlwaysOnTop(true);
		filterDialog.setLocation(100, 100);
		int opt = filterDialog.showFilterDialog();
		if(opt == ApplicationConstants.APPLY_OPTION){
			currentFilter = filterDialog.getFilterQuery();
			showContent(filterDialog.getOutputQuery());
		}
	}

	
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2){
			if(e.getSource().equals(sampleContentTable)){
				if(getDatabaseTable() == null)
					return;
				
				QuickEditVO vo = new QuickEditVO();
				vo.setTableName(getDatabaseTable().getModelName());
				vo.setSchemaName(getDatabaseTable().getSchemaName());
				
				int columnIndex = sampleContentTable.getSelectedColumn();
				int rowIndex = sampleContentTable.getSelectedRow();
				int columnCount = sampleContentTable.getColumnCount();
				String q = "SELECT ROWID, ORA_ROWSCN FROM " + vo.getSchemaName() + "." + vo.getTableName() + " WHERE ";
				StringBuffer qbuf = new StringBuffer("SELECT ROWID, ORA_ROWSCN FROM ");
				qbuf.append(vo.getSchemaName())
					.append(".")
					.append(vo.getTableName())
					.append(" WHERE ");
					
				for (int i = 0; i < columnCount; i++) {
					Object value = sampleContentTable.getModel().getValueAt(rowIndex, i);
					
					if(value == null){
						q += sampleContentTable.getModel().getColumnName(i) + " IS NULL ";
						if(i != columnCount-1){
							q += "AND ";
						}
						continue;
					}
					
					Class clazz = sampleContentTable.getColumnClass(i);
					if(clazz.getCanonicalName().equalsIgnoreCase("java.util.Date") 
							|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Date")
							|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Timestamp")
							|| clazz.getCanonicalName().equalsIgnoreCase("java.sql.Time")){
						SimpleDateFormat dateFormat = new SimpleDateFormat(ApplicationConstants.INSERT_DATE_FORMAT);
						if(value instanceof java.util.Date){
							java.util.Date utilDate = (java.util.Date) value;
							value = ApplicationConstants.SQL_DATE_FUNCTION + "('" +
								dateFormat.format(utilDate) + "', " + ApplicationConstants.SQL_DATE_FORMAT + ")";
						}
						q += sampleContentTable.getModel().getColumnName(i) + " = "
							+ (
							(value != null) ? value.toString() : "") + " ";
						if(i != columnCount-1){
							q += "AND ";
						}
						continue;
					}
						
					q += sampleContentTable.getModel().getColumnName(i) + " = '"
						+ (
						(value != null) ? value.toString() : "") + "' ";
					if(i != columnCount-1){
						q += "AND ";
					}
				}
				Connection con = null;
				try{
					con = getConnectionProperties().getDataSource().getConnection();
					Statement stmt = con.prepareStatement(q);
					ResultSet rs = stmt.executeQuery(q);
					if(rs == null)
						return;
					while(rs.next()){
						ROWID rid = (ROWID) rs.getObject("ROWID");
						if(rid != null){
							vo.setRowid(rid.stringValue());
						}
						String x = rs.getString("ORA_ROWSCN");
						if(x != null){
							vo.setOraRowscn(x);
						}
					}
				}catch(Exception ex){
					return;
				}finally{
					if(con != null){
						try {
							con.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
				vo.setCurrentColumnName(sampleContentTable.getModel().getColumnName(columnIndex));
				Object value = sampleContentTable.getModel().getValueAt(rowIndex, columnIndex);
				vo.setCurrentColumnValue((value != null) ? value.toString() : "");
				vo.setConnectionProperties(getConnectionProperties());
				openQuickEditDialog(vo);
			
			}
		}
	}

	public void openQuickEditDialog(QuickEditVO vo) {
		QuickEditDialog editDialog = new QuickEditDialog(getParentFrame(), vo);
		int opt = editDialog.showDialog();
		if(opt == ApplicationConstants.APPLY_OPTION){
			showContent(this.queryString);
		}
	}

	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
