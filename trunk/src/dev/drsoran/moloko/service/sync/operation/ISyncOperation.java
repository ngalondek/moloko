package dev.drsoran.moloko.service.sync.operation;

import android.content.SyncResult;


public interface ISyncOperation
{
   public boolean execute( SyncResult result );
}
