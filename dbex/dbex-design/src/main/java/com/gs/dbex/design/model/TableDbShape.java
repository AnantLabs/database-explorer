/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

import com.gs.utils.text.StringUtil;

/**
 * @author Sabuj Das
 * 
 */
public class TableDbShape extends BaseDbShape implements Serializable,
		MovableShape<TableDbShape>, DrawableShape<TableDbShape> {

	private String displayName;
	
	public TableDbShape(String tableName) {
		setModelName(tableName);
		if(StringUtil.hasValidContent(tableName)){
			setDisplayName(tableName.toUpperCase());
		}
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public Boolean canBeDrawn() {
		return true;
	}

	@Override
	public void draw(Graphics graphics, Point at) {
		
	}

	@Override
	public Boolean canMove(Point from, Point to) {
		return null;
	}

	@Override
	public void move(Graphics graphics, Point from, Point to) {
		
	}

}
