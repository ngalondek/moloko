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

import java.util.Comparator;
import java.util.Date;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.TypedDirectedSyncOperations;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.SyncUtils;
import dev.drsoran.moloko.util.SyncUtils.SyncProperties;
import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
import dev.drsoran.provider.Rtm.RawTasks;


/**
 * 
 * @author Will Ross Jun 21, 2007
 */
public class RtmTask extends RtmData implements ITwoWaySyncable< RtmTask >
{
   private static final String TAG = "Moloko." + RtmTask.class.getSimpleName();
   
   
   private final static class LessIdComperator implements Comparator< RtmTask >
   {
      
      public int compare( RtmTask object1, RtmTask object2 )
      {
         return object1.id.compareTo( object2.id );
      }
      
   }
   
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
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
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
   


   public RtmTask( Element elt, String taskSeriesId )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      due = parseDate( textNullIfEmpty( elt, "due" ) );
      hasDueTime = Integer.parseInt( elt.getAttribute( "has_due_time" ) );
      added = parseDate( textNullIfEmpty( elt, "added" ) );
      completed = parseDate( textNullIfEmpty( elt, "completed" ) );
      deleted = parseDate( textNullIfEmpty( elt, "deleted" ) );
      
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
   


   public RtmTask( Element elt, String taskSeriesId, boolean deleted )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      this.deleted = parseDate( textNullIfEmpty( elt, "deleted" ) );
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
   


   public Date getDeleted()
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
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      return ContentProviderSyncOperation.newInsert( ContentProviderOperation.newInsert( RawTasks.CONTENT_URI )
                                                                             .withValues( RtmTasksProviderPart.getContentValues( this,
                                                                                                                                 true ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                                   id ) )
                                                                             .build() )
                                         .build();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( RtmTask serverElement )
   {
      return computeMergeOperations( null,
                                     null,
                                     serverElement,
                                     SyncDirection.LOCAL_ONLY ).getLocalOperation();
   }
   


   public TypedDirectedSyncOperations< RtmTask > computeMergeOperations( RtmTimeline timeLine,
                                                                         ModificationList modifications,
                                                                         RtmTask updateElement,
                                                                         SyncDirection mergeDirection )
   {
      SyncUtils.doPreSyncCheck( id,
                                 updateElement.id,
                                 timeLine,
                                 modifications,
                                 mergeDirection );
      
      final Uri uri = Queries.contentUriWithId( RawTasks.CONTENT_URI, id );
      
      final SyncProperties< RtmTask > mergeProperties = SyncProperties.newInstance( mergeDirection,
                                                                                    this,
                                                                                    updateElement,
                                                                                    uri,
                                                                                    modifications );
      
      SyncUtils.updateDate( due, update.due, uri, RawTasks.DUE_DATE, result );
      
      if ( hasDueTime != update.hasDueTime )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( RawTasks.HAS_DUE_TIME,
                                                         update.hasDueTime )
                                             .build() );
      
      SyncUtils.updateDate( added,
                            update.added,
                            uri,
                            RawTasks.ADDED_DATE,
                            result );
      
      SyncUtils.updateDate( completed,
                            update.completed,
                            uri,
                            RawTasks.COMPLETED_DATE,
                            result );
      
      SyncUtils.updateDate( deleted,
                            update.deleted,
                            uri,
                            RawTasks.DELETED_DATE,
                            result );
      
      if ( priority.compareTo( update.priority ) != 0 )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( RawTasks.PRIORITY,
                                                         convertPriority( update.priority ) )
                                             .build() );
      
      if ( postponed != update.postponed )
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( RawTasks.POSTPONED,
                                                         update.postponed )
                                             .build() );
      
      if ( SyncUtils.hasChanged( estimate, update.estimate ) )
      {
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( RawTasks.ESTIMATE,
                                                         update.estimate )
                                             .build() );
         
         result.add( ContentProviderOperation.newUpdate( uri )
                                             .withValue( RawTasks.ESTIMATE_MILLIS,
                                                         update.estimateMillis )
                                             .build() );
      }
      
      return mergeProperties.getOperations();
   }
   


   @SuppressWarnings( "unchecked" )
   public IServerSyncOperation< RtmTask > computeServerInsertOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   @SuppressWarnings( "unchecked" )
   public IServerSyncOperation< RtmTask > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   public IServerSyncOperation< RtmTask > computeServerUpdateOperation( RtmTimeline timeLine,
                                                                        ModificationList modifications )
   {
      return computeMergeOperations( timeLine,
                                     modifications,
                                     this,
                                     SyncDirection.SERVER_ONLY ).getServerOperation();
   }
   


   public IContentProviderSyncOperation computeRemoveModificationsOperation( ModificationList modifications )
   {
      if ( modifications.hasModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                    id ) ) )
         return ContentProviderSyncOperation.newDelete( ModificationsProviderPart.getRemoveModificationOps( RawTasks.CONTENT_URI,
                                                                                                            id ) )
                                            .build();
      else
         return NoopContentProviderSyncOperation.INSTANCE;
   }
}
