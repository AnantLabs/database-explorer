/**
 * 
 */
package com.gs.dbex.serviceprovider;

import java.util.HashMap;
import java.util.Map;

import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Sabuj.das
 *
 */
public class DbexServiceProviderContext {

	private static DbexServiceProviderContext instance;
	private DbexServiceProviderContext() {
		
	}
	public static DbexServiceProviderContext getInstance() {
		if(null == instance)
			instance = new DbexServiceProviderContext();
		return instance;
	}
	
	
	/* -------------------- Properties --------------------------*/
	
	public Map<String, ConnectionProperties> connectedConnectionPropertiesMap = new HashMap<String, ConnectionProperties>();
	
}
