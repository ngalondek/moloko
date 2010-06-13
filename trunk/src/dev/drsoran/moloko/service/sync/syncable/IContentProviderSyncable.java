package dev.drsoran.moloko.service.sync.syncable;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface IContentProviderSyncable
{
   public ISyncOperation computeContentProviderInsertOperation();
   


   public ISyncOperation computeContentProviderUpdateOperation();
   


   public ISyncOperation computeContentProviderDeleteOperation();
}
