package dev.drsoran.moloko.service.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;


public class ContentProviderSyncableList< T extends IContentProviderSyncable< T > >
         extends SyncableList< IContentProviderSyncOperation, T >
{
   private final ContentProviderClient provider;
   
   

   public ContentProviderSyncableList( ContentProviderClient provider,
      Collection< T > elements ) throws NullPointerException
   {
      super( elements );
      
      if ( provider == null )
         throw new NullPointerException();
      
      this.provider = provider;
   }
   


   public ContentProviderSyncableList( ContentProviderClient provider,
      Collection< T > elements, Comparator< T > comparator )
      throws NullPointerException
   {
      super( elements, comparator );
      
      if ( provider == null )
         throw new NullPointerException();
      
      this.provider = provider;
   }
   


   @Override
   public IContentProviderSyncOperation computeInsertOperation( T newElement,
                                                                Object... params )
   {
      return newElement.computeContentProviderInsertOperation( provider, params );
   }
   


   @Override
   public IContentProviderSyncOperation computeDeleteOperation( T elementToDelete,
                                                                Object... params )
   {
      return elementToDelete.computeContentProviderDeleteOperation( provider,
                                                                    params );
   }
   


   @Override
   protected IContentProviderSyncOperation internalComputeUpdateOperation( T old,
                                                                           T updateElement,
                                                                           Object... params )
   {
      return old.computeContentProviderUpdateOperation( provider,
                                                        updateElement,
                                                        params );
   }
}
