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
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            DbexSplashWindow window = new DbexSplashWindow();
            window.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbexSplashWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DbexSplashWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DbexSplashWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(DbexSplashWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private NewDbexSplashPanel dbexSplashPanel;

    public DbexSplashWindow() {
        initComponents();
    }

    private void initComponents(){
        setMinimumSize(new Dimension(530, 300));
        setPreferredSize(getMinimumSize());

        getContentPane().setLayout(new BorderLayout());
        dbexSplashPanel = new NewDbexSplashPanel();
        getContentPane().add(dbexSplashPanel, BorderLayout.CENTER);
    }

}
