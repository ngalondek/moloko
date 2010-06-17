package dev.drsoran.moloko.service.sync.syncable;

import android.content.ContentProviderClient;
import dev.drsoran.moloko.service.sync.operation.ISyncOperation;


public interface IContentProviderSyncable< T >
{
   public ISyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                Object... params );
   


   public ISyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                T update,
                                                                Object... params );
   


   public ISyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                Object... params );
}
