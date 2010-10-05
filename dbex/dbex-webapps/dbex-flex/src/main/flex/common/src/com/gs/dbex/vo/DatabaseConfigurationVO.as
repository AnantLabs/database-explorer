package com.gs.dbex.vo
{
	[RemoteClass(alias="com.gs.dbex.model.vo.cfg.DatabaseConfigurationVO")]
	public class DatabaseConfigurationVO
	{
		public var configurationId:Number;
		public var connectionPropId:Number;
		public var hostName:String;
		public var portNumber:Number;
		public var driverClassName:String;
		public var userName:String;
		public var password:String;
		public var storageType:String;
		public var schemaName:String;
		public var savePassword:Boolean;
		public var sidServiceName:String;
	}
}