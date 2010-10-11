package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;
	
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="com.gs.dbex.model.db.Database")]
	public class Database extends BaseDbModel
	{
		public var schemaList:ArrayCollection;
		
		public function Database()
		{
			super();
			schemaList = new ArrayCollection();
		}
		
	}
}