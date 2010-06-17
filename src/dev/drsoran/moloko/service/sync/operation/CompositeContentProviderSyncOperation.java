package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class CompositeContentProviderSyncOperation extends
         ContentProviderSyncOperation
{
   private final ArrayList< ContentProviderSyncOperation > operations = new ArrayList< ContentProviderSyncOperation >();
   
   

   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
   }
   


   public CompositeContentProviderSyncOperation(
      ContentProviderClient provider,
      ArrayList< ContentProviderSyncOperation > operations, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      this.operations.addAll( operations );
   }
   


   public void add( ContentProviderSyncOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( operation );
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
   


   @Override
   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      int num = 0;
      
      for ( Iterator< ContentProviderSyncOperation > i = operations.iterator(); i.hasNext(); )
      {
         num += i.next().getBatch( batch );
      }
      
      return num;
   }
}
