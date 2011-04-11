/*
 * Copyright 2007, MetaDimensional Technologies Inc.
 * 
 * 
 * This file is part of the RememberTheMilk Java API.
 * 
 * The RememberTheMilk Java API is free software; you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 * 
 * The RememberTheMilk Java API is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.Comparator;
import java.util.Date;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.ParcelableDate;
import dev.drsoran.rtm.RtmSmartFilter;


public class RtmList extends RtmData implements
         IContentProviderSyncable< RtmList >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko." + RtmList.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmList > CREATOR = new Parcelable.Creator< RtmList >()
   {
      
      public RtmList createFromParcel( Parcel source )
      {
         return new RtmList( source );
      }
      


      public RtmList[] newArray( int size )
      {
         return new RtmList[ size ];
      }
      
   };
   
   
   private static final class LessIdComperator implements Comparator< RtmList >
   {
      public int compare( RtmList object1, RtmList object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final String name;
   
   private final ParcelableDate created;
   
   private final ParcelableDate modified;
   
   private final ParcelableDate deleted;
   
   private final int locked;
   
   private final int archived;
   
   private final int position;
   
   private final RtmSmartFilter smartFilter;
   
   

   public RtmList( String id, String name, Date created, Date modified,
      Date deleted, int locked, int archived, int position,
      RtmSmartFilter smartFilter )
   {
      this.id = id;
      this.name = name;
      this.created = created != null ? new ParcelableDate( created ) : null;
      this.modified = modified != null ? new ParcelableDate( modified ) : null;
      this.deleted = deleted != null ? new ParcelableDate( deleted ) : null;
      this.locked = locked;
      this.archived = archived;
      this.position = position;
      this.smartFilter = smartFilter;
   }
   


   public RtmList( Element elt )
   {
      this.id = elt.getAttribute( "id" );
      this.name = elt.getAttribute( "name" );
      this.created = null;
      this.modified = null;
      if ( Integer.parseInt( elt.getAttribute( "deleted" ) ) == 0 )
         this.deleted = null;
      else
         this.deleted = new ParcelableDate( new Date() );
      this.locked = Integer.parseInt( elt.getAttribute( "locked" ) );
      this.archived = Integer.parseInt( elt.getAttribute( "archived" ) );
      this.position = Integer.parseInt( elt.getAttribute( "position" ) );
      
      final Element filter = child( elt, "filter" );
      
      if ( filter != null )
      {
         this.smartFilter = new RtmSmartFilter( filter );
      }
      else
      {
         this.smartFilter = null;
      }
   }
   


   public RtmList( Parcel source )
   {
      this.id = source.readString();
      this.name = source.readString();
      this.created = source.readParcelable( null );
      this.modified = source.readParcelable( null );
      this.deleted = source.readParcelable( null );
      this.locked = source.readInt();
      this.archived = source.readInt();
      this.position = source.readInt();
      this.smartFilter = source.readParcelable( null );
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getName()
   {
      return name;
   }
   


   public Date getCreatedDate()
   {
      return created != null ? created.getDate() : null;
   }
   


   public Date getModifiedDate()
   {
      return modified != null ? modified.getDate() : null;
   }
   


   public Date getDeletedDate()
   {
      return deleted != null ? deleted.getDate() : null;
   }
   


   public int getLocked()
   {
      return locked;
   }
   


   public int getArchived()
   {
      return archived;
   }
   


   public int getPosition()
   {
      return position;
   }
   


   public RtmSmartFilter getSmartFilter()
   {
      return smartFilter;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( name );
      dest.writeParcelable( created, flags );
      dest.writeParcelable( modified, flags );
      dest.writeParcelable( deleted, flags );
      dest.writeInt( locked );
      dest.writeInt( archived );
      dest.writeInt( position );
      dest.writeParcelable( smartFilter, flags );
   }
   


   @Override
   public String toString()
   {
      return "<"
         + id
         + ","
         + name
         + ( ( smartFilter != null ) ? "," + smartFilter.getFilterString()
                                    : Strings.EMPTY_STRING ) + ">";
   }
   


   public Uri getContentUriWithId()
   {
      return Queries.contentUriWithId( Lists.CONTENT_URI, id );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Lists.CONTENT_URI )
                                                                             .withValues( RtmListsProviderPart.getContentValues( this,
                                                                                                                                 true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( getContentUriWithId() )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmList update )
   {
      if ( !id.equals( update.id ) )
         throw new IllegalArgumentException( "Update id " + update.id
            + " differs this id " + id );
      
      final Uri uri = getContentUriWithId();
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( name, update.name ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LIST_NAME,
                                                         update.name )
                                             .build() );
      
      if ( deleted != update.deleted )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LIST_DELETED,
                                                         update.deleted )
                                             .build() );
      
      if ( locked != update.locked )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.LOCKED,
                                                         update.locked )
                                             .build() );
      
      if ( archived != update.archived )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.ARCHIVED,
                                                         update.archived )
                                             .build() );
      
      if ( position != update.position )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.POSITION,
                                                         update.position )
                                             .build() );
      
      // This list gets smart
      if ( smartFilter == null && update.smartFilter != null )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.IS_SMART_LIST, 1 )
                                             .build() );
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER,
                                                         update.smartFilter.getFilterString() )
                                             .build() );
      }
      
      // This list gets normal
      else if ( smartFilter != null && update.smartFilter == null )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.IS_SMART_LIST, 0 )
                                             .build() );
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER, null )
                                             .build() );
      }
      
      // Filter has changed
      else if ( smartFilter != null
         && update.smartFilter != null
         && !smartFilter.getFilterString()
                        .equals( update.smartFilter.getFilterString() ) )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Lists.FILTER,
                                                         update.smartFilter.getFilterString() )
                                             .build() );
      }
      
      return result.build();
   }
   


   public IContentProviderSyncOperation computeAfterServerInsertOperation( RtmList serverElement )
   {
      return NoopContentProviderSyncOperation.INSTANCE;
   }
}
