/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * @author Sabuj Das
 *
 */
public abstract class RectangularShape extends Shape implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4396319855974789586L;
	
	private int width;
	private int height;
	
	private Graphics graphics;
	
	public RectangularShape() {
		this(0, 0, 0, 0);
	}

	public RectangularShape(int x, int y) {
		this(x, y, 0, 0);
	}

	public RectangularShape(int x, int y, int width, int height) {
		super(x, y);
		this.width = width;
		this.height = height;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getLocation(){
		return new Point(getX(),getY());
	}
	
	public Dimension getSize(){
		return new Dimension(width, height);
	}
	
	
	
	public abstract void drawShape();
	
	public void repaintShape(){
		drawShape();
	}
	
	public boolean isInside(Point point){
		if(null == point)
			return false;
		if(point.x > getX() && point.x < (getX() + getWidth())){
			if(point.y > getY() && point.y < (getY() + getHeight())){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOutside(Point point){
		return (!isInside(point) && !isOnPerimeter(point));
	}
	
	public boolean isOnPerimeter(Point point){
		if(null == point)
			return false;
		if(!isInside(point)){
			if(point.x == getX()){
				if(point.y >= getY() && point.y <= (getY() + getHeight())){
					return true;
				}
			} else if(point.x == (getX() + getWidth())){
				if(point.y >= getY() && point.y <= (getY() + getHeight())){
					return true;
				}
			} else if(point.y == getY()){
				if(point.x >= getX() && point.x <= (getX() + getWidth())){
					return true;
				}
			} else if(point.y == (getY() + getHeight())){
				if(point.x >= getX() && point.x <= (getX() + getWidth())){
					return true;
				}
			}
		}
		return false;
	}
	
	public long getArea(){
		return getWidth() * getHeight();
	}
	
	public void setLocation(Point point){
		if(null != point){
			setX(point.x);
			setY(point.y);
		}
	}
	
	public Rectangle getBounds(){
		return new Rectangle(getX(), getY(), width, height);
	}
	
	public boolean contains(int x, int y){
		return contains(new Point(x,y));
	}
	
	public boolean contains(Point point){
		if(isInside(point) || isOnPerimeter(point)){
			return true;
		}
		return false;
	}
}
