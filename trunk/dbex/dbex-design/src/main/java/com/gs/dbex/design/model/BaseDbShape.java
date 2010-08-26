/**
 * 
 */
package com.gs.dbex.design.model;

import java.io.Serializable;

import com.gs.dbex.common.enums.ShapeTypeEnum;
import com.gs.dbex.model.BaseDbModel;

/**
 * @author Sabuj Das
 * 
 */
public abstract class BaseDbShape<T extends BaseDbModel> extends RectangularShape implements
		Serializable, Comparable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3590940323242845687L;

	private static int shapeCount = 0;

	private T dbModel;
	private String modelName;
	private ShapeTypeEnum shapeTypeEnum;
	

	public BaseDbShape(T dbModel) {
		this.dbModel = dbModel;
		modelName = (null != dbModel) ? dbModel.getModelName()
				: ("UNNAMED_MODEL_" + shapeCount++);
	}

	/**
	 * @return the dbModel
	 */
	public T getDbModel() {
		return dbModel;
	}

	/**
	 * @param dbModel
	 *            the dbModel to set
	 */
	public void setDbModel(T dbModel) {
		this.dbModel = dbModel;
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
	public int compareTo(T o) {
		return this.modelName.compareTo(o.getModelName());
	}
}
