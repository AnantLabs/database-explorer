/**
 * 
 */
package com.gs.dbex.application.context;

/**
 * @author sabuj.das
 *
 */
public class ApplicationClassFactory {

	private static ApplicationClassFactory instance;
	private static String[] applicationCommonContextFiles = new String[] {
		"/context/dbex-service-context.xml"
	};
	private ApplicationClassFactory() {
		
	}
	
	public static ApplicationClassFactory getInstance() {
		if(instance == null)
			instance = new ApplicationClassFactory();
		return instance;
	}
	
	
}
