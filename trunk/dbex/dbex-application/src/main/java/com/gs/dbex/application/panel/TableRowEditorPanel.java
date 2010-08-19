package com.gs.dbex.application.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.gs.dbex.application.comps.ColumnRow;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.core.oracle.OracleDbGrabber;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Table;


/**
 * @author sabuj.das
 *
 */
public class TableRowEditorPanel extends JPanel implements ActionListener {

	/**
	 * serialVersionUID = 2375662655300316343L
	 */
	private static final long serialVersionUID = 2375662655300316343L;
	
	private JTable table;
	private TableModel model;
	private ConnectionProperties connectionProperties;
	private String schemaName, tableName;
	
	private Table databaseTable;
	private List<String> primaryKeyColumnNameList;
	private List<String> notNullColumnNameList;
	
	private Map<String, ColumnRow> columnRowMap = new HashMap<String, ColumnRow>();
	
	public TableRowEditorPanel(final JTable table, String schemaName, String tableName, ConnectionProperties properties) {
		this.table = table;
		this.model = table.getModel();
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.primaryKeyColumnNameList = new ArrayList<String>();
		this.notNullColumnNameList = new ArrayList<String>();
		this.connectionProperties = properties;
		grabTable();
		setLayout(new GridBagLayout());
		initComponents();
	}
	

	private void grabTable(){
		Connection con = null;
		try{
			con = getConnectionProperties().getDataSource().getConnection();
			databaseTable = new OracleDbGrabber().grabTable(con, schemaName, tableName, ReadDepthEnum.DEEP);
			List<PrimaryKey> primaryKeyList = databaseTable.getPrimaryKeys();
			for (PrimaryKey pk : primaryKeyList) {
				primaryKeyColumnNameList.add(pk.getColumnName());
			}
			for(Column c : databaseTable.getColumnlist()){
				if(!c.getNullable()){
					notNullColumnNameList.add(c.getModelName());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void initComponents(){
		int rowNo = table.getSelectedRow();
		int colCount = model.getColumnCount();
		int maxColumnLength = 0;
		for (int i=0; i < colCount; i++) {
			ColumnRow columnRow = new ColumnRow();
			String colName = table.getColumnName(i);
			if(maxColumnLength <= colName.length()){
				maxColumnLength = colName.length();
			}
			columnRow.setColumnName(colName);
			Class clazz = table.getColumnClass(i);
			columnRow.setColumnClass(clazz);
			Object value = model.getValueAt(rowNo, i);
			columnRow.setColumnValue(value);
			columnRowMap.put(colName, columnRow);
		}
		setBackground(new Color(255, 255, 255));
		GridBagConstraints gridBagConstraints;
		int i = 0;
		for(String colName : columnRowMap.keySet()){
			ColumnRow columnRow = columnRowMap.get(colName);
			JLabel colNameLabel = new JLabel();
			JTextField colValueTextField = new JTextField();
			if(primaryKeyColumnNameList.contains(colName)){
				colNameLabel.setText(colName + " [ PK ] ");
				colValueTextField.setEditable(false);
			}else if(notNullColumnNameList.contains(colName)){
				colNameLabel.setText(colName + " [ * ] ");
				colNameLabel.setToolTipText("Not NULL");
				colValueTextField.setEditable(true);
			}else{
				colNameLabel.setText(colName);
				colValueTextField.setEditable(true);
			}
			
			
			colValueTextField.setText(
				(columnRow.getColumnValue() != null) ? columnRow.getColumnValue().toString() : ""	
			);
			
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = i;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			add(colNameLabel, gridBagConstraints);
			
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = i;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			add(colValueTextField, gridBagConstraints);
			
			/*gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 2;
			gridBagConstraints.gridy = i;
			gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			add(new JLabel("<" + columnRow.getColumnClass().getName() + ">"), gridBagConstraints);*/
			
			i++;
		}
	}
	
	
	/**
	 * @return the table
	 */
	public JTable getTable() {
		return table;
	}





	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the model
	 */
	public TableModel getModel() {
		return model;
	}

	/**
	 * @return the connectionProperties
	 */
	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param connectionProperties the connectionProperties to set
	 */
	public void setConnectionProperties(ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
