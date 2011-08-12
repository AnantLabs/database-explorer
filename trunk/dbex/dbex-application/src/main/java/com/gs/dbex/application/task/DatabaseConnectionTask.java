package com.gs.dbex.application.task;

import java.sql.SQLException;

import javax.swing.SwingWorker;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.service.DatabaseConnectionService;
import com.gs.dbex.service.DbexServiceBeanFactory;

public class DatabaseConnectionTask extends SwingWorker<Void, ConnectionProperties> implements WorkerTaskConstants{

	
	private final ConnectionProperties connectionProperties;
	
	public DatabaseConnectionTask(final ConnectionProperties connectionProperties) {
		this.connectionProperties = connectionProperties;
	}
	
	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	@Override
	protected Void doInBackground() throws Exception {
		
		firePropertyChange(PROPERTY_PROGRESS, null, TASK_STATUS_START);
		DatabaseConnectionService databaseConnectionService = DbexServiceBeanFactory.getBeanFactory().getDatabaseConnectionService();
		if(null == connectionProperties || null == databaseConnectionService){
			firePropertyChange(TASK_STATUS_FAILED, null, new Object[]{"Invalid connection properties !!!", null});
			return null;
		}
		
		try{
			boolean connected = databaseConnectionService.connectToDatabase(connectionProperties);
			if(connected){
				firePropertyChange(TASK_STATUS_DONE, null, "Connection Successful !!!");
			} else {
				firePropertyChange(TASK_STATUS_FAILED, null, new Object[]{"Connection Failed !!!", null});
			}
		} catch (DbexException e){
			firePropertyChange(TASK_STATUS_FAILED, null, new Object[]{"Connection Failed !!!", e});
		} catch (Exception e){
			firePropertyChange(TASK_STATUS_FAILED, null, new Object[]{"Connection Failed !!!", e});
		} 
		
		return null;
	}
	
	public void stop() {
		cancel(true);
		firePropertyChange(TASK_STATUS_ABORT, null, null);
	}
	
}
