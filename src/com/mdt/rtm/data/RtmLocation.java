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
import dev.drsoran.moloko.content.db.DbUtils;
import dev.drsoran.moloko.content.db.RtmLocationsTable;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.provider.Rtm.Locations;


/**
 * Represents a location.
 * 
 * @author Edouard Mercier
 * @since 2008.05.22
 */
public class RtmLocation extends RtmData implements
         IContentProviderSyncable< RtmLocation >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = "Moloko."
      + RtmLocation.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmLocation > CREATOR = new Parcelable.Creator< RtmLocation >()
   {
      
      public RtmLocation createFromParcel( Parcel source )
      {
         return new RtmLocation( source );
      }
      


      public RtmLocation[] newArray( int size )
      {
         return new RtmLocation[ size ];
      }
      
   };
   
   
   private static final class LessIdComperator implements
            Comparator< RtmLocation >
   {
      public int compare( RtmLocation object1, RtmLocation object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   public final String id;
   
   public final String name;
   
   public final float longitude;
   
   public final float latitude;
   
   public final String address;
   
   public final boolean viewable;
   
   public int zoom;
   
   

   public RtmLocation( String id, String name, float lon, float lat,
      String address, boolean viewable, int zoom )
   {
      this.id = id;
      this.name = name;
      this.longitude = lon;
      this.latitude = lat;
      this.address = address;
      this.viewable = viewable;
      this.zoom = zoom;
   }
   


   public RtmLocation( Element element )
   {
      id = textNullIfEmpty( element, "id" );
      name = textNullIfEmpty( element, "name" );
      longitude = Float.parseFloat( element.getAttribute( "longitude" ) );
      latitude = Float.parseFloat( element.getAttribute( "latitude" ) );
      address = textNullIfEmpty( element, "address" );
      zoom = Integer.parseInt( element.getAttribute( "zoom" ) );
      viewable = element.getAttribute( "viewable" ).equals( "1" ) ? true
                                                                 : false;
   }
   


   public RtmLocation( Parcel source )
   {
      id = source.readString();
      name = source.readString();
      longitude = source.readFloat();
      latitude = source.readFloat();
      address = source.readString();
      viewable = source.readInt() == 1;
      zoom = source.readInt();
   }
   


   public Date getDeletedDate()
   {
      return null;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( name );
      dest.writeFloat( longitude );
      dest.writeFloat( latitude );
      dest.writeString( address );
      dest.writeInt( viewable ? 1 : 0 );
      dest.writeInt( zoom );
   }
   


   public Uri getContentUriWithId()
   {
      return DbUtils.contentUriWithId( Locations.CONTENT_URI, id );
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( Locations.CONTENT_URI )
                                                                             .withValues( RtmLocationsTable.getContentValues( this,
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
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmLocation update )
   {
      if ( !id.equals( update.id ) )
         throw new IllegalArgumentException( "Update id " + update.id
            + " differs this id " + id );
      
      final Uri uri = getContentUriWithId();
      
      final ContentProviderSyncOperation.Builder result = ContentProviderSyncOperation.newUpdate();
      
      if ( SyncUtils.isDifferent( name, update.name ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.LOCATION_NAME,
                                                         update.name )
                                             .build() );
      
      if ( longitude != update.longitude )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.LONGITUDE,
                                                         update.longitude )
                                             .build() );
      
      if ( latitude != update.latitude )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.LATITUDE,
                                                         update.latitude )
                                             .build() );
      
      if ( SyncUtils.isDifferent( address, update.address ) )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.ADDRESS,
                                                         update.address )
                                             .build() );
      
      if ( viewable != update.viewable )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.VIEWABLE,
                                                         update.viewable )
                                             .build() );
      
      if ( zoom != update.zoom )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( Locations.ZOOM,
                                                         update.zoom )
                                             .build() );
      
      return result.build();
   }
}
