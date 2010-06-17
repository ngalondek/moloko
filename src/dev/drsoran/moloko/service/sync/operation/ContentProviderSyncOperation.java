package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;
import java.util.Collection;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.os.RemoteException;


public class ContentProviderSyncOperation implements ISyncOperation
{
   public final static int OP_INSERT = 0;
   
   public final static int OP_UPDATE = 1;
   
   public final static int OP_DELETE = 2;
   
   private final ArrayList< ContentProviderOperation > operations;
   
   protected final int operationType;
   
   protected final ContentProviderClient provider;
   
   

   protected ContentProviderSyncOperation()
   {
      this.provider = null;
      this.operations = null;
      this.operationType = 0;
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      int operationType ) throws NullPointerException
   {
      if ( provider == null )
         throw new NullPointerException();
      
      this.operations = new ArrayList< ContentProviderOperation >();
      this.provider = provider;
      this.operationType = operationType;
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      ContentProviderOperation operation, int operationType )
      throws NullPointerException
   {
      if ( operation == null || provider == null )
         throw new NullPointerException();
      
      this.operations = new ArrayList< ContentProviderOperation >( 1 );
      this.provider = provider;
      this.operations.add( operation );
      this.operationType = operationType;
   }
   


   public ContentProviderSyncOperation( ContentProviderClient provider,
      Collection< ContentProviderOperation > operations, int operationType )
      throws NullPointerException
   {
      if ( operations == null || provider == null )
         throw new NullPointerException();
      
      this.operations = new ArrayList< ContentProviderOperation >( operations );
      this.provider = provider;
      this.operationType = operationType;
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
   


   public final static void updateSyncResult( SyncResult result,
                                              int operationType,
                                              int count )
   {
      switch ( operationType )
      {
         case OP_INSERT:
            result.stats.numInserts += count;
            break;
         case OP_UPDATE:
            result.stats.numUpdates += count;
            break;
         case OP_DELETE:
            result.stats.numDeletes += count;
            break;
         default :
            break;
      }
   }
}
