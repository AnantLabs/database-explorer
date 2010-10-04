package com.gs.dbex.application.business
{
	import com.gs.dbex.common.business.AbstractServiceLocator;
	
	public class DbexApplicationServiceLocator extends AbstractServiceLocator
	{
		private static var instance:DbexApplicationServiceLocator;
		
		public function DbexApplicationServiceLocator()
		{
			 if(DbexApplicationServiceLocator.instance!=null){
				throw new Error( "Only one ServiceLocator instance should be instantiated" );
			}
			 DbexApplicationServiceLocator.instance = this;
		}
		
		public static function getInstance():DbexApplicationServiceLocator
		{
			if(instance == null)
			{
				instance = new DbexApplicationServiceLocator();
			}
			return instance;
		}
	}
}