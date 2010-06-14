package dev.drsoran.moloko.service.sync.syncable;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface IServerSyncable< T >
{
   public ISyncOperation computeServerInsertOperation( Service service );
   


   public ISyncOperation computeServerUpdateOperation( Service service, T update );
   


   public ISyncOperation computeServerDeleteOperation( Service service );
}
