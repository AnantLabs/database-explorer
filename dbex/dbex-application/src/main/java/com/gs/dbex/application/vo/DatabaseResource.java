/**
 * 
 */
package com.gs.dbex.application.vo;

import java.io.Serializable;

import javax.swing.ImageIcon;

import com.gs.dbex.common.enums.DatabaseResourceEnum;

/**
 * @author Green Moon
 *
 */
public class DatabaseResource implements Serializable, Comparable<DatabaseResource>{

	private String schemaName;
	private String resourceName;
	private DatabaseResourceEnum resourceType;
	private ImageIcon imageIcon;
	
	public DatabaseResource() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}



	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}



	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}



	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}



	/**
	 * @return the resourceType
	 */
	public DatabaseResourceEnum getResourceType() {
		return resourceType;
	}



	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(DatabaseResourceEnum resourceType) {
		this.resourceType = resourceType;
	}



	/**
	 * @return the imageIcon
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}



	/**
	 * @param imageIcon the imageIcon to set
	 */
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}



	public int compareTo(DatabaseResource other) {
		return this.getResourceName().compareToIgnoreCase(other.getResourceName());
	}

}
