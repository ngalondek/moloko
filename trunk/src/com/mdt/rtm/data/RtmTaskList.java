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
import android.text.TextUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.ParcelableDate;


/**
 * 
 * @author Will Ross Jun 22, 2007
 */
public class RtmTaskList extends RtmData
{
   @SuppressWarnings( "unused" )
   private static final String TAG = "Moloko."
      + RtmTaskList.class.getSimpleName();
   
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
   
   private final ParcelableDate current;
   
   private List< RtmTaskSeries > series;
   
   private List< RtmTaskSeries > seriesesWithDeletedTasks;
   
   
   
   public RtmTaskList( String id )
   {
      this( id, new ArrayList< RtmTaskSeries >( 0 ), null );
   }
   
   
   
   public RtmTaskList( RtmTaskList other )
   {
      this( other.id, other.series, MolokoDateUtils.getDate( other.current ) );
   }
   
   
   
   public RtmTaskList( Parcel source )
   {
      id = source.readString();
      series = source.createTypedArrayList( RtmTaskSeries.CREATOR );
      seriesesWithDeletedTasks = source.createTypedArrayList( RtmTaskSeries.CREATOR );
      current = source.readParcelable( null );
   }
   
   
   
   public RtmTaskList( String id, List< RtmTaskSeries > series, Date current )
   {
      this.id = id;
      
      // do not use Collections.emptyList() here cause we
      // need a mutable list.
      this.series = new ArrayList< RtmTaskSeries >( series );
      this.seriesesWithDeletedTasks = null;
      this.current = current != null ? new ParcelableDate( current ) : null;
   }
   
   
   
   public RtmTaskList( Element elt )
   {
      id = elt.getAttribute( "id" );
      
      readTaskSerieses( elt );
      readTaskSeriesesWithDeletedTasks( elt );
      
      current = parseParcableDate( textNullIfEmpty( elt, "current" ) );
      
      if ( TextUtils.isEmpty( id ) )
      {
         throw new IllegalStateException( "No id found in task list." );
      }
   }
   
   
   
   private void readTaskSerieses( Element elt )
   {
      final List< Element > children = children( elt, "taskseries" );
      
      series = new ArrayList< RtmTaskSeries >( children.size() );
      
      // Look for taskseries element
      for ( Element seriesElt : children )
      {
         series.add( new RtmTaskSeries( seriesElt, id ) );
      }
   }
   
   
   
   private void readTaskSeriesesWithDeletedTasks( Element elt )
   {
      final List< Element > deleted = children( elt, "deleted" );
      
      // There may also be 'deleted' elements in which are 'taskseries'
      // elements
      if ( deleted.size() > 0 )
      {
         seriesesWithDeletedTasks = new ArrayList< RtmTaskSeries >( deleted.size() );
         
         for ( Element deletedElement : deleted )
         {
            for ( Element seriesElt : children( deletedElement, "taskseries" ) )
            {
               seriesesWithDeletedTasks.add( new RtmTaskSeries( seriesElt,
                                                                id,
                                                                true ) );
            }
         }
      }
   }
   
   
   
   public String getId()
   {
      return id;
   }
   
   
   
   public List< RtmTaskSeries > getSeries()
   {
      return series;
   }
   
   
   
   public List< RtmTaskSeries > getSeriesWithDeletedTasks()
   {
      if ( seriesesWithDeletedTasks != null )
         return seriesesWithDeletedTasks;
      else
         return Collections.emptyList();
   }
   
   
   
   public Date getCurrent()
   {
      return ( current != null ) ? current.getDate() : null;
   }
   
   
   
   public void add( RtmTaskSeries taskSeries )
   {
      series.add( taskSeries );
   }
   
   
   
   public void addGeneratedSeries( Element elt )
   {
      if ( series.size() == 0 )
         throw new IllegalStateException( String.format( "Expected RtmTaskList '%s' to have at least taskseries for generated taskseries '%s'",
                                                         id,
                                                         elt.getTextContent() ) );
      
      final List< Element > generated = RtmData.children( elt, "taskseries" );
      
      for ( Element element : generated )
      {
         series.add( newGenerated( element, series.get( 0 ) ) );
      }
   }
   
   
   
   private static RtmTaskSeries newGenerated( Element elt,
                                              RtmTaskSeries reference )
   {
      final String id = elt.getAttribute( "id" );
      final Date modified = RtmData.parseDate( elt.getAttribute( "modified" ) );
      
      final List< Element > taskElements = RtmData.children( elt, "task" );
      final List< RtmTask > tasks = new ArrayList< RtmTask >( taskElements.size() );
      
      for ( Element taskElement : taskElements )
      {
         tasks.add( new RtmTask( taskElement, id, reference.getListId() ) );
      }
      
      return new RtmTaskSeries( id,
                                reference.getListId(),
                                reference.getCreatedDate(),
                                modified,
                                reference.getName(),
                                reference.getSource(),
                                tasks,
                                reference.getNotes(),
                                reference.getLocationId(),
                                reference.getURL(),
                                reference.getRecurrence(),
                                reference.isEveryRecurrence(),
                                reference.getTagsJoined(),
                                reference.getParticipants() );
   }
   
   
   
   public int describeContents()
   {
      return 0;
   }
   
   
   
   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeTypedList( series );
      dest.writeParcelable( current, flags );
   }
   
   
   
   @Override
   public String toString()
   {
      return "RtmTaskList<" + id + ",#" + series.size()
         + ( ( current != null ) ? "," + current.getDate() : "" ) + ">";
   }
   
}
