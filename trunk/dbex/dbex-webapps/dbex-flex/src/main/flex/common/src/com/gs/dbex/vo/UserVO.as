package com.gs.dbex.vo
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="com.gs.dbex.model.vo.UserVO")]
	public class UserVO
	{
		
		private var _userId:Number;
		public var userName:String;
		public var fullName:String;
		public var emailAddress:String;
		public var password:String;
		public var connectionPropertiesVOs:ArrayCollection;
		
		public function UserVO()
		{
			_userId = NaN;
			connectionPropertiesVOs = new ArrayCollection();
		}

		public function get userId():Number{
  			return _userId;
	  	}
	  	public function set userId(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_userId = NaN;
	  		}else{
	  			_userId = Number(value);	
	  		}
	  	}
		
	}
}