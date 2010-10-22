package com.gs.dbex.vo
{
	import com.gs.dbex.common.model.db.ResultSetDataTable;
	import com.gs.dbex.common.model.db.Table;
	
	[RemoteClass(alias="com.gs.dbex.model.vo.PaginationResult")]
	public class PaginationResultVO
	{
		
		public var totalRows:int;
		public var startRow:int;
		public var rowsPerPage:int;
		public var endRow:int;
		public var currentPage:int;
		public var nextPage:Boolean;
		public var previousPage:Boolean;
		public var totalPages:int;
		
		public var dataTable:ResultSetDataTable;

		public var currentTable:Table;
		
		public function PaginationResultVO(currentPage:int=0, rowsPerPage:int=0) {
			this.currentPage = currentPage;
			this.rowsPerPage = rowsPerPage;
			this.startRow = (currentPage - 1) * rowsPerPage;
		}	
			
			
		public function setRowAttributes(totalRows:int):void {
	
			if (totalRows == 0){
				this.startRow = 0;
				this.endRow = 0;
				this.totalRows = 0;
				this.nextPage = false;
				this.previousPage = false;
				this.totalPages = 0;
			}
			else {
				var rowStart:int = (currentPage - 1) * rowsPerPage;
				var rowEnd:int = rowStart + rowsPerPage - 1;
				if (rowEnd > totalRows - 1) {
					rowEnd = totalRows - 1;
				}
				this.totalRows = totalRows;
				if((totalRows % rowsPerPage) == 0){
					this.totalPages = (totalRows / rowsPerPage) as int;
				} else {
					this.totalPages = ((totalRows / rowsPerPage) + 1) as int;
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
	}
}