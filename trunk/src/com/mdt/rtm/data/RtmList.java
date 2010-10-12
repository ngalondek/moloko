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

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.sync.operation.CompositeContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.Lists;
import dev.drsoran.rtm.RtmSmartFilter;


public class RtmList extends RtmData implements
         IContentProviderSyncable< RtmList >
{
   private final static String TAG = RtmList.class.getSimpleName();
   
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
   
   private final int deleted;
   
   private final int locked;
   
   private final int archived;
   
   private final int position;
   
   private final RtmSmartFilter smartFilter;
   
   

   public RtmList( String id, String name, int deleted, int locked,
      int archived, int position, RtmSmartFilter smartFilter )
   {
      this.id = id;
      this.name = name;
      this.deleted = deleted;
      this.locked = locked;
      this.archived = archived;
      this.position = position;
      this.smartFilter = smartFilter;
   }
   


   public RtmList( Element elt )
   {
      this.id = elt.getAttribute( "id" );
      this.name = elt.getAttribute( "name" );
      this.deleted = Integer.parseInt( elt.getAttribute( "deleted" ) );
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
      this.deleted = source.readInt();
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
   


   public int getDeleted()
   {
      return deleted;
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
      dest.writeInt( deleted );
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
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newInsert( Lists.CONTENT_URI )
                                                                       .withValues( RtmListsProviderPart.getContentValues( this,
                                                                                                                           true ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.INSERT );
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newDelete( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                             id ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.DELETE );
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               RtmList update,
                                                                               Object... params )
   {
      CompositeContentProviderSyncOperation result = null;
      
      if ( this.id.equals( update.id ) )
      {
         final Uri uri = Queries.contentUriWithId( Lists.CONTENT_URI, id );
         
         result = new CompositeContentProviderSyncOperation( provider,
                                                             IContentProviderSyncOperation.Op.UPDATE );
         
         if ( Strings.hasStringChanged( name, update.name ) )
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
                                                .withValue( Lists.IS_SMART_LIST,
                                                            1 )
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
                                                .withValue( Lists.IS_SMART_LIST,
                                                            0 )
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
      }
      else
      {
         Log.e( TAG, "ContentProvider update failed. Different RtmList IDs." );
      }
      
      return ( result == null || result.plainSize() > 0 )
                                                         ? result
                                                         : NoopContentProviderSyncOperation.INSTANCE;
   }
}
