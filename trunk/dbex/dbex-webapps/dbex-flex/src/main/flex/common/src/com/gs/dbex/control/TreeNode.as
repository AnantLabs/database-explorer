package com.gs.dbex.control
{
	import com.gs.dbex.common.model.IDbexObject;
	
	import mx.collections.ArrayCollection;
	
	public class TreeNode
	{
		private var _type:String="";
        private var _name:String="";
        private var _isLeaf:Boolean;
        private var _children:ArrayCollection;
        private var _parentNode:TreeNode;
        private var _data:IDbexObject;
        private var _icon:Class;

        public function TreeNode()
        {

        }

        public function addChild(child:TreeNode):void{
            if(null == child){
            	return;
            }
            if (this.children == null) {
            	this.children = new ArrayCollection();
            }
            children.addItem(child);
            this.isLeaf = false;      
        }
        
        public function get type():String{
        	return _type;
        }
        public function set (value:String):void{
        	_type = value;
        }
        
        public function get name():String{
        	return _name;
        }
        public function set name(value:String):void{
        	_name = value;
        }
        
        public function get isLeaf():Boolean{
        	return _isLeaf;
        }
        public function set isLeaf(value:Boolean):void{
        	_isLeaf = value;
        }
        
        public function get children():ArrayCollection{
        	return _children;
        }
        public function set children(value:ArrayCollection):void{
        	_children = value;
        }
        
        public function get parentNode():TreeNode{
        	return _parentNode;
        }
        public function set parentNode(value:TreeNode):void{
        	_parentNode = value;
        }
        
        public function get data():IDbexObject{
        	return _data;
        }
        public function set data(value:IDbexObject):void{
        	_data = data;
        }
        
        public function get icon():Class{
        	return _icon;
        }
        public function set icon(value:Class):void{
        	_icon = value;
        }
	}
}