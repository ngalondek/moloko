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

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmAuth extends RtmData
{
   
   public enum Perms
   {
         nothing, read, write, delete
   }
   
   public static final Parcelable.Creator< RtmAuth > CREATOR = new Parcelable.Creator< RtmAuth >()
   {
      
      public RtmAuth createFromParcel( Parcel source )
      {
         return new RtmAuth( source );
      }
      


      public RtmAuth[] newArray( int size )
      {
         return new RtmAuth[ size ];
      }
      
   };
   
   private final String token;
   
   private final Perms perms;
   
   private final RtmUser user;
   
   

   public RtmAuth( String token, Perms perms, RtmUser user )
   {
      this.token = token;
      this.perms = perms;
      this.user = user;
   }
   


   public RtmAuth( Element elt )
   {
      if ( !elt.getNodeName().equals( "auth" ) )
      {
         throw new IllegalArgumentException( "Element "
                                             + elt.getNodeName()
                                             + " does not represent an Auth object." );
      }
      
      this.token = text( child( elt, "token" ) );
      this.perms = Enum.valueOf( Perms.class, text( child( elt, "perms" ) ) );
      this.user = new RtmUser( child( elt, "user" ) );
   }
   


   public RtmAuth( Parcel source )
   {
      token = source.readString();
      perms = Perms.valueOf( source.readString() );
      user = new RtmUser( source );
   }
   


   public String getToken()
   {
      return token;
   }
   


   public Perms getPerms()
   {
      return perms;
   }
   


   public RtmUser getUser()
   {
      return user;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( token );
      dest.writeString( perms.toString() );
      user.writeToParcel( dest, flags );
   }
}
