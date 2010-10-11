package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;

	[RemoteClass(alias="com.gs.dbex.model.db.ForeignKey")]
	public class ForeignKey extends BaseDbModel
	{
		public var pkTableCat:String;
		public var pkTableSchem:String;
		public var pkTableName:String;
		public var fkTableCat:String;
		public var fkTableSchem:String;
		public var fkTableName:String;
		public var fkColumnName:String;
		public var fkName:String;
		public var pkName:String;
		public var pkColumnName:String;
		
		public var keySeq:Number;
		public var updateRule:Number;
		public var deleteRule:Number;
		public var deferrability:Number;
		
		public function ForeignKey()
		{
			super();
		}
		
	}
}