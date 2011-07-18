package com.gs.dbex.application.task;

import javax.swing.SwingWorker;

import com.gs.dbex.model.cfg.ConnectionProperties;

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
		Long startTime = System.currentTimeMillis();
		Long totalTime = 0L;
		
		firePropertyChange(PROPERTY_PROGRESS, null, TASK_STATUS_START);
		setProgress(0);
		
		if(null == connectionProperties){
			firePropertyChange(TASK_STATUS_ABORT, null, "Invalid connection properties !!!");
			return null;
		}
		
		
		
		return null;
	}
	
	
}
