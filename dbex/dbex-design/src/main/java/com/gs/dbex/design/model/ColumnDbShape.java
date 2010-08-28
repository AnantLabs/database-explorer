/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.gs.dbex.design.DbexColorConstants;
import com.gs.dbex.design.DbexDesignConstants;
import com.gs.dbex.model.BaseDbModel;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 * 
 */
public class ColumnDbShape extends BaseDbShape<Column> implements
		DrawableShape<ColumnDbShape>, MovableShape<ColumnDbShape>, Serializable {

	private String displayName;

	private Image imagePk;
	private Image imageFk;
	
	public ColumnDbShape(Column column) {
		super(column);
		if (null != column) {
			setDisplayName(column.getModelName());
		}
		imagePk = new ImageIcon(getClass()
				.getResource(DbexDesignConstants.IMAGE_PATH
						+ "PrimaryKeyColumn.gif")).getImage(); 
		imageFk = new ImageIcon(getClass()
				.getResource(DbexDesignConstants.IMAGE_PATH
						+ "ForeignKeyColumn.gif")).getImage();
	}

	@Override
	public void populateGraphicsContent(Graphics graphics, Dimension canvasSize) {
		
	}
	
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void drawShape() {
		Graphics graphics = getGraphics();
		Color oldFg = graphics.getColor();
		Column column = getDbModel(); 
		if(null != column){
			if(column.getPrimaryKey() || column.getForeignKey()){
				Point imgLoc = new Point(
						getX() + 4,
						getY() + 4 );
				graphics.drawImage(
						(column.getPrimaryKey() ? imagePk : imageFk), 
						imgLoc.x, imgLoc.y, null);
				Table parentTable = column.getParentTable();
				if(null != parentTable){
					if(parentTable.hasImportedTableForColumn(column)){
						Point jointCircleCenter = new Point(
								getX() - 1,
								getY() + getHeight()/2 );
						Point jointCirclePoint = new Point(
								jointCircleCenter.x - DbexDesignConstants.JOINT_CIRCLE_RADIUS,
								jointCircleCenter.y - DbexDesignConstants.JOINT_CIRCLE_RADIUS );
						graphics.setColor(DbexColorConstants.JOINT_CIRCLE_COLOR);
						graphics.fillOval(jointCirclePoint.x, jointCirclePoint.y, 
								DbexDesignConstants.JOINT_CIRCLE_RADIUS*2, DbexDesignConstants.JOINT_CIRCLE_RADIUS*2);
					} else if(parentTable.hasExportedTableForColumn(column)){
						Point jointCircleCenter = new Point(
								getX() + getWidth(),
								getY() + getHeight()/2 );
						Point jointCirclePoint = new Point(
								jointCircleCenter.x - DbexDesignConstants.JOINT_CIRCLE_RADIUS,
								jointCircleCenter.y - DbexDesignConstants.JOINT_CIRCLE_RADIUS );
						graphics.setColor(DbexColorConstants.JOINT_CIRCLE_COLOR);
						graphics.fillOval(jointCirclePoint.x, jointCirclePoint.y, 
								DbexDesignConstants.JOINT_CIRCLE_RADIUS*2, DbexDesignConstants.JOINT_CIRCLE_RADIUS*2);
					}
				}
				
			}
			graphics.setColor(DbexColorConstants.COLUMN_NAMES_FG_COLOR);
			graphics.drawString(column.getModelName(), 
					getX() + DbexDesignConstants.TABLE_LEFT_MARGIN_WIDTH + 2, 
					getY() + getHeight() - 4);
			
			
			
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

	@Override
	public Boolean canBeDrawn() {
		return true;
	}
	
	@Override
	public String tooltipText(Point mousePosition) {
		// TODO Auto-generated method stub
		return getDisplayName();
	}
	
	@Override
	public BaseDbShape<Column> onShape(Point mousePosition) {
		// TODO Auto-generated method stub
		return null;
	}
}
