/**
 * 
 */
package com.gs.dbex.launcher;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.gs.dbex.application.context.ApplicationCommonContext;
import com.gs.dbex.common.DbexCommonContext;
import com.gs.dbex.historyMgr.ApplicationDataHistoryMgr;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class ApplicationDataLoader {
	
	private static final Logger logger = Logger.getLogger(ApplicationDataLoader.class);
	private static DbexCommonContext dbexCommonContext = DbexCommonContext.getInstance();
	private static ApplicationCommonContext applicationCommonContext = ApplicationCommonContext.getInstance();
	private ApplicationDataHistoryMgr applicationDataHistoryMgr;
	
	public ApplicationDataLoader() {
		// TODO Auto-generated constructor stub
	}
	
	public void populateInitialContext(){
		populateDbexCommonContext();
		populateApplicationCommonContext();
	}

	private void populateDbexCommonContext() {
		
	}

	private void populateApplicationCommonContext() {
		List<ConnectionProperties> propsList = getApplicationDataHistoryMgr().loadAllConnectionProperties();
		if(propsList != null){
			Collections.sort(propsList);
			for (ConnectionProperties p : propsList) {
				applicationCommonContext.getConnectionPropertiesMap().put(p.getConnectionName()	,  p);
			}
		}
		
	}

	public ApplicationDataHistoryMgr getApplicationDataHistoryMgr() {
		return applicationDataHistoryMgr;
	}

	public void setApplicationDataHistoryMgr(
			ApplicationDataHistoryMgr applicationDataHistoryMgr) {
		this.applicationDataHistoryMgr = applicationDataHistoryMgr;
	}

}
