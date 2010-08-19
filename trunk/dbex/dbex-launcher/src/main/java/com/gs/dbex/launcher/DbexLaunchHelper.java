/**
 * 
 */
package com.gs.dbex.launcher;

import java.util.List;

import com.gs.dbex.historyMgr.ApplicationDataHistoryMgr;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author sabuj.das
 *
 */
public class DbexLaunchHelper {

	private ApplicationDataHistoryMgr applicationDataHistoryMgr;
	
	public DbexLaunchHelper() {
		// TODO Auto-generated constructor stub
	}

	public ApplicationDataHistoryMgr getApplicationDataHistoryMgr() {
		return applicationDataHistoryMgr;
	}

	public void setApplicationDataHistoryMgr(
			ApplicationDataHistoryMgr applicationDataHistoryMgr) {
		this.applicationDataHistoryMgr = applicationDataHistoryMgr;
	}
	
	public List<ConnectionProperties> loadAllConnectionProperties(){
		return getApplicationDataHistoryMgr().loadAllConnectionProperties();
	}
}
