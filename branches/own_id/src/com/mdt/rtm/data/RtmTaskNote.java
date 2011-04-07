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
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.rtm.ParcelableDate;
import dev.drsoran.rtm.RtmEntity;


/**
 * Represents a single task note.
 * 
 * @author Edouard Mercier
 * @since 2008.04.22
 */
public class RtmTaskNote extends RtmEntity implements
         IContentProviderSyncable< RtmTaskNote >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + RtmTaskNote.class.getSimpleName();
   
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
   
   public final static LessRtmIdComperator< RtmTaskNote > LESS_RTM_ID = new LessRtmIdComperator< RtmTaskNote >();
   
   private final long taskSeriesId;
   
   private final String rtmTaskSeriesId;
   
   private final ParcelableDate created;
   
   private final ParcelableDate modified;
   
   private final ParcelableDate deleted;
   
   private final String title;
   
   private final String text;
   
   

   public RtmTaskNote( long id, String rtmId, long taskSeriesId,
      String rtmTaskSeriesId, Date created, Date modified, Date deleted,
      String title, String text )
   {
      super( id, rtmId );
      
      this.taskSeriesId = taskSeriesId;
      this.rtmTaskSeriesId = rtmTaskSeriesId;
      this.created = ( created != null ) ? new ParcelableDate( created ) : null;
      this.modified = ( modified != null ) ? new ParcelableDate( modified )
                                          : null;
      this.deleted = ( deleted != null ) ? new ParcelableDate( deleted ) : null;
      this.title = title;
      this.text = text;
   }
   


   public RtmTaskNote( Element element, String rtmTaskSeriesId )
   {
      super( textNullIfEmpty( element, "id" ) );
      
      this.taskSeriesId = RtmEntity.NO_ID;
      this.rtmTaskSeriesId = rtmTaskSeriesId;
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
      super( source );
      
      taskSeriesId = source.readLong();
      rtmTaskSeriesId = source.readString();
      created = source.readParcelable( null );
      modified = source.readParcelable( null );
      deleted = source.readParcelable( null );
      title = source.readString();
      text = source.readString();
   }
   


   public long getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public String getRtmTaskSeriesId()
   {
      return rtmTaskSeriesId;
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
   


   @Override
   public int describeContents()
   {
      return 0;
   }
   


   @Override
   public void writeToParcel( Parcel dest, int flags )
   {
      super.writeToParcel( dest, flags );
      
      dest.writeLong( taskSeriesId );
      dest.writeString( rtmTaskSeriesId );
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
                                                                             .withValues( RtmNotesProviderPart.getContentValues( this ) )
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
      if ( !rtmId.equals( update.rtmId ) )
         throw new IllegalArgumentException( "Update id " + update.rtmId
            + " differs this id " + rtmId );
      
      final Uri uri = getContentUriWithId();
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.hasChanged( created, update.created ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_CREATED_DATE,
                                                         MolokoDateUtils.getTime( update.created ) )
                                             .build() );
      
      if ( SyncUtils.hasChanged( modified, update.modified ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Notes.NOTE_MODIFIED_DATE,
                                                         MolokoDateUtils.getTime( update.modified ) )
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
