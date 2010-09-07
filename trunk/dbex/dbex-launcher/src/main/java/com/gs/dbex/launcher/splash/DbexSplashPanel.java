/**
 * 
 */
package com.gs.dbex.launcher.splash;

import java.awt.Color;
import java.awt.Dimension;
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

    
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        splashProgressBar = new JProgressBar();
        jSeparator1 = new JSeparator();
        splashLogLabel = new JLabel();
        copyRightLabel = new JLabel();

        setBackground(new Color(255, 255, 255));
        setBorder(BorderFactory.createLineBorder(new Color(0, 102, 153)));
        setMinimumSize(new Dimension(488, 282));
        setLayout(new GridBagLayout());

        jLabel1.setIcon(new ImageIcon(getClass().getResource("/images/DatabaseExplorer_Txt.PNG"))); // NOI18N
        jLabel1.setVerticalAlignment(SwingConstants.BOTTOM);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jLabel1, gridBagConstraints);

        jLabel2.setIcon(new ImageIcon(getClass().getResource("/images/dbex_128x128.png"))); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        add(jLabel2, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        add(splashProgressBar, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        add(jSeparator1, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        add(splashLogLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        add(copyRightLabel, gridBagConstraints);
    }


    // Variables declaration - do not modify
    private JLabel copyRightLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JSeparator jSeparator1;
    private JLabel splashLogLabel;
    private JProgressBar splashProgressBar;
    // End of variables declaration

}
