package com.gs.dbex.common.command
{
	import com.gs.dbex.common.event.DbexBaseEvent;
	
	public interface BaseCommand
	{
	  /** Called by the Front Controller to execute the command.
       * 
       * <p>The single entry point into an ICommand, the 
       * execute() method is called by the Front Controller when a 
       * user-gesture indicates that the user wishes to perform a 
       * task for which a particularconcrete command class has been 
       * provided.</p>
       *
       * @param event When the Front Controller receives notification
       * of a user gesture, the Event that it receives contains both the
       * type of the event (indicating which command should handle the
       * work) but also any data associated with the event.
       *
       * <p>
       * For instance, if a "login" event has been broadcasted,
       * to which the controller has registered the LoginCommand,
       * the event may also contain some associated data, such as
       * the number of prior attempts at login have been made
       * already. In this case, the event.type would be set to
       * "login" while other properties define in the custom login 
       * event object would contain (by way of example)
       * an attribute such as attemptedLogins. 
       * </p>
       *
       * <p>
       * By careful use of custom event objects, the same
       * concrete command class is capable of responding in slightly
       * different ways to similar user gesture requests.
       * </p>
       */
      function execute( event:DbexBaseEvent ) : void;
	}
}