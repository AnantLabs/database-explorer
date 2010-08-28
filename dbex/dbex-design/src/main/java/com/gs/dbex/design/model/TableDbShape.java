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
import com.gs.dbex.design.model.dependency.TableDependency;
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
	private TableDependency tableDependency;
	private Dimension canvasSize;
	
	private Map<String, Point> tablePkPointMap
		= new HashMap<String, Point>();
	private Map<String, Point> tableFkPointMap
		= new HashMap<String, Point>();
	
	public TableDbShape(Table table) {
		super(table);
		columnDbShapes = new ArrayList<ColumnDbShape>();
		if(null != table){
			setDisplayName(table.getModelName());
			if(null != table.getColumnlist()){
				for (int i = 0; i < table.getColumnlist().size(); i++) {
					Column column = table.getColumnlist().get(i);
					ColumnDbShape columnDbShape = new ColumnDbShape(column);
					columnDbShapes.add(columnDbShape);
				}
			}
		}
	}

	
	@Override
	public void populateGraphicsContent(Graphics graphics, Dimension canvasSize) {
		if(null == graphics)
			return;
		if(null != canvasSize)
			setCanvasSize(canvasSize);
		
		setWidth(DrawingUtil.calculateTableWidth(getGraphics(), getDbModel(), true));
		setHeight(DrawingUtil.calculateTableHeight(getGraphics(), getDbModel(), true));
		if(getX() == 0 && getY() == 0){
			setX(canvasSize.width - getWidth()/2);
			setY(DbexDesignConstants.TABLE_LEFT_MARGIN_WIDTH);
		}
		int colStart_X = getX() + 1;
		int colStart_Y = getY() + DrawingUtil.calculateCellHeight(getGraphics()) + 2;
		int cellHeight = DrawingUtil.calculateCellHeight(getGraphics());
		if(null != columnDbShapes){
			for (int i = 0; i < columnDbShapes.size(); i++) {
				ColumnDbShape columnDbShape = columnDbShapes.get(i);
				columnDbShape.setX(colStart_X);
				columnDbShape.setY(colStart_Y + (cellHeight * (i)));
				columnDbShape.setWidth(getWidth());
				columnDbShape.setHeight(cellHeight);
				columnDbShape.setGraphics(graphics);
				columnDbShape.populateGraphicsContent(graphics, canvasSize);
			}
		}
	}
	
	@Override
	public String tooltipText(Point mousePosition) {
		if(null == mousePosition)
			return "";
		if(isOnPerimeter(mousePosition)){
			if(isInside(mousePosition)){
				if(null != columnDbShapes){
					for (int i = 0; i < columnDbShapes.size(); i++) {
						ColumnDbShape columnDbShape = columnDbShapes.get(i);
						if(columnDbShape.isInside(mousePosition) || columnDbShape.isOnPerimeter(mousePosition)){
							return columnDbShape.getDisplayName();
						}
					}
				}
			}
			return getModelName();
		}
		
		return null;
	}
	
	public Dimension getCanvasSize() {
		return canvasSize;
	}


	public void setCanvasSize(Dimension canvasSize) {
		this.canvasSize = canvasSize;
	}


	public List<ColumnDbShape> getColumnDbShapes() {
		return columnDbShapes;
	}

	public void setColumnDbShapes(List<ColumnDbShape> columnDbShapes) {
		this.columnDbShapes = columnDbShapes;
	}

	public TableDependency getTableDependency() {
		return tableDependency;
	}

	public void setTableDependency(TableDependency tableDependency) {
		this.tableDependency = tableDependency;
	}

	public Map<String, Point> getTablePkPointMap() {
		return tablePkPointMap;
	}

	public void setTablePkPointMap(Map<String, Point> tablePkPointMap) {
		this.tablePkPointMap = tablePkPointMap;
	}

	public Map<String, Point> getTableFkPointMap() {
		return tableFkPointMap;
	}

	public void setTableFkPointMap(Map<String, Point> tableFkPointMap) {
		this.tableFkPointMap = tableFkPointMap;
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
