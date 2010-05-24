package dev.drsoran.moloko.service.async;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

import android.os.Handler;
import dev.drsoran.moloko.service.IRtmService;


public abstract class AbstractAsyncServicePart implements ILazyConnector
{
   protected IRtmService syncService = null;
   
   protected Handler handler = null;
   
   protected ExecutorService executor = null;
   
   

   protected AbstractAsyncServicePart()
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
         
         ProxyExecutorService proxyExecutorService = (ProxyExecutorService) this.executor;
         
         final Collection< Callable< Void > > cachedTasks = proxyExecutorService.getTaskList();
         
         if ( cachedTasks.size() > 0 )
            executor.invokeAny( cachedTasks );
         
         this.executor = executor;
      }
      
   }
   


   public void disconnectService()
   {
      if ( isConnected() )
      {
         this.executor = new ProxyExecutorService();
         this.handler = null;
         this.syncService = null;
      }
      else
      {
         this.executor.shutdownNow();
      }
   }
   


   public boolean isConnected()
   {
      return !( executor instanceof ProxyExecutorService );
      
   }
   
}
