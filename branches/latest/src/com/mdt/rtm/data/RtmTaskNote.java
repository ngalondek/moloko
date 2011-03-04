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
import org.w3c.dom.Text;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.rtm.ParcelableDate;


/**
 * Represents a single task note.
 * 
 * @author Edouard Mercier
 * @since 2008.04.22
 */
public class RtmTaskNote extends RtmData implements
         IContentProviderSyncable< RtmTaskNote >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + RtmTaskNote.class.getSimpleName();
   
   
   private final static class LessIdComperator implements
            Comparator< RtmTaskNote >
   {
      
      public int compare( RtmTaskNote object1, RtmTaskNote object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
   public static final Parcelable.Creator< RtmTaskNote > CREATOR = new Parcelable.Creator< RtmTaskNote >()
   {
      
      public RtmTaskNote createFromParcel( Parcel source )
      {
         return new RtmTaskNote( source );
      }
      


      public RtmTaskNote[] newArray( int size )
      {
         return new RtmTaskNote[ size ];
      }
      
   };
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final ParcelableDate created;
   
   private final ParcelableDate modified;
   
   private final ParcelableDate deleted;
   
   private final String title;
   
   private final String text;
   
   

   public RtmTaskNote( String id, String taskSeriesId, Date created,
      Date modified, Date deleted, String title, String text )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.created = ( created != null ) ? new ParcelableDate( created ) : null;
      this.modified = ( modified != null ) ? new ParcelableDate( modified )
                                          : null;
      this.deleted = ( deleted != null ) ? new ParcelableDate( deleted ) : null;
      this.title = title;
      this.text = text;
   }
   


   public RtmTaskNote( Element element, String taskSeriesId )
   {
      id = textNullIfEmpty( element, "id" );
      this.taskSeriesId = taskSeriesId;
      created = parseParcableDate( element.getAttribute( "created" ) );
      modified = parseParcableDate( element.getAttribute( "modified" ) );
      deleted = null;
      title = textNullIfEmpty( element, "title" );
      
      if ( element.getChildNodes().getLength() > 0 )
      {
         final Text innerText = (Text) element.getChildNodes().item( 0 );
         text = innerText.getData();
      }
      else
      {
         text = null;
      }
   }
   


   public RtmTaskNote( Parcel source )
   {
      id = source.readString();
      taskSeriesId = source.readString();
      created = source.readParcelable( null );
      modified = source.readParcelable( null );
      deleted = source.readParcelable( null );
      title = source.readString();
      text = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public Date getCreatedDate()
   {
      return ( created != null ) ? created.getDate() : null;
   }
   


   public Date getModifiedDate()
   {
      return ( modified != null ) ? modified.getDate() : null;
   }
   


   public Date getDeletedDate()
   {
      return ( deleted != null ) ? deleted.getDate() : null;
   }
   


   public String getTitle()
   {
      return title;
   }
   


   public String getText()
   {
      return text;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( taskSeriesId );
      dest.writeParcelable( created, 0 );
      dest.writeParcelable( modified, 0 );
      dest.writeParcelable( deleted, 0 );
      dest.writeString( title );
      dest.writeString( text );
   }
   


   public Uri getContentUriWithId()
   {
      return Queries.contentUriWithId( Notes.CONTENT_URI, id );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                                             .withValues( RtmNotesProviderPart.getContentValues( this,
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
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmTaskNote update )
   {
      if ( !id.equals( update.id ) )
         throw new IllegalArgumentException( "Update id " + update.id
            + " differs this id " + id );
      
      final Uri uri = getContentUriWithId();
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( created, update.created ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_CREATED_DATE,
                                                         update.created )
                                             .build() );
      
      if ( SyncUtils.hasChanged( modified, update.modified ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_MODIFIED_DATE,
                                                         update.modified )
                                             .build() );
      
      if ( SyncUtils.hasChanged( title, update.title ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TITLE,
                                                         update.title )
                                             .build() );
      
      if ( SyncUtils.hasChanged( text, update.text ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_TEXT,
                                                         update.text )
                                             .build() );
      
      return result.build();
   }
   
}
