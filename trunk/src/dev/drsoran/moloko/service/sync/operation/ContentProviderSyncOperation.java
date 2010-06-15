package dev.drsoran.moloko.service.sync.operation;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.content.SyncResult;


public class ContentProviderSyncOperation implements ISyncOperation
{
   public final static int OP_INSERT = 0;
   
   public final static int OP_UPDATE = 1;
   
   public final static int OP_DELETE = 2;
   
   private final ContentProviderOperation operation;
   
   private final ContentProviderClient provider;
   
   private final int operationType;
   
   

   public ContentProviderSyncOperation( ContentProviderClient provider,
      ContentProviderOperation operation, int operationType )
      throws NullPointerException
   {
      if ( operation == null || provider == null )
         throw new NullPointerException();
      
      this.provider = provider;
      this.operation = operation;
      this.operationType = operationType;
   }
   


   public boolean execute( SyncResult result )
   {
      boolean ok = true;
      
      try
      {
         operation.apply( provider.getLocalContentProvider(), null, 0 );
         
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
      return ok;
   }
}
