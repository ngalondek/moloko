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


public class RtmTimeline extends RtmData
{
   
   public static final Parcelable.Creator< RtmTimeline > CREATOR =
      new Parcelable.Creator< RtmTimeline >()
      {
         
         public RtmTimeline createFromParcel( Parcel source )
         {
            return new RtmTimeline( source );
         }
         


         public RtmTimeline[] newArray( int size )
         {
            return new RtmTimeline[ size ];
         }
         
      };
   
   private final String id;
   
   

   public RtmTimeline( String id )
   {
      this.id = id;
   }
   


   public RtmTimeline( Element elt )
   {
      id = text( elt );
   }
   


   public RtmTimeline( Parcel source )
   {
      id = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
   }
}
