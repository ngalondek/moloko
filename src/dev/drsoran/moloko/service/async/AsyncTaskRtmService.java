package dev.drsoran.moloko.service.async;

import java.util.Collection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import android.os.Handler;
import android.os.RemoteException;

import com.mdt.rtm.data.RtmTasks;

import dev.drsoran.moloko.service.IRtmService;
import dev.drsoran.moloko.service.async.AsyncRtmService.Task;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.util.ResultCallback;


public class AsyncTaskRtmService implements Task, ILazyConnector
{
   private IRtmService syncService = null;
   
   private Handler handler = null;
   
   private ExecutorService executor = null;
   
   

   public AsyncTaskRtmService()
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
   


   public Future< ? > getList( final String listId,
                               final String filter,
                               final Date lastSync,
                               final ResultCallback< RtmTasks > callback )
   {
      return executor.submit( new Callable< Void >()
      {
         public Void call()
         {
            RtmTasks res = null;
            
            try
            {
               res =
                  syncService.tasks_getList( listId,
                                             filter,
                                             ( lastSync != null ? new ParcelableDate( lastSync )
                                                               : null ) );
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
