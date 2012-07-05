package com.gs.dbex.application.panel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import com.gs.dbex.application.comps.ColumnRow;
import com.gs.dbex.common.ApplicationContextProvider;
import com.gs.dbex.common.enums.ReadDepthEnum;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.PrimaryKey;
import com.gs.dbex.model.db.Table;
import com.gs.dbex.service.DatabaseMetadataService;
import com.gs.utils.text.StringUtil;


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
	private final boolean addRecord;
	private final Map<String, ColumnRow> columnRowMap = new LinkedHashMap<String, ColumnRow>();
	
	public TableRowEditorPanel(final JTable table, String schemaName, String tableName, ConnectionProperties properties, boolean addRecord) {
		this.table = table;
		this.model = table.getModel();
		this.schemaName = schemaName;
		this.tableName = tableName;
		this.primaryKeyColumnNameList = new ArrayList<String>();
		this.notNullColumnNameList = new ArrayList<String>();
		this.connectionProperties = properties;
		this.addRecord = addRecord;
		grabTable();
		setLayout(new GridBagLayout());
		initComponents();
	}
	

	private void grabTable(){
		try{
			databaseTable = 
				(
					(DatabaseMetadataService)ApplicationContextProvider.getInstance().getApplicationContext()
					.getBean(DatabaseMetadataService.BEAN_NAME)
				).getTableDetails(connectionProperties, schemaName, tableName, ReadDepthEnum.DEEP);
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
			Object value = null;
			if(rowNo >= 0){
				value = model.getValueAt(rowNo, i);
			}
			columnRow.setColumnValue(value);
			columnRowMap.put(colName, columnRow);
		}
		setBackground(new Color(255, 255, 255));
		GridBagConstraints gridBagConstraints;
		int rowCount = 0;
		for(String colName : columnRowMap.keySet()){
			ColumnRow columnRow = columnRowMap.get(colName);
			JLabel colNameLabel = new JLabel();
			final JTextField colValueTextField = new JTextField();
			if(primaryKeyColumnNameList.contains(colName)){
				colNameLabel.setText(colName + " [ PK ] ");
				colValueTextField.setEditable(addRecord);
			}else if(notNullColumnNameList.contains(colName)){
				colNameLabel.setText(colName + " [ * ] ");
				colNameLabel.setToolTipText("Not NULL");
				colValueTextField.setEditable(true);
			}else{
				colNameLabel.setText(colName);
				colValueTextField.setEditable(true);
			}
			
			colValueTextField.setName(colName);
			
			colValueTextField.setText(
				(columnRow.getColumnValue() != null) ? columnRow.getColumnValue().toString() : ""	
			);
			
			colValueTextField.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					String name = colValueTextField.getName();
					if(StringUtil.hasValidContent(name)){
						ColumnRow cr = columnRowMap.get(name);
						if(null != cr){
							cr.setColumnValue(colValueTextField.getText());
						}
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					
				}
			});
			colValueTextField.addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
					/*String name = colValueTextField.getName();
					if(StringUtil.hasValidContent(name)){
						ColumnRow cr = columnRowMap.get(name);
						if(null != cr){
							cr.setColumnValue(colValueTextField.getText());
						}
					}*/
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridy = rowCount;
			gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
			gridBagConstraints.insets = new Insets(2, 2, 2, 2);
			add(colNameLabel, gridBagConstraints);
			
			gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = rowCount;
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
			
			rowCount++;
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


	public Map<String, ColumnRow> getColumnRowMap() {
		return columnRowMap;
	}

}
