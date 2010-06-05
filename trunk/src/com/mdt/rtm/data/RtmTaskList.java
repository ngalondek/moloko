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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.content.SyncResult;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import dev.drsoran.moloko.service.sync.SyncAdapter;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskList extends RtmData implements Comparable< RtmTaskList >
{
   private static final String TAG = RtmTaskList.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmTaskList > CREATOR = new Parcelable.Creator< RtmTaskList >()
   {
      
      public RtmTaskList createFromParcel( Parcel source )
      {
         return new RtmTaskList( source );
      }
      


      public RtmTaskList[] newArray( int size )
      {
         return new RtmTaskList[ size ];
      }
      
   };
   
   private final String id;
   
   private final List< RtmTaskSeries > series;
   
   

   public RtmTaskList( String id )
   {
      this.id = id;
      this.series = new ArrayList< RtmTaskSeries >();
   }
   


   public RtmTaskList( Parcel source )
   {
      id = source.readString();
      series = source.createTypedArrayList( RtmTaskSeries.CREATOR );
   }
   


   public RtmTaskList( Element elt )
   {
      id = elt.getAttribute( "id" );
      final List< Element > children = children( elt, "taskseries" );
      series = new ArrayList< RtmTaskSeries >( children.size() );
      for ( Element seriesElt : children )
      {
         series.add( new RtmTaskSeries( seriesElt ) );
      }
      // There may also be 'deleted' elements in which are 'taskseries' elements
      for ( Element deletedElement : children( elt, "deleted" ) )
      {
         for ( Element seriesElt : children( deletedElement, "taskseries" ) )
         {
            series.add( new RtmTaskSeries( seriesElt, true ) );
         }
      }
      
      if ( id == null || id.length() == 0 )
      {
         throw new RuntimeException( "No id found in task list." );
      }
   }
   


   public String getId()
   {
      return id;
   }
   


   public List< RtmTaskSeries > getSeries()
   {
      return Collections.unmodifiableList( series );
   }
   


   public boolean add( RtmTaskSeries taskSeries )
   {
      return this.series.add( taskSeries );
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeTypedList( series );
   }
   


   public boolean computeSyncWith( int direction,
                                   RtmTaskList other,
                                   ArrayList< ContentProviderOperation > operations,
                                   SyncResult result )
   {
      boolean ok = true;
      
      switch ( direction )
      {
         case SyncAdapter.Direction.IN:
            // get all
            break;
         case SyncAdapter.Direction.OUT:
            Log.e( TAG, "Unsupported sync direction: OUT" );
         default :
            ok = false;
            break;
      }
      
      return ok;
   }
   


   public int compareTo( RtmTaskList other )
   {
      return this.id.compareTo( other.id );
   }
}
