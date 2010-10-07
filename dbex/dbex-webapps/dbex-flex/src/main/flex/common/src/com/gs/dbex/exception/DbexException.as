package com.gs.dbex.exception
{
	[RemoteClass(alias="com.gs.dbex.common.exception.DbexException")]
	public class DbexException extends Exception
	{
		
		public var exceptionCode:String;
		public var exceptionMessage:String;
		
		public function DbexException()
		{
			super();
		}
		
	}
}