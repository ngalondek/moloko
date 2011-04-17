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

import java.util.Date;

import org.w3c.dom.Element;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.rtm.ParcelableDate;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmTask extends RtmData
{
   private static final String TAG = "Moloko." + RtmTask.class.getSimpleName();
   
   public static final Parcelable.Creator< RtmTask > CREATOR = new Parcelable.Creator< RtmTask >()
   {
      public RtmTask createFromParcel( Parcel source )
      {
         return new RtmTask( source );
      }
      


      public RtmTask[] newArray( int size )
      {
         return new RtmTask[ size ];
      }
   };
   
   private final String id;
   
   private final String taskSeriesId;
   
   private final ParcelableDate due;
   
   private final int hasDueTime;
   
   private final ParcelableDate added;
   
   private final ParcelableDate completed;
   
   private final ParcelableDate deleted;
   
   private final Priority priority;
   
   private final int postponed;
   
   private final String estimate;
   
   private long estimateMillis;
   
   
   public enum Priority
   {
      High, Medium, Low, None
   }
   
   

   public static String convertPriority( Priority priority )
   {
      switch ( priority )
      {
         case None:
            return "n";
         case Low:
            return "3";
         case Medium:
            return "2";
         case High:
            return "1";
         default :
            Log.e( TAG, "Unrecognized RTM task priority: '" + priority + "'" );
            return "n";
      }
   }
   


   public static Priority convertPriority( String priority )
   {
      if ( TextUtils.isEmpty( priority ) )
         return Priority.None;
      
      switch ( priority.charAt( 0 ) )
      {
         case 'n':
            return Priority.None;
         case '3':
            return Priority.Low;
         case '2':
            return Priority.Medium;
         case '1':
            return Priority.High;
         default :
            Log.e( TAG, "Unrecognized RTM task priority: '" + priority + "'" );
            return Priority.None;
      }
   }
   


   public RtmTask( String id, String taskSeriesId, Date due, int hasDueTime,
      Date added, Date completed, Date deleted, Priority priority,
      int postponed, String estimate, long estimateMillis )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.due = ( due != null ) ? new ParcelableDate( due ) : null;
      this.hasDueTime = hasDueTime;
      this.added = ( added != null ) ? new ParcelableDate( added ) : null;
      this.completed = ( completed != null ) ? new ParcelableDate( completed )
                                            : null;
      this.deleted = ( deleted != null ) ? new ParcelableDate( deleted ) : null;
      this.priority = priority;
      this.postponed = postponed;
      this.estimate = estimate;
      this.estimateMillis = estimateMillis;
   }
   


   public RtmTask( Element elt, String taskSeriesId, String listId )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      due = parseParcableDate( textNullIfEmpty( elt, "due" ) );
      hasDueTime = Integer.parseInt( elt.getAttribute( "has_due_time" ) );
      added = parseParcableDate( textNullIfEmpty( elt, "added" ) );
      completed = parseParcableDate( textNullIfEmpty( elt, "completed" ) );
      deleted = parseParcableDate( textNullIfEmpty( elt, "deleted" ) );
      
      String priorityStr = elt.getAttribute( "priority" );
      if ( priorityStr.length() > 0 )
      {
         switch ( priorityStr.charAt( 0 ) )
         {
            case 'N':
            case 'n':
               priority = Priority.None;
               break;
            case '3':
               priority = Priority.Low;
               break;
            case '2':
               priority = Priority.Medium;
               break;
            case '1':
               priority = Priority.High;
               break;
            default :
               System.err.println( "Unrecognized RTM task priority: '"
                  + priorityStr + "'" );
               priority = Priority.Medium;
         }
      }
      else
      {
         priority = Priority.None;
      }
      
      if ( elt.hasAttribute( "postponed" ) == true
         && elt.getAttribute( "postponed" ).length() > 0 )
      {
         postponed = Integer.parseInt( elt.getAttribute( "postponed" ) );
      }
      else
      {
         postponed = 0;
      }
      
      estimate = textNullIfEmpty( elt, "estimate" );
      
      parseEstimate();
   }
   


   public RtmTask( Element elt, String taskSeriesId, String listId,
      boolean deleted )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      this.deleted = parseParcableDate( textNullIfEmpty( elt, "deleted" ) );
      due = null;
      hasDueTime = 0;
      added = null;
      completed = null;
      priority = Priority.None;
      postponed = 0;
      estimate = null;
      estimateMillis = -1;
   }
   


   public RtmTask( Parcel source )
   {
      this.id = source.readString();
      this.taskSeriesId = source.readString();
      this.due = source.readParcelable( null );
      this.hasDueTime = source.readInt();
      this.added = source.readParcelable( null );
      this.completed = source.readParcelable( null );
      this.deleted = source.readParcelable( null );
      this.priority = Priority.valueOf( source.readString() );
      this.postponed = source.readInt();
      this.estimate = source.readString();
      this.estimateMillis = source.readLong();
   }
   


   public String getId()
   {
      return id;
   }
   


   public String getTaskSeriesId()
   {
      return taskSeriesId;
   }
   


   public Date getDue()
   {
      return ( due != null ) ? due.getDate() : null;
   }
   


   public int getHasDueTime()
   {
      return hasDueTime;
   }
   


   public Date getAdded()
   {
      return ( added != null ) ? added.getDate() : null;
   }
   


   public Date getCompleted()
   {
      return ( completed != null ) ? completed.getDate() : null;
   }
   


   public Date getCreatedDate()
   {
      return getAdded();
   }
   


   public Date getDeletedDate()
   {
      return ( deleted != null ) ? deleted.getDate() : null;
   }
   


   public boolean isDeleted()
   {
      return deleted != null;
   }
   


   public Priority getPriority()
   {
      return priority;
   }
   


   public int getPostponed()
   {
      return postponed;
   }
   


   public String getEstimate()
   {
      return estimate;
   }
   


   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   


   @Override
   public String toString()
   {
      return "Task<" + id + ">";
   }
   


   public int describeContents()
   {
      return 0;
   }
   


   public void writeToParcel( Parcel dest, int flags )
   {
      dest.writeString( id );
      dest.writeString( taskSeriesId );
      dest.writeParcelable( due, flags );
      dest.writeInt( hasDueTime );
      dest.writeParcelable( added, flags );
      dest.writeParcelable( completed, flags );
      dest.writeParcelable( deleted, flags );
      dest.writeString( priority.toString() );
      dest.writeInt( postponed );
      dest.writeString( estimate );
      dest.writeLong( estimateMillis );
   }
   


   private void parseEstimate()
   {
      if ( !TextUtils.isEmpty( estimate ) )
      {
         this.estimateMillis = RtmDateTimeParsing.parseEstimated( estimate );
      }
      else
      {
         this.estimateMillis = -1;
      }
   }
}
