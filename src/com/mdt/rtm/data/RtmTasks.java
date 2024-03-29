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
   
   

   public RtmTasks()
   {
      this.lists = new ArrayList< RtmTaskList >();
   }
   


   public RtmTasks( Element elt )
   {
      final List< Element > children = children( elt, "list" );
      this.lists = new ArrayList< RtmTaskList >( children.size() );
      for ( Element listElt : children )
      {
         lists.add( new RtmTaskList( listElt ) );
      }
   }
   


   public RtmTasks( Parcel source )
   {
      lists = source.createTypedArrayList( RtmTaskList.CREATOR );
   }
   


   public List< RtmTaskList > getLists()
   {
      return Collections.unmodifiableList( lists );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeTypedList( lists );
   }
   


   public void add( RtmTaskList taskList )
   {
      this.lists.add( taskList );
   }
   


   @Override
   public String toString()
   {
      return "#Lists: " + lists.size();
   }
   
}
