package com.gs.dbex.vo
{
	[RemoteClass(alias="com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO")]
	public class ConnectionPropertiesVO
	{
		public var connectionPropId:Number;
		public var userId:Number;
		public var connectionName:String;
		public var databaseType:String;
		public var connectionUrl:String;
		public var displayOrder:Number = 0;
		public var databaseConfiguration:DatabaseConfigurationVO = new DatabaseConfigurationVO();
		
	}
}