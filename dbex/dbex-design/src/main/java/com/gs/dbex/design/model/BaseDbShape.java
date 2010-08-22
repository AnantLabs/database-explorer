/**
 * 
 */
package com.gs.dbex.design.model;

import java.io.Serializable;

import com.gs.dbex.common.enums.ShapeTypeEnum;

/**
 * @author Sabuj Das
 *
 */
public abstract class BaseDbShape extends RectangularShape implements Serializable, Comparable<BaseDbShape> {

	private static int shapeCount = 0;
	
	private String modelName;
	private ShapeTypeEnum shapeTypeEnum;
	
	public BaseDbShape() {
		this("UN-NAMED" + (shapeCount++));
	}
	
	public BaseDbShape(String modelName) {
		setModelName(modelName);
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public ShapeTypeEnum getShapeTypeEnum() {
		return shapeTypeEnum;
	}

	public void setShapeTypeEnum(ShapeTypeEnum shapeTypeEnum) {
		this.shapeTypeEnum = shapeTypeEnum;
	}
	
	@Override
	public String toString() {
		return getModelName();
	}
	
	@Override
	public int compareTo(BaseDbShape o) {
		return this.modelName.compareTo(o.modelName);
	}
}
