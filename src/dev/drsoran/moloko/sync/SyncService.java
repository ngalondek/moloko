package dev.drsoran.moloko.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


/**
 * Service to handle Account sync. This is invoked with an intent with action ACTION_AUTHENTICATOR_INTENT. It
 * instantiates the {@link SyncAdapter} and returns its IBinder.
 */
public class SyncService extends Service
{
   private static final Object syncAdapterLock = new Object();
   
   private static SyncAdapter syncAdapter = null;
   
   

   /*
    * {@inheritDoc}
    */
   @Override
   public void onCreate()
   {
      synchronized ( syncAdapterLock )
      {
         if ( syncAdapter == null )
         {
            syncAdapter = new SyncAdapter( getApplicationContext(), true );
         }
      }
   }
   


   /*
    * {@inheritDoc}
    */
   @Override
   public IBinder onBind( Intent intent )
   {
      return syncAdapter.getSyncAdapterBinder();
   }
}
