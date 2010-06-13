package dev.drsoran.moloko.service.sync.syncable;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface ISyncableLists< T > extends Iterable< T >
{
   public ISyncOperation computeInsertOperation();
   


   public ISyncOperation computeUpdateOperation();
   


   public ISyncOperation computeDeleteOperation();
}
