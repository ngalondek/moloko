package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class ContentProviderSyncOperation extends AbstractContentProviderSyncOperation
{
   private final ArrayList< ContentProviderOperation > operations;
   
   

   public ContentProviderSyncOperation( ContentProviderClient provider,
      int operationType ) throws NullPointerException
   {
      super( provider, operationType );
      
      if ( provider == null )
         throw new NullPointerException();
      
      this.operations = new ArrayList< ContentProviderOperation >();
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      ContentProviderOperation operation, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< ContentProviderOperation >( 1 );
      this.operations.add( operation );
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      Collection< ContentProviderOperation > operations, int operationType )
      throws NullPointerException
   {
      super( provider, operationType );
      
      this.operations = new ArrayList< ContentProviderOperation >( operations );
   }
   


   public void add( ContentProviderOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      this.operations.add( operation );
   }
   


   public void addAll( Collection< ContentProviderOperation > operations ) throws NullPointerException
   {
      if ( operations == null )
         throw new NullPointerException();
      
      this.operations.addAll( operations );
   }
   


   public boolean execute( SyncResult result )
   {
      boolean ok = true;
      
      try
      {
         provider.applyBatch( operations );
         updateSyncResult( result, operationType, operations.isEmpty() ? 0 : 1 );
      }
      catch ( OperationApplicationException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      catch ( RemoteException e )
      {
         ++result.stats.numIoExceptions;
         ok = false;
      }
      
      return ok;
   }
   


   public int getBatch( ArrayList< ContentProviderOperation > batch )
   {
      batch.addAll( operations );
      return operations.size();
   }
   


   public int getOperationType()
   {
      return operationType;
   }
}