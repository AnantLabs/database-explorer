package com.gs.dbex.common.business
{
	/**
	 * IServiceLocator is the interface for service locators which 
	 * allow service to be located and securitycredentials to be managed.
	 * 
	 */


   import mx.messaging.Consumer;
   import mx.messaging.MessageAgent;
   import mx.messaging.Producer;
   import mx.rpc.AbstractService;
   import mx.rpc.http.HTTPService;
   import mx.rpc.remoting.RemoteObject;
   import mx.rpc.soap.WebService;
   
   public interface IServiceLocator 
   {
		/**
		 * Return the RemoteObject for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 */
      function getRemoteObject( serviceId : String ) : RemoteObject;
      
		/**
		 * Return the HTTPService for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 */
      function getHTTPService( serviceId : String ) : HTTPService;
      
		/**
		 * Return the WebService for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 */
      function getWebService( destinationId : String ) : WebService;
      
		/**
		 * Return the message Consumer for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 */
      function getConsumer( destinationId : String ) : Consumer;
      
		/**
		 * Return the message Produce for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 */
      function getProducer( destinationId : String ) : Producer;
      
		/**
		 * Return the DataService for the given service id.
		 * @param serviceId the service id.
		 * @return the RemoteObject.
		 * todo karthik: evealuate this
		 */
//      function getDataService( destinationId : String ) : DataService;
      
		/**
		 * Set the credentials for all registered services. Note that services
		 * that use a proxy or a third-party adapter to a remote endpoint will
		 * need to setRemoteCredentials instead.
		 * @param username the username to set.
		 * @param password the password to set.
		 */
      function setCredentials( username : String,   password : String ) : void;
      
		/**
		 * Logs the user out of all registered services.
		 */
      function logout() : void;
   }
}