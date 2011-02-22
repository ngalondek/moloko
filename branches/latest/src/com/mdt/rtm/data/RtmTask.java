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
import java.util.List;

import org.w3c.dom.Element;

import android.content.ContentProviderOperation;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.service.parcel.ParcelableDate;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.DirectedSyncOperations;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopServerSyncOperation;
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
   
   private final String listId;
   
   private final ParcelableDate due;
   
   private final int hasDueTime;
   
   private final ParcelableDate added;
   
   private final ParcelableDate modified;
   
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
   


   public RtmTask( String id, String taskSeriesId, String listId, Date due,
      int hasDueTime, Date added, Date modified, Date completed, Date deleted,
      Priority priority, int postponed, String estimate, long estimateMillis )
   {
      this.id = id;
      this.taskSeriesId = taskSeriesId;
      this.listId = listId;
      this.due = ( due != null ) ? new ParcelableDate( due ) : null;
      this.hasDueTime = hasDueTime;
      this.added = ( added != null ) ? new ParcelableDate( added ) : null;
      this.modified = ( modified != null ) ? new ParcelableDate( modified )
                                          : null;
      this.completed = ( completed != null ) ? new ParcelableDate( completed )
                                            : null;
      this.deleted = ( deleted != null ) ? new ParcelableDate( deleted ) : null;
      this.priority = priority;
      this.postponed = postponed;
      this.estimate = estimate;
      this.estimateMillis = estimateMillis;
   }
   


   public RtmTask( Element elt, String taskSeriesId, String listId,
      ParcelableDate modified )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      this.listId = listId;
      due = parseDate( textNullIfEmpty( elt, "due" ) );
      hasDueTime = Integer.parseInt( elt.getAttribute( "has_due_time" ) );
      added = parseDate( textNullIfEmpty( elt, "added" ) );
      this.modified = modified;
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
   


   public RtmTask( Element elt, String taskSeriesId, String listId,
      boolean deleted )
   {
      id = elt.getAttribute( "id" );
      this.taskSeriesId = taskSeriesId;
      this.listId = listId;
      this.deleted = parseDate( textNullIfEmpty( elt, "deleted" ) );
      due = null;
      hasDueTime = 0;
      added = null;
      modified = null;
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
      this.listId = source.readString();
      this.due = source.readParcelable( null );
      this.hasDueTime = source.readInt();
      this.added = source.readParcelable( null );
      this.modified = source.readParcelable( null );
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
   


   public String getListId()
   {
      return listId;
   }
   


   public Date getDue()
   {
      return ( due != null ) ? due.getDate() : null;
   }
   


   private static SyncResultDirection syncDueDate( SyncProperties< RtmTask > properties,
                                                   ParcelableDate serverValue,
                                                   ParcelableDate localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.DUE_DATE,
                                                           serverValue,
                                                           localValue,
                                                           ParcelableDate.class );
      return dir;
   }
   


   public int getHasDueTime()
   {
      return hasDueTime;
   }
   


   private static SyncResultDirection syncHasDueTime( SyncProperties< RtmTask > properties,
                                                      int serverValue,
                                                      int localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.HAS_DUE_TIME,
                                                           serverValue,
                                                           localValue,
                                                           Integer.class );
      return dir;
   }
   


   public Date getAdded()
   {
      return ( added != null ) ? added.getDate() : null;
   }
   


   private static SyncResultDirection syncAddedDate( SyncProperties< RtmTask > properties,
                                                     ParcelableDate serverValue,
                                                     ParcelableDate localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.ADDED_DATE,
                                                           serverValue,
                                                           localValue,
                                                           ParcelableDate.class );
      return dir;
   }
   


   public Date getCompleted()
   {
      return ( completed != null ) ? completed.getDate() : null;
   }
   


   private static SyncResultDirection syncCompletedDate( SyncProperties< RtmTask > properties,
                                                         ParcelableDate serverValue,
                                                         ParcelableDate localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.COMPLETED_DATE,
                                                           serverValue,
                                                           localValue,
                                                           ParcelableDate.class );
      return dir;
   }
   


   public Date getCreatedDate()
   {
      return getAdded();
   }
   


   public Date getModifiedDate()
   {
      return ( modified != null ) ? modified.getDate() : null;
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
   


   private static SyncResultDirection syncPriority( SyncProperties< RtmTask > properties,
                                                    Priority serverValue,
                                                    Priority localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.PRIORITY,
                                                           convertPriority( serverValue ),
                                                           convertPriority( localValue ),
                                                           String.class );
      if ( dir == SyncResultDirection.SERVER )
         ;
      return dir;
   }
   


   public int getPostponed()
   {
      return postponed;
   }
   


   private static SyncResultDirection syncPostponed( SyncProperties< RtmTask > properties,
                                                     int serverValue,
                                                     int localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.POSTPONED,
                                                           serverValue,
                                                           localValue,
                                                           Integer.class );
      return dir;
   }
   


   public String getEstimate()
   {
      return estimate;
   }
   


   private static SyncResultDirection syncEstimate( SyncProperties< RtmTask > properties,
                                                    String serverValue,
                                                    String localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.ESTIMATE,
                                                           serverValue,
                                                           localValue,
                                                           String.class );
      return dir;
   }
   


   public long getEstimateMillis()
   {
      return estimateMillis;
   }
   


   private static SyncResultDirection syncEstimateMillis( SyncProperties< RtmTask > properties,
                                                          long serverValue,
                                                          long localValue )
   {
      final SyncResultDirection dir = SyncUtils.syncValue( properties,
                                                           RawTasks.ESTIMATE_MILLIS,
                                                           serverValue,
                                                           localValue,
                                                           Long.class );
      return dir;
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
      dest.writeString( listId );
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
   


   public List< IContentProviderSyncOperation > computeContentProviderUpdateOperations( Date lastSync,
                                                                                        RtmTask serverElement )
   {
      final SyncProperties< RtmTask > properties = SyncProperties.newLocalOnlyInstance( lastSync,
                                                                                        serverElement,
                                                                                        this,
                                                                                        Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                                  id ) );
      return syncImpl( serverElement, this, properties ).operations.getLocalOperations();
   }
   


   public DirectedSyncOperations computeMergeOperations( Date lastSync,
                                                         RtmTimeline timeline,
                                                         ModificationList modifications,
                                                         RtmTask serverElement,
                                                         RtmTask localElement )
   {
      final SyncProperties< RtmTask > properties = SyncProperties.newInstance( SyncDirection.BOTH,
                                                                               lastSync,
                                                                               serverElement,
                                                                               localElement,
                                                                               Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                         id ),
                                                                               modifications,
                                                                               timeline );
      return syncImpl( serverElement, localElement, properties ).operations;
   }
   


   public List< IServerSyncOperation< ? > > computeServerUpdateOperations( Date lastSync,
                                                                           RtmTimeline timeline,
                                                                           ModificationList modifications )
   {
      final SyncProperties< RtmTask > properties = SyncProperties.newInstance( SyncDirection.SERVER_ONLY,
                                                                               lastSync,
                                                                               this,
                                                                               this,
                                                                               Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                         id ),
                                                                               modifications,
                                                                               timeline );
      return syncImpl( this, this, properties ).operations.getServerOperations();
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
   


   private SyncProperties< RtmTask > syncImpl( RtmTask serverElement,
                                               RtmTask localElement,
                                               SyncProperties< RtmTask > properties )
   {
      SyncUtils.doPreSyncCheck( localElement.id, serverElement.id, properties );
      
      syncDueDate( properties, serverElement.due, localElement.due );
      
      syncHasDueTime( properties,
                      serverElement.hasDueTime,
                      localElement.hasDueTime );
      
      syncAddedDate( properties, serverElement.added, localElement.added );
      
      syncCompletedDate( properties,
                         serverElement.completed,
                         localElement.completed );
      
      syncPriority( properties, serverElement.priority, localElement.priority );
      
      syncPostponed( properties,
                     serverElement.postponed,
                     localElement.postponed );
      
      syncEstimate( properties, serverElement.estimate, localElement.estimate );
      
      syncEstimateMillis( properties,
                          serverElement.estimateMillis,
                          localElement.estimateMillis );
      
      return properties;
   }
}
