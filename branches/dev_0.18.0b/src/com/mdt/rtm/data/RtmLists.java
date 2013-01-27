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
import java.util.List;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;


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
   
   private final List< Pair< String, RtmList > > lists;
   
   

   public RtmLists()
   {
      this.lists = new ArrayList< Pair< String, RtmList > >( 0 );
   }
   


   public RtmLists( Element elt )
   {
      final List< Element > children = children( elt, "list" );
      this.lists = new ArrayList< Pair< String, RtmList > >( children.size() );
      for ( Element listElt : children )
      {
         final RtmList list = new RtmList( listElt );
         lists.add( Pair.create( list.getId(), list ) );
      }
   }
   


   public RtmLists( Parcel source )
   {
      final Parcelable[] plainLists = source.readParcelableArray( null );
      
      if ( plainLists != null )
      {
         lists = new ArrayList< Pair< String, RtmList > >( plainLists.length );
         
         for ( Parcelable parcelable : plainLists )
         {
            final RtmList list = (RtmList) parcelable;
            lists.add( Pair.create( list.getId(), list ) );
         }
      }
      else
         lists = new ArrayList< Pair< String, RtmList > >( 0 );
   }
   


   public RtmList getList( String id )
   {
      for ( Pair< String, RtmList > idWithList : lists )
         if ( idWithList.first.equals( id ) )
            return idWithList.second;
      
      return null;
   }
   


   public List< Pair< String, RtmList > > getLists()
   {
      return Collections.unmodifiableList( lists );
   }
   


   public List< RtmList > getListsPlain()
   {
      final List< RtmList > plainLists = new ArrayList< RtmList >( lists.size() );
      
      for ( Pair< String, RtmList > idWithList : lists )
         plainLists.add( idWithList.second );
      
      return plainLists;
   }
   


   public List< String > getListIds()
   {
      final List< String > listIds = new ArrayList< String >( lists.size() );
      
      for ( Pair< String, RtmList > idWithList : lists )
         listIds.add( idWithList.first );
      
      return listIds;
   }
   


   public List< String > getListNames()
   {
      final List< String > listNames = new ArrayList< String >( lists.size() );
      
      for ( Pair< String, RtmList > idWithList : lists )
         listNames.add( idWithList.second.getName() );
      
      return listNames;
   }
   


   public void add( RtmList list )
   {
      lists.add( Pair.create( list.getId(), list ) );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      final List< RtmList > lists = getListsPlain();
      
      RtmList[] listArray = new RtmList[ lists.size() ];
      listArray = lists.toArray( listArray );
      
      dest.writeParcelableArray( listArray, flags );
   }
}
