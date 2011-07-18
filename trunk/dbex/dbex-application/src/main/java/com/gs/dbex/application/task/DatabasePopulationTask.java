package com.gs.dbex.application.task;

import java.awt.Component;
import java.util.List;

import javax.swing.SwingWorker;

import com.gs.dbex.model.BaseDbModel;
import com.gs.dbex.model.cfg.ConnectionProperties;


public class DatabasePopulationTask extends
		SwingWorker<Void, BaseDbModel> implements WorkerTaskConstants{

	private final BaseDbModel databaseModel;
	private final ConnectionProperties connectionProperties;
	
	private Component sourceComponent;
	
	public DatabasePopulationTask(BaseDbModel databaseModel,
			ConnectionProperties connectionProperties) {
		super();
		this.databaseModel = databaseModel;
		this.connectionProperties = connectionProperties;
	}


	public BaseDbModel getDatabaseModel() {
		return databaseModel;
	}

	public ConnectionProperties getConnectionProperties() {
		return connectionProperties;
	}

	public Component getSourceComponent() {
		return sourceComponent;
	}

	public void setSourceComponent(Component sourceComponent) {
		this.sourceComponent = sourceComponent;
	}


	@Override
	protected Void doInBackground() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
