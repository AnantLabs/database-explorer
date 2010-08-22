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
public interface MovableShape<S extends RectangularShape> {

	public Boolean canMove(Point from, Point to);
	
	public void move(Graphics graphics, Point from, Point to);
	
}
