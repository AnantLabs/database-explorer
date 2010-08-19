/**
 * 
 */
package com.gs.dbex.application.table;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author sabuj.das
 *
 */
public class CopyTablePanel extends JPanel {

	private String[] schemaNames;
	private String sourceTableName;
	
    public CopyTablePanel() {
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        newSchemaListComboBox = new JComboBox();
        jLabel2 = new JLabel();
        newTableNameTextField = new JTextField();
        copyDataCheckBox = new JCheckBox();
        jPanel1 = new JPanel();

        setLayout(new GridBagLayout());

        jLabel1.setText("New OWNER");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(4, 2, 2, 2);
        add(jLabel1, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 2, 2, 2);
        add(newSchemaListComboBox, gridBagConstraints);

        jLabel2.setText("New Name");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(newTableNameTextField, gridBagConstraints);

        copyDataCheckBox.setText("Copy Data");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        add(copyDataCheckBox, gridBagConstraints);

        jPanel1.setLayout(new BorderLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }


    private JCheckBox copyDataCheckBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JPanel jPanel1;
    private JComboBox newSchemaListComboBox;
    private JTextField newTableNameTextField;
    
	public JCheckBox getCopyDataCheckBox() {
		return copyDataCheckBox;
	}

	public JComboBox getNewSchemaListComboBox() {
		return newSchemaListComboBox;
	}

	public JTextField getNewTableNameTextField() {
		return newTableNameTextField;
	}

	public String[] getSchemaNames() {
		return schemaNames;
	}

	public void setSchemaNames(String[] schemaNames) {
		this.schemaNames = schemaNames;
		if(schemaNames.length > 0){
			this.newSchemaListComboBox.setModel(new DefaultComboBoxModel(schemaNames));
			this.newSchemaListComboBox.setSelectedIndex(0);
			this.newSchemaListComboBox.updateUI();
		}
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

    
}
