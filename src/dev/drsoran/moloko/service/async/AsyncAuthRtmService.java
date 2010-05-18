package dev.drsoran.moloko.service.async;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import android.os.Handler;
import android.os.RemoteException;

import com.mdt.rtm.ApplicationInfo;
import com.mdt.rtm.data.RtmAuth;
import com.mdt.rtm.data.RtmAuth.Perms;

import dev.drsoran.moloko.service.IRtmService;
import dev.drsoran.moloko.service.async.AsyncRtmService.Auth;
import dev.drsoran.moloko.service.parcel.ParcelableApplicationInfo;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncAuthRtmService implements Auth, ILazyConnector
{
   private IRtmService syncService = null;
   
   private Handler handler = null;
   
   private ExecutorService executor = null;
   
   

   public AsyncAuthRtmService()
   {
      this.executor = new ProxyExecutorService();
   }
   


   public void connectService( IRtmService service,
                               Handler handler,
                               ExecutorService executor ) throws InterruptedException,
                                                         ExecutionException
   {      
      if ( !isConnected() )
      {
         this.syncService = service;
         this.handler = handler;
         
         ProxyExecutorService proxyExecutorService =
            (ProxyExecutorService) this.executor;
         
         final Collection< Callable< Void > > cachedTasks =
            proxyExecutorService.getTaskList();
         
         if ( cachedTasks.size() > 0 )
            executor.invokeAny( cachedTasks );
         
         this.executor = executor;
      }
   }
   


   public boolean isConnected()
   {
      return !( executor instanceof ProxyExecutorService );
   }
   


   public Future< ? > beginAuthorization( final ApplicationInfo applicationInfo,
                                          final Perms permission,
                                          final ResultCallback< String > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            String res = null;
            
            try
            {
               res =
                  syncService.beginAuthorization( new ParcelableApplicationInfo( applicationInfo,
                                                                                 permission ) );
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            handler.post( callback.setResult( res ) );
            
            return null;
         }
      } );
   }
   


   public Future< ? > checkAuthToken( final String authToken,
                                      final ResultCallback< RtmAuth > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            RtmAuth res = null;
            
            try
            {
               res = syncService.checkAuthToken( authToken );
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            handler.post( callback.setResult( res ) );
            
            return null;
         }
      } );
   }
   


   public Future< ? > completeAuthorization( final ResultCallback< String > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            String res = null;
            
            try
            {
               res = syncService.completeAuthorization();
            }
            catch ( RemoteException e )
            {
               handler.post( callback.setResult( res, e ) );
            }
            
            handler.post( callback.setResult( res ) );
            
            return null;
         }
      } );
   }
}
