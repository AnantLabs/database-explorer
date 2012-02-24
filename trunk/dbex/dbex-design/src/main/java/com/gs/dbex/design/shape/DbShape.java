/* ******************************************************************************
 * 	
 * 	Name	: BaseShape.java
 * 	Type	: com.gs.dbex.design.shape.BaseShape
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
import java.awt.Shape;

/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public interface DbShape {

	public Shape createShape();
	
	public void paintShape(Graphics2D graphics2d);
	
}
