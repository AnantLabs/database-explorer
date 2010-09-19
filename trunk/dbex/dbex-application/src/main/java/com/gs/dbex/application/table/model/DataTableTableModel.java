package com.gs.dbex.application.table.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.gs.utils.jdbc.ResultSetDataTable;

public class DataTableTableModel implements TableModel, ActionListener {

	private ResultSetDataTable resultSetDataTable;
	private int columnCount;
	private int rowCount;

	public DataTableTableModel(ResultSetDataTable resultSetDataTable) {
		this.resultSetDataTable = resultSetDataTable;
		if(null != this.resultSetDataTable){
			populateTableModel();
		}
	}

	private void populateTableModel() {
		columnCount = resultSetDataTable.getColumnCount();
		rowCount = resultSetDataTable.getRowCount();
	}

	public ResultSetDataTable getResultSetDataTable() {
		return resultSetDataTable;
	}

	public void setResultSetDataTable(ResultSetDataTable resultSetDataTable) {
		this.resultSetDataTable = resultSetDataTable;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getRowCount() {
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return resultSetDataTable.getColumnnames()[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return resultSetDataTable.getColumnClassMap().get(getColumnName(columnIndex));
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(null != resultSetDataTable.getDataTable()){
			return resultSetDataTable.getDataTable()[rowIndex][ columnIndex];
		}
		return "";
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}

}
