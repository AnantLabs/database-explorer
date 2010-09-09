/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gs.mockup.dbex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Sabuj Das
 */
public class DbexSplashWindow extends JWindow{

    public static void main(String[] a){
        
        DbexSplashWindow window = new DbexSplashWindow();
        window.setVisible(true);
    }

    private DbexSplashPanel dbexSplashPanel;

    public DbexSplashWindow() {
        initComponents();
    }

    private void initComponents(){
        setMinimumSize(new Dimension(402, 275));
        setPreferredSize(getMinimumSize());

        getContentPane().setLayout(new BorderLayout());
        dbexSplashPanel = new DbexSplashPanel();
        getContentPane().add(dbexSplashPanel, BorderLayout.CENTER);
    }

}
