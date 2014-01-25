/* 
 *	Copyright (c) 2011 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.sync.elements;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation.Builder;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;


public class InSyncTask extends SyncTaskBase implements
         IContentProviderSyncable< InSyncTask >
{
   @SuppressWarnings( "unused" )
   private final static String TAG = InSyncTask.class.getSimpleName();
   
   public final static LessIdComperator< InSyncTask > LESS_ID = new LessIdComperator< InSyncTask >();
   
   
   
   public InSyncTask( RtmTaskSeries taskSeries, RtmTask task )
   {
      super( taskSeries, task );
   }
   
   
   
   public InSyncTask( RtmTaskSeries taskSeries, String taskId )
   {
      super( taskSeries, taskId );
   }
   
   
   
   public InSyncTask( RtmTaskSeries taskSeries )
   {
      super( taskSeries );
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newInsert();
      
      // Insert new taskseries
      operation.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                             .withValues( RtmTaskSeriesTable.getContentValues( taskSeries,
                                                                                               true ) )
                                             .build() );
      
      // Insert task
      operation.add( ContentProviderOperation.newInsert( RawTasks.CONTENT_URI )
                                             .withValues( RtmTasksProviderPart.getContentValues( task,
                                                                                                 true ) )
                                             .build() );
      
      // Insert participants
      final ParticipantList participantList = taskSeries.getParticipants();
      
      if ( participantList.getCount() > 0 )
         operation.addAll( RtmParticipantsTable.insertParticipants( participantList ) );
      
      return operation.build();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderUpdateOperation( InSyncTask serverElement )
   {
      final ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      // Check for a moved task. The task with the same ID is now in another taskseries.
      final boolean hasTaskMoved = SyncUtils.isDifferent( serverElement.taskSeries.getId(),
                                                         taskSeries.getId() );
      
      if ( hasTaskMoved )
      {
         // Insert new taskseries
         operations.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                                 .withValues( RtmTaskSeriesTable.getContentValues( serverElement.taskSeries,
                                                                                                   true ) )
                                                 .build() );
         
         // Insert participants
         final ParticipantList participantList = serverElement.taskSeries.getParticipants();
         
         if ( participantList.getCount() > 0 )
            operations.addAll( RtmParticipantsTable.insertParticipants( participantList ) );
      }
      
      // Sync task
      syncTask( serverElement, operations );
      
      if ( !hasTaskMoved )
      {
         // Sync participants
         syncParticipants( serverElement, operations );
         
         // Sync RtmTaskSeries
         syncTaskSeries( serverElement, operations );
      }
      
      return operations.build();
   }
   
   
   
   private void syncTask( InSyncTask serverElement, Builder operations )
   {
      final Uri contentUri = DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                       task.getId() );
      
      // Check for a moved task. The task with the same ID is now in another taskseries.
      //
      // The former taskseries will be removed by a trigger in RtmTasksProviderPart if it
      // references no more rawtasks after move.
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getId(),
                                 taskSeries.getId() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.TASKSERIES_ID,
                                                             serverElement.taskSeries.getId() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getDue(), task.getDue() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.DUE_DATE,
                                                             MolokoDateUtils.getTime( serverElement.task.getDue() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getHasDueTime(),
                                 task.getHasDueTime() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.HAS_DUE_TIME,
                                                             serverElement.task.getHasDueTime() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getAdded(), task.getAdded() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.ADDED_DATE,
                                                             MolokoDateUtils.getTime( serverElement.task.getAdded() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getCompleted(),
                                 task.getCompleted() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.COMPLETED_DATE,
                                                             MolokoDateUtils.getTime( serverElement.task.getCompleted() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getPriority(),
                                 task.getPriority() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.PRIORITY,
                                                             RtmTask.convertPriority( serverElement.task.getPriority() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getPostponed(),
                                 task.getPostponed() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.POSTPONED,
                                                             serverElement.task.getPostponed() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getEstimate(),
                                 task.getEstimate() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.ESTIMATE,
                                                             serverElement.task.getEstimate() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.task.getEstimateMillis(),
                                 task.getEstimateMillis() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( RawTasks.ESTIMATE_MILLIS,
                                                             serverElement.task.getEstimateMillis() )
                                                 .build() );
   }
   
   
   
   private void syncParticipants( InSyncTask serverElement, Builder operations )
   {
      operations.add( taskSeries.getParticipants()
                                .computeContentProviderUpdateOperation( serverElement.taskSeries.getParticipants() ) );
   }
   
   
   
   private void syncTaskSeries( InSyncTask serverElement, Builder operations )
   {
      final Uri contentUri = DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                       taskSeries.getId() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getListId(),
                                 taskSeries.getListId() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.LIST_ID,
                                                             serverElement.taskSeries.getListId() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getCreatedDate(),
                                 taskSeries.getCreatedDate() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.TASKSERIES_CREATED_DATE,
                                                             MolokoDateUtils.getTime( serverElement.taskSeries.getCreatedDate() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getModifiedDate(),
                                 taskSeries.getModifiedDate() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.MODIFIED_DATE,
                                                             MolokoDateUtils.getTime( serverElement.taskSeries.getModifiedDate() ) )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getName(),
                                 taskSeries.getName() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.TASKSERIES_NAME,
                                                             serverElement.taskSeries.getName() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getSource(),
                                 taskSeries.getSource() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.SOURCE,
                                                             serverElement.taskSeries.getSource() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getLocationId(),
                                 taskSeries.getLocationId() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.LOCATION_ID,
                                                             serverElement.taskSeries.getLocationId() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getURL(),
                                 taskSeries.getURL() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.URL,
                                                             serverElement.taskSeries.getURL() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( serverElement.taskSeries.getRecurrence(),
                                 taskSeries.getRecurrence() ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.RECURRENCE,
                                                             serverElement.taskSeries.getRecurrence() )
                                                 .build() );
      
      if ( SyncUtils.isDifferent( Boolean.valueOf( serverElement.taskSeries.isEveryRecurrence() ),
                                 Boolean.valueOf( taskSeries.isEveryRecurrence() ) ) )
         operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                 .withValue( TaskSeries.RECURRENCE_EVERY,
                                                             serverElement.taskSeries.isEveryRecurrence()
                                                                                                         ? 1
                                                                                                         : 0 )
                                                 .build() );
      
      {
         final String joinedServerTags = serverElement.taskSeries.getTagsJoined();
         
         if ( SyncUtils.isDifferent( joinedServerTags,
                                    taskSeries.getTagsJoined() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TAGS,
                                                                serverElement.taskSeries.hasTags()
                                                                                                  ? joinedServerTags
                                                                                                  : null )
                                                    .build() );
      }
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // RtmTaskSeries, Notes, Participant gets deleted by a RtmTaskSeriesProvider DB trigger if it references no more
      // RawTasks.
      return ContentProviderSyncOperation.newDelete( ContentProviderOperation.newDelete( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                                   task.getId() ) )
                                                                             .build() )
                                         .build();
   }
}
