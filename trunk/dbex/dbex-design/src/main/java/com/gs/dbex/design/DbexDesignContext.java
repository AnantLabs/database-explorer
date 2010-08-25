/**
 * 
 */
package com.gs.dbex.design;

/**
 * @author Sabuj.das
 *
 */
public final class DbexDesignContext {

	private static DbexDesignContext instance;
	
	private DbexDesignContext() {
		
	}

	public static DbexDesignContext getInstance() {
		if(null == instance)
			instance = new DbexDesignContext();
		return instance;
	}
	
	private int zoomScale = DbexDesignConstants.DEFAULT_SCALE;
	public int getZoomScale() {
		return zoomScale;
	}
	public void setZoomScale(int zoomScale) {
		this.zoomScale = zoomScale;
	}
	
	
}
