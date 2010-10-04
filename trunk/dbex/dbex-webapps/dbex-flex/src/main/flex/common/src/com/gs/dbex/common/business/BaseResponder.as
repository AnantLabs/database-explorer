package com.gs.dbex.common.business
{
	public interface BaseResponder
	{
		  /**
       * The onResult method interface is used to mark the method on a
       * concrete Responder that will handle the results from a successful
       * call to a server-side service.  The actual data returned will be
       * held in the event.
       *
       * @param event An object containing the data passed back from the
       * service call, it is recommended that this be immediately narrowed
       * within the concrete responder by using an appropriate cast.  For
       * instance, if you invoke a Java method that returns an AccountVO
       * value object, cast event.result to an AccountVO as follows:
       * <p>
       * <code> var customerAccount:AccountVO = AccountVO( event.result );</code>
       * </p>
       * <p>
       * It is considered good practice when building applications with the
       * RIA framework, to indicate the return types from the server
       * by appropriate casting.
       * </p>
       *
       * <p>
       * Java Developers should take care not to use
       * the Java casting notation - a common mistake for RIA developers
       * migrating from J2EE development.
       * </p>
       */
      function onResult( event : * = null ) : void;

      /**
       * The onFault method interface is used to mark the method on a
       * concrete Responder that will handle the information from a failed
       * call to a server-side service.  The actual data returned will be
       * held in the event.
       *
       * @param event An object containing the data passed back from the
       * service call
       */
      function onFault( event : * = null ) : void;
      
		
	}
}