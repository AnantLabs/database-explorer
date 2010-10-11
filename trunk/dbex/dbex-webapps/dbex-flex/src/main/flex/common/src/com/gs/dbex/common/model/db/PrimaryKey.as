package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;

	[RemoteClass(alias="com.gs.dbex.model.db.PrimaryKey")]
	public class PrimaryKey extends BaseDbModel
	{
		public var tableCat:String;
		public var tableSchem:String;
		public var tableName:String;
		public var columnName:String;
		private var _keySeq:Number;
		
		public function PrimaryKey()
		{
			super();
		}
		
		public function get keySeq():Number{
  			return _keySeq;
	  	}
	  	public function set keySeq(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_keySeq = NaN;
	  		}else{
	  			_keySeq = Number(value);	
	  		}
	  	}
		
	}
}