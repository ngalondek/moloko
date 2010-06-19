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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mdt.rtm.data;

import java.util.Comparator;
import java.util.Date;

import org.w3c.dom.Element;
import org.w3c.dom.Text;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.content.Queries;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.service.sync.util.ParamChecker;
import dev.drsoran.provider.Rtm.Notes;


/**
 * Represents a single task note.
 * 
 * @author Edouard Mercier
 * @since 2008.04.22
 */
public class RtmTaskNote extends RtmData implements
         IContentProviderSyncable< RtmTaskNote >
{
   private final static String TAG = RtmTaskNote.class.getSimpleName();
   
   
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
   
   private String id;
   
   private Date created;
   
   private Date modified;
   
   private String title;
   
   private String text;
   
   

   public RtmTaskNote( String id, Date created, Date modified, String title,
      String text )
   {
      this.id = id;
      this.created = created;
      this.modified = modified;
      this.title = title;
      this.text = text;
   }
   


   public RtmTaskNote( Element element )
   {
      id = element.getAttribute( "id" );
      created = parseDate( element.getAttribute( "created" ) );
      modified = parseDate( element.getAttribute( "modified" ) );
      title = element.getAttribute( "title" );
      if ( element.getChildNodes().getLength() > 0 )
      {
         Text innerText = (Text) element.getChildNodes().item( 0 );
         text = innerText.getData();
      }
   }
   


   public RtmTaskNote( Parcel source )
   {
      id = source.readString();
      created = new Date( source.readLong() );
      modified = new Date( source.readLong() );
      title = source.readString();
      text = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public Date getCreated()
   {
      return created;
   }
   


   public Date getModified()
   {
      return modified;
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
      dest.writeLong( created.getTime() );
      dest.writeLong( modified.getTime() );
      dest.writeString( title );
      dest.writeString( text );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      ContentProviderSyncOperation operation = null;
      
      final boolean ok = ParamChecker.checkParams( TAG,
                                                   "ContentProvider insert failed. ",
                                                   new Class[]
                                                   { String.class },
                                                   params );
      
      if ( ok )
      {
         final String taskSeriesId = (String) params[ 0 ];
         
         new ContentProviderSyncOperation( provider,
                                           ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                                   .withValues( RtmNotesProviderPart.getContentValues( this,
                                                                                                                       taskSeriesId,
                                                                                                                       true ) )
                                                                   .build(),
                                           IContentProviderSyncOperation.Op.INSERT );
      }
      
      return operation;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation( ContentProviderClient provider,
                                                                               Object... params )
   {
      return new ContentProviderSyncOperation( provider,
                                               ContentProviderOperation.newDelete( Queries.contentUriWithId( Notes.CONTENT_URI,
                                                                                                             id ) )
                                                                       .build(),
                                               IContentProviderSyncOperation.Op.DELETE );
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( ContentProviderClient provider,
                                                                               RtmTaskNote update,
                                                                               Object... params )
   {
      ContentProviderSyncOperation operation = null;
      
      final boolean ok = ParamChecker.checkParams( TAG,
                                                   "ContentProvider update failed. ",
                                                   new Class[]
                                                   { String.class },
                                                   params );
      
      if ( ok )
      {
         final String taskSeriesId = (String) params[ 0 ];
         
         operation = new ContentProviderSyncOperation( provider,
                                                       ContentProviderOperation.newUpdate( Notes.CONTENT_URI )
                                                                               .withValues( RtmNotesProviderPart.getContentValues( update,
                                                                                                                                   taskSeriesId,
                                                                                                                                   false ) )
                                                                               .build(),
                                                       IContentProviderSyncOperation.Op.UPDATE );
      }
      
      return operation;
   }
   
}
