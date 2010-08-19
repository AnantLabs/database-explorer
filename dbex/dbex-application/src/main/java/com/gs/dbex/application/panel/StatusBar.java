/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author sabuj.das
 *
 */
public class StatusBar extends JPanel {

	private JPanel jPanel1,jPanel2,jPanel3,jPanel4,jPanel5,jPanel6,jPanel7,jPanel8;
	private JLabel currentStatusLabel,charCountLabel,lineNumberLabel,
	insertLabel,editorTextPropertyLabel,capsLockLabel,numLockLabel,scrollLockLabel;
		
	public StatusBar() {
		initComponents();
	}
	
	private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jPanel1 = new JPanel();
        currentStatusLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        charCountLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lineNumberLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        insertLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        editorTextPropertyLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        capsLockLabel = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        numLockLabel = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        scrollLockLabel = new JLabel();

        setName("Status_Panel"); // NOI18N
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        setPreferredSize(new java.awt.Dimension(729, 25));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel1.setMinimumSize(new java.awt.Dimension(55, 25));
        jPanel1.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel1.setLayout(new java.awt.BorderLayout());

        currentStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jPanel1.add(currentStatusLabel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        add(jPanel1, gridBagConstraints);

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel2.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel2.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel2.setLayout(new java.awt.BorderLayout());

        charCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(charCountLabel, java.awt.BorderLayout.CENTER);

        add(jPanel2, new java.awt.GridBagConstraints());

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel3.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel3.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel3.setLayout(new java.awt.BorderLayout());

        lineNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel3.add(lineNumberLabel, java.awt.BorderLayout.CENTER);

        add(jPanel3, new java.awt.GridBagConstraints());

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel4.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel4.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel4.setLayout(new java.awt.BorderLayout());

        insertLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        insertLabel.setText("INS");
        jPanel4.add(insertLabel, java.awt.BorderLayout.CENTER);

        add(jPanel4, new java.awt.GridBagConstraints());

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel5.setMinimumSize(new java.awt.Dimension(200, 25));
        jPanel5.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel5.setLayout(new java.awt.BorderLayout());

        editorTextPropertyLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel5.add(editorTextPropertyLabel, java.awt.BorderLayout.CENTER);

        add(jPanel5, new java.awt.GridBagConstraints());

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel6.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel6.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel6.setLayout(new java.awt.BorderLayout());

        capsLockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel6.add(capsLockLabel, java.awt.BorderLayout.CENTER);

        add(jPanel6, new java.awt.GridBagConstraints());

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel7.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel7.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel7.setLayout(new java.awt.BorderLayout());

        numLockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel7.add(numLockLabel, java.awt.BorderLayout.CENTER);

        add(jPanel7, new java.awt.GridBagConstraints());

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 255)));
        jPanel8.setMinimumSize(new java.awt.Dimension(50, 25));
        jPanel8.setPreferredSize(new java.awt.Dimension(102, 240));
        jPanel8.setLayout(new java.awt.BorderLayout());

        scrollLockLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel8.add(scrollLockLabel, java.awt.BorderLayout.CENTER);

        add(jPanel8, new java.awt.GridBagConstraints());


	}

	public JLabel getCurrentStatusLabel() {
		return currentStatusLabel;
	}
	
	
}
