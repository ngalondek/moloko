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

import android.text.TextUtils;
import android.util.Log;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskList;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.sync.operation.TaskServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.sync.util.SyncUtils.SyncResultDirection;
import dev.drsoran.moloko.util.LogUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.moloko.util.Strings;
import dev.drsoran.moloko.util.parsing.RecurrenceParsing;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public class OutSyncTask implements IServerSyncable< OutSyncTask, RtmTaskList >
{
   
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
   


   public IServerSyncOperation< RtmTaskList > computeServerInsertOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.newInstance();
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
            final String repeat;
            
            if ( taskSeries.getRecurrence() != null )
               repeat = RecurrenceParsing.parseRecurrencePattern( taskSeries.getRecurrence(),
                                                                  taskSeries.isEveryRecurrence() );
            else
               repeat = null;
            
            final List< Modification > modsList = new ArrayList< Modification >( 2 );
            modsList.add( properties.getModification( TaskSeries.RECURRENCE ) );
            
            final Modification isEvryMod = properties.getModification( TaskSeries.RECURRENCE_EVERY );
            
            if ( isEvryMod != null )
               modsList.add( isEvryMod );
            
            operation.add( timeline.tasks_setRecurrence( taskSeries.getListId(),
                                                         taskSeries.getId(),
                                                         task.getId(),
                                                         repeat ),
                           modsList );
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
                           properties.getModification( TaskSeries.LOCATION_ID ) );
         }
         
         // Postponed
         {
            final int serverPostponed = serverElement.task.getPostponed();
            final int localPostponed = task.getPostponed();
            
            if ( SyncUtils.getSyncDirection( properties,
                                             RawTasks.POSTPONED,
                                             serverPostponed,
                                             localPostponed,
                                             Integer.class ) == SyncResultDirection.SERVER )
            {
               // Postpone the task "the difference between local and server" times.
               final int diffPostponed = localPostponed - serverPostponed;
               
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
               else
               {
                  Log.w( LogUtils.toTag( OutSyncTask.class ),
                         "Unexpected postponed difference " + diffPostponed
                            + ". server=" + serverPostponed + ", local="
                            + localPostponed );
               }
            }
         }
      }
      
      return operation.build( TaskServerSyncOperation.class );
   }
   


   public IServerSyncOperation< RtmTaskList > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.newInstance();
   }
   
}
