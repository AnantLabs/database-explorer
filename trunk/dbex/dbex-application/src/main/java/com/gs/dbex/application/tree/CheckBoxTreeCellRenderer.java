/**
 * 
 */
package com.gs.dbex.application.tree;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.gs.dbex.application.chkbx.TristateCheckBox;

/**
 * @author sabuj.das
 *
 */
public class CheckBoxTreeCellRenderer extends JPanel implements
		TreeCellRenderer {

	private CheckBoxTreeSelectionModel selectionModel; 
    private TreeCellRenderer delegate; 
    private TristateCheckBox checkBox = new TristateCheckBox();
    //private JCheckBox checkBox = new JCheckBox();
 
    public CheckBoxTreeCellRenderer(TreeCellRenderer delegate, CheckBoxTreeSelectionModel selectionModel){ 
        this.delegate = delegate; 
        this.selectionModel = selectionModel; 
        setLayout(new BorderLayout()); 
        setOpaque(false); 
        checkBox.setOpaque(false); 
        checkBox.setState(TristateCheckBox.SELECTED);
    } 
 
 
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus){ 
        Component renderer = delegate.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus); 
 
        TreePath path = tree.getPathForRow(row); 
        if(path!=null){ 
        	if(selectionModel.isPathSelected(path, true)) 
                checkBox.setState(TristateCheckBox.SELECTED); 
            else 
                checkBox.setState(selectionModel.isPartiallySelected(path) ? null : TristateCheckBox.NOT_SELECTED);  
        } 
        removeAll(); 
        add(checkBox, BorderLayout.WEST); 
        add(renderer, BorderLayout.CENTER); 
        return this; 
    } 

}
