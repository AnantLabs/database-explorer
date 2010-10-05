package com.gs.dbex.common.view
{
	import com.gs.dbex.common.business.RIAError;
	import com.gs.dbex.common.business.RIAMessageCodes;
	
	import flash.utils.Dictionary;
	
	public class ViewLocator
   {
      private static var viewLocator : ViewLocator;
      private var viewHelpers : Dictionary;      
      
      /**
       * Singleton access to the ViewLocator is assured through the static getInstance()
       * method, which is used to retrieve the only ViewLocator instance in a RIA
       * application.
       *
       * <p>Wherever there is a need to retreive the ViewLocator instance, it is achieved
       * using the following code:</p>
       *
       * <pre>
       * var viewLocator:ViewLocator = ViewLocator.getInstance();
       * </pre>
       */
      public static function getInstance() : ViewLocator
      {
         if ( viewLocator == null )
            viewLocator = new ViewLocator();
   
         return viewLocator;
      }

      /**
       * The ViewLocator constructor should only be created
       * through the static singleton getInstance() method.  ViewLocator
       * maintains a hash map of ViewHelpers, keyed on viewName with a
       * particular view as the value stored in the hash map.
       */
      public function ViewLocator()
      {
         if ( ViewLocator.viewLocator != null )
         {
            throw new RIAError(
               RIAMessageCodes.SINGLETON_EXCEPTION, "ViewLocator" );
         }
         
         viewHelpers = new Dictionary();      
      }

      /**
       * Registers a viewHelper under a canonical viewName.
       *
       * <p>In order that the application developer need not know
       * the implementation of the view, a ViewHelper capable of manipulating
       * a given view is registered under a simple canonical name.</p>
       * <p>
       * For example, a LoginViewHelper may allow the manipulation of a
       * Login window, that may start life as a PopUpWindow, but later be
       * changed to a screen in a ViewStack.  By registering the LoginViewHelper
       * with the viewName "login", then any code that fetches the ViewHelper
       * by it's name "login", and then calls methods on the ViewHelper, is
       * completely insulated from any changes in the implementation of the
       * view, and the implementation of the ViewHelper.
       * </p>
       * <p>
       * If a view is already registered with the canonical name, and Error
       * is thrown.
       * </p>
       * @param viewName A simple canonical name for the view that the ViewHelper
       * will manipulate, eg "login"
       * @param viewHelper An instance of a ViewHelper
       */
      public function register( viewName : String, viewHelper : ViewHelper ) : void
      {
         if ( registrationExistsFor( viewName ) )
         {
            throw new RIAError(
               RIAMessageCodes.VIEW_ALREADY_REGISTERED, viewName );
         }
   
         viewHelpers[ viewName ] = viewHelper;
      }
      
      /**
       * Unregisters a viewHelper using its canonical name.
       *
       * @param viewName The canonical name for the view to be removed
       */
      public function unregister( viewName : String ) : void
      {
         if ( !registrationExistsFor( viewName ) )
         {
            throw new RIAError(
               RIAMessageCodes.VIEW_NOT_FOUND, viewName );
         }
         
         delete viewHelpers[ viewName ];
      }
      
      /**
       * Retrieves the ViewHelper instance that has previously been registered
       * with viewName.
       *
       * @param viewName The name of the view for which we wish to retrieve a
       * ViewHelper, eg "login"
       * @returns The ViewHelper instance that is required to manipulate the
       * view registered with viewName
       */
      public function getViewHelper( viewName : String ) : ViewHelper
      {
         if ( !registrationExistsFor( viewName ) )
         {
            throw new RIAError(
               RIAMessageCodes.VIEW_NOT_FOUND, viewName );
         }
         
         return viewHelpers[ viewName ];
      }
      
      /**
       * Returns whether a view has been registered with a canonical name.
       *
       * <p>If two views are registered with the same canonical name,
       * the second entry will overwrite the first. This method can be used to check
       * whether a view has already been registered with a canonical name.</p>
       *
       * @param The canonical name for the view that the ViewHelper will check, eg
       * "login"
       * @return A Boolean that indicates if a view is already registered with that
       * view name
       */
      public function registrationExistsFor( viewName : String ) : Boolean
      {
         return viewHelpers[ viewName ] != undefined;
      }
   }
}
