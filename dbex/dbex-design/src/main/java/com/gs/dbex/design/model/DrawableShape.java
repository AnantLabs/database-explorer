/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Graphics;
import java.awt.Point;

/**
 * @author Sabuj Das
 *
 */
public interface DrawableShape<S extends RectangularShape> {

	public Boolean canBeDrawn();
	
	public void draw(Graphics graphics, Point at);
	
}
