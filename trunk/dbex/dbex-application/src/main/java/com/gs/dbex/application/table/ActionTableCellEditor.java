/**
 * 
 */
package com.gs.dbex.application.table;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;

/**
 * @author sabuj.das
 *
 */
public abstract class ActionTableCellEditor implements TableCellEditor, ActionListener{ 
    public final Icon DOTDOTDOT_ICON = new ImageIcon(
    		getClass().getResource(//OracleGuiConstants.IMAGE_PATH+
    				"view_blob_clob.png")); 
 
    private TableCellEditor editor; 
    private JButton customEditorButton = new JButton(DOTDOTDOT_ICON); 
 
    protected JTable table; 
    protected int row, column; 
 
    public ActionTableCellEditor(TableCellEditor editor){ 
        this.editor = editor; 
        customEditorButton.addActionListener(this); 
 
        // ui-tweaking 
        customEditorButton.setFocusable(false); 
        customEditorButton.setFocusPainted(false); 
        customEditorButton.setMargin(new Insets(0, 0, 0, 0)); 
    } 
 
    public Component getTableCellEditorComponent(JTable table, Object value
                    , boolean isSelected, int row, int column){ 
        JPanel panel = new JPanel(new BorderLayout()); 
        panel.add(editor.getTableCellEditorComponent(table, value, isSelected, row, column)); 
        panel.add(customEditorButton, BorderLayout.EAST); 
        this.table = table; 
        this.row = row; 
        this.column = column; 
        return panel; 
    } 
 
    public Object getCellEditorValue(){ 
        return editor.getCellEditorValue(); 
    } 
 
    public boolean isCellEditable(EventObject anEvent){ 
        return editor.isCellEditable(anEvent); 
    } 
 
    public boolean shouldSelectCell(EventObject anEvent){ 
        return editor.shouldSelectCell(anEvent); 
    } 
 
    public boolean stopCellEditing(){ 
        return editor.stopCellEditing(); 
    } 
 
    public void cancelCellEditing(){ 
        editor.cancelCellEditing(); 
    } 
 
    public void addCellEditorListener(CellEditorListener l){ 
        editor.addCellEditorListener(l); 
    } 
 
    public void removeCellEditorListener(CellEditorListener l){ 
        editor.removeCellEditorListener(l); 
    } 
 
    public final void actionPerformed(ActionEvent e){ 
        editor.cancelCellEditing(); 
        editCell(table, row, column); 
    } 
 
    protected abstract void editCell(JTable table, int row, int column); 
}