/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author sabuj.das
 *
 */
public class ResourceRenamePanel extends JPanel {

    public ResourceRenamePanel() {
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        resourceLabel = new JLabel();
        newResourceNameTextField = new JTextField();
        jPanel1 = new JPanel();

        setLayout(new GridBagLayout());

        resourceLabel.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(resourceLabel, gridBagConstraints);

        newResourceNameTextField.setText("");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        add(newResourceNameTextField, gridBagConstraints);

        jPanel1.setLayout(new BorderLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }



    private JLabel resourceLabel;
    private JPanel jPanel1;
    private JTextField newResourceNameTextField;
    
	public JTextField getNewResourceNameTextField() {
		return newResourceNameTextField;
	}

	public JLabel getResourceLabel() {
		return resourceLabel;
	}

    

}
