/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gs.dbex.design.DbexColorConstants;
import com.gs.dbex.design.DbexDesignConstants;
import com.gs.dbex.design.util.DrawingUtil;
import com.gs.dbex.model.db.Column;
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
	private List<ColumnDbShape> columnDbShapes;
	
	private Map<String, Point> tablePkPointMap
		= new HashMap<String, Point>();
	private Map<String, Point> tableFkPointMap
		= new HashMap<String, Point>();
	
	public TableDbShape(Graphics graphics, Table table) {
		super(graphics, table);
		setX(100);
		setY(100);
		columnDbShapes = new ArrayList<ColumnDbShape>();
		if(null != table){
			setDisplayName(table.getModelName());
			setWidth(DrawingUtil.calculateTableWidth(getGraphics(), table, true));
			setHeight(DrawingUtil.calculateTableHeight(getGraphics(), table, true));
			populateColumnDbShapes(table);
		}
	}
	
	private void populateColumnDbShapes(Table table) {
		int colStart_X = getX() + 1;
		int colStart_Y = getY() + DrawingUtil.calculateCellHeight(getGraphics()) + 2;
		int cellHeight = DrawingUtil.calculateCellHeight(getGraphics());
		if(null != table.getColumnlist()){
			for (int i = 0; i < table.getColumnlist().size(); i++) {
				Column column = table.getColumnlist().get(i);
				ColumnDbShape columnDbShape = new ColumnDbShape(getGraphics(), column);
				columnDbShape.setX(colStart_X);
				columnDbShape.setY(colStart_Y + (cellHeight * (i)));
				columnDbShape.setWidth(getWidth());
				columnDbShape.setHeight(cellHeight);
				columnDbShapes.add(columnDbShape);
			}
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
	public void drawShape() {
		Graphics graphics = getGraphics();
		Color oldFg = graphics.getColor();
		Table table = getDbModel();
		if(null != table){
			Point location = getLocation();
			Dimension size = getSize();
			// draw the border
			graphics.setColor(DbexColorConstants.TABLE_BORDER_COLOR);
			graphics.drawRect(location.x, location.y, size.width, size.height);
			graphics.setColor(DbexColorConstants.COLUMN_NAMES_BG_COLOR);
			graphics.fillRect(location.x+1, location.y+1, size.width-1, size.height-1);
			// draw the header
			graphics.setColor(DbexColorConstants.TABLE_BORDER_COLOR);
			graphics.drawRect(location.x, location.y, size.width, DrawingUtil.calculateCellHeight(graphics));
			graphics.setColor(DbexColorConstants.TABLE_HEADER_BG_COLOR);
			graphics.fillRect(location.x+1, location.y+1, size.width-1, DrawingUtil.calculateCellHeight(graphics)-1);
			graphics.setColor(DbexColorConstants.TABLE_HEADER_FG_COLOR);
			graphics.drawString(table.getModelName(), location.x+2, 
					location.y+DrawingUtil.calculateCellHeight(graphics)-4);
			// draw the left margin
			graphics.setColor(DbexColorConstants.TABLE_BORDER_COLOR);
			graphics.drawRect(location.x, location.y + DrawingUtil.calculateCellHeight(graphics),
					DbexDesignConstants.TABLE_LEFT_MARGIN_WIDTH, size.height-DrawingUtil.calculateCellHeight(graphics));
			graphics.setColor(DbexColorConstants.TABLE_LEFT_MARGIN_BG_COLOR);
			graphics.fillRect(location.x+1, location.y + DrawingUtil.calculateCellHeight(graphics)+1,
					DbexDesignConstants.TABLE_LEFT_MARGIN_WIDTH-1, size.height-1-DrawingUtil.calculateCellHeight(graphics));
			
			// draw columns
			if(null != columnDbShapes){
				for (int i=0; i<columnDbShapes.size(); i++) {
					ColumnDbShape columnDbShape = columnDbShapes.get(i);
					columnDbShape.drawShape();
					if(i < columnDbShapes.size()-1){
						graphics.setColor(DbexColorConstants.TABLE_BORDER_COLOR);
						graphics.drawLine(
								columnDbShape.getX() + DbexDesignConstants.TABLE_LEFT_MARGIN_WIDTH+2, 
								columnDbShape.getY() + columnDbShape.getHeight() + 2, 
								location.x + size.width ,
								columnDbShape.getY() + columnDbShape.getHeight() + 2);
					}
				}
			}
		}
		graphics.setColor(oldFg);
	}

	@Override
	public Boolean canMove(Point from, Point to) {
		return true;
	}

	@Override
	public void move(Graphics graphics, Point from, Point to) {
		if(!canMove(from, to))
			return;
		if(null != to){
			setX(to.x);
			setY(to.y);
		}
		repaintShape();
	}

}
