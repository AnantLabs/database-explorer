/* ******************************************************************************
 * 	
 * 	Name	: Line.java
 * 	Type	: com.gs.dbex.design.model.Line
 * 
 * 	Created	: Feb 9, 2012
 * 	
 * 	Author	: Sabuj Das [ mailto::sabuj.das@gmail.com ]
 * 
 * -----------------------------------------------------------------------------*
 * 																				*
 * Copyright © Sabuj Das 2010 All Rights Reserved. 								*
 * <br/>No part of this document may be reproduced without written 				*
 * consent from the author.														*
 * 																				*
 ****************************************************************************** */

package com.gs.dbex.design.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public abstract class Line implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1634340752412172L;

	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	/**
	 * 
	 */
	public Line() {
		this(0,0,0,0);
	}

	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Line(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public abstract void drawLine(Graphics graphics, Dimension canvasSize);
	
}
