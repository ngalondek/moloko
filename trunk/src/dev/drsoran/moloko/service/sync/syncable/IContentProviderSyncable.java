package dev.drsoran.moloko.service.sync.syncable;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;


public interface IContentProviderSyncable< T >
{
   public ContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                Object... params );
   


   public ContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                T update,
                                                                Object... params );
   


   public ContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                Object... params );
}
