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
import java.util.List;

import android.content.ContentProviderOperation;
import android.net.Uri;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskSeries;

import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.NoopContentProviderSyncOperation;
import dev.drsoran.moloko.sync.syncable.IContentProviderSyncable;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.TaskSeries;
import dev.drsoran.rtm.ParticipantList;


public class InSyncRtmTaskSeries implements
         IContentProviderSyncable< InSyncRtmTaskSeries >
{
   private static final class LessIdComperator implements
            Comparator< InSyncRtmTaskSeries >
   {
      public int compare( InSyncRtmTaskSeries object1,
                          InSyncRtmTaskSeries object2 )
      {
         return object1.taskSeries.getId()
                                  .compareTo( object2.taskSeries.getId() );
      }
   }
   
   private final RtmTaskSeries taskSeries;
   
   public final static LessIdComperator LESS_ID = new LessIdComperator();
   
   

   public InSyncRtmTaskSeries( RtmTaskSeries taskSeries )
   {
      if ( taskSeries == null )
         throw new NullPointerException( "taskSeries is null" );
      
      this.taskSeries = taskSeries;
   }
   


   public List< InSyncRtmTask > getInSyncTasks()
   {
      final List< InSyncRtmTask > res = new ArrayList< InSyncRtmTask >( taskSeries.getTasks()
                                                                                  .size() );
      for ( RtmTask task : taskSeries.getTasks() )
         res.add( new InSyncRtmTask( task ) );
      
      return res;
   }
   


   public Date getDeletedDate()
   {
      return taskSeries.getDeletedDate();
   }
   


   @Override
   public String toString()
   {
      return taskSeries.toString();
   }
   


   public IContentProviderSyncOperation computeContentProviderInsertOperation()
   {
      final ContentProviderSyncOperation.Builder operation = ContentProviderSyncOperation.newInsert();
      
      // Insert new taskseries
      {
         operation.add( ContentProviderOperation.newInsert( TaskSeries.CONTENT_URI )
                                                .withValues( RtmTaskSeriesProviderPart.getContentValues( taskSeries,
                                                                                                         true ) )
                                                .build() );
      }
      
      // Insert tasks
      {
         for ( RtmTask rtmTask : taskSeries.getTasks() )
         {
            operation.add( RtmTasksProviderPart.insertTask( rtmTask ) );
         }
      }
      
      // Check for notes
      {
         final List< RtmTaskNote > notes = taskSeries.getNotes().getNotes();
         
         for ( RtmTaskNote rtmTaskNote : notes )
         {
            operation.add( ContentProviderOperation.newInsert( Notes.CONTENT_URI )
                                                   .withValues( RtmNotesProviderPart.getContentValues( rtmTaskNote,
                                                                                                       true ) )
                                                   .build() );
         }
      }
      
      // Check for participants
      {
         final ParticipantList participantList = taskSeries.getParticipants();
         operation.addAll( ParticipantsProviderPart.insertParticipants( participantList ) );
      }
      
      return operation.build();
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // RtmTaskSeries gets deleted by a DB trigger if it references no more RawTasks.
      return NoopContentProviderSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( InSyncRtmTaskSeries serverElement )
   {
      final ContentProviderSyncOperation.Builder operations = ContentProviderSyncOperation.newUpdate();
      
      // Sync tasks
      {
         final ContentProviderSyncableList< InSyncRtmTask > syncTasksList = new ContentProviderSyncableList< InSyncRtmTask >( getInSyncTasks(),
                                                                                                                              InSyncRtmTask.LESS_ID );
         final List< IContentProviderSyncOperation > taskOperations = SyncDiffer.inDiff( serverElement.getInSyncTasks(),
                                                                                         syncTasksList,
                                                                                         false /* never full sync */);
         operations.add( taskOperations );
      }
      
      // Sync notes
      {
         final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( taskSeries.getNotes()
                                                                                                                                    .getNotes(),
                                                                                                                          RtmTaskNote.LESS_ID );
         final List< IContentProviderSyncOperation > noteOperations = SyncDiffer.inDiff( serverElement.taskSeries.getNotes()
                                                                                                                 .getNotes(),
                                                                                         syncNotesList,
                                                                                         true /* always full sync */);
         operations.add( noteOperations );
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
         
         final String joinedServerTags = serverElement.taskSeries.getTagsJoined();
         
         if ( SyncUtils.hasChanged( joinedServerTags,
                                    taskSeries.getTagsJoined() ) )
            operations.add( ContentProviderOperation.newUpdate( contentUri )
                                                    .withValue( TaskSeries.TAGS,
                                                                joinedServerTags )
                                                    .build() );
      }
      
      return operations.build();
   }
}
