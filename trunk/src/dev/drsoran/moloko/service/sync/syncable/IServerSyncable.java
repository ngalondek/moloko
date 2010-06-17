package dev.drsoran.moloko.service.sync.syncable;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface IServerSyncable< T >
{
   public ISyncOperation computeServerInsertOperation( Service service,
                                                       Object... params );
   


   public ISyncOperation computeServerUpdateOperation( Service service,
                                                       T update,
                                                       Object... params );
   


   public ISyncOperation computeServerDeleteOperation( Service service,
                                                       Object... params );
}
