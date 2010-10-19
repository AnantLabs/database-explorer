package com.gs.dbex.common.view
{
	import com.gs.dbex.common.model.db.ResultSetDataTable;
	
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;


	public class ResultSetDataGrid extends DataGrid
	{
		
		private var _resultSetDataTable:ResultSetDataTable;
		
		public function ResultSetDataGrid()
		{
			super();
		}
		
		/* 
		public override function set dataProvider(value:Object):void{
			
		} */
		
		public function get resultSetDataTable():ResultSetDataTable{
			return _resultSetDataTable;
		}
		
		public function set resultSetDataTable(value:ResultSetDataTable):void{
			if(null == value)
				return;
			_resultSetDataTable = value;
			if(value.columnCount > 0){
				var columns:Array = new Array();
				for (var col:int = 0; col< value.columnCount; col++) {
					var column:DataGridColumn = new DataGridColumn();
					column.headerText = value.columnnames[col];
					column.width = 100;
					column.dataField = value.columnnames[col];
					columns.push(column);
				}
				super.columns = columns;
				validateNow();
				dataProvider =  value.getDataTableArray();
			}
			validateNow();
		}
	}
}