/* ******************************************************************************
 * 	
 * 	Name	: MyShape.java
 * 	Type	: com.gs.dbex.design.model.MyShape
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

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public abstract class MyShape implements Shape, Serializable {

	/* (non-Javadoc)
	 * @see java.awt.Shape#getBounds()
	 */
	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getBounds2D()
	 */
	@Override
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(double, double)
	 */
	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(java.awt.geom.Point2D)
	 */
	@Override
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#intersects(double, double, double, double)
	 */
	@Override
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#intersects(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(double, double, double, double)
	 */
	@Override
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#contains(java.awt.geom.Rectangle2D)
	 */
	@Override
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see java.awt.Shape#getPathIterator(java.awt.geom.AffineTransform, double)
	 */
	@Override
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

}
