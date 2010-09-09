package com.gs.dbex.launcher.splash;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.gs.dbex.launcher.DatabaseExplorerLauncher;
import com.gs.dbex.launcher.DbexLaunchHelper;
import com.gs.utils.swing.window.WindowUtil;

public class DbexSplashWindow extends JWindow {

	private DatabaseExplorerLauncher databaseExplorerLauncher;
	
	private DbexSplashPanel dbexSplashPanel;

	public DbexSplashWindow(DatabaseExplorerLauncher launcher) {
		databaseExplorerLauncher = launcher;
		setMinimumSize(new Dimension(402, 275));
		setPreferredSize(getMinimumSize());
		WindowUtil.bringToCenter(this);
		initComponents();
		
	}

	private void initComponents() {
		

		getContentPane().setLayout(new BorderLayout());
		dbexSplashPanel = new DbexSplashPanel();
		getContentPane().add(dbexSplashPanel, BorderLayout.CENTER);
		
		setVisible(true);
		WorkerOutput o = null;
		ApplicationLoaderWorker worker = new ApplicationLoaderWorker(databaseExplorerLauncher, dbexSplashPanel);
		try {
			o = worker.doInBackground();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(o != null){
			if("done".equals(o.message)){
				dbexSplashPanel.setProgressValue(100);
				databaseExplorerLauncher.launchApplication();
				dispose();
			}
		}
	}
	
	private class ApplicationLoaderWorker extends SwingWorker<WorkerOutput, DbexSplashPanel>{

		private DatabaseExplorerLauncher databaseExplorerLauncher;
		private DbexSplashPanel dbexSplashPanel;
		
		private ApplicationLoaderWorker(
				DatabaseExplorerLauncher databaseExplorerLauncher,
				DbexSplashPanel dbexSplashPanel) {
			this.databaseExplorerLauncher = databaseExplorerLauncher;
			this.dbexSplashPanel = dbexSplashPanel;
		}

		@Override
		protected WorkerOutput doInBackground() throws Exception {
			Thread.sleep(500);
			dbexSplashPanel.setProgressValue(0);
			try{
				dbexSplashPanel.setProgressMessage("Creating required files/folders.");
	        	databaseExplorerLauncher.createFiles();
	        }catch (Exception ex){
	        	dbexSplashPanel.setProgressMessage("ERROR Creating required files/folders.");
	        }
	        Thread.sleep(500);
			dbexSplashPanel.setProgressValue(20);
			Thread.sleep(500);
			dbexSplashPanel.setProgressMessage("Populating initial context.");
			databaseExplorerLauncher.getApplicationDataLoader().populateInitialContext();
			dbexSplashPanel.setProgressValue(70);
			Thread.sleep(500);
			WorkerOutput o = new WorkerOutput();
			o.message = "done";
			o.parcentage = 100;
			return o;
		}

		
		
	}

	private class WorkerOutput{
		String message;
		int parcentage = 0;
	}
}
