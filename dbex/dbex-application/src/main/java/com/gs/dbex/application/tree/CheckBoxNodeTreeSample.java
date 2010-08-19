package com.gs.dbex.application.tree;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class CheckBoxNodeTreeSample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("CheckBox Tree");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("TableName");
		
	    CheckBoxTreeNode accessibilityOptions[] = {
	        new CheckBoxTreeNode(
	            "Move system caret with focus/selection changes", false),
	        new CheckBoxTreeNode("Always expand alt text for images", true) };
	    CheckBoxTreeNode browsingOptions[] = {
	        new CheckBoxTreeNode("Notify when downloads complete", true),
	        new CheckBoxTreeNode("Disable script debugging", true),
	        new CheckBoxTreeNode("Use AutoComplete", true),
	        new CheckBoxTreeNode("Browse in a new process", false) };
	    
	    for (CheckBoxTreeNode checkBoxTreeNode : browsingOptions) {
	    	root.add(new DefaultMutableTreeNode(checkBoxTreeNode));
		}
	    
	    
	    JTree tree = new JTree(root);

	    CheckBoxTreeNodeRenderer renderer = new CheckBoxTreeNodeRenderer();
	    tree.setCellRenderer(renderer);

	    tree.setCellEditor(new CheckBoxTreeNodeEditor(tree));
	    tree.setEditable(true);

	    JScrollPane scrollPane = new JScrollPane(tree);
	    frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(300, 150);
	    frame.setVisible(true);
	}

}
