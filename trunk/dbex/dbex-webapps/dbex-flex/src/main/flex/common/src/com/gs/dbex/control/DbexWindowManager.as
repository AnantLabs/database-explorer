package com.gs.dbex.control
{
	import com.gs.dbex.layout.ResizableWindow;
	
	import flash.events.Event;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.events.FlexEvent;
	import mx.managers.PopUpManager;
	
	public class DbexWindowManager
	{
		
		public static const DBEX_CONNECTION_DIALOG:String = "DBEX_CONNECTION_DIALOG";
		public static const JDBC_DRIVER_MANAGER_DIALOG:String = "JDBC_DRIVER_MANAGER_DIALOG";
		
		private static var manager:DbexWindowManager = new DbexWindowManager();
		private var openWindows:Object = new Object();
		private var openPopups:Object = new Object();
		private var openWindowKeys:ArrayCollection = new ArrayCollection();
		private var _workArea:Canvas;
		
		public static function getInstance():DbexWindowManager{
			return manager;
		}
		
		public function set workArea(area:Canvas):void{
			_workArea = area;
			menuPanelResized();
		}
		[Bindable]
		public function get workArea():Canvas{
			return _workArea;
		}
		
		public function reset():void{
			var keyList:ArrayCollection = new ArrayCollection();
			for(var i:uint=0; i< openWindowKeys.length;i++){
				keyList.addItem(openWindowKeys.getItemAt(i) as String);
			}
			
			for(var k:uint=0; k< keyList.length;k++){
				unregister(keyList.getItemAt(k) as String);
			}
		}
		
		public function get workAreaOrigin():Object{
			var origin:Object = new Object();
			if(workArea!=null){
				var reference:Point = new Point();
				reference.x = 0;
				reference.y = 0;
				var point:Point = workArea.contentToGlobal(reference);
				origin["x"] = point.x;
				origin["y"] = point.y;
			}
			return origin;
		}

		public function getWindow(key:String):ResizableWindow{
			return openWindows[key];
		}
		
		public function openWindow(key:String, windowClass:Class):ResizableWindow{
			if(openWindowKeys.contains(key)){
				unregister(key, true);
			}
			var window:ResizableWindow = PopUpManager.createPopUp(workArea, windowClass, false) as ResizableWindow;
			window.x = workAreaOrigin.x;
			window.y = workAreaOrigin.y;
			register(key, window);
			return window;
		}
		
		public function openCenterWindow(key:String, windowClass:Class):ResizableWindow{
			if(openWindowKeys.contains(key)){
				unregister(key, true);
			}
			var window:ResizableWindow = PopUpManager.createPopUp(workArea, windowClass, false) as ResizableWindow;
			window.x = workAreaOrigin.width/2 - window.width/2;
			window.y = workAreaOrigin.height/2 - window.height/2;
			register(key, window);
			return window;
		}
		
		public function register(key:String, window:ResizableWindow):void{
			window.key = key;
			openWindowKeys.addItem(key);
			openWindows[key] = window;
			window.addEventListener("minWindow", minWindow);
			window.addEventListener("maxWindow", maxWindow);
			window.addEventListener("closeWindow", closeWindow);
		}
	    public function containsWindow(key:String):Boolean{
	    	var window:ResizableWindow = openWindows[key] as ResizableWindow;
	    	if(window==null)
	    	   return false;
	    	 else 
	    	   return true;   
	    	   
	    }	
		
		public function unregister(key:String, cascade:Boolean=false):void{
			
			if(cascade){
				for(var i:uint=0; i< openWindowKeys.length; i++){
					var child:ResizableWindow = openWindows[openWindowKeys.getItemAt(i)] as ResizableWindow;
					if(child != null && child.parentReference!=null
						&& child.parentReference.key == key){
						unregister(child.key, true);		
					}
				}
			}
			var window:ResizableWindow = openWindows[key] as ResizableWindow;
			window.removeEventListener("minWindow", minWindow);
			window.removeEventListener("maxWindow", maxWindow);
			window.removeEventListener("closeWindow", closeWindow);
			if(window.parentReference!=null){
				window.parentReference.removeEventListener(Event.CLOSE, window.closeWindow);
			}
			openWindows[key] = null;
			openWindowKeys.removeItemAt(openWindowKeys.getItemIndex(key));
			if(window.isMinimized){
				TaskBarManager.getInstance().removeIfPresent(key);
			}
			window.cleanResources();
			PopUpManager.removePopUp(window);
		}

		public function restore(key:String):void{
			var window:ResizableWindow = getWindow(key);
			if(window!=null){
				window.isMinimized = false;
				window.visible = true;
				window.bringToFront();
			}
		}
		
		public function menuPanelResized():void{
			for(var i:uint=0; i< openWindowKeys.length;i++){
				var key:String = openWindowKeys.getItemAt(i) as String;
				var window:ResizableWindow = openWindows[key] as ResizableWindow;
				if(window!=null && window.isMaximized){
					window.maximizeWindow(true);
				}
			}
		}
		
		public function minimizeAll():void{
			for(var i:uint=0; i< openWindowKeys.length;i++){
				var key:String = openWindowKeys.getItemAt(i) as String;
				var window:ResizableWindow = openWindows[key] as ResizableWindow;
				if(window!=null && (!window.isMinimized)){
					window.minimizeWindow();
				}
			}
		}
		
		/**
		 * Registers the popup
		 * @param key
		 * @param window
		 * 
		 */
		public function registerPopup(key:String, window:Object):void{
			openPopups[key] = window;
		}
		
		/**
		 * Returns the popup
		 * @param key
		 * @return 
		 * 
		 */
		public function getPopup(key:String):Object{
			return openPopups[key];
		}		
		
		/**
		 * Unregisters the popup
		 * @param key
		 * 
		 */
		public function unregisterPopup(key:String):void{
			openPopups[key] = null;
		}

		private function minWindow(event:FlexEvent):void{
			var window:ResizableWindow = ResizableWindow(event.currentTarget);
			if(window.isMinimized){
				return;
			}
			window.minimizeWindow();
		}

		private function maxWindow(event:FlexEvent):void{
			var window:ResizableWindow = ResizableWindow(event.currentTarget);
			window.maximizeWindow();
		}

		private function closeWindow(event:FlexEvent):void{
			var window:ResizableWindow = ResizableWindow(event.currentTarget);
			unregister(window.key);
		}
	}
}