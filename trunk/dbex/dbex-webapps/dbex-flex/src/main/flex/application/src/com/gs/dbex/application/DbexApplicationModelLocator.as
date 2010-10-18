package com.gs.dbex.application
{
	import com.gs.dbex.common.model.db.Database;
	import com.gs.dbex.common.model.db.Table;
	
	[Bindable]
	public class DbexApplicationModelLocator
	{
		private static var _instance:DbexApplicationModelLocator = new DbexApplicationModelLocator();
		
		public function DbexApplicationModelLocator()
		{
		}
		
		public static function getInstance():DbexApplicationModelLocator{
			return _instance;
		}


		public var databaseModel:Database;
		public var selectedTable:Table;
	}
}