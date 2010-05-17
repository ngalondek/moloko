package dev.drsoran.moloko.service;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.service.auth.AsyncRtmServiceAuth;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncRtmService implements ServiceConnection
{
   private final static String TAG = AsyncRtmService.class.getSimpleName();
   
   
   public interface Auth
   {
      public Future< ? > beginAuthorization( ApplicationInfo applicationInfo,
                                             RtmAuth.Perms permission,
                                             ResultCallback< String > callback );
      


      public Future< ? > completeAuthorization( ResultCallback< String > callback );
      


      public Future< ? > checkAuthToken( String authToken,
                                         ResultCallback< RtmAuth > callback );
   }
   

   public interface Task
   {
      public Future< ? > getList( String listId,
                                  String filter,
                                  Date lastSync,
                                  ResultCallback< RtmTasks > callback );
   }
   
   // class AsyncRtmService
   
   public final Auth AUTH = new AsyncRtmServiceAuth();
   
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
         
         ( (AsyncRtmServiceAuth) AUTH ).connectService( syncService,
                                                        handler,
                                                        executor );
      }
      catch ( RemoteException e )
      {
         // In this case the service has crashed before we could even
         // do anything with it; we can count on soon being
         // disconnected (and then reconnected if it can be restarted)
         // so there is no need to do anything here.
      }
      catch ( InterruptedException e )
      {
         // TODO Auto-generated catch block
      }
      catch ( ExecutionException e )
      {
         // TODO Auto-generated catch block
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
   


   public Future< ? > task_getList( final String listId,
                                    final String filter,
                                    final Date lastSync,
                                    final ResultCallback< RtmTasks > callback )
   {
      
      return executor.submit( new Runnable()
      {
         public void run()
         {
            RtmTasks res = null;
            
            try
            {
               if ( isConnected() )
               {
                  res =
                     syncService.tasks_getList( listId,
                                                filter,
                                                ( lastSync != null ? new ParcelableDate( lastSync )
                                                                  : null ) );
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
