/**
 * 
 */
package com.gs.dbex.application.table.renderer;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import com.gs.dbex.application.table.BlobTableCell;

/**
 * @author sabuj.das
 *
 */
public class BlobButtonCellRenderer extends AbstractCellEditor implements
		TableCellRenderer, TableCellEditor {

	BlobTableCell editCell;
    
    public BlobButtonCellRenderer() {
        
    }
    
    public Component getTableCellRendererComponent(JTable table,
        Object value, boolean isSelected, boolean hasFocus, int row, int column) {
             
        return (BlobTableCell)value;
    }         
     
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column)  {

        editCell = (BlobTableCell)value;
        return editCell;
    }

    public Object getCellEditorValue() {        
        return editCell;
    }

}
