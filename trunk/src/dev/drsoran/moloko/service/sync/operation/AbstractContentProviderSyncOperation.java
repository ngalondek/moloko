package dev.drsoran.moloko.service.sync.operation;

import java.util.ArrayList;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.SyncResult;


public abstract class AbstractContentProviderSyncOperation implements
         IContentProviderSyncOperation
{
   private final static class NopSyncOperation implements
            IContentProviderSyncOperation
   {
      
      public int getBatch( ArrayList< ContentProviderOperation > batch )
      {
         return 0;
      }
      


      public int getOperationType()
      {
         return Op.NOOP;
      }
      


      public boolean execute( SyncResult result )
      {
         return true;
      }
      
   }
   
   public final static NopSyncOperation NOOP = new NopSyncOperation();
   
   protected final int operationType;
   
   protected final ContentProviderClient provider;
   
   

   public AbstractContentProviderSyncOperation( ContentProviderClient provider,
      int operationType ) throws NullPointerException
   {
      if ( provider == null )
         throw new NullPointerException();
      
      this.provider = provider;
      this.operationType = operationType;
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
         case Op.INSERT:
            result.stats.numInserts += count;
            break;
         case Op.UPDATE:
            result.stats.numUpdates += count;
            break;
         case Op.DELETE:
            result.stats.numDeletes += count;
            break;
         default :
            break;
      }
   }
   
}
