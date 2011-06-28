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
 * Represents the notes of a task.
 * 
 * @author Edouard Mercier
 * @since 2008.04.22
 */
public class RtmTaskNotes extends RtmData
{
   public static final Parcelable.Creator< RtmTaskNotes > CREATOR = new Parcelable.Creator< RtmTaskNotes >()
   {
      
      public RtmTaskNotes createFromParcel( Parcel source )
      {
         return new RtmTaskNotes( source );
      }
      


      public RtmTaskNotes[] newArray( int size )
      {
         return new RtmTaskNotes[ size ];
      }
      
   };
   
   private List< RtmTaskNote > notes;
   
   

   public RtmTaskNotes()
   {
   }
   


   public RtmTaskNotes( List< RtmTaskNote > notes )
   {
      if ( notes == null )
         throw new NullPointerException( "notes is null" );
      
      this.notes = notes;
   }
   


   public RtmTaskNotes( Element element, String taskSeriesId )
   {
      final List< Element > children = children( element, "note" );
      notes = new ArrayList< RtmTaskNote >( children.size() );
      for ( Element child : children )
      {
         notes.add( new RtmTaskNote( child, taskSeriesId ) );
      }
   }
   


   public RtmTaskNotes( Parcel source )
   {
      notes = source.createTypedArrayList( RtmTaskNote.CREATOR );
   }
   


   public List< RtmTaskNote > getNotes()
   {
      return notes == null ? Collections.< RtmTaskNote > emptyList() : notes;
   }
   


   public void setNotes( List< RtmTaskNote > notes )
   {
      if ( notes == null )
         throw new NullPointerException( "notes is null" );
      
      this.notes = new ArrayList< RtmTaskNote >( notes );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeTypedList( notes );
   }
}
