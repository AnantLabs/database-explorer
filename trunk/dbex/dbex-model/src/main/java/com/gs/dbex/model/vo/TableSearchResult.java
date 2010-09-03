/**
 * 
 */
package com.gs.dbex.model.vo;

import java.sql.Timestamp;

import com.gs.dbex.model.common.ColumnHeader;

/**
 * @author sabuj.das
 *
 */
public class TableSearchResult extends SearchResult {

	private String tableSpaceName;
	private String clusterName;
	private Long numberOfRows;
	private Character cacheIndicator;
	private String tableLock;
	private Character nested;
	
	
	@ColumnHeader(title="Matching Table(s)", index=0)
	public String getObjectName() {
		return super.getObjectName();
	}
	
	@ColumnHeader(title="Owner(s)", index=1)
	public String getOwnerName() {
		return super.getOwnerName();
	}
	
	@ColumnHeader(title="Table Space", index=2)
	public String getTableSpaceName() {
		return tableSpaceName;
	}
	
	@ColumnHeader(title="Row Count", index=3)
	public Long getNumberOfRows() {
		return numberOfRows;
	}
	
	@ColumnHeader(title="Status", index=4)
	public String getStatus() {
		return super.getStatus();
	}
	
	@ColumnHeader(title="Date Created", index=5)
	public Timestamp getCreatedDate() {
		return super.getCreatedDate();
	}
	
	//@ColumnHeader(title="Is Temporary", index=6)
	public Character getTemporary() {
		return super.getTemporary();
	}
	
	//@ColumnHeader(title="Is Granted", index=7)
	public Character getGranted() {
		return super.getGranted();
	}
	
	//@ColumnHeader(title="Last DDL Date", index=8)
	public Timestamp getLastDDLDate() {
		return super.getLastDDLDate();
	}
	
	//@ColumnHeader(title="Is Secondary", index=9)
	public Character getSecondary() {
		return super.getSecondary();
	}
	
	//@ColumnHeader(title="Cluster", index=10)
	public String getClusterName() {
		return clusterName;
	}
	
	//@ColumnHeader(title="Cache", index=11)
	public Character getCacheIndicator() {
		return cacheIndicator;
	}
	
	//@ColumnHeader(title="Lock", index=12)
	public String getTableLock() {
		return tableLock;
	}
	
	//@ColumnHeader(title="Is Nested", index=13)
	public Character getNested() {
		return nested;
	}
	
	
	public void setTableSpaceName(String tableSpaceName) {
		this.tableSpaceName = tableSpaceName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public void setNumberOfRows(Long numberOfRows) {
		this.numberOfRows = numberOfRows;
	}
	public void setCacheIndicator(Character cacheIndicator) {
		this.cacheIndicator = cacheIndicator;
	}
	public void setTableLock(String tableLock) {
		this.tableLock = tableLock;
	}
	public void setNested(Character nested) {
		this.nested = nested;
	}
	
	
	
}
