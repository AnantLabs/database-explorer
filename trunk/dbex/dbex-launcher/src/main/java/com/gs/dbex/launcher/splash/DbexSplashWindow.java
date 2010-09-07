package com.gs.dbex.launcher.splash;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JWindow;

import com.gs.utils.swing.window.WindowUtil;

public class DbexSplashWindow extends JWindow{

    public static void main(String[] a){
        DbexSplashWindow window = new DbexSplashWindow();
        
        window.setVisible(true);
        
    }

    private DbexSplashPanel dbexSplashPanel;

    public DbexSplashWindow() {
        initComponents();
        WindowUtil.bringToCenter(this);
    }

    private void initComponents(){
        setMinimumSize(new Dimension(488, 282));
        setPreferredSize(getMinimumSize());
        
        getContentPane().setLayout(new BorderLayout());
        dbexSplashPanel = new DbexSplashPanel();
        getContentPane().add(dbexSplashPanel, BorderLayout.CENTER);
    }

}
