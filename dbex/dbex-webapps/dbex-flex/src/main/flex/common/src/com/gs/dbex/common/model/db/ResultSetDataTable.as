package com.gs.dbex.common.model.db
{
	import mx.utils.ObjectProxy;
	
	
	[RemoteClass(alias="com.gs.utils.jdbc.ResultSetDataTable")]
	public class ResultSetDataTable
	{
		public function ResultSetDataTable()
		{
		}
		
		public var rowCount:int;
		public var columnCount:int;
		[ArrayElementType("Array")]
		public var dataTable:Array;
		[ArrayElementType("String")]
		public var columnnames:Array;
		
		private var _dataTableArray:Array;
		
		public function getDataTableArray():Array{
			populateDataTableArray();
			return _dataTableArray;
		}
		
		private function populateDataTableArray():void{
			_dataTableArray = new Array();
			if(columnCount > 0){
				for (var row:int = 0; row < rowCount; row++) {
					var obj:Object = new Object();
					for (var col:int = 0; col< columnCount; col++) {
						obj[columnnames[col]] = dataTable[row][col];
					}
					_dataTableArray.push(obj);
				}
			}
		}
	}
}