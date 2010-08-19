/**
 * 
 */
package com.gs.dbex.application.table.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.gs.dbex.model.common.ColumnHeader;
import com.gs.dbex.model.db.Column;

/**
 * @author Green Moon
 *
 */
public class ColumnDetailsTableModel implements TableModel {

	private List<Column> dataList = new ArrayList<Column>();
	private List<String> columnNameList = new ArrayList<String>(100);
	private int columnCount;
	private int rowCount;
	

	public ColumnDetailsTableModel(List<Column> data) {
		this.dataList = data;
		prepareModel();
	}
	
	private void prepareModel(){
		rowCount = dataList.size();
		Method[] ms = Column.class.getMethods();
		for (Method method : ms) {
			if(method.getName().startsWith("get") ){
				ColumnHeader ch = method.getAnnotation(ColumnHeader.class);
				if(ch != null){
					columnNameList.add(ch.title());
				}
			}
		}
		columnCount = columnNameList.size();
		
	}
	
	

	
	
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	
	
	public Class<?> getColumnClass(int columnIndex) {
		Method[] ms = Column.class.getMethods();
		for (Method method : ms) {
			if(method.getName().startsWith("get") && method.isAnnotationPresent(ColumnHeader.class)){
				ColumnHeader ch = method.getAnnotation(ColumnHeader.class);
				if(ch != null){
					if(columnIndex == ch.index())
						return method.getReturnType();
				}
			}
		}
		return String.class;
	}

	
	
	public int getColumnCount() {
		return columnCount;
	}

	
	
	public String getColumnName(int columnIndex) {
		Method[] ms = Column.class.getMethods();
		for (Method method : ms) {
			if(method.getName().startsWith("get") && method.isAnnotationPresent(ColumnHeader.class)){
				ColumnHeader ch = method.getAnnotation(ColumnHeader.class);
				if(ch != null){
					if(columnIndex == ch.index())
						return ch.title();
				}
			}
		}
		return "";
	}

	
	public int getRowCount() {
		return rowCount;
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		Column column = dataList.get(rowIndex);
		Method[] ms = column.getClass().getMethods();
		for (Method method : ms) {
			if(method.getName().startsWith("get") && method.isAnnotationPresent(ColumnHeader.class)){
				ColumnHeader ch = method.getAnnotation(ColumnHeader.class);
				if(ch != null){
					if(columnIndex == ch.index())
						try {
							return method.invoke(column, null);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
				}
			}
		}
		return "";
	}

	
	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub

	}

	
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub

	}

	public List<Column> getDataList() {
		return dataList;
	}

	public void setDataList(List<Column> dataList) {
		this.dataList = dataList;
		prepareModel();
	}

	public List<String> getColumnNameList() {
		return columnNameList;
	}

	public void setColumnNameList(List<String> columnNameList) {
		this.columnNameList = columnNameList;
	}

	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}




}
