package com.gs.dbex.common.business
{
	public class CommonServiceLocator extends AbstractServiceLocator
	{
		private static var instance:CommonServiceLocator;
		
		public function CommonServiceLocator()
		{
			if(instance != null)
			{				
				throw new RIAError("already instantiated",false);
			}
			CommonServiceLocator.instance = this;
		}
		
		public static function getInstance():CommonServiceLocator
		{
			
			if(instance ==  null)
			{
				instance = new CommonServiceLocator();
			}
			
			return instance;
		}
		
	}
}