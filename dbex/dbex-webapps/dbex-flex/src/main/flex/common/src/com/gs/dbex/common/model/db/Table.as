package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;
	import com.gs.dbex.common.model.IDbexObject;
	
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gs.dbex.model.db.Table")]
	public class Table extends BaseDbModel implements IDbexObject
	{
		public var tableCatalog:String;
		public var tableSchema:String;
		private var _autoIncrementValue:Number;
		
		
		public var primaryKeys:ArrayCollection;
		public var columnlist:ArrayCollection;
		public var importedKeys:ArrayCollection;
		public var exportedKeys:ArrayCollection;
		
		public function Table()
		{
			super();
			primaryKeys = new ArrayCollection();
			columnlist = new ArrayCollection();
			importedKeys = new ArrayCollection();
			exportedKeys = new ArrayCollection();
		}
		
		public function get autoIncrementValue():Number{
  			return _autoIncrementValue;
	  	}
	  	public function set autoIncrementValue(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_autoIncrementValue = NaN;
	  		}else{
	  			_autoIncrementValue = Number(value);	
	  		}
	  	}
	}
}