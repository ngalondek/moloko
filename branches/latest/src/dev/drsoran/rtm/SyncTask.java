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

import com.mdt.rtm.data.RtmTask;
import com.mdt.rtm.data.RtmTask.Priority;
import com.mdt.rtm.data.RtmTaskNote;
import com.mdt.rtm.data.RtmTaskNotes;
import com.mdt.rtm.data.RtmTaskSeries;
import com.mdt.rtm.data.RtmTimeline;

import dev.drsoran.moloko.content.Modification;
import dev.drsoran.moloko.content.ModificationSet;
import dev.drsoran.moloko.content.ModificationsProviderPart;
import dev.drsoran.moloko.content.RtmTasksProviderPart;
import dev.drsoran.moloko.sync.lists.ContentProviderSyncableList;
import dev.drsoran.moloko.sync.operation.ContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IContentProviderSyncOperation;
import dev.drsoran.moloko.sync.operation.IServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ISyncOperation.Op;
import dev.drsoran.moloko.sync.operation.NoopServerSyncOperation;
import dev.drsoran.moloko.sync.operation.ServerSyncOperation;
import dev.drsoran.moloko.sync.syncable.IServerSyncable;
import dev.drsoran.moloko.sync.util.SyncDiffer;
import dev.drsoran.moloko.sync.util.SyncProperties;
import dev.drsoran.moloko.sync.util.SyncUtils;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.moloko.util.Queries;
import dev.drsoran.provider.Rtm.RawTasks;
import dev.drsoran.provider.Rtm.TaskSeries;


public class SyncTask implements IContentProviderSyncOperation< SyncTask >,
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
   


   public List< IContentProviderSyncOperation > computeContentProviderUpdateOperations( SyncTask serverElement )
   {
      return syncImpl( serverElement,
                       this,
                       SyncProperties.newLocalOnlyInstance( serverElement.getModifiedDate(),
                                                            this.getModifiedDate(),
                                                            Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                      getTaskSeriesId() ) ),
                       SyncProperties.< RtmTaskSeries > newLocalOnlyInstance( serverElement.getModifiedDate(),
                                                                              this.getModifiedDate(),
                                                                              Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                                        getId() ) ) ).operations.getLocalOperations();
   }
   


   public IServerSyncOperation< RtmTaskSeries > computeServerUpdateOperation( RtmTimeline timeline,
                                                                              ModificationSet modifications,
                                                                              SyncTask serverElement )
   {
      ServerSyncOperation.Builder< SyncTask, RtmTaskSeries > operation = ServerSyncOperation.newUpdate( this );
      
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
                                                  getId() ) );
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
                                                   getName() ) );
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
                                                       getLocationId() ) );
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
                                                  getUrl() ) );
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
                                                       getPriority() ) );
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
   


   public IContentProviderSyncOperation handleServerUpdateResult( RtmTaskSeries resultElement )
   {
      if ( resultElement == null )
         throw new IllegalStateException( "resultElement is null" );
      
      if ( !resultElement.getId().equals( getTaskSeriesId() ) )
         throw new IllegalStateException( "resultElement's ID "
            + resultElement.getId() + " differs to expected ID "
            + getTaskSeriesId() );
      
      final ContentProviderSyncOperation.Builder builder = ContentProviderSyncOperation.newUpdate();
      final List< SyncTask > tasks = fromTaskSeries( resultElement );
      
      for ( SyncTask syncTask : tasks )
         builder.add( computeContentProviderUpdateOperations( syncTask ) );
      
      return builder.build();
   }
   


   public IContentProviderSyncOperation removeModifications( ModificationSet modifications,
                                                             boolean revert )
   {
      final ContentProviderSyncOperation.Builder builder = ContentProviderSyncOperation.newDelete();
      
      if ( revert )
      {
         builder.addAll( modifications.getRevertAllOperations( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                         getId() ) ) );
         builder.addAll( modifications.getRevertAllOperations( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                         getTaskSeriesId() ) ) );
      }
      else
      {
         if ( modifications.hasModification( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                                       getId() ) ) )
         {
            builder.add( ModificationsProviderPart.getRemoveModificationOps( RawTasks.CONTENT_URI,
                                                                             getId() ) );
            modifications.removeAll( Queries.contentUriWithId( RawTasks.CONTENT_URI,
                                                               getId() ) );
         }
         
         if ( modifications.hasModification( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                       getTaskSeriesId() ) ) )
         {
            builder.add( ModificationsProviderPart.getRemoveModificationOps( TaskSeries.CONTENT_URI,
                                                                             getTaskSeriesId() ) );
            modifications.removeAll( Queries.contentUriWithId( TaskSeries.CONTENT_URI,
                                                               getTaskSeriesId() ) );
         }
      }
      
      return builder.build();
   }
   


   private SyncProperties< RtmTaskSeries > syncImpl( SyncTask serverElement,
                                                     SyncTask localElement,
                                                     SyncProperties< RtmTaskSeries > taskSeriesProperties,
                                                     SyncProperties< RtmTaskSeries > taskProperties )
   {
      // Here we need a separate SyncProperties instance for TaskSeries and Task cause we need the
      // different ContentProvider URIs for modification checks.
      
      SyncUtils.doPreSyncCheck( serverElement.getTaskSeriesId(),
                                localElement.getTaskSeriesId(),
                                taskSeriesProperties );
      
      SyncUtils.doPreSyncCheck( serverElement.getId(),
                                localElement.getId(),
                                taskProperties );
      
      // RtmTaskSeries
      if ( SyncUtils.syncValue( taskSeriesProperties,
                                TaskSeries.LIST_ID,
                                serverElement.getListId(),
                                localElement.getListId(),
                                String.class ) == SyncResultDirection.SERVER )
      {
         String oldListId = serverElement.getListId();
         
         // The case we come from computeServerUpdateOperations() we take the synced
         // value.
         if ( serverElement == localElement )
         {
            final Modification modification = taskSeriesProperties.modifications.find( taskSeriesProperties.uri,
                                                                                       TaskSeries.LIST_ID );
            
            if ( modification != null )
               oldListId = modification.getSyncedValue();
         }
         
         taskSeriesProperties.operations.merge( localElement,
                                                taskSeriesProperties.timeline.tasks_moveTo( oldListId,
                                                                                            localElement.getListId(),
                                                                                            getTaskSeriesId(),
                                                                                            getId() ),
                                                Op.UPDATE );
      }
      
      SyncUtils.syncValue( taskSeriesProperties,
                           TaskSeries.TASKSERIES_CREATED_DATE,
                           MolokoDateUtils.getTime( serverElement.getCreatedDate() ),
                           MolokoDateUtils.getTime( localElement.getCreatedDate() ),
                           Long.class );
      
      SyncUtils.syncValue( taskSeriesProperties,
                           TaskSeries.MODIFIED_DATE,
                           MolokoDateUtils.getTime( serverElement.getModifiedDate() ),
                           MolokoDateUtils.getTime( localElement.getModifiedDate() ),
                           Long.class );
      
      if ( SyncUtils.syncValue( taskSeriesProperties,
                                TaskSeries.TASKSERIES_NAME,
                                serverElement.getName(),
                                localElement.getName(),
                                String.class ) == SyncResultDirection.SERVER )
      {
         taskSeriesProperties.operations.merge( localElement,
                                                taskSeriesProperties.timeline.tasks_setName( getListId(),
                                                                                             getTaskSeriesId(),
                                                                                             getId(),
                                                                                             localElement.getName() ),
                                                Op.UPDATE );
      }
      
      SyncUtils.syncValue( taskSeriesProperties,
                           TaskSeries.SOURCE,
                           serverElement.getSource(),
                           localElement.getSource(),
                           String.class );
      
      // syncTasks( properties, serverElement.tasks, localElement.tasks );
      
      syncNotes( taskSeriesProperties,
                 serverElement.getNotes().getNotes(),
                 localElement.getNotes().getNotes() );
      
      if ( SyncUtils.syncValue( taskSeriesProperties,
                                TaskSeries.LOCATION_ID,
                                serverElement.getLocationId(),
                                localElement.getLocationId(),
                                String.class ) == SyncResultDirection.SERVER )
      {
         
         taskSeriesProperties.operations.merge( localElement,
                                                taskSeriesProperties.timeline.tasks_setLocation( getListId(),
                                                                                                 getTaskSeriesId(),
                                                                                                 getId(),
                                                                                                 localElement.getLocationId() ),
                                                Op.UPDATE );
      }
      
      if ( SyncUtils.syncValue( taskSeriesProperties,
                                TaskSeries.URL,
                                serverElement.getUrl(),
                                localElement.getUrl(),
                                String.class ) == SyncResultDirection.SERVER )
      {
         taskSeriesProperties.operations.merge( localElement,
                                                taskSeriesProperties.timeline.tasks_setURL( getListId(),
                                                                                            getTaskSeriesId(),
                                                                                            getId(),
                                                                                            localElement.getUrl() ),
                                                Op.UPDATE );
      }
      
      SyncUtils.syncValue( taskSeriesProperties,
                           TaskSeries.RECURRENCE,
                           serverElement.getRecurrence(),
                           localElement.getRecurrence(),
                           String.class );
      
      SyncUtils.syncValue( taskSeriesProperties,
                           TaskSeries.RECURRENCE_EVERY,
                           serverElement.isEveryRecurrence() ? 1 : 0,
                           localElement.isEveryRecurrence() ? 1 : 0,
                           Integer.class );
      
      syncTags( taskSeriesProperties,
                serverElement.getTags(),
                localElement.getTags() );
      
      syncParticipants( taskSeriesProperties,
                        serverElement.getParticipants(),
                        localElement.getParticipants() );
      
      // RtmTask
      SyncUtils.syncValue( taskProperties,
                           RawTasks.DUE_DATE,
                           MolokoDateUtils.getTime( serverElement.getDueDate() ),
                           MolokoDateUtils.getTime( localElement.getDueDate() ),
                           Long.class );
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.HAS_DUE_TIME,
                           serverElement.hasDueTime(),
                           localElement.hasDueTime(),
                           Integer.class );
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.ADDED_DATE,
                           MolokoDateUtils.getTime( serverElement.getAddedDate() ),
                           MolokoDateUtils.getTime( localElement.getAddedDate() ),
                           Long.class );
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.COMPLETED_DATE,
                           MolokoDateUtils.getTime( serverElement.getCompletedDate() ),
                           MolokoDateUtils.getTime( localElement.getCompletedDate() ),
                           Long.class );
      
      if ( SyncUtils.syncValue( taskProperties,
                                RawTasks.PRIORITY,
                                RtmTask.convertPriority( serverElement.getPriority() ),
                                RtmTask.convertPriority( localElement.getPriority() ),
                                String.class ) == SyncResultDirection.SERVER )
      {
         taskProperties.operations.merge( localElement,
                                          taskProperties.timeline.tasks_setPriority( getListId(),
                                                                                     getTaskSeriesId(),
                                                                                     getId(),
                                                                                     localElement.getPriority() ),
                                          Op.UPDATE );
      }
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.POSTPONED,
                           serverElement.getPosponed(),
                           localElement.getPosponed(),
                           Integer.class );
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.ESTIMATE,
                           serverElement.getEstimate(),
                           localElement.getEstimate(),
                           String.class );
      
      SyncUtils.syncValue( taskProperties,
                           RawTasks.ESTIMATE_MILLIS,
                           serverElement.getEstimateMillis(),
                           localElement.getEstimateMillis(),
                           Long.class );
      
      // Merge all collected operations into one cause it is logically the same element.
      taskSeriesProperties.operations.addAllLocalOps( taskProperties.operations.getLocalOperations() );
      taskSeriesProperties.operations.mergeAllServerOps( localElement,
                                                         taskProperties.operations.getServerOperations() );
      
      return taskSeriesProperties;
   }
   


   private static void syncNotes( SyncProperties properties,
                                  List< RtmTaskNote > serverValues,
                                  List< RtmTaskNote > localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            final ContentProviderSyncableList< RtmTaskNote > syncNotesList = new ContentProviderSyncableList< RtmTaskNote >( localValues,
                                                                                                                             RtmTaskNote.LESS_ID );
            final List< IContentProviderSyncOperation > noteOperations = SyncDiffer.diff( serverValues,
                                                                                          syncNotesList );
            properties.operations.addAllLocalOps( noteOperations );
         }
            break;
         default :
            break;
      }
   }
   


   private static void syncTags( SyncProperties properties,
                                 List< Tag > serverValues,
                                 List< Tag > localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            final ContentProviderSyncableList< Tag > syncList = new ContentProviderSyncableList< Tag >( localValues );
            final List< IContentProviderSyncOperation > syncOperations = SyncDiffer.diff( serverValues,
                                                                                          syncList );
            properties.operations.addAllLocalOps( syncOperations );
         }
            break;
         default :
            break;
      }
   }
   


   private static void syncParticipants( SyncProperties properties,
                                         ParticipantList serverValues,
                                         ParticipantList localValues )
   {
      switch ( properties.syncDirection )
      {
         case LOCAL_ONLY:
         {
            properties.operations.addAllLocalOps( localValues.computeContentProviderUpdateOperations( serverValues ) );
         }
            break;
         default :
            break;
      }
   }
}
