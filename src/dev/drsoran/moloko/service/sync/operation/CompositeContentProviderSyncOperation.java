package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class CompositeContentProviderSyncOperation extends
         AbstractContentProviderSyncOperation
{
   private final ArrayList< IContentProviderSyncOperation > operations;
   
   

   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< IContentProviderSyncOperation >();
   }
   


   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider,
      ArrayList< IContentProviderSyncOperation > operations, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< IContentProviderSyncOperation >( operations );
   }
   


   public void add( IContentProviderSyncOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( operation );
   }
   


   public void addAll( ArrayList< IContentProviderSyncOperation > operations ) throws NullPointerException
   {
      if ( operations == null )
         throw new NullPointerException();
      
      this.operations.addAll( operations );
   }
   


   public void add( ContentProviderOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( new ContentProviderSyncOperation( provider,
                                                        operation,
                                                        operationType ) );
   }
   


   public boolean execute( SyncResult result )
   {
      final ArrayList< ContentProviderOperation > batch = new ArrayList< ContentProviderOperation >();
      
      getBatch( batch );
      
      boolean ok = true;
      
      try
      {
         provider.applyBatch( batch );
         updateSyncResult( result, operationType, batch.size() );
      }
      catch ( RemoteException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      catch ( OperationApplicationException e )
      {
         result.databaseError = true;
         ok = false;
      }
      
      return ok;
   }
   


   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      int num = 0;
      
      for ( Iterator< IContentProviderSyncOperation > i = operations.iterator(); i.hasNext(); )
      {
         num += i.next().getBatch( batch );
      }
      
      return num;
   }
}
