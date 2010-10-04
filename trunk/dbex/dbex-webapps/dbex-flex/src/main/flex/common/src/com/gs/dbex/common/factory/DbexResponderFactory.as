package com.gs.dbex.common.factory
{
	import com.gs.dbex.common.business.BaseResponder;
	import flash.utils.Dictionary;
	import mx.core.DeferredInstanceFromClass;
	
	public class DbexResponderFactory
	{
		
		  private static var instance : DbexResponderFactory;		
	      private var objects : Dictionary = new Dictionary();
	      
	      public function DbexResponderFactory() 
	      {   
	         registerObjects();
	         
	      }
	      public function registerObjects():void{
	      	
	      	
	      }      
	      public static function getInstance():DbexResponderFactory {
	         if ( instance == null )
	         {
	         	instance = new DbexResponderFactory();
	         }           
	         return instance;
	
	      }
	     public function getResponder(generator:Class):BaseResponder{
	     	var base:BaseResponder= BaseResponder(getBaseResponder(generator));
	     	return base;
	     }
	     private function getBaseResponder(classObj:Class):Object{
	         var myResponder : Object = objects[ classObj ];
	         
	         if ( myResponder == null )
	         {
	         	myResponder = addResponder(classObj);
	         } 
	            
	         return myResponder;   
	     }
	     
	     private function addResponder(classObj:Class):Object{
	     	var instanceCreator:DeferredInstanceFromClass = new DeferredInstanceFromClass(classObj);
	     	objects[classObj]=instanceCreator.getInstance();
	     	return objects[classObj];
	     }   
			
	}
}