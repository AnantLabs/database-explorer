package com.gs.dbex.vo
{
	[RemoteClass(alias="com.gs.dbex.model.vo.cfg.DatabaseConfigurationVO")]
	public class DatabaseConfigurationVO
	{
		private var _configurationId:Number;
		private var _connectionPropId:Number;
		public var hostName:String;
		private var _portNumber:Number;
		public var driverClassName:String;
		public var userName:String;
		public var password:String;
		public var storageType:String;
		public var schemaName:String;
		public var savePassword:Boolean;
		public var sidServiceName:String;
		
		public function DatabaseConfigurationVO(){
			_connectionPropId = NaN;
			_configurationId = NaN;
		}
		
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
	  	
	  	public function get configurationId():Number{
  			return _configurationId;
	  	}
	  	public function set configurationId(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_configurationId = NaN;
	  		}else{
	  			_configurationId = Number(value);	
	  		}
	  	}
	  	
	  	public function get portNumber():Number{
  			return _portNumber;
	  	}
	  	public function set portNumber(value:*):void{
	  		if(value == null || isNaN(value) || String(value) == ""){
	  			_portNumber = NaN;
	  		}else{
	  			_portNumber = Number(value);	
	  		}
	  	}
	}
}