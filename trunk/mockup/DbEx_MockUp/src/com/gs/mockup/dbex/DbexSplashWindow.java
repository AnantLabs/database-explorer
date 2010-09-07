/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gs.mockup.dbex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JWindow;

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
        setMinimumSize(new Dimension(488, 282));
        setPreferredSize(getMinimumSize());

        getContentPane().setLayout(new BorderLayout());
        dbexSplashPanel = new DbexSplashPanel();
        getContentPane().add(dbexSplashPanel, BorderLayout.CENTER);
    }

}
