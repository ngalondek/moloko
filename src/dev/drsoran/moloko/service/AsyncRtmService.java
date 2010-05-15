package dev.drsoran.moloko.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.service.parcel.ParcelableRtmAuth;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncRtmService implements ServiceConnection
{
   private final static String TAG = AsyncRtmService.class.getSimpleName();
   
   // private final Lock lock = new ReentrantLock();
   //   
   // private final Condition svcConnected = lock.newCondition();
   //   
   // private final Condition svcDisconnected = lock.newCondition();
   
   private final Handler handler = new Handler();
   
   private final ExecutorService executor = Executors.newSingleThreadExecutor();
   
   private ApplicationInfo applicationInfo;
   
   private RtmAuth.Perms permission;
   
   private IRtmService syncService = null;
   
   private Context context = null;
   
   

   public AsyncRtmService( Context context,
                           final ApplicationInfo applicationInfo,
                           final RtmAuth.Perms permisson )
   {
      this.context = context;
      this.applicationInfo = applicationInfo;
      this.permission = permisson;
      connect( context );
   }
   


   public void updateService( final ApplicationInfo applicationInfo,
                              final RtmAuth.Perms permission ) throws RemoteException
   {
      if ( syncService == null )
      {
         throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
      }
      
      executor.submit( new Callable< Void >()
      {
         
         public Void call() throws RemoteException
         {
            try
            {
               syncService.initialize( new ParcelableApplicationInfo( applicationInfo,
                                                                      permission ) );
               
               AsyncRtmService.this.applicationInfo = applicationInfo;
               AsyncRtmService.this.permission = permission;
               
               Log.d( TAG, "Service updated." );
            }
            catch ( RemoteException e )
            {
               throw new RtmServiceException( RtmServiceConstants.ServiceState.INTERNAL_ERROR );
            }
            
            return null;
         }
         
      } );
   }
   


   public static String getExceptionCause( final Exception e )
   {
      if ( e instanceof RtmServiceException )
      {
         return ( (RtmServiceException) e ).rtmCause;
      }
      else
      {
         return e.getMessage();
      }
   }
   


   public static int getExceptionCode( final Exception e )
   {
      if ( e instanceof RtmServiceException )
      {
         return ( (RtmServiceException) e ).errorCode;
      }
      else
      {
         return 0;
      }
   }
   


   public void shutdown()
   {
      disconnect();
   }
   


   public void onServiceConnected( ComponentName name, IBinder service )
   {
      syncService = IRtmService.Stub.asInterface( service );
      try
      {
         syncService.initialize( new ParcelableApplicationInfo( applicationInfo,
                                                                permission ) );
         Log.d( TAG, "Service connected." );
      }
      catch ( RemoteException e )
      {
         // In this case the service has crashed before we could even
         // do anything with it; we can count on soon being
         // disconnected (and then reconnected if it can be restarted)
         // so there is no need to do anything here.
      }
   }
   


   public void onServiceDisconnected( ComponentName name )
   {
      // This is called when the connection with the service has been
      // unexpectedly disconnected -- that is, its process crashed.
      syncService = null;
      
      Log.d( TAG, "Service disconnected." );
   }
   


   public boolean isConnected()
   {
      boolean is = false;
      
      is = syncService != null;
      
      return is;
   }
   


   // / ASYNCRONOUS INTERFACE ///
   
   public Future< ? > beginAuthorization( final ApplicationInfo applicationInfo,
                                          final RtmAuth.Perms permission,
                                          final ResultCallback< String > callback )
   {
      return executor.submit( new Runnable()
      {
         public void run()
         {
            String res = null;
            
            try
            {
               if ( isConnected() )
               {
                  res =
                     syncService.beginAuthorization( new ParcelableApplicationInfo( applicationInfo,
                                                                                    permission ) );
               }
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
               return;
            }
            
            handler.post( callback.setResult( res ) );
         }
      } );
   }
   


   public Future< ? > completeAuthorization( final ResultCallback< String > callback )
   {
      return executor.submit( new Runnable()
      {
         public void run()
         {
            String res = null;
            
            try
            {
               if ( isConnected() )
                  res = syncService.completeAuthorization();
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
               return;
            }
            
            handler.post( callback.setResult( res ) );
         }
      } );
   }
   


   public Future< ? > checkAuthToken( final String authToken,
                                      final ResultCallback< RtmAuth > callback )
   {
      return executor.submit( new Runnable()
      {
         public void run()
         {
            RtmAuth res = null;
            
            try
            {
               if ( isConnected() )
               {
                  final ParcelableRtmAuth parcelableRtmAuth =
                     syncService.checkAuthToken( authToken );
                  
                  if ( parcelableRtmAuth != null )
                     res = parcelableRtmAuth.getRtmAuth();
               }
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
               return;
            }
            
            handler.post( callback.setResult( res ) );
         }
      } );
   }
   


   // / PRIVATE IMPLEMENTATIONS ///
   
   private void connect( final Context context )
   {
      // Establish a couple connections with the service, binding
      // by interface names. This allows other applications to be
      // installed that replace the remote service by implementing
      // the same interface.
      context.bindService( new Intent( IRtmService.class.getName() ),
                           this,
                           Context.BIND_AUTO_CREATE );
   }
   


   // private boolean waitConnected( long time, TimeUnit unit ) throws
   // InterruptedException
   // {
   // lock.lock();
   //      
   // boolean ok = false;
   //      
   // try
   // {
   // ok = svcConnected.await( time, unit );
   // }
   // finally
   // {
   // lock.unlock();
   // }
   //      
   // return ok;
   // }
   
   private void disconnect()
   {
      if ( syncService != null )
      {
         /*
          * try { syncService.unregisterCallback( rtmServiceCallback ); } catch ( RemoteException e ) { // There is
          * nothing special we need to do if the service // has crashed. }
          */

         context.unbindService( this );
      }
   }
}
