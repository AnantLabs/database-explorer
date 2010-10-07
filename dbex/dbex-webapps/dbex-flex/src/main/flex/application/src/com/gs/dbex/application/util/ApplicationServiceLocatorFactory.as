package com.gs.dbex.application.util
{
	import com.gs.dbex.application.business.DbexServices;
	import com.gs.dbex.common.business.RIAError;
	import com.gs.dbex.common.business.RIAMessageCodes;
	import com.gs.dbex.common.business.Services;
	
	import flash.utils.Dictionary;
	
	import mx.core.DeferredInstanceFromClass;
	
	public final class ApplicationServiceLocatorFactory
	{
		private static var  instance:ApplicationServiceLocatorFactory;
	    private var services : Dictionary = new Dictionary();		
		public function ApplicationServiceLocatorFactory(){
			if(instance){
				throw new RIAError(RIAMessageCodes.SINGLETON_EXCEPTION);
			}
			instance=this;
			createServices();
		}
		
		private function createServices():void{
			createService(com.gs.dbex.common.business.Services);
			createService(com.gs.dbex.application.business.DbexServices);
		}
	     private function createService(classObj:Class):void{
	     	if(services[classObj]){
	     		throw new RIAError(RIAMessageCodes.SINGLETON_EXCEPTION);
	     	}
	     	var instanceCreator:DeferredInstanceFromClass = new DeferredInstanceFromClass(classObj);
	     	services[classObj]=instanceCreator.getInstance();     	
	     }   
		
	}
}