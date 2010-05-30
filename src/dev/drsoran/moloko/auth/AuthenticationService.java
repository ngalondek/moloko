package dev.drsoran.moloko.auth;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Service to handle Account authentication. It instantiates the authenticator and returns its IBinder.
 */
public class AuthenticationService extends Service
{
   @SuppressWarnings( "unused" )
   private static final String TAG = AuthenticationService.class.getSimpleName();
   
   private Authenticator authenticator;
   
   

   @Override
   public void onCreate()
   {
      authenticator = new Authenticator( this );
   }
   


   @Override
   public IBinder onBind( Intent intent )
   {
      if ( intent != null
         && intent.getAction()
                  .equals( AccountManager.ACTION_AUTHENTICATOR_INTENT ) )
      {
         return authenticator.getIBinder();
      }
      
      return null;
   }
}
