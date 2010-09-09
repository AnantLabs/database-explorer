/**
 * 
 */
package com.gs.dbex.launcher.splash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * @author Sabuj Das
 *
 */
public class DbexSplashPanel extends JPanel {

    /** Creates new form DbexSplashPanel */
    public DbexSplashPanel() {
        initComponents();
    }

    public void setProgressValue(int v){
    	splashProgressBar.setValue(v);
    	updateUI();
    }
    
    public void setProgressMessage(String message){
    	splashMessageLabel.setText(message);
    	updateUI();
    }
    
    private void initComponents() {
    	GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jPanel1 = new JPanel();
        splashProgressBar = new JProgressBar();
        splashMessageLabel = new JLabel();
        jLabel3 = new JLabel();
        jSeparator1 = new JSeparator();

        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153)));
        setMinimumSize(new Dimension(402, 300));
        setPreferredSize(new Dimension(402, 300));
        setLayout(new GridBagLayout());

        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/dbex_text.PNG"))); // NOI18N
        jLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jLabel1, gridBagConstraints);

        jLabel2.setIcon(new ImageIcon(getClass().getResource("/images/dbex_128x128.png"))); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(jLabel2, gridBagConstraints);

        jPanel1.setBackground(new Color(67, 109, 209));
        jPanel1.setLayout(new GridBagLayout());

        splashProgressBar.setBackground(new Color(67, 109, 209));
        splashProgressBar.setForeground(new Color(40, 210, 43));
        splashProgressBar.setValue(0);
        splashProgressBar.setBorderPainted(false);
        splashProgressBar.setDoubleBuffered(true);
        splashProgressBar.setFocusable(false);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(6, 0, 0, 0);
        jPanel1.add(splashProgressBar, gridBagConstraints);

        splashMessageLabel.setFont(new Font("Tahoma", 1, 11)); // NOI18N
        splashMessageLabel.setText("Starting Database Explorer...");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        jPanel1.add(splashMessageLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(jPanel1, gridBagConstraints);

        jLabel3.setIcon(new ImageIcon(getClass().getResource("/images/top_image.png"))); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        add(jLabel3, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(jSeparator1, gridBagConstraints);
    }


    // Variables declaration - do not modify
    private JLabel splashMessageLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JPanel jPanel1;
    private JSeparator jSeparator1;
    private JProgressBar splashProgressBar;
    // End of variables declaration

}
