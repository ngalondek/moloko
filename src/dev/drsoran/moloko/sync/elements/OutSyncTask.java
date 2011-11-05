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

package dev.drsoran.moloko.sync.elements;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.CreationsProviderPart;
import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ServerSyncOperation;
import dev.drsoran.moloko.sync.operation.TaskServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public class OutSyncTask implements IServerSyncable< OutSyncTask, RtmTaskList >
{
   private final static String TAG = OutSyncTask.class.toString();
   
   
   private final static class LessIdComperator implements
            Comparator< OutSyncTask >
   {
      public int compare( OutSyncTask object1, OutSyncTask object2 )
      {
         return object1.task.getId().compareTo( object2.task.getId() );
      }
   }
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   private final RtmTaskSeries taskSeries;
   
   private final RtmTask task;
   
   
   
   public OutSyncTask( RtmTaskSeries taskSeries )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskseries is null" );
      
      if ( taskSeries.getTasks().size() != 1 )
         throw new IllegalStateException( "Expected taskseries with 1 task, found "
            + taskSeries.getTasks().size() + " task(s)" );
      
      this.taskSeries = taskSeries;
      this.task = taskSeries.getTasks().get( 0 );
   }
   
   
   
   public OutSyncTask( RtmTaskSeries taskSeries, String taskId )
   {
      this( taskSeries, taskSeries.getTask( taskId ) );
   }
   
   
   
   public OutSyncTask( RtmTaskSeries taskSeries, RtmTask task )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskseries is null" );
      if ( task == null )
         throw new NullPointerException( "task is null" );
      
      this.taskSeries = taskSeries;
      this.task = task;
   }
   
   
   
   public final static List< OutSyncTask > fromTaskSeries( RtmTaskSeries taskSeries )
   {
      final List< OutSyncTask > result = new ArrayList< OutSyncTask >();
      
      for ( RtmTask task : taskSeries.getTasks() )
         result.add( new OutSyncTask( taskSeries, task ) );
      
      return result;
   }
   
   
   
   public RtmTaskSeries getTaskSeries()
   {
      return taskSeries;
   }
   
   
   
   public RtmTask getTask()
   {
      return task;
   }
   
   
   
   public Date getCreatedDate()
   {
      return taskSeries.getCreatedDate();
   }
   
   
   
   public Date getModifiedDate()
   {
      return taskSeries.getModifiedDate();
   }
   
   
   
   public Date getDeletedDate()
   {
      return task.getDeletedDate();
   }
   
   
   
   public boolean hasModification( ModificationSet modificationSet )
   {
      return modificationSet.hasModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                        taskSeries.getId() ) )
         || modificationSet.hasModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                       task.getId() ) );
      
   }
   
   
   
   public IContentProviderSyncOperation handleAfterServerInsert( OutSyncTask serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      /**
       * Change the ID of the local taskseries to the ID of the server taskseries. Referencing entities will also be
       * changed by a DB trigger.
       **/
      operation.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                   taskSeries.getId() ) )
                                             .withValue( TaskSeries._ID,
                                                         serverElement.taskSeries.getId() )
                                             .build() );
      
      /** Change the ID of the local rawtask to the ID of the server rawtask. **/
      operation.add( ContentProviderOperation.newUpdate( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                   task.getId() ) )
                                             .withValue( RawTasks._ID,
                                                         serverElement.task.getId() )
                                             .build() );
      
      /** Remove the old task IDs from the creations table, marking this task as send **/
      operation.add( CreationsProviderPart.deleteCreation( TaskSeries.CONTENT_URI,
                                                           taskSeries.getId() ) );
      operation.add( CreationsProviderPart.deleteCreation( RawTasks.CONTENT_URI,
                                                           task.getId() ) );
      
      /** Remove all modifications with the old task IDs **/
      
      operation.add( ModificationsProviderPart.getRemoveModificationOps( TaskSeries.CONTENT_URI,
                                                                         taskSeries.getId() ) );
      operation.add( ModificationsProviderPart.getRemoveModificationOps( RawTasks.CONTENT_URI,
                                                                         task.getId() ) );
      
      return operation.build();
   }
   
   
   
   /**
    * This stores only outgoing differences between the local task and the server task as modification.
    * 
    * This is needed due to the fact that add a task on RTM side is no single operation and may be interrupted.
    */
   public IContentProviderSyncOperation computeServerInsertModification( OutSyncTask serverElement )
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newUpdate();
      
      /** RtmTaskSeries **/
      {
         // All differences to the new server element will be added as modification. The Modification.newValue
         // is the local task value and the Modification.syncedValue is the value from the new inserted task
         // from RTM side.
         final Uri newUri = Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                      serverElement.taskSeries.getId() );
         
         // Recurrence
         if ( SyncUtils.hasChanged( taskSeries.getRecurrence(),
                                    serverElement.taskSeries.getRecurrence() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  TaskSeries.RECURRENCE,
                                                                  taskSeries.getRecurrence(),
                                                                  serverElement.taskSeries.getRecurrence() ) );
         
         // Tags
         if ( SyncUtils.hasChanged( taskSeries.getTagsJoined(),
                                    serverElement.taskSeries.getTagsJoined() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  TaskSeries.TAGS,
                                                                  taskSeries.getTagsJoined(),
                                                                  serverElement.taskSeries.getTagsJoined() ) );
         
         // Location
         if ( SyncUtils.hasChanged( taskSeries.getLocationId(),
                                    serverElement.taskSeries.getLocationId() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  TaskSeries.LOCATION_ID,
                                                                  taskSeries.getLocationId(),
                                                                  serverElement.taskSeries.getLocationId() ) );
         // URL
         if ( SyncUtils.hasChanged( taskSeries.getURL(),
                                    serverElement.taskSeries.getURL() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  TaskSeries.URL,
                                                                  taskSeries.getURL(),
                                                                  serverElement.taskSeries.getURL() ) );
      }
      
      /** RtmTask **/
      {
         // All differences to the new server element will be added as modification
         final Uri newUri = Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                      serverElement.task.getId() );
         
         // Priority
         if ( SyncUtils.hasChanged( task.getPriority(),
                                    serverElement.task.getPriority() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  RawTasks.PRIORITY,
                                                                  RtmTask.convertPriority( task.getPriority() ),
                                                                  RtmTask.convertPriority( serverElement.task.getPriority() ) ) );
         
         // Completed date
         if ( SyncUtils.hasChanged( task.getCompleted(),
                                    serverElement.task.getCompleted() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  RawTasks.COMPLETED_DATE,
                                                                  MolokoDateUtils.getTime( task.getCompleted() ),
                                                                  MolokoDateUtils.getTime( serverElement.task.getCompleted() ) ) );
         
         // Due date
         if ( SyncUtils.hasChanged( task.getDue(), serverElement.task.getDue() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  RawTasks.DUE_DATE,
                                                                  MolokoDateUtils.getTime( task.getDue() ),
                                                                  MolokoDateUtils.getTime( serverElement.task.getDue() ) ) );
         
         // Has due time
         if ( SyncUtils.hasChanged( task.getHasDueTime(),
                                    serverElement.task.getHasDueTime() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  RawTasks.HAS_DUE_TIME,
                                                                  task.getHasDueTime(),
                                                                  serverElement.task.getHasDueTime() ) );
         
         // Estimate
         if ( SyncUtils.hasChanged( task.getEstimate(),
                                    serverElement.task.getEstimate() ) )
            operation.add( Modification.newModificationOperation( newUri,
                                                                  RawTasks.ESTIMATE,
                                                                  task.getEstimate(),
                                                                  serverElement.task.getEstimate() ) );
         
         /**
          * Postponed can not be synced. Otherwise we had to store the initial due date on local task creation of the
          * task and set this initial date after creation of the task on RTM side. After this, we could call postpone
          * 1..n times. This is not supported atm.
          **/
      }
      
      return operation.build();
   }
   
   
   
   public IServerSyncOperation< RtmTaskList > computeServerUpdateOperation( RtmTimeline timeline,
                                                                            ModificationSet modifications,
                                                                            OutSyncTask serverElement )
   {
      TaskServerSyncOperation.Builder< RtmTaskList > operation = TaskServerSyncOperation.newUpdate();
      
      // In case we have no server element (incremental sync)
      if ( serverElement == null )
         serverElement = this;
      
      /** RtmTaskSeries **/
      {
         final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                            ? null
                                                                                            : serverElement.getModifiedDate(),
                                                                       getModifiedDate(),
                                                                       Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                 taskSeries.getId() ),
                                                                       modifications );
         // ListId
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.LIST_ID,
                                          serverElement.taskSeries.getListId(),
                                          taskSeries.getListId(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            String oldListId = null;
            
            // In case we have no server element (incremental sync), we look for the modification
            if ( serverElement == this )
            {
               final Modification modification = modifications.find( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                               taskSeries.getId() ),
                                                                     TaskSeries.LIST_ID );
               if ( modification != null )
                  oldListId = modification.getSyncedValue();
            }
            else
            {
               oldListId = serverElement.taskSeries.getListId();
            }
            
            operation.add( timeline.tasks_moveTo( oldListId,
                                                  taskSeries.getListId(),
                                                  taskSeries.getId(),
                                                  task.getId() ),
                           properties.getModification( TaskSeries.LIST_ID ) );
         }
         
         // Name
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.TASKSERIES_NAME,
                                          serverElement.taskSeries.getName(),
                                          taskSeries.getName(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setName( taskSeries.getListId(),
                                                   taskSeries.getId(),
                                                   task.getId(),
                                                   taskSeries.getName() ),
                           properties.getModification( TaskSeries.TASKSERIES_NAME ) );
         }
         
         // Recurrence
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.RECURRENCE,
                                          serverElement.taskSeries.getRecurrence(),
                                          taskSeries.getRecurrence(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            // The RTM API needs the repeat parameter as sentence, not pattern.
            final String repeat = taskSeries.getRecurrenceSentence();
            
            operation.add( timeline.tasks_setRecurrence( taskSeries.getListId(),
                                                         taskSeries.getId(),
                                                         task.getId(),
                                                         repeat ),
                           properties.getModification( TaskSeries.RECURRENCE ) );
         }
         
         // Tags
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.TAGS,
                                          serverElement.taskSeries.getTagsJoined(),
                                          taskSeries.getTagsJoined(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setTags( taskSeries.getListId(),
                                                   taskSeries.getId(),
                                                   task.getId(),
                                                   taskSeries.getTags() ),
                           properties.getModification( TaskSeries.TAGS ) );
         }
         
         // Location
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.LOCATION_ID,
                                          serverElement.taskSeries.getLocationId(),
                                          taskSeries.getLocationId(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setLocation( taskSeries.getListId(),
                                                       taskSeries.getId(),
                                                       task.getId(),
                                                       taskSeries.getLocationId() ),
                           properties.getModification( TaskSeries.LOCATION_ID ) );
         }
         
         // URL
         if ( SyncUtils.getSyncDirection( properties,
                                          TaskSeries.URL,
                                          serverElement.taskSeries.getURL(),
                                          taskSeries.getURL(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setURL( taskSeries.getListId(),
                                                  taskSeries.getId(),
                                                  task.getId(),
                                                  taskSeries.getURL() ),
                           properties.getModification( TaskSeries.URL ) );
         }
      }
      
      /** RtmTask **/
      {
         final SyncProperties properties = SyncProperties.newInstance( serverElement == this
                                                                                            ? null
                                                                                            : serverElement.getModifiedDate(),
                                                                       getModifiedDate(),
                                                                       Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                 task.getId() ),
                                                                       modifications );
         // Priority
         if ( SyncUtils.getSyncDirection( properties,
                                          RawTasks.PRIORITY,
                                          RtmTask.convertPriority( serverElement.task.getPriority() ),
                                          RtmTask.convertPriority( task.getPriority() ),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setPriority( taskSeries.getListId(),
                                                       taskSeries.getId(),
                                                       task.getId(),
                                                       task.getPriority() ),
                           properties.getModification( RawTasks.PRIORITY ) );
         }
         
         // Completed date
         if ( SyncUtils.getSyncDirection( properties,
                                          RawTasks.COMPLETED_DATE,
                                          MolokoDateUtils.getTime( serverElement.task.getCompleted() ),
                                          MolokoDateUtils.getTime( task.getCompleted() ),
                                          Long.class ) == SyncResultDirection.SERVER )
         {
            if ( task.getCompleted() != null )
               operation.add( timeline.tasks_complete( taskSeries.getListId(),
                                                       taskSeries.getId(),
                                                       task.getId() ),
                              properties.getModification( RawTasks.COMPLETED_DATE ) );
            else
               operation.add( timeline.tasks_uncomplete( taskSeries.getListId(),
                                                         taskSeries.getId(),
                                                         task.getId() ),
                              properties.getModification( RawTasks.COMPLETED_DATE ) );
         }
         
         // Due date
         if ( SyncUtils.getSyncDirection( properties,
                                          RawTasks.DUE_DATE,
                                          MolokoDateUtils.getTime( serverElement.task.getDue() ),
                                          MolokoDateUtils.getTime( task.getDue() ),
                                          Long.class ) == SyncResultDirection.SERVER
            || SyncUtils.getSyncDirection( properties,
                                           RawTasks.HAS_DUE_TIME,
                                           serverElement.task.getHasDueTime(),
                                           task.getHasDueTime(),
                                           Integer.class ) == SyncResultDirection.SERVER )
         {
            final Modification dueMod = properties.getModification( RawTasks.DUE_DATE );
            final Modification hasTimeMod = properties.getModification( RawTasks.HAS_DUE_TIME );
            
            final List< Modification > modsList = new ArrayList< Modification >( 2 );
            
            if ( dueMod != null )
               modsList.add( dueMod );
            if ( hasTimeMod != null )
               modsList.add( hasTimeMod );
            
            operation.add( timeline.tasks_setDueDate( taskSeries.getListId(),
                                                      taskSeries.getId(),
                                                      task.getId(),
                                                      task.getDue(),
                                                      task.getHasDueTime() != 0
                                                                               ? true
                                                                               : false ),
                           modsList );
         }
         
         // Estimate
         if ( SyncUtils.getSyncDirection( properties,
                                          RawTasks.ESTIMATE,
                                          serverElement.task.getEstimate(),
                                          task.getEstimate(),
                                          String.class ) == SyncResultDirection.SERVER )
         {
            operation.add( timeline.tasks_setEstimate( taskSeries.getListId(),
                                                       taskSeries.getId(),
                                                       task.getId(),
                                                       TextUtils.isEmpty( task.getEstimate() )
                                                                                              ? Strings.EMPTY_STRING
                                                                                              : task.getEstimate() ),
                           properties.getModification( RawTasks.ESTIMATE ) );
         }
         
         // Postponed
         {
            final int localPostponed = task.getPostponed();
            
            if ( SyncUtils.getSyncDirection( properties,
                                             RawTasks.POSTPONED,
                                             serverElement.task.getPostponed(),
                                             localPostponed,
                                             Integer.class ) == SyncResultDirection.SERVER )
            {
               final int serverPostponed;
               
               // In case we have no server element (incremental sync), we look for the modification
               if ( serverElement == this )
               {
                  final Modification modification = modifications.find( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                  task.getId() ),
                                                                        RawTasks.POSTPONED );
                  if ( modification != null )
                     serverPostponed = modification.getSyncedValue( Integer.class );
                  else
                  {
                     serverPostponed = localPostponed;
                     Log.e( TAG, "Expected postponed modification" );
                  }
               }
               else
               {
                  serverPostponed = serverElement.task.getPostponed();
               }
               
               // Postpone the task "the difference between local and server" times.
               final int diffPostponed = localPostponed - serverPostponed;
               
               // Check that on server side the task was not also postponed.
               if ( diffPostponed > 0 )
               {
                  for ( int i = 0; i < diffPostponed; i++ )
                  {
                     operation.add( timeline.tasks_postpone( taskSeries.getListId(),
                                                             taskSeries.getId(),
                                                             task.getId() ),
                                    // Only the last method invocation clears the modification
                                    i + 1 == diffPostponed
                                                          ? properties.getModification( RawTasks.POSTPONED )
                                                          : null );
                  }
               }
            }
         }
      }
      
      return operation.build( TaskServerSyncOperation.class );
   }
   
   
   
   public IServerSyncOperation< RtmTaskList > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return ServerSyncOperation.newDelete( timeLine.tasks_delete( taskSeries.getListId(),
                                                                   taskSeries.getId(),
                                                                   task.getId() ) )
                                .build( TaskServerSyncOperation.class );
   }
   
}
