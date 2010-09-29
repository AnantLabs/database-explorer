package com.gs.dbex.control
{
	import com.gs.dbex.layout.MinimizeBar;
	import com.gs.dbex.layout.ResizableWindow;
	
	import flash.events.Event;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.containers.ApplicationControlBar;
	import mx.controls.Image;
	import mx.controls.Menu;
	import mx.core.Application;
	import mx.events.MenuEvent;
	
	public class TaskBarManager
	{
		
	    private static const HEIGHT:uint = 25;
		private static var manager:TaskBarManager = new TaskBarManager();
		
		/**                                                                                                                                                                 
	     *  A reference to application control bar.
	     */
	    private var applicationControlBar:ApplicationControlBar;
	
	    /**
	    * Contains the menu items for the minimized list
	    */ 
	    [Bindable]
	    private var minimizedCollection:ArrayCollection = new ArrayCollection();

		public static function getInstance():TaskBarManager{
			return manager;
		}
		
		public function TaskBarManager(){
			applicationControlBar = initialize();
		}
		/**
	     *  Adds the window to the Taskbar.
	     */
	    public function addToTaskBar(item:ResizableWindow):void{
	    	if(applicationControlBar.visible == false){
	    		applicationControlBar.visible = true;
				Application.application.invalidateProperties();
				Application.application.invalidateSize();
				
	    	}
	    	if(!isWindowAdded(item)){
		    	var barItem:TaskBarItem = new TaskBarItem();
		    	barItem.key = item.key;
		    	barItem.label = item.title;
		    	minimizedCollection.addItem(barItem);
	    	}
	    }	    
	    
	    public function removeIfPresent(key:String):void{
	    	for(var i:uint=0;i<minimizedCollection.length;i++){
	    		var item:TaskBarItem = minimizedCollection.getItemAt(i) as TaskBarItem;
	    		if(item.key==key){
	    			minimizedCollection.removeItemAt(i);
	    			break;
	    		}
	    	}
	    	if(minimizedCollection.length==0){
	    		applicationControlBar.visible = false;
	    	}
	    }
	    
	    /**
	     *  @private
	     *  Initializes the Task Bar component;
	     */
	    private function initialize():ApplicationControlBar
	    {
			var applicationControlBar2:MinimizeBar = new MinimizeBar();
			var root:Application = Application.application as Application;
			
			applicationControlBar2.width = root.width;
			applicationControlBar2.height = 30;
			applicationControlBar2.y = root.height - 30;
			applicationControlBar2.alpha = 0.5;
			applicationControlBar2.visible = false;
			applicationControlBar2.addEventListener("menuSelection", showMenu);

			root.addChild(applicationControlBar2);
			return applicationControlBar2;
	    }
	    
	    private function showMenu(event:Event):void{
	    	var minMenu:Menu = Menu.createMenu(applicationControlBar, minimizedCollection, false);
	    	minMenu.labelField = "label";
	    	minMenu.addEventListener(MenuEvent.ITEM_CLICK, restore);
	    	// Calculate position of Menu in Application's coordinates. 
	    	var image:Image = applicationControlBar.getChildByName("menuImage") as Image;
	    	var point1:Point = new Point();
            point1.x=0;
            point1.y=0;                
            point1=image.localToGlobal(point1);
            minMenu.show(point1.x, point1.y - (minimizedCollection.length*20));
	    }
	    
	    private function restore(event:MenuEvent):void{
	    	var barItem:TaskBarItem = event.item as TaskBarItem;
	    	minimizedCollection.removeItemAt(minimizedCollection.getItemIndex(barItem));
	    	if(minimizedCollection.length==0){
	    		applicationControlBar.visible = false;
	    	}
	    	DbexWindowManager.getInstance().restore(barItem.key);
	    }
		
		/*
		 * Custom method to check for duplicates... 
		 * To address the issue with reset of minimized flag across modules
		 */ 
		private function isWindowAdded(window:ResizableWindow):Boolean{
			var isAdded:Boolean = false;
			for(var i:uint=0;i<minimizedCollection.length;i++){
	    		var item:TaskBarItem = minimizedCollection.getItemAt(i) as TaskBarItem;
	    		if(item.key==window.key){
	    			isAdded = true;
	    			break;
	    		}
	    	}
			return isAdded;
		}
	}
}