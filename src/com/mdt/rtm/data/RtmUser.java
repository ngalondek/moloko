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
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmUser extends RtmData
{
   public static final Parcelable.Creator< RtmUser > CREATOR =
      new Parcelable.Creator< RtmUser >()
      {
         
         public RtmUser createFromParcel( Parcel source )
         {
            return new RtmUser( source );
         }
         


         public RtmUser[] newArray( int size )
         {
            return new RtmUser[ size ];
         }
         
      };
   
   private final String id;
   
   private final String username;
   
   private final String fullname;
   
   

   public RtmUser( String id, String username, String fullname )
   {
      this.id = id;
      this.username = username;
      this.fullname = fullname;
   }
   


   public RtmUser( Element elt )
   {
      if ( !elt.getNodeName().equals( "user" ) )
      {
         throw new IllegalArgumentException( "Element " + elt.getNodeName()
            + " does not represent a User object." );
      }
      
      this.id = elt.getAttribute( "id" );
      this.username = elt.getAttribute( "username" );
      this.fullname = elt.getAttribute( "fullname" );
   }
   


   public RtmUser( Parcel source )
   {
      id = source.readString();
      username = source.readString();
      fullname = source.readString();
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getUsername()
   {
      return username;
   }
   


   public String getFullname()
   {
      return fullname;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( username );
      dest.writeString( fullname );
   }
}
