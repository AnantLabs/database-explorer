/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.List;

import com.gs.dbex.model.BaseDbModel;
import com.gs.dbex.model.db.Schema;
import com.gs.utils.text.StringUtil;


/**
 * @author Sabuj Das
 *
 */
public class SchemaDbShape extends BaseDbShape<Schema> implements Serializable {

	/**
	 * serialVersionUID = -7266600008023990597L
	 */
	private static final long serialVersionUID = -7266600008023990597L;
	
	private String displayName;
	private List<TableDbShape> tableDbShapes;
	
	public SchemaDbShape(Schema dbModel) {
		super(dbModel);
		if(null != dbModel){
			setDisplayName(dbModel.getModelName());
		}
	}

	public List<TableDbShape> getTableDbShapes() {
		return tableDbShapes;
	}

	public void setTableDbShapes(List<TableDbShape> tableDbShapes) {
		this.tableDbShapes = tableDbShapes;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public void drawShape() {
		
	}

	@Override
	public void populateGraphicsContent(Graphics graphics, Dimension canvasSize) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String tooltipText(Point mousePosition) {
		// TODO Auto-generated method stub
		return "";
	}
	
	@Override
	public BaseDbShape<Schema> onShape(Point mousePosition) {
		// TODO Auto-generated method stub
		return null;
	}
}
