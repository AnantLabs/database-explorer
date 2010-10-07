package com.gs.dbex.vo
{
	[RemoteClass(alias="com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO")]
	public class ConnectionPropertiesVO
	{
		private var _connectionPropId:Number;
		private var _userId:Number;
		public var connectionName:String;
		public var databaseType:String;
		public var connectionUrl:String;
		public var displayOrder:Number = 0;
		public var databaseConfiguration:DatabaseConfigurationVO = new DatabaseConfigurationVO();
		
		public function get connectionPropId():Number{
  			return _connectionPropId;
	  	}
	  	public function set connectionPropId(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_connectionPropId = NaN;
	  		}else{
	  			_connectionPropId = Number(value);	
	  		}
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