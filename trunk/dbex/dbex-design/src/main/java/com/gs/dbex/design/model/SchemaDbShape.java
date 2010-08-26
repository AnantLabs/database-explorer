/**
 * 
 */
package com.gs.dbex.design.model;

import java.awt.Graphics;
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
	
	public SchemaDbShape(Graphics graphics, Schema dbModel) {
		super(graphics, dbModel);
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
	
	
}
