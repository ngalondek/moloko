package dev.drsoran.moloko.service.sync.lists;

import java.util.Collection;
import java.util.Comparator;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;


public class ContentProviderSyncableList< T extends IContentProviderSyncable< T > >
         extends SyncableList< T >
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
   public ISyncOperation computeInsertOperation( T newElement )
   {
      return newElement.computeContentProviderInsertOperation( provider );
   }
   


   @Override
   public ISyncOperation computeDeleteOperation( T elementToDelete )
   {
      return elementToDelete.computeContentProviderDeleteOperation( provider );
   }
   


   @Override
   protected ISyncOperation internalComputeUpdateOperation( T old,
                                                            T updateElement )
   {
      return old.computeContentProviderUpdateOperation( provider, updateElement );
   }
}
