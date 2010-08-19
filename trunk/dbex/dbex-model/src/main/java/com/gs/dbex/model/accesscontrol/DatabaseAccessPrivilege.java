/**
 * 
 */
package com.gs.dbex.model.accesscontrol;

/**
 * @author Sabuj Das
 *
 */
public class DatabaseAccessPrivilege {
	
	private String catalogName;
	private String schemaName;
	private String grantor;
	private String grantee;
	private String privilegeName;
	private Boolean isGrantable;
	
	public DatabaseAccessPrivilege() {
		// TODO Auto-generated constructor stub
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getGrantor() {
		return grantor;
	}

	public void setGrantor(String grantor) {
		this.grantor = grantor;
	}

	public String getGrantee() {
		return grantee;
	}

	public void setGrantee(String grantee) {
		this.grantee = grantee;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

	public Boolean getIsGrantable() {
		return isGrantable;
	}

	public void setIsGrantable(Boolean isGrantable) {
		this.isGrantable = isGrantable;
	}
}
