/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Point;
import java.io.Serializable;

/**
 * @author Sabuj Das
 *
 */
public abstract class RectangularShape implements Serializable {

	private Long x1, y1, x2, y2;
	
	private Point leftTopPoint;
	private Point leftBottomPoint;
	private Point rightTopPoint;
	private Point rightBottomPoint;
	
}
