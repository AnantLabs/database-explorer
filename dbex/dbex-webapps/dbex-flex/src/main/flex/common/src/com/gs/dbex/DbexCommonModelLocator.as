package com.gs.dbex
{
	import mx.collections.ArrayCollection;
	
	[Bindable]
	public class DbexCommonModelLocator
	{
		private static var _instance:DbexCommonModelLocator = new DbexCommonModelLocator();
		
		public function DbexCommonModelLocator()
		{
		}
		
		public static function getInstance():DbexCommonModelLocator{
			return _instance;
		}

		public var connectionPropertiesColl:ArrayCollection = new ArrayCollection();
		
		public var connectionNameColl:ArrayCollection = new ArrayCollection();
	}
}