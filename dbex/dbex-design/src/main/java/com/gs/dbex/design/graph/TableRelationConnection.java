/**
 * 
 */
package com.gs.dbex.design.graph;

import java.awt.Color;
import java.awt.Point;

/**
 * @author sabuj.das
 *
 */
public class TableRelationConnection {

	private Point relationPoint;
	private Color connectionColor;
	private RelationConnectionDirection connectionDirection;
	
	public TableRelationConnection() {
		connectionDirection = new RelationConnectionDirection();
	}

	public Point getRelationPoint() {
		return relationPoint;
	}

	public Color getConnectionColor() {
		return connectionColor;
	}

	public RelationConnectionDirection getConnectionDirection() {
		return connectionDirection;
	}

	public void setRelationPoint(Point relationPoint) {
		this.relationPoint = relationPoint;
	}

	public void setConnectionColor(Color connectionColor) {
		this.connectionColor = connectionColor;
	}

	public void setConnectionDirection(
			RelationConnectionDirection connectionDirection) {
		this.connectionDirection = connectionDirection;
	}
	
	
}
