/**
 * 
 */
package com.gs.dbex.model.vo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

import com.gs.dbex.model.db.Table;
import com.gs.utils.jdbc.ResultSetDataTable;

/**
 * @author sabuj.das
 *
 */
public class PaginationResult implements Serializable{

	private int totalRows;
	private int startRow;
	private int rowsPerPage;
	private int endRow;
	private int currentPage;
    private boolean nextPage;
    private boolean previousPage;
    private int totalPages;
	
	private transient ResultSet resultSet;
	private ResultSetDataTable dataTable;
	
	private Table currentTable;
	
	public PaginationResult() {
		// TODO Auto-generated constructor stub
	}
	public PaginationResult(int currentPage, int rowsPerPage) {
		this.currentPage = currentPage;
		this.rowsPerPage = rowsPerPage;
		this.startRow = (currentPage - 1) * rowsPerPage;
	}
	
	@SuppressWarnings("unchecked")
	public PaginationResult(ResultSet resultSet, int currentPage, int rowsPerPage) {
		this.currentPage = currentPage;
		this.rowsPerPage = rowsPerPage;
		this.startRow = (currentPage - 1) * rowsPerPage;
		this.resultSet = resultSet;
	}
	
	public void setRowAttributes(int totalRows) {

		if (totalRows == 0){
			this.startRow = 0;
			this.endRow = 0;
			this.totalRows = 0;
			this.nextPage = false;
			this.previousPage = false;
			this.totalPages = 0;
		}
		else {
			int rowStart = (currentPage - 1) * rowsPerPage;
			int rowEnd = rowStart + rowsPerPage - 1;
			if (rowEnd > totalRows - 1) {
				rowEnd = totalRows - 1;
			}
			this.totalRows = totalRows;
			if((totalRows % rowsPerPage) == 0){
				this.totalPages = (int)(totalRows / rowsPerPage);
			} else {
				this.totalPages = (int)(totalRows / rowsPerPage) + 1;
			}
			this.startRow = rowStart;
			this.endRow = rowEnd;
			this.nextPage = true;
			this.previousPage = true;
			if((this.currentPage * this.rowsPerPage)>=totalRows){
				this.nextPage = false;
			}
			if(this.currentPage == 1){
				this.previousPage = false;
			}
			 if(this.rowsPerPage >= totalRows){
				this.previousPage = false;
				this.nextPage = false;
			}
			
		}
	}
	
	public Table getCurrentTable() {
		return currentTable;
	}
	public void setCurrentTable(Table currentTable) {
		this.currentTable = currentTable;
	}
	public ResultSetDataTable getDataTable() {
		return dataTable;
	}
	public void setDataTable(ResultSetDataTable dataTable) {
		this.dataTable = dataTable;
	}
	/**
	 * @return the totalRows
	 */
	public int getTotalRows() {
		return totalRows;
	}
	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}
	/**
	 * @return the rowsPerPage
	 */
	public int getRowsPerPage() {
		return rowsPerPage;
	}
	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}
	/**
	 * @return the currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}
	/**
	 * @return the nextPage
	 */
	public boolean isNextPage() {
		return nextPage;
	}
	/**
	 * @return the previousPage
	 */
	public boolean isPreviousPage() {
		return previousPage;
	}
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @return the resultSet
	 */
	public ResultSet getResultSet() {
		return resultSet;
	}
	/**
	 * @param totalRows the totalRows to set
	 */
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	/**
	 * @param rowsPerPage the rowsPerPage to set
	 */
	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}
	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @param nextPage the nextPage to set
	 */
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	/**
	 * @param previousPage the previousPage to set
	 */
	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @param resultSet the resultSet to set
	 */
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	
}
