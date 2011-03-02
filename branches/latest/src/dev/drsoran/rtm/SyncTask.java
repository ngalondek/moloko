/*
 * Copyright (c) 2010 Ronny Röhricht
 * 
 * This file is part of Moloko.
 * 
 * Moloko is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Moloko is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Moloko. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.rtm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;
import com.mdt.rtm.data.RtmTaskNotes;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public class SyncTask implements IContentProviderSyncable< SyncTask >,
         IServerSyncable< SyncTask, RtmTaskSeries >
{
   
   private final static class LessIdComperator implements Comparator< SyncTask >
   {
      public int compare( SyncTask object1, SyncTask object2 )
      {
         return object1.getId().compareTo( object2.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmTaskSeries taskSeries;
   
   private final RtmTask task;
   
   

   public SyncTask( RtmTaskSeries taskSeries, String taskId )
   {
      this( taskSeries, taskSeries.getTask( taskId ) );
   }
   


   public SyncTask( RtmTaskSeries taskSeries, RtmTask task )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskseries is null" );
      if ( task == null )
         throw new NullPointerException( "task is null" );
      
      this.taskSeries = taskSeries;
      this.task = task;
   }
   


   public String getId()
   {
      return task.getId();
   }
   


   public String getTaskSeriesId()
   {
      return taskSeries.getId();
   }
   


   public Date getCreatedDate()
   {
      return taskSeries.getCreatedDate();
   }
   


   public Date getModifiedDate()
   {
      return taskSeries.getModifiedDate();
   }
   


   public Date getDueDate()
   {
      return task.getDue();
   }
   


   public int hasDueTime()
   {
      return task.getHasDueTime();
   }
   


   public Date getAddedDate()
   {
      return task.getAdded();
   }
   


   public Date getCompletedDate()
   {
      return task.getCompleted();
   }
   


   public Date getDeletedDate()
   {
      return task.getDeletedDate();
   }
   


   public int getPosponed()
   {
      return task.getPostponed();
   }
   


   public String getName()
   {
      return taskSeries.getName();
   }
   


   public String getSource()
   {
      return taskSeries.getSource();
   }
   


   public String getUrl()
   {
      return taskSeries.getURL();
   }
   


   public String getRecurrence()
   {
      return taskSeries.getRecurrence();
   }
   


   public boolean isEveryRecurrence()
   {
      return taskSeries.isEveryRecurrence();
   }
   


   public String getLocationId()
   {
      return taskSeries.getLocationId();
   }
   


   public String getListId()
   {
      return taskSeries.getListId();
   }
   


   public Priority getPriority()
   {
      return task.getPriority();
   }
   


   public String getEstimate()
   {
      return task.getEstimate();
   }
   


   public long getEstimateMillis()
   {
      return task.getEstimateMillis();
   }
   


   public List< String > getTagStrings()
   {
      return taskSeries.getTagStrings();
   }
   


   public List< Tag > getTags()
   {
      return taskSeries.getTags();
   }
   


   public RtmTaskNotes getNotes()
   {
      return taskSeries.getNotes();
   }
   


   public ParticipantList getParticipants()
   {
      return taskSeries.getParticipants();
   }
   


   public final static List< SyncTask > fromTaskSeries( RtmTaskSeries taskSeries )
   {
      final List< SyncTask > result = new ArrayList< SyncTask >();
      
      for ( RtmTask task : taskSeries.getTasks() )
         result.add( new SyncTask( taskSeries, task ) );
      
      return result;
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newInsert();
      
      // Insert new RtmTask
      {
         operation.add( RtmTasksProviderPart.insertTask( task ) );
      }
      
      return operation.build();
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerInsertOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.newInstance();
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncTask serverElement )
   {
      ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      // RtmTask
      {
         final Uri contentUri = Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                          getId() );
         
         if ( SyncUtils.hasChanged( serverElement.getDueDate(), getDueDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.DUE_DATE,
                                                                MolokoDateUtils.getTime( serverElement.getDueDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.hasDueTime(), hasDueTime() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.HAS_DUE_TIME,
                                                                serverElement.hasDueTime() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getAddedDate(),
                                    getAddedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ADDED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.getAddedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getCompletedDate(),
                                    getCompletedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.COMPLETED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.getCompletedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getPriority(), getPriority() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.PRIORITY,
                                                                RtmTask.convertPriority( serverElement.getPriority() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getPosponed(), getPosponed() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.POSTPONED,
                                                                getPosponed() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getEstimate(), getEstimate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ESTIMATE,
                                                                getEstimate() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.getEstimateMillis(),
                                    getEstimateMillis() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( RawTasks.ESTIMATE_MILLIS,
                                                                getEstimateMillis() )
                                                    .build() );
      }
      
      return operations.build();
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerUpdateOperation( RtmTimeline timeline,
                                                                              ModificationSet modifications,
                                                                              SyncTask serverElement )
   {
      ServerSyncOperation.Builder< RtmTaskSeries > operation = ServerSyncOperation.newUpdate();
      
      // In case we have no server element (incremental sync)
      if ( serverElement == null )
         serverElement = this;
      
      // RtmTaskSeries ///
      {
         final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                            ? null
                                                                                            : serverElement.getModifiedDate(),
                                                                       getModifiedDate(),
                                                                       Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                 getTaskSeriesId() ),
                                                                       modifications );
         // ListId
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.LIST_ID,
                                          serverElement.getListId(),
                                          getListId(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            String oldListId = null;
            
            // In case we have no server element (incremental sync), we look for the modification
            if ( serverElement == this )
            {
               final Modification modification = modifications.find( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                               getTaskSeriesId() ),
                                                                     TaskSeries.LIST_ID );
               if ( modification != null )
                  oldListId = modification.getSyncedValue();
            }
            else
            {
               oldListId = serverElement.getListId();
            }
            
            operation.add( timeline.tasks_moveTo( oldListId,
                                                  getListId(),
                                                  getTaskSeriesId(),
                                                  getId() ),
                           properties.getModification( TaskSeries.LIST_ID ) );
         }
         
         // Name
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.TASKSERIES_NAME,
                                          serverElement.getName(),
                                          getName(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setName( getListId(),
                                                   getTaskSeriesId(),
                                                   getId(),
                                                   getName() ),
                           properties.getModification( TaskSeries.TASKSERIES_NAME ) );
         }
         
         // Location
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.LOCATION_ID,
                                          serverElement.getLocationId(),
                                          getLocationId(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setLocation( getListId(),
                                                       getTaskSeriesId(),
                                                       getId(),
                                                       getLocationId() ),
                           properties.getModification( TaskSeries.LOCATION_ID ) );
         }
         
         // URL
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.URL,
                                          serverElement.getUrl(),
                                          getUrl(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setURL( getListId(),
                                                  getTaskSeriesId(),
                                                  getId(),
                                                  getUrl() ),
                           properties.getModification( TaskSeries.URL ) );
         }
      }
      
      // RtmTask ///
      {
         final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                            ? null
                                                                                            : serverElement.getModifiedDate(),
                                                                       getModifiedDate(),
                                                                       Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                 getId() ),
                                                                       modifications );
         // Priority
         if ( SyncUtils.getSyncDirection( properties,
                                          RawTasks.PRIORITY,
                                          RtmTask.convertPriority( serverElement.getPriority() ),
                                          RtmTask.convertPriority( getPriority() ),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setPriority( getListId(),
                                                       getTaskSeriesId(),
                                                       getId(),
                                                       getPriority() ),
                           properties.getModification( RawTasks.PRIORITY ) );
         }
      }
      
      return operation.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                                   getId() ) )
                                                                             .build() )
                                         .build();
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.newInstance();
   }
}
