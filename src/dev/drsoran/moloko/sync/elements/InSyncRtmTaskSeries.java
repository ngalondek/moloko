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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncableList;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.ParticipantList;


public class InSyncRtmTaskSeries implements
         IContentProviderSyncableList< InSyncRtmTaskSeries >
{
   private static final class LessIdComperator implements
            Comparator< InSyncRtmTaskSeries >
   {
      @Override
      public int compare( InSyncRtmTaskSeries object1,
                          InSyncRtmTaskSeries object2 )
      {
         return object1.taskSeries.getId()
                                  .compareTo( object2.taskSeries.getId() );
      }
   }
   
   private final RtmTaskSeries taskSeries;
   
   private final List< InSyncRtmTask > inSyncTasks;
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   
   
   public InSyncRtmTaskSeries( RtmTaskSeries taskSeries )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskSeries is null" );
      
      this.taskSeries = taskSeries;
      this.inSyncTasks = rtmTasksToInSyncTasks( taskSeries );
   }
   
   
   
   public List< InSyncRtmTask > getInSyncTasks()
   {
      return inSyncTasks;
   }
   
   
   
   @Override
   public Date getDeletedDate()
   {
      // Always return null here since taskserieses have no deleted date.
      return null;
   }
   
   
   
   @Override
   public boolean hasDeletedElements()
   {
      boolean hasDeleted = false;
      
      // Deleted tasks?
      for ( Iterator< InSyncRtmTask > i = inSyncTasks.iterator(); i.hasNext()
         && !hasDeleted; )
      {
         hasDeleted = i.next().getDeletedDate() != null;
      }
      
      return hasDeleted;
   }
   
   
   
   public boolean hasUndeletedTasks()
   {
      boolean hasUndeleted = false;
      
      // Deleted tasks?
      for ( Iterator< InSyncRtmTask > i = inSyncTasks.iterator(); i.hasNext()
         && !hasUndeleted; )
      {
         hasUndeleted = i.next().getDeletedDate() == null;
      }
      
      return hasUndeleted;
   }
   
   
   
   @Override
   public String toString()
   {
      return taskSeries.toString();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newInsert();
      
      if ( hasUndeletedTasks() )
      {
         // Insert new taskseries
         {
            operation.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                                   .withValues( RtmTaskSeriesProviderPart.getContentValues( taskSeries,
                                                                                                            true ) )
                                                   .build() );
         }
         
         // Insert tasks
         {
            for ( InSyncRtmTask inSyncTask : inSyncTasks )
            {
               // For mixed deleted tasks and non-deleted tasks in one taskseries
               if ( inSyncTask.getDeletedDate() == null )
                  operation.add( inSyncTask.computeContentProviderInsertOperation() );
            }
         }
         
         // Insert participants
         {
            final ParticipantList participantList = taskSeries.getParticipants();
            operation.addAll( ParticipantsProviderPart.insertParticipants( participantList ) );
         }
      }
      
      return operation.build();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // RtmTaskSeries, Notes, Participant gets deleted by a RtmTaskSeriesProvider DB trigger if it references no more
      // RawTasks.
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newDelete();
      
      // Delete tasks
      for ( InSyncRtmTask inSyncTask : inSyncTasks )
      {
         // For mixed deleted tasks and non-deleted tasks in one taskseries
         if ( inSyncTask.getDeletedDate() != null )
            operation.add( inSyncTask.computeContentProviderDeleteOperation() );
      }
      
      return operation.build();
   }
   
   
   
   @Override
   public IContentProviderSyncOperation computeContentProviderUpdateOperation( InSyncRtmTaskSeries serverElement )
   {
      final ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      // Sync tasks
      {
         final ContentProviderSyncableList< InSyncRtmTask > syncTasksList = new ContentProviderSyncableList< InSyncRtmTask >( inSyncTasks,
                                                                                                                              InSyncRtmTask.LESS_ID );
         final List< IContentProviderSyncOperation > taskOperations = SyncDiffer.inDiff( serverElement.inSyncTasks,
                                                                                         syncTasksList,
                                                                                         false /* never full sync */);
         operations.add( taskOperations );
      }
      
      // Sync participants
      {
         operations.add( taskSeries.getParticipants()
                                   .computeContentProviderUpdateOperation( serverElement.taskSeries.getParticipants() ) );
      }
      
      // Sync RtmTaskSeries
      {
         final Uri contentUri = Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                          taskSeries.getId() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getListId(),
                                    taskSeries.getListId() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.LIST_ID,
                                                                serverElement.taskSeries.getListId() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getCreatedDate(),
                                    taskSeries.getCreatedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TASKSERIES_CREATED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.taskSeries.getCreatedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getModifiedDate(),
                                    taskSeries.getModifiedDate() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.MODIFIED_DATE,
                                                                MolokoDateUtils.getTime( serverElement.taskSeries.getModifiedDate() ) )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getName(),
                                    taskSeries.getName() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TASKSERIES_NAME,
                                                                serverElement.taskSeries.getName() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getSource(),
                                    taskSeries.getSource() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.SOURCE,
                                                                serverElement.taskSeries.getSource() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getLocationId(),
                                    taskSeries.getLocationId() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.LOCATION_ID,
                                                                serverElement.taskSeries.getLocationId() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getURL(),
                                    taskSeries.getURL() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.URL,
                                                                serverElement.taskSeries.getURL() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( serverElement.taskSeries.getRecurrence(),
                                    taskSeries.getRecurrence() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.RECURRENCE,
                                                                serverElement.taskSeries.getRecurrence() )
                                                    .build() );
         
         if ( SyncUtils.hasChanged( Boolean.valueOf( serverElement.taskSeries.isEveryRecurrence() ),
                                    Boolean.valueOf( taskSeries.isEveryRecurrence() ) ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.RECURRENCE_EVERY,
                                                                serverElement.taskSeries.isEveryRecurrence()
                                                                                                            ? 1
                                                                                                            : 0 )
                                                    .build() );
         
         {
            final String joinedServerTags = serverElement.taskSeries.getTagsJoined();
            
            if ( SyncUtils.hasChanged( joinedServerTags,
                                       taskSeries.getTagsJoined() ) )
               operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                       .withValue( TaskSeries.TAGS,
                                                                   serverElement.taskSeries.hasTags()
                                                                                                     ? joinedServerTags
                                                                                                     : null )
                                                       .build() );
         }
      }
      
      return operations.build();
   }
   
   
   
   protected static List< InSyncRtmTask > rtmTasksToInSyncTasks( RtmTaskSeries taskSeries )
   {
      List< InSyncRtmTask > inSyncTasks = new ArrayList< InSyncRtmTask >( taskSeries.getTasks()
                                                                                    .size() );
      for ( RtmTask task : taskSeries.getTasks() )
         inSyncTasks.add( new InSyncRtmTask( task ) );
      
      return inSyncTasks;
   }
}
