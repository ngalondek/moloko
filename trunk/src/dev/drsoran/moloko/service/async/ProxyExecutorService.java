package dev.drsoran.moloko.service.async;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class ProxyExecutorService implements ExecutorService
{
   
   private final Collection< Callable< Void > > taskList =
      new ArrayList< Callable< Void > >();
   
   

   public ProxyExecutorService()
   {
   }
   


   public Collection< Callable< Void >> getTaskList()
   {
      return taskList;
   }
   


   public boolean awaitTermination( long timeout, TimeUnit unit ) throws InterruptedException
   {
      return true;
   }
   


   public < T > List< Future< T >> invokeAll( Collection< Callable< T >> arg0 ) throws InterruptedException
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public < T > List< Future< T >> invokeAll( Collection< Callable< T >> arg0,
                                              long arg1,
                                              TimeUnit arg2 ) throws InterruptedException
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public < T > T invokeAny( Collection< Callable< T >> arg0 ) throws InterruptedException,
                                                              ExecutionException
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public < T > T invokeAny( Collection< Callable< T >> arg0,
                             long arg1,
                             TimeUnit arg2 ) throws InterruptedException,
                                            ExecutionException,
                                            TimeoutException
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public boolean isShutdown()
   {
      new UnsupportedOperationException( "Not supported yet." );
      return false;
   }
   


   public boolean isTerminated()
   {
      new UnsupportedOperationException( "Not supported yet." );
      return false;
   }
   


   public void shutdown()
   {
      new UnsupportedOperationException( "Not supported yet." );
   }
   


   public List< Runnable > shutdownNow()
   {
      taskList.clear();
      return null;
   }
   


   @SuppressWarnings( "unchecked" )
   public < T > Future< T > submit( Callable< T > callable )
   {
      taskList.add( (Callable< Void >) callable );
      return new FutureTask< T >( callable );
   }
   


   public Future< ? > submit( Runnable task )
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public < T > Future< T > submit( Runnable task, T result )
   {
      new UnsupportedOperationException( "Not supported yet." );
      return null;
   }
   


   public void execute( Runnable command )
   {
      new UnsupportedOperationException( "Not supported yet." );
   }
}
