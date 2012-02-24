/* ******************************************************************************
 * 	
 * 	Name	: ColumnShape.java
 * 	Type	: com.gs.dbex.design.shape.ColumnShape
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

package com.gs.dbex.design.shape;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.gs.dbex.model.db.Column;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public class ColumnShape extends Rectangle implements Serializable, DbShape {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5513054241149905902L;
	private Column column;
	private String imagePath;
	private ImageIcon imageIcon;
	
	public ColumnShape(Column column) {
		this.column = column;
	}

	public ColumnShape(Column column, String imagePath) {
		this.column = column;
		this.imagePath = imagePath;
	}

	public ColumnShape(Column column, ImageIcon imageIcon) {
		this.column = column;
		this.imageIcon = imageIcon;
	}


	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	@Override
	public Shape createShape() {
		if(null == getColumn())
			return null;
		ColumnShape columnShape = new ColumnShape(getColumn());
		
		return columnShape;
	}

	@Override
	public void paintShape(Graphics2D graphics2d) {
		graphics2d.draw(this);
	}


}
