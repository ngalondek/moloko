package dev.drsoran.moloko.service.sync.syncable;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;


public interface IContentProviderSyncable< T >
{
   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params );
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               T update,
                                                                               Object... params );
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params );
}
