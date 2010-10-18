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
	}
}