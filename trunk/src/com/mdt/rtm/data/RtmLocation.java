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

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Represents a location.
 * 
 * @author Edouard Mercier
 * @since 2008.05.22
 */
public class RtmLocation extends RtmData
{
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
   
   public final String id;
   
   public final String name;
   
   public final float longitude;
   
   public final float latitude;
   
   public final String address;
   
   public final boolean viewable;
   
   public int zoom;
   
   

   public RtmLocation( Element element )
   {
      id = element.getAttribute( "id" );
      name = element.getAttribute( "name" );
      longitude = Float.parseFloat( element.getAttribute( "longitude" ) );
      latitude = Float.parseFloat( element.getAttribute( "latitude" ) );
      address = element.getAttribute( "address" );
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
}
