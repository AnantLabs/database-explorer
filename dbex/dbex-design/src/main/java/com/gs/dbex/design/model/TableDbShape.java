/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.gs.dbex.model.db.Table;
import com.gs.utils.text.StringUtil;

/**
 * @author Sabuj Das
 * 
 */
public class TableDbShape extends BaseDbShape<Table> implements Serializable,
		MovableShape<TableDbShape>, DrawableShape<TableDbShape> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -687226079740961023L;
	
	private String displayName;
	
	private Map<String, Point> tablePkPointMap
		= new HashMap<String, Point>();
	private Map<String, Point> tableFkPointMap
		= new HashMap<String, Point>();
	
	public TableDbShape(Table table) {
		super(table);
		if(null != table){
			setDisplayName(table.getModelName());
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
	public void drawShape(Graphics graphics) {
		Color oldFg = graphics.getColor();
		Table table = getDbModel();
		if(null != table){
			
		}
		graphics.setColor(oldFg);
	}

	@Override
	public Boolean canMove(Point from, Point to) {
		return null;
	}

	@Override
	public void move(Graphics graphics, Point from, Point to) {
		
	}

}
