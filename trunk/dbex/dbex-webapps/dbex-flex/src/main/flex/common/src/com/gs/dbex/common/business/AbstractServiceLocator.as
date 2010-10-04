package com.gs.dbex.common.business
{
   /**
    * The ServiceLocator allows service to be located and security
    * credentials to be managed.
    * 
    * Although credentials are set against a service they apply to the channel
    * i.e. the set of services belonging to the channel share the same
    * credentials.
    * 
    * You must always make sure you call logout at the end of the user's
    * session.
    */

   import com.gs.dbex.common.event.BaseEventDispatcher;
   import com.gs.dbex.common.event.SyncCallEvent;
   
   import flash.utils.describeType;
   
   import mx.controls.Alert;
   import mx.core.UIComponent;
   import mx.messaging.Consumer;
   import mx.messaging.MessageAgent;
   import mx.messaging.Producer;
   import mx.rpc.AbstractInvoker;
   import mx.rpc.AbstractService;
   import mx.rpc.http.HTTPService;
   import mx.rpc.remoting.RemoteObject;
   import mx.rpc.soap.WebService;

   
   public class AbstractServiceLocator extends UIComponent implements IServiceLocator
   {   
      private static var instance : AbstractServiceLocator;
      
      private var services : Array;
      private var httpServices : Array;
      private var messageAgents : Array;
      private var dataServices : Array;
      
      // Constructor should be private but current AS3.0 does not allow it
      public function AbstractServiceLocator() 
      {   
                     
      }

      /**
       * Return the ServiceLocator instance.
       * @return the instance.
       */
      public static function getInstance() : AbstractServiceLocator 
      {
         if ( instance == null )
         {
            instance = new AbstractServiceLocator();
         }
            
         return instance;
      }
               
      /**
       * @deprecated  As of RIA 2.1, replaced by {@link #getRemoteObject( string )}
       * 
       * Returns the service defined for the id, to allow services to be looked up
       * using the ServiceLocator by a canonical name.
       *
       * <p>If no service exists for the service name, an Error will be thrown.</p>
       * @param The id of the service to be returned. This is the id defined in the
       * concrete service locator implementation.
       */
      public  function getService( serviceId : String ) : AbstractService
      {
         return AbstractService( getServiceForId( serviceId ) );
      }      

      /**
       * @deprecated  As of RIA 2.1
       * 
       * Returns an AbstractInvoker defined for the id, to allow services to be looked up
       * using the ServiceLocator by a canonical name.
       *
       * <p>If no service exists for the service name, an Error will be thrown.</p>
       * @param The id of the service to be returned. This is the id defined in the
       * concrete service locator implementation.
       */
      public function getInvokerService( serviceId : String ) : AbstractInvoker
      {
         return AbstractInvoker( getServiceForId( serviceId ) );
      } 
      
      /**
       * Return the RemoteObject for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getRemoteObject( serviceId : String ) : RemoteObject
      {
         return RemoteObject( getServiceForId( serviceId ) );
      }
      
      /**
       * Return the HTTPService for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getHTTPService( serviceId : String ) : HTTPService
      {
         return HTTPService( getServiceForId( serviceId ) );
      }
      
      /**
       * Return the WebService for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getWebService( serviceId : String ) : WebService
      {
         return WebService( getServiceForId( serviceId ) );
      }
      /**
       * Return the WebService for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getSyncWebService( serviceId : String) : WebService
      {
		 dispatchSyncCallEvent();	
         return WebService( getServiceForId( serviceId ) );
      }
      private function dispatchSyncCallEvent():void{
	BaseEventDispatcher.getInstance().dispatchEvent(new SyncCallEvent());     	      	
      }
      /**
       * Return the message Consumer for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getConsumer( serviceId : String ) : Consumer
      {
         return Consumer( getServiceForId( serviceId ) );
      }
      
      /**
       * Return the message Produce for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getProducer( serviceId : String ) : Producer
      {
         return Producer( getServiceForId( serviceId ) );
      }
      
      /**
       * Return the DataService for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
/*       protected  function getDataService( serviceId : String ) : DataService
      {
         return DataService( getServiceForId( serviceId ) );
      }
 */         
      /**
       * Set the credentials for all registered services. Note that services
       * that use a proxy or a third-party adapter to a remote endpoint will
       * need to setRemoteCredentials instead.
       * @param username the username to set.
       * @param password the password to set.
       */
      public function setCredentials( username : String, password : String ) : void 
      {
         setServiceCredentials( username, password );
         setHTTPServiceCredentials( username, password );
         setMessageAgentCredentials( username, password );
 //        setDataServicesCredentials( username, password );
      }
      
      /**
       * Logs the user out of all registered services.
       */
      public function logout() : void
      {
          logoutFromServices();
          logoutFromHTTPServices();
          logoutFromMessageAgents();
//          logoutFromDataServices();
      }
      
      /**
       * Return the service with the given id.
       * @param serviceId the id of the service to return.
       * @return the service.
       */
      private function getServiceForId( serviceId : String ) : Object
      {
      	//Alert.show("serviceId=" + serviceId);
         if ( this[ serviceId ] == null )
         {
            throw new RIAError(
               RIAMessageCodes.NO_SERVICE_FOUND, serviceId );
         }
         
         return this[ serviceId ];
      }
      
      /**
       * Set the user's credentials on all registered services.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setServiceCredentials(
         username : String,
         password : String ) : void
      {
         var list : Array = getServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : AbstractService = list[ i ];
            setCredentialsOnService( service, username, password );
         }
      }
      
      /**
       * Set the user's credentials on all registered HTTP services.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setHTTPServiceCredentials(
         username : String,
         password : String ) : void
      {
         var list : Array = getHTTPServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : HTTPService = list[ i ];
            setCredentialsOnHTTPService( service, username, password );
         }
      }
      
      /**
       * Set the user's credentials on all registered message agents.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setMessageAgentCredentials(
         username : String,
         password : String ) : void
      {
         var list : Array = getMessageAgents();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : MessageAgent = list[ i ];
            setCredentialsOnMessageAgent( service, username, password );
         }
      }
      
      /**
       * Set the user's credentials on all registered data services.
       * @param username the username to set.
       * @param password the password to set.
       */
/*       private function setDataServicesCredentials(
         username : String,
         password : String ) : void
      {
         var list : Array = getDataServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : DataService = list[ i ];
            setCredentialsOnDataService( service, username, password );
         }
      }
 */      
      /**
       * Logs the user out of all registered services.
       */
      private function logoutFromServices() : void
      {
          var list : Array = getServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : AbstractService = list[ i ];
            service.logout();
         }
      }
      
      /**
       * Logs the user out of all registered HTTP services.
       */
      private function logoutFromHTTPServices() : void
      {
          var list : Array = getHTTPServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : HTTPService = list[ i ];
            service.logout();
         }
      }
      
      /**
       * Logs the user out of all registered message agents.
       */
      private function logoutFromMessageAgents() : void
      {
          var list : Array = getMessageAgents();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : MessageAgent = list[ i ];
            service.logout();
         }
      }
      
      /**
       * Logs the user out of all registered data services.
       */
/*       private function logoutFromDataServices() : void
      {
          var list : Array = getDataServices();
         
         for ( var i : uint = 0; i < list.length; i++ )
         {
            var service : DataService = list[ i ];
            service.logout();
         }
      }
 */      
      /**
       * Sets the credentials on a service. Logout is called first to clear
       * any existing credentials.
       * @param service the service.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setCredentialsOnService(
          service : AbstractService,
          username : String,
          password : String ) : void
      {
          service.logout();
          service.setCredentials( username, password );
      }
      
      /**
       * Sets the credentials on an HTTP service. Logout is called first to
       * clear any existing credentials.
       * @param service the service.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setCredentialsOnHTTPService(
          service : HTTPService,
          username : String,
          password : String ) : void
      {
          service.logout();
          service.setCredentials( username, password );
      }
      
      /**
       * Sets the credentials on a message agent. Logout is called first to
       * clear any existing credentials.
       * @param service the message agent.
       * @param username the username to set.
       * @param password the password to set.
       */
      private function setCredentialsOnMessageAgent(
          service : MessageAgent,
          username : String,
          password : String ) : void
      {
          service.logout();
          service.setCredentials( username, password );
      }
      
      /**
       * Sets the credentials on a data service. Logout is called first to
       * clear any existing credentials.
       * @param service the data service.
       * @param username the username to set.
       * @param password the password to set.
       */
/*       private function setCredentialsOnDataService(
          service : DataService,
          username : String,
          password : String ) : void
      {
          service.logout();
          service.setCredentials( username, password );
      }
 */      
      /**
       * Return the configured services.
       * @return the services.
       */
      private function getServices() : Array
      {         
         if ( services == null )
         {
            services = new Array();
            
            var accessors : XMLList = getAccessors();
         
            for ( var i : uint = 0; i < accessors.length(); i++ )
            {
               var name : String = accessors[ i ];
               var obj : Object = this[ name ];
               if ( obj is AbstractService )
               {
                  services.push( obj );
               }
            }
         }
         
         return services;
      }
      
      /**
       * Return the configured HTTP services.
       * @return the HTTP services.
       */
      private function getHTTPServices() : Array
      {         
         if ( httpServices == null )
         {
            httpServices = new Array();
            
            var accessors : XMLList = getAccessors();
         
            for ( var i : uint = 0; i < accessors.length(); i++ )
            {
               var name : String = accessors[ i ];
               var obj : Object = this[ name ];
               if ( obj is HTTPService )
               {
                  httpServices.push( obj );
               }
            }
         }
         
         return httpServices;
      }
      
      /**
       * Return the configured message agents.
       * @return the message agents.
       */
      private function getMessageAgents() : Array
      {         
         if ( messageAgents == null )
         {
            messageAgents = new Array();
            
            var accessors : XMLList = getAccessors();
         
            for ( var i : uint = 0; i < accessors.length(); i++ )
            {
               var name : String = accessors[ i ];
               var obj : Object = this[ name ];
               if ( obj is MessageAgent )
               {
                  messageAgents.push( obj );
               }
            }
         }
         
         return messageAgents;
      }

      /**
       * Return the RemoteObject for the given service id.
       * @param serviceId the service id.
       * @return the RemoteObject.
       */
      public function getSyncRemoteObject( serviceId : String ) : RemoteObject
      {
      	dispatchSyncCallEvent();
         return RemoteObject( getServiceForId( serviceId ) );
      }
      
      /**
       * Return the configured data management services.
       * @return the data management services.
       */
/*       private function getDataServices() : Array
      {         
         if ( dataServices == null )
         {
            dataServices = new Array();
            
            var accessors : XMLList = getAccessors();
         
            for ( var i : uint = 0; i < accessors.length(); i++ )
            {
               var name : String = accessors[ i ];
               var obj : Object = this[ name ];
               if ( obj is DataService )
               {
                  dataServices.push( obj );
               }
            }
         }
         
         return dataServices;
      }
 */      
      /**
       * Return all the accessors on this object.
       * @return this object's accessors.
       */
      private function getAccessors() : XMLList
      {
         var description : XML = describeType(this );
         var accessors : XMLList =
            description.accessor.( @access == "readwrite" ).@name;
            
         return accessors;
      }
   }
}
