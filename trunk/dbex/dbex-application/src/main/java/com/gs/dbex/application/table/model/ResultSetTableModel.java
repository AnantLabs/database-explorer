/**
 * 
 */
package com.gs.dbex.application.table.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.gs.dbex.application.button.BlobButton;
import com.gs.dbex.application.constants.GuiCommandConstants;
import com.gs.utils.jdbc.JdbcUtil;

/**
 * @author sabuj.das
 *
 */
public class ResultSetTableModel implements TableModel, ActionListener{
	
	private ResultSet resultSet;
	private ResultSetMetaData resultSetMetaData;
	private int columnCount;
	private int rowCount;
	
	public ResultSetTableModel(ResultSet resultSet) throws SQLException {
		setResultSet(resultSet);
	}

	public ResultSetTableModel(ResultSet resultSet, int rows) throws SQLException {
		rowCount = rows;
		setResultSet(resultSet);
	}

	public void setResultSet(ResultSet rs) throws SQLException {
		// set the resultset
		this.resultSet = rs;
		// get the resultset metadata from the resultset
		this.resultSetMetaData = rs.getMetaData();
		// get column count from the metadata
		this.columnCount = resultSetMetaData.getColumnCount();
		// move to the last row of the resultset
		if(ResultSet.TYPE_SCROLL_INSENSITIVE == rs.getType()){
			rs.last();
			this.rowCount = rs.getRow();
		}
		
	}
	
	

	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	public Class<?> getColumnClass(int columnIndex) {
		Class type = String.class;
		try {
			int columnType = resultSetMetaData.getColumnType(columnIndex + 1);
			type = JdbcUtil.getJavaClassForSqlType(columnType);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return type;
	}

	
	public int getColumnCount() {
		return columnCount;
	}

	
	public String getColumnName(int columnIndex) {
		try {
			return resultSetMetaData.getColumnLabel(columnIndex + 1);
		} catch (SQLException e) {
			return e.toString();
		}
	}

	
	public int getRowCount() {
		return rowCount;
	}

	
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			if(ResultSet.TYPE_SCROLL_INSENSITIVE == getResultSet().getType()){
				getResultSet().absolute(rowIndex + 1); // Go to the specified row
			}else{
				int i = 0;
				while(getResultSet().next()){
					if(i == rowIndex)
						break;
					i++;
				}
			}
			
			Object cellValue = resultSet.getObject(columnIndex + 1); // Get value of the column
			if (cellValue == null)
				return null;
			else if(cellValue instanceof Blob){
				return "BLOB [view is not implemented.]";
			}
			else{
				/*int columnType = resultSetMetaData.getColumnType(columnIndex + 1);
				if(o instanceof java.util.Date){
					SimpleDateFormat dateFormat = new SimpleDateFormat(OracleGuiConstants.INSERT_DATE_FORMAT);
					java.util.Date utilDate = (java.util.Date) o;
					String s = dateFormat.format(utilDate);
					o = s;
				} else if(o instanceof java.sql.Date){
					SimpleDateFormat dateFormat = new SimpleDateFormat(OracleGuiConstants.INSERT_DATE_FORMAT);
					java.sql.Date sqlDate = (java.sql.Date) o;
					String s = dateFormat.format(sqlDate);
					o = s;
				} else if(o instanceof java.sql.Timestamp){
					SimpleDateFormat dateFormat = new SimpleDateFormat(OracleGuiConstants.INSERT_DATE_FORMAT);
					java.sql.Timestamp ts = (java.sql.Timestamp) o;
					java.util.Date utilDate = new java.util.Date();
					utilDate.setTime(ts.getTime());
					String s = dateFormat.format(utilDate);
					o = s;
				} */ 
				return cellValue;
			}
				
		} catch (SQLException e) {
			return e.getMessage();
		} 
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

	public ResultSet getResultSet() {
		return resultSet;
	}

	
	protected void finalize() throws Throwable {
		closeResultSet();
	}

	public void closeResultSet(){
		try{
			if(resultSet.getStatement() != null){
				resultSet.getStatement().close();
			}
		}catch(SQLException sqle){
			
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		if(GuiCommandConstants.VIEW_BLOB_TEXT_ACT_CMD.equals(e.getActionCommand())){
			Blob data = ((BlobButton)e.getSource()).getBlobData();
			try {
				String s = JdbcUtil.readFromBlob(data);
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

}
/*
class JTableButtonRenderer implements TableCellRenderer {
	private TableCellRenderer defaultRenderer;

	public JTableButtonRenderer(TableCellRenderer renderer) {
		defaultRenderer = renderer;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Component)
			return (Component) value;
		return defaultRenderer.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
	}
}

class JTableButtonMouseListener implements MouseListener {
	private JTable table;
	

	private void forwardEventToButton(MouseEvent e) {
		TableColumnModel columnModel = table.getColumnModel();
		int column = columnModel.getColumnIndexAtX(e.getX());
		int row = e.getY() / table.getRowHeight();
		Object value;
		JButton button;
		MouseEvent buttonEvent;

		if (row >= table.getRowCount() || row < 0
				|| column >= table.getColumnCount() || column < 0)
			return;

		value = table.getValueAt(row, column);

		if (!(value instanceof JButton))
			return;

		button = (JButton) value;

		buttonEvent = (MouseEvent) SwingUtilities.convertMouseEvent(table, e,
				button);
		button.dispatchEvent(buttonEvent);
		// This is necessary so that when a button is pressed and released
		// it gets rendered properly. Otherwise, the button may still appear
		// pressed down when it has been released.
		table.repaint();
	}

	public JTableButtonMouseListener(JTable table) {
		this.table = table;
	}

	public void mouseClicked(MouseEvent e) {
		forwardEventToButton(e);
	}

	public void mouseEntered(MouseEvent e) {
		forwardEventToButton(e);
	}

	public void mouseExited(MouseEvent e) {
		forwardEventToButton(e);
	}

	public void mousePressed(MouseEvent e) {
		forwardEventToButton(e);
	}

	public void mouseReleased(MouseEvent e) {
		forwardEventToButton(e);
	}
}
*/