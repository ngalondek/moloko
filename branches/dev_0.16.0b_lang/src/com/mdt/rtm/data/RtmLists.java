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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;


public class RtmLists extends RtmData
{
   
   public static final Parcelable.Creator< RtmLists > CREATOR = new Parcelable.Creator< RtmLists >()
   {
      
      public RtmLists createFromParcel( Parcel source )
      {
         return new RtmLists( source );
      }
      


      public RtmLists[] newArray( int size )
      {
         return new RtmLists[ size ];
      }
      
   };
   
   private final Map< String, RtmList > lists;
   
   

   public RtmLists()
   {
      this.lists = new HashMap< String, RtmList >();
   }
   


   public RtmLists( Element elt )
   {
      final List< Element > children = children( elt, "list" );
      this.lists = new HashMap< String, RtmList >( children.size() );
      for ( Element listElt : children )
      {
         final RtmList list = new RtmList( listElt );
         lists.put( list.getId(), list );
      }
   }
   


   public RtmLists( Parcel source )
   {
      lists = new HashMap< String, RtmList >();
      
      final Bundle bundle = source.readBundle();
      final Set< String > keys = bundle.keySet();
      
      for ( String key : keys )
      {
         lists.put( key, (RtmList) bundle.getParcelable( key ) );
      }
   }
   


   public RtmList getList( String id )
   {
      return lists.get( id );
   }
   


   public Map< String, RtmList > getLists()
   {
      return Collections.unmodifiableMap( lists );
   }
   


   public List< RtmList > getListsPlain()
   {
      return new ArrayList< RtmList >( lists.values() );
   }
   


   public void add( RtmList list )
   {
      lists.put( list.getId(), list );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      final Bundle bundle = new Bundle( lists.size() );
      
      final Set< String > keys = lists.keySet();
      
      for ( String key : keys )
      {
         bundle.putParcelable( key, lists.get( key ) );
      }
      
      dest.writeBundle( bundle );
   }
}
