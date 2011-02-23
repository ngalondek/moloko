/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

import java.util.Date;
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Tags;


public class Tag implements IContentProviderSyncable< Tag >, Parcelable
{
   
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko." + Tag.class.getSimpleName();
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final String tag;
   
   public static final Parcelable.Creator< Tag > CREATOR = new Parcelable.Creator< Tag >()
   {
      public Tag createFromParcel( Parcel source )
      {
         return new Tag( source );
      }
      


      public Tag[] newArray( int size )
      {
         return new Tag[ size ];
      }
   };
   
   

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
   


   public Tag( Parcel source )
   {
      id = source.readString();
      taskSeriesId = source.readString();
      tag = source.readString();
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
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( taskSeriesId );
      dest.writeString( tag );
   }
   


   @Override
   public boolean equals( Object o )
   {
      if ( !( o instanceof Tag ) )
         return false;
      else
      {
         final Tag other = (Tag) o;
         
         // Only if booth tags have an id we compare it.
         // It may happen that we create temporary tag objects
         // with no ID. In this case the ID is not important.
         boolean equals = ( id != null && other.id != null )
                                                            ? id.equals( other.id )
                                                            : true;
         
         equals = equals
            && !SyncUtils.hasChanged( taskSeriesId, other.taskSeriesId );
         
         equals = equals && !SyncUtils.hasChanged( tag, other.tag );
         
         return equals;
      }
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Tags.CONTENT_URI )
                                                                             .withValues( TagsProviderPart.getContentValues( this,
                                                                                                                             true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( Tags.CONTENT_URI,
                                                                                                                   id ) )
                                                                             .build() )
                                         .build();
   }
   


   public List< IContentProviderSyncOperation > computeContentProviderUpdateOperations( Date lastSync,
                                                                                        Tag update )
   {
      final Uri uri = Queries.contentUriWithId( Tags.CONTENT_URI, id );
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( taskSeriesId, update.taskSeriesId ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Tags.TASKSERIES_ID,
                                                         update.taskSeriesId )
                                             .build() );
      
      if ( SyncUtils.hasChanged( tag, update.tag ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Tags.TAG, update.tag )
                                             .build() );
      
      return result.asList();
   }
}
