package dev.drsoran.moloko.service.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import com.mdt.rtm.Service;

import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IServerSyncable;

// TODO: Replace ISyncOperation parameter by more special one.
public class ServerSyncableList< T extends IServerSyncable< T > > extends
         SyncableList< ISyncOperation, T >
{
   
   private final Service service;
   
   

   public ServerSyncableList( Service service, Collection< T > elements )
      throws NullPointerException
   {
      super( elements );
      
      if ( service == null )
         throw new NullPointerException();
      
      this.service = service;
   }
   


   public ServerSyncableList( Service service, Collection< T > elements,
      Comparator< T > comparator ) throws NullPointerException
   {
      super( elements, comparator );
      
      if ( service == null )
         throw new NullPointerException();
      
      this.service = service;
   }
   


   @Override
   public ISyncOperation computeInsertOperation( T newElement, Object... params )
   {
      return newElement.computeServerInsertOperation( service, params );
   }
   


   @Override
   public ISyncOperation computeDeleteOperation( T elementToDelete,
                                                 Object... params )
   {
      return elementToDelete.computeServerDeleteOperation( service, params );
   }
   


   @Override
   protected ISyncOperation internalComputeUpdateOperation( T old,
                                                            T updateElement,
                                                            Object... params )
   {
      return old.computeServerUpdateOperation( service, updateElement, params );
   }
}
