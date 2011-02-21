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

import java.util.Date;
import java.util.List;

import android.content.ContentProviderOperation;

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;
import com.mdt.rtm.data.RtmTask.Priority;

import dev.drsoran.moloko.content.ModificationList;
import dev.drsoran.moloko.content.ParticipantsProviderPart;
import dev.drsoran.moloko.content.RtmNotesProviderPart;
import dev.drsoran.moloko.content.RtmTaskSeriesProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.content.TagsProviderPart;
import dev.drsoran.moloko.service.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.service.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.service.sync.operation.TypedDirectedSyncOperations;
import dev.drsoran.moloko.service.sync.syncable.ITwoWaySyncable;
import dev.drsoran.provider.Rtm.Notes;
import dev.drsoran.provider.Rtm.Tags;
import dev.drsoran.provider.Rtm.TaskSeries;


public class SyncTask implements ITwoWaySyncable< SyncTask >
{
   private final RtmTaskSeries taskSeries;
   
   private final RtmTask task;
   
   

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
      return task.getDeleted();
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
   


   public List< String > getTags()
   {
      return taskSeries.getTagStrings();
   }
   


   public RtmTaskNotes getNotes()
   {
      return taskSeries.getNotes();
   }
   


   public ParticipantList getParticipants()
   {
      return taskSeries.getParticipants();
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
      
      // Insert new RtmTask
      {
         operation.add( RtmTasksProviderPart.insertTask( task ) );
      }
      
      // Check for tags
      {
         final List< Tag > tags = taskSeries.getTags();
         
         for ( Tag tag : tags )
         {
            operation.add( ContentProviderOperation.newInsert( Tags.CONTENT_URI )
                                                   .withValues( TagsProviderPart.getContentValues( tag,
                                                                                                   true ) )
                                                   .build() );
         }
      }
      
      // Check for notes
      {
         final List< RtmTaskNote > notesList = taskSeries.getNotes().getNotes();
         
         for ( final RtmTaskNote rtmTaskNote : notesList )
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
   


   public IServerSyncOperation< SyncTask > computeServerInsertOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   public IContentProviderSyncOperation computeContentProviderUpdateOperation( SyncTask serverElement )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IServerSyncOperation< SyncTask > computeServerUpdateOperation( RtmTimeline timeLine,
                                                                         ModificationList modifictaions )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IContentProviderSyncOperation computeContentProviderDeleteOperation()
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IServerSyncOperation< SyncTask > computeServerDeleteOperation( RtmTimeline timeLine )
   {
      return NoopServerSyncOperation.INSTANCE;
   }
   


   public TypedDirectedSyncOperations< SyncTask > computeMergeOperations( RtmTimeline timeLine,
                                                                          ModificationList modifications,
                                                                          SyncTask updateElement,
                                                                          MergeDirection mergeDirection )
   {
      // TODO Auto-generated method stub
      return null;
   }
   


   public IContentProviderSyncOperation computeRemoveModificationsOperation( ModificationList modifications )
   {
      // TODO Auto-generated method stub
      return null;
   }
}
