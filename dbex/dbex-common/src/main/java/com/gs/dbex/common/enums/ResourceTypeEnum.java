/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author sabuj.das
 *
 */
public enum ResourceTypeEnum {

	CATALOG("DATABASE_SCHEMA", "Catalog", Boolean.FALSE),
	SCHEMA("DATABASE_SCHEMA", "Schema", Boolean.FALSE),
	TABLE("DATABASE_TABLE", "Table", Boolean.TRUE),
	COLUMN("DATABASE_COLUMN", "Column", Boolean.TRUE);
	
	private String resourceType;
	private String resourceName;
	private Boolean editableResource;
	
	private ResourceTypeEnum(String resourceType, String resourceName,
			Boolean editableResource) {
		this.resourceType = resourceType;
		this.resourceName = resourceName;
		this.editableResource = editableResource;
	}

	public String getResourceType() {
		return resourceType;
	}

	public String getResourceName() {
		return resourceName;
	}

	public Boolean getEditableResource() {
		return editableResource;
	}
	
	
}
