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
   
   private final ArrayList< ContentProviderOperation > operations = new ArrayList< ContentProviderOperation >();
   
   private final ContentProviderClient provider;
   
   private final int operationType;
   
   

   public ContentProviderSyncOperation( ContentProviderClient provider,
      ContentProviderOperation operation, int operationType )
      throws NullPointerException
   {
      if ( operation == null || provider == null )
         throw new NullPointerException();
      
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
      
      this.provider = provider;
      this.operations.addAll( operations );
      this.operationType = operationType;
   }
   


   public void add( ContentProviderOperation operation ) throws NullPointerException
   {
      if ( operation == null )
         throw new NullPointerException();
      
      operations.add( operation );
   }
   


   public boolean execute( SyncResult result )
   {
      boolean ok = true;
      
      try
      {
         provider.applyBatch( operations );
         
         switch ( operationType )
         {
            case OP_INSERT:
               ++result.stats.numInserts;
               break;
            case OP_UPDATE:
               ++result.stats.numUpdates;
               break;
            case OP_DELETE:
               ++result.stats.numDeletes;
               break;
            default :
               ok = false;
               break;
         }
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
}
