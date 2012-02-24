/* ******************************************************************************
 * 	
 * 	Name	: ReferenceLine.java
 * 	Type	: com.gs.dbex.design.model.ReferenceLine
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import com.gs.dbex.model.BaseDbModel;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class ReferenceLine<S extends BaseDbModel, T extends BaseDbModel> extends Line implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8073291068015345371L;

	private S source;
	private T target;
	private boolean biDirectional;
	
	private Color lineColor;
	private String caption;
	
	public ReferenceLine(S source, T target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	public ReferenceLine(int x1, int y1, int x2, int y2, S source, T target) {
		super(x1, y1, x2, y2);
		this.source = source;
		this.target = target;
	}

	public S getSource() {
		return source;
	}

	public void setSource(S source) {
		this.source = source;
	}

	public T getTarget() {
		return target;
	}

	public void setTarget(T target) {
		this.target = target;
	}

	public boolean isBiDirectional() {
		return biDirectional;
	}

	public void setBiDirectional(boolean biDirectional) {
		this.biDirectional = biDirectional;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public void drawLine(Graphics graphics, Dimension canvasSize) {
		if(null == graphics || null == canvasSize)
			return;
		Graphics2D graphics2d = (Graphics2D) graphics;
		if(null == getSource() || null == getTarget()){
			graphics2d.setColor(Color.RED);
			graphics2d.drawString("Invalid Reference", getX1(), getY1());
			return;
		}
		
	}
	
	
	
}
