package com.gs.dbex.common
{
	import com.gs.dbex.util.StringUtil;
	
	import mx.collections.ArrayCollection;
	
	public class DatabaseTypeEnum
	{
		
		private var _type:String;
		private var _driverClass:String;
		private var _description:String;
		
		public function DatabaseTypeEnum(t:String, driver:String, desc:String)
		{
			_type = t;
			_driverClass = driver;
			_description = desc;
		}

		public function get type():String{
			return _type;
		}
		public function get driverClass():String{
			return _driverClass;
		}
		public function get description():String{
			return _description;
		}
		
		public static const ORACLE:DatabaseTypeEnum = new DatabaseTypeEnum("ORACLE", "oracle.jdbc.driver.OracleDriver", "Oracle");
		public static const MYSQL:DatabaseTypeEnum = new DatabaseTypeEnum("MYSQL", "com.mysql.jdbc.Driver", "MySql");
		public static const MSSQL_2005:DatabaseTypeEnum = new DatabaseTypeEnum("MSSQL_2005", "com.microsoft.sqlserver.jdbc.SQLServerDriver", "Sql Server");
		public static const OTHER:DatabaseTypeEnum = new DatabaseTypeEnum("OTHER", "", "Other");
		
		public static function getTypes():ArrayCollection{
			var coll:ArrayCollection = new ArrayCollection();
			coll.addItem(ORACLE);
			coll.addItem(MYSQL);
			coll.addItem(MSSQL_2005);
			coll.addItem(OTHER);
			return coll;
		}
		
		public static function getDatabaseTypeEnum(dbType:String):DatabaseTypeEnum{
			if(!StringUtil.hasValidContent(dbType)){
				return OTHER;
			}
			if(ORACLE.type == dbType){
				return ORACLE;
			} else if(ORACLE.type == dbType){
				return ORACLE;
			} else if(MYSQL.type == dbType){
				return MYSQL;
			} else if(MSSQL_2005.type == dbType){
				return MSSQL_2005;
			}
			return OTHER;
		}
	
	}
}