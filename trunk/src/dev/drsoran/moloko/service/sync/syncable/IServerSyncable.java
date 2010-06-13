package dev.drsoran.moloko.service.sync.syncable;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface IServerSyncable
{
   public ISyncOperation computeServerInsertOperation();
   


   public ISyncOperation computeServerUpdateOperation();
   


   public ISyncOperation computeServerDeleteOperation();
}
