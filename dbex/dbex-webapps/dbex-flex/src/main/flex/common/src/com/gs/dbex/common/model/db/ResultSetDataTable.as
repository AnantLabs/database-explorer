package com.gs.dbex.common.model.db
{
	import mx.collections.ArrayCollection;
	
	
	[RemoteClass(alias="com.gs.utils.jdbc")]
	public class ResultSetDataTable
	{
		public function ResultSetDataTable()
		{
		}

		public var rowCount;
		public var columnCount;
		public var dataTable:ArrayCollection;
		public var columnnames;
	}
}