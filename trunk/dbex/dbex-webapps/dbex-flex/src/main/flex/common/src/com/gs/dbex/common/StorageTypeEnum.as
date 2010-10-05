package com.gs.dbex.common
{
	public class StorageTypeEnum
	{
		private var _type:String;
		private var _description:String;
		
		public function StorageTypeEnum(t:String, desc:String)
		{
			_type = t;
			_description = desc;
		}

		public function get type():String{
			return _type;
		}

		public function get description():String{
			return _description;
		}
		
		public static const CATALOG:StorageTypeEnum = new StorageTypeEnum("CATALOG", "Catalog");
		public static const SCHEMA:StorageTypeEnum = new StorageTypeEnum("SCHEMA", "Schema");
	}
}