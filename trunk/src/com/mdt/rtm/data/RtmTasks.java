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
import java.util.Date;
import java.util.List;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;
import dev.drsoran.moloko.service.parcel.ParcelableDate;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmTasks extends RtmData
{
   public static final Parcelable.Creator< RtmTasks > CREATOR = new Parcelable.Creator< RtmTasks >()
   {
      
      public RtmTasks createFromParcel( Parcel source )
      {
         return new RtmTasks( source );
      }
      


      public RtmTasks[] newArray( int size )
      {
         return new RtmTasks[ size ];
      }
      
   };
   
   private final List< RtmTaskList > lists;
   
   private final ParcelableDate current;
   
   

   public RtmTasks()
   {
      this.lists = new ArrayList< RtmTaskList >();
      this.current = null;
   }
   


   public RtmTasks( Element elt )
   {
      final List< Element > children = children( elt, "list" );
      this.lists = new ArrayList< RtmTaskList >( children.size() );
      for ( Element listElt : children )
      {
         lists.add( new RtmTaskList( listElt ) );
      }
      
      current = parseDate( textNullIfEmpty( elt, "current" ) );
   }
   


   public RtmTasks( Parcel source )
   {
      lists = source.createTypedArrayList( RtmTaskList.CREATOR );
      current = source.readParcelable( null );
   }
   


   public List< RtmTaskList > getLists()
   {
      return Collections.unmodifiableList( lists );
   }
   


   public Date getCurrent()
   {
      return ( current != null ) ? current.getDate() : null;
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeTypedList( lists );
      dest.writeParcelable( current, flags );
   }
   


   public void add( RtmTaskList taskList )
   {
      this.lists.add( taskList );
   }
   


   public void removeDeletedTaskSeries()
   {
      for ( RtmTaskList taskList : lists )
      {
         taskList.removeDeletedTaskSeries();
      }
   }
}
