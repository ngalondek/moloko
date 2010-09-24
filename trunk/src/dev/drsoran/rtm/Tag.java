package dev.drsoran.rtm;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tags;


public class Tag implements IContentProviderSyncable< Tag >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = Tag.class.getSimpleName();
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final String tag;
   
   

   public Tag( String taskSeriesId, String tag )
   {
      this.id = null;
      this.taskSeriesId = taskSeriesId;
      this.tag = tag;
   }
   


   public Tag( String id, String taskSeriesId, String tag )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.tag = tag;
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public String getTag()
   {
      return tag;
   }
   


   @Override
   public boolean equals( Object o )
   {
      if ( !( o instanceof Tag ) )
         return super.equals( o );
      else
      {
         Tag other = (Tag) o;
         
         boolean equals = ( id != null && other.id != null )
                                                            ? id.equals( other.id )
                                                            : true;
         
         equals = equals
            && ( ( taskSeriesId == null && other.taskSeriesId == null ) || taskSeriesId.equals( other.taskSeriesId ) );
         
         equals = equals
            && ( ( tag == null && other.tag == null ) || tag.equals( other.tag ) );
         return equals;
      }
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newInsert( Tags.CONTENT_URI )
                                                                       .withValues( TagsProviderPart.getContentValues( this,
                                                                                                                       true ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.INSERT );
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newDelete( Queries.contentUriWithId( Tags.CONTENT_URI,
                                                                                                             id ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.DELETE );
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               Tag update,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newUpdate( Queries.contentUriWithId( Tags.CONTENT_URI,
                                                                                                             id ) )
                                                                       .withValues( TagsProviderPart.getContentValues( update,
                                                                                                                       false ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.UPDATE );
   }
   
}
