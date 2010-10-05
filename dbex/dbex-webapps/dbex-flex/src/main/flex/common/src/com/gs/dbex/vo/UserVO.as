package com.gs.dbex.vo
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="com.gs.dbex.model.vo.UserVO")]
	public class UserVO
	{
		
		public var userId:Number;
		public var userName:String;
		public var fullName:String;
		public var emailAddress:String;
		public var password:String;
		public var connectionPropertiesVOs:ArrayCollection;
		
		public function UserVO()
		{
			connectionPropertiesVOs = new ArrayCollection();
		}

	}
}