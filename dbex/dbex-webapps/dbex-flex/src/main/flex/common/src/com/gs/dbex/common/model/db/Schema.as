package com.gs.dbex.common.model.db
{
	import com.gs.dbex.common.model.BaseDbModel;
	
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="com.gs.dbex.model.db.Schema")]
	public class Schema extends BaseDbModel
	{
		public var tableList:ArrayCollection;
		
		public function Schema()
		{
			super();
			tableList = new ArrayCollection();
		}
		
	}
}