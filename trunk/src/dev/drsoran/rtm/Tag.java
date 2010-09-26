/*
Copyright (c) 2010 Ronny R�hricht   

This file is part of Moloko.

Moloko is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Moloko is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Moloko.  If not, see <http://www.gnu.org/licenses/>.

Contributors:
	Ronny R�hricht - implementation
*/

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