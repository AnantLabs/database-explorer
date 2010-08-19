/**
 * 
 */
package com.gs.dbex.historyMgr;

/**
 * @author sabuj.das
 *
 */
public class DbexHistoryMgrBeanFactory {

	private static DbexHistoryMgrBeanFactory instance;
	
	private DbexHistoryMgrBeanFactory() {
		// TODO Auto-generated constructor stub
	}

	public static DbexHistoryMgrBeanFactory getInstance() {
		if(instance == null)
			instance = new DbexHistoryMgrBeanFactory();
		return instance;
	}
	
	private ApplicationDataHistoryMgr applicationDataHistoryMgr;

	public ApplicationDataHistoryMgr getApplicationDataHistoryMgr() {
		return applicationDataHistoryMgr;
	}

	public void setApplicationDataHistoryMgr(
			ApplicationDataHistoryMgr applicationDataHistoryMgr) {
		this.applicationDataHistoryMgr = applicationDataHistoryMgr;
	}
	
	
	
}
