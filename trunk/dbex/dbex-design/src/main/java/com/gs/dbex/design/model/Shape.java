/**
 * 
 */
package com.gs.dbex.design.model;

import java.io.Serializable;

/**
 * @author Sabuj Das
 *
 */
public abstract class Shape implements Serializable {

	private int x;
	private int y;
	
	public Shape() {
		this(0, 0);
	}

	public Shape(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}


}
