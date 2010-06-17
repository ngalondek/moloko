package dev.drsoran.moloko.service.sync.operation;

import android.content.SyncResult;


public interface ISyncOperation
{
   static class NopSyncOperation implements ISyncOperation
   {
      public boolean execute( SyncResult result )
      {
         return true;
      }
   }
   
   public final static ISyncOperation NOP_SYNCOPERATION = new NopSyncOperation();
   
   

   public boolean execute( SyncResult result );
}
