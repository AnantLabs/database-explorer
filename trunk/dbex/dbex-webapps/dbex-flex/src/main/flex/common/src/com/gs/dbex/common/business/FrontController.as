package com.gs.dbex.common.business
{
	import com.gs.dbex.common.command.BaseCommand;
	import com.gs.dbex.common.event.BaseEventDispatcher;
	import com.gs.dbex.common.event.DbexBaseEvent;
	import flash.utils.Dictionary;
	
	public class FrontController
	{
		private var commands : Dictionary = new Dictionary();

     /**
      * Registers a ICommand class with the Front Controller, against an event name
      * and listens for events with that name.
      *
      * <p>When an event is broadcast that matches commandName,
      * the ICommand class referred to by commandRef receives control of the
      * application, by having its execute() method invoked.</p>
      *
      * @param commandName The name of the event that will be broadcast by the
      * when a particular user gesture occurs, eg "login"
      *
      * @param commandRef An ICommand Class reference upon which execute()
      * can be called when the Front Controller hears an event broadcast with
      * commandName. Typically, this argument is passed as "LoginCommand" 
      * or similar.
      */     
      public function addCommand( commandName : String, commandRef : Class ) : void
      {
         if( commands[ commandName ] != null )
         {
            throw new RIAError(
               RIAMessageCodes.COMMAND_ALREADY_REGISTERED, commandName );
         }
         
         commands[ commandName ] = commandRef;
     	BaseEventDispatcher.getInstance().addEventListener( commandName, executeCommand );
      }
      
      protected function executeCommand( event:DbexBaseEvent ) : void
      {
         var commandToInitialise : Class = getCommand( event.type );
         var commandToExecute : BaseCommand = new commandToInitialise();

         commandToExecute.execute( event );
      }
      
      /**
       * Returns the command class registered with the command name. 
       */
      protected function getCommand( commandName : String ) : Class
      {
         var command : Class = commands[ commandName ];
         
         if ( command == null )
         {
            throw new RIAError(
               RIAMessageCodes.COMMAND_NOT_FOUND, commandName );
         } 
            
         return command;
      }  
	}
}