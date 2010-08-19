package com.gs.dbex.application.table;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellEditor;

import com.gs.utils.jdbc.JdbcUtil;

public class StringActionTableCellEditor extends ActionTableCellEditor{ 
	
    public StringActionTableCellEditor(TableCellEditor editor){ 
        super(editor); 
    } 
 
    protected void editCell(JTable table, int row, int column){ 
        JTextArea textArea = new JTextArea(10, 50); 
        Object value = table.getValueAt(row, column); 
        if(value!=null){ 
        	if(value instanceof Blob){
        		try {
					textArea.setText(JdbcUtil.readFromBlob((Blob) value));
				} catch (SQLException e) {
					textArea.setText(e.getMessage());
				} catch (IOException e) {
					textArea.setText(e.getMessage());
				}
        	}else
        		textArea.setText((String)value); 
            textArea.setCaretPosition(0); 
        } 
        int result = JOptionPane.showOptionDialog(table 
                , new JScrollPane(textArea), (String)table.getColumnName(column) 
                , JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null); 
        if(result==JOptionPane.OK_OPTION) 
            table.setValueAt(textArea.getText(), row, column); 
    } 
} 
