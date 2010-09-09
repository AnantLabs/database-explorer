/**
 * 
 */
package com.gs.dbex.launcher;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gs.dbex.application.context.ApplicationCommonContext;
import com.gs.dbex.application.frame.DatabaseExplorerFrame;
import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.launcher.splash.DbexSplashWindow;

/**
 * @author sabuj.das
 *
 */
public class DatabaseExplorerLauncher {

	private static final Logger logger = Logger.getLogger(DatabaseExplorerLauncher.class);
	private static DbexCommonContext dbexCommonContext = DbexCommonContext.getInstance();
	private static ApplicationCommonContext applicationCommonContext = ApplicationCommonContext.getInstance();
	
	private ApplicationDataLoader applicationDataLoader;
	
	
	public DatabaseExplorerLauncher() {
		getApplicationDataLoader();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("Launching Database Explorer.");
		
		logger.info("Creating Spring Beans");
		dbexCommonContext.applicationSpringContext = new ClassPathXmlApplicationContext(
				new String[] { "/context/dbex-launcher-context.xml"});
		
		DatabaseExplorerLauncher launcher = (DatabaseExplorerLauncher) dbexCommonContext.applicationSpringContext.getBean("databaseExplorerLauncher");
		DbexSplashWindow splashWindow = new DbexSplashWindow(launcher);
	}
	
	public void launchApplication(){
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
        
        logger.info("Initialize Context.");
        
        DatabaseExplorerFrame explorerFrame = new DatabaseExplorerFrame();
		if(explorerFrame != null){
			explorerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			explorerFrame.setVisible(true);
		}
     
	}
	
	public void createFiles() throws Exception{
		File profilesDir = new File(dbexCommonContext.getProfilesDirName());
		if(!profilesDir.exists()){
			boolean created = profilesDir.mkdir();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("Directory [ " + profilesDir.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create Directory [ " + profilesDir.getCanonicalPath() + " ].");
			}
		} 
		File userDir = new File(dbexCommonContext.getUserDataPath());
		if(!userDir.exists()){
			boolean created = userDir.mkdir();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("Directory [ " + userDir.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create Directory [ " + userDir.getCanonicalPath() + " ].");
			}
		} 
		
		
		File appDataDir = new File(dbexCommonContext.getApplicationDataDir());
		if(!appDataDir.exists()){
			boolean created = appDataDir.mkdir();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("Directory [ " + appDataDir.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create Directory [ " + appDataDir.getCanonicalPath() + " ].");
			}
		} 
		File historyDataDir = new File(dbexCommonContext.getLocalHistoryPath());
		if(!historyDataDir.exists()){
			boolean created = historyDataDir.mkdir();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("Directory [ " + historyDataDir.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create Directory [ " + historyDataDir.getCanonicalPath() + " ].");
			}
		} 
		
		File connPropsFile = new File(dbexCommonContext.getConnectionConfigFileName());
		if(!connPropsFile.exists()){
			boolean created = connPropsFile.createNewFile();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("File [ " + connPropsFile.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create File [ " + connPropsFile.getCanonicalPath() + " ].");
			}
		}
		
		File driverMgrFile = new File(dbexCommonContext.getDriverManagerFileName());
		if(!driverMgrFile.exists()){
			boolean created = driverMgrFile.createNewFile();
			if(logger.isDebugEnabled()){
				if(created)
					logger.debug("File [ " + driverMgrFile.getCanonicalPath() + " ] created.");
				else
					logger.debug("Cannot Create File [ " + driverMgrFile.getCanonicalPath() + " ].");
			}
		}
	}
	
	
	public ApplicationDataLoader getApplicationDataLoader() {
		return applicationDataLoader;
	}

	public void setApplicationDataLoader(ApplicationDataLoader applicationDataLoader) {
		this.applicationDataLoader = applicationDataLoader;
	}

	
}
