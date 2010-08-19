/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @author sabuj.das
 *
 */
public class TruncateTablePanel extends JPanel {

    public TruncateTablePanel() {
        initComponents();
    }

    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        buttonGroup1 = new ButtonGroup();
        jLabel1 = new JLabel();
        dropRadioButton = new JRadioButton();
        reuseRadioButton = new JRadioButton();
        jPanel1 = new JPanel();

        setLayout(new GridBagLayout());

        jLabel1.setText("Storage : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 2, 2, 2);
        add(jLabel1, gridBagConstraints);

        buttonGroup1.add(dropRadioButton);
        dropRadioButton.setSelected(true);
        dropRadioButton.setText("DROP STORAGE");
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 2, 2, 2);
        add(dropRadioButton, gridBagConstraints);

        buttonGroup1.add(reuseRadioButton);
        reuseRadioButton.setText("REUSE STORAGE");

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 0, 0);
        add(reuseRadioButton, gridBagConstraints);

        jPanel1.setLayout(new BorderLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jPanel1, gridBagConstraints);
    }



    private ButtonGroup buttonGroup1;
    private JRadioButton dropRadioButton;
    private JLabel jLabel1;
    private JPanel jPanel1;
    private JRadioButton reuseRadioButton;

	public JRadioButton getDropRadioButton() {
		return dropRadioButton;
	}

	public JRadioButton getReuseRadioButton() {
		return reuseRadioButton;
	}

    
}
