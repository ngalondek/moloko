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

import org.w3c.dom.Element;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import dev.drsoran.moloko.content.Queries;
import dev.drsoran.moloko.content.RtmListsProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.syncable.IContentProviderSyncable;
import dev.drsoran.provider.Rtm.Lists;


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
   
   private String id;
   
   private String name;
   
   

   public RtmList( String id, String name )
   {
      this.id = id;
      this.name = name;
   }
   


   public RtmList( Element elt )
   {
      id = elt.getAttribute( "id" );
      name = elt.getAttribute( "name" );
   }
   


   public RtmList( Parcel source )
   {
      id = source.readString();
      name = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getName()
   {
      return name;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( name );
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
      IContentProviderSyncOperation operation = null;
      
      if ( this.id.equals( update.id ) )
      {
         operation = new ContentProviderSyncOperation( provider,
                                                       ContentProviderOperation.newUpdate( Queries.contentUriWithId( Lists.CONTENT_URI,
                                                                                                                     id ) )
                                                                               .withValues( RtmListsProviderPart.getContentValues( update,
                                                                                                                                   false ) )
                                                                               .build(),
                                                       IContentProviderSyncOperation.Op.UPDATE );
      }
      else
      {
         Log.e( TAG, "ContentProvider update failed. Different RtmList IDs." );
      }
      
      return operation;
   }
}
