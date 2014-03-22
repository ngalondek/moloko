/* 
 *	Copyright (c) 2014 Ronny Röhricht
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

package dev.drsoran.rtm.sync;

import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.COMPLETED_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.DUE_DATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.ESTIMATE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.HAS_DUE_TIME;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.LIST_ID;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.LOCATION_ID;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.NAME;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.POSTPONED;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.PRIORITY;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.RECURRENCE;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.TAGS;
import static dev.drsoran.rtm.content.ContentProperties.RtmTaskProperties.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dev.drsoran.rtm.RtmResponse;
import dev.drsoran.rtm.RtmServiceException;
import dev.drsoran.rtm.RtmTransaction;
import dev.drsoran.rtm.Strings;
import dev.drsoran.rtm.model.RtmConstants;
import dev.drsoran.rtm.model.RtmNote;
import dev.drsoran.rtm.model.RtmTask;
import dev.drsoran.rtm.service.IRtmContentEditService;
import dev.drsoran.rtm.service.IRtmContentRepository;
import dev.drsoran.rtm.service.RtmErrorCodes;


public class RtmTasksSyncHandler implements IRtmSyncHandler
{
   private final Comparator< RtmTask > comparator;
   
   private final IRtmSyncPartner syncPartner;
   
   
   
   public RtmTasksSyncHandler( IRtmSyncPartner syncPartner,
      Comparator< RtmTask > comparator )
   {
      if ( syncPartner == null )
      {
         throw new IllegalArgumentException( "syncPartner" );
      }
      if ( comparator == null )
      {
         throw new IllegalArgumentException( "comparator" );
      }
      
      this.syncPartner = syncPartner;
      this.comparator = comparator;
   }
   
   
   
   @Override
   public RtmSyncResult handleIncomingSync( IRtmContentRepository contentRepository,
                                            long lastInSyncMillisUtc )
   {
      if ( contentRepository == null )
      {
         throw new IllegalArgumentException( "contentRepository" );
      }
      
      final List< RtmTask > rtmTasks;
      try
      {
         rtmTasks = contentRepository.tasks_getList( lastInSyncMillisUtc );
      }
      catch ( RtmServiceException e )
      {
         return RtmSyncResult.newFailed( e );
      }
      
      final List< RtmTask > syncPartnerTasks = syncPartner.getTasks( lastInSyncMillisUtc );
      
      sortLists( syncPartnerTasks, rtmTasks );
      
      for ( RtmTask rtmTask : rtmTasks )
      {
         final int posPartnerList = Collections.binarySearch( syncPartnerTasks,
                                                              rtmTask,
                                                              comparator );
         RtmTask syncPartnerTask = null;
         if ( posPartnerList > -1 )
         {
            syncPartnerTask = syncPartnerTasks.get( posPartnerList );
         }
         
         // Check if the RTM element is not deleted
         if ( rtmTask.getDeletedMillisUtc() == RtmConstants.NO_TIME )
         {
            // INSERT: The RTM element is not contained in the partner list.
            if ( syncPartnerTask == null )
            {
               syncPartner.insertTask( rtmTask );
            }
            
            // UPDATE: The RTM element is contained in the partner list.
            else
            {
               syncPartner.updateTask( syncPartnerTask, rtmTask );
            }
         }
         else
         {
            // DELETE: The RTM element is contained in the partner list and is deleted at RTM side.
            if ( syncPartnerTask != null )
            {
               syncPartner.deleteTask( syncPartnerTask );
            }
         }
      }
      
      return RtmSyncResult.newSucceeded();
   }
   
   
   
   @Override
   public RtmSyncResult handleOutgoingSync( IRtmContentEditService contentEditService,
                                            String timelineId,
                                            long lastOutSyncMillis )
   {
      if ( contentEditService == null )
      {
         throw new IllegalArgumentException( "contentEditService" );
      }
      if ( timelineId == null )
      {
         throw new IllegalArgumentException( "timelineId" );
      }
      
      final List< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      try
      {
         final List< RtmTask > syncPartnerElements = syncPartner.getTasks( lastOutSyncMillis );
         
         for ( int i = 0, count = syncPartnerElements.size(); i < count; i++ )
         {
            final RtmTask syncPartnerTask = syncPartnerElements.get( i );
            
            // Check if the partner element is not deleted
            if ( syncPartnerTask.getDeletedMillisUtc() == RtmConstants.NO_TIME )
            {
               // INSERT: The partner element is new.
               if ( syncPartnerTask.getId() == RtmConstants.NO_ID )
               {
                  final Collection< RtmTransaction > sendNewTaskTransactions = sendNewTaskToRtm( contentEditService,
                                                                                                 timelineId,
                                                                                                 syncPartnerTask );
                  transactions.addAll( sendNewTaskTransactions );
               }
               
               // UPDATE: The partner element is known at RTM side.
               else
               {
                  final Collection< RtmTransaction > sendUpdateTransactions = sendTaskUpdates( contentEditService,
                                                                                               timelineId,
                                                                                               lastOutSyncMillis,
                                                                                               syncPartnerTask );
                  transactions.addAll( sendUpdateTransactions );
               }
            }
            
            // DELETE: The partner element is deleted and not new.
            else if ( syncPartnerTask.getId() != RtmConstants.NO_ID )
            {
               final Collection< RtmTransaction > deleteTransactions = deleteTask( contentEditService,
                                                                                   timelineId,
                                                                                   syncPartnerTask );
               transactions.addAll( deleteTransactions );
            }
         }
         
         return RtmSyncResult.newSucceeded( transactions );
      }
      catch ( Exception e )
      {
         return RtmSyncResult.newFailed( e, transactions );
      }
   }
   
   
   
   private Collection< RtmTransaction > sendNewTaskToRtm( IRtmContentEditService contentEditService,
                                                          String timelineId,
                                                          RtmTask syncPartnerTask ) throws RtmServiceException
   {
      final RtmResponse< List< RtmTask >> rtmResponse = contentEditService.tasks_add( timelineId,
                                                                                      syncPartnerTask.getListId(),
                                                                                      syncPartnerTask.getName() );
      
      final Map< String, RtmTask > rtmTasksFromResponse = new LinkedHashMap< String, RtmTask >();
      final List< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      storeRtmTasksAndTransaction( rtmResponse,
                                   rtmTasksFromResponse,
                                   transactions );
      
      for ( RtmTask rtmTask : rtmResponse.getElement() )
      {
         final TaskId taskId = new TaskId( rtmTask.getListId(),
                                           rtmTask.getTaskSeriesId(),
                                           rtmTask.getId() );
         
         final SendTaskModificationsReturn result = sendTaskModifications( contentEditService,
                                                                           timelineId,
                                                                           taskId,
                                                                           syncPartnerTask );
         rtmTasksFromResponse.putAll( result.rtmTasks );
         transactions.addAll( result.transactions );
         
         for ( RtmNote syncPartnerTaskNote : syncPartnerTask.getNotes() )
         {
            final RtmResponse< RtmNote > noteResponse = contentEditService.tasks_notes_add( timelineId,
                                                                                            taskId.listId,
                                                                                            taskId.taskSeriesId,
                                                                                            taskId.id,
                                                                                            syncPartnerTaskNote.getTitle(),
                                                                                            syncPartnerTaskNote.getText() );
            addIfUndoable( transactions, noteResponse );
            rtmTask.addNote( noteResponse.getElement() );
         }
      }
      
      boolean isFirst = true;
      for ( RtmTask rtmTask : rtmTasksFromResponse.values() )
      {
         if ( isFirst )
         {
            syncPartner.updateTask( syncPartnerTask, rtmTask );
            isFirst = false;
         }
         else
         {
            syncPartner.insertTask( rtmTask );
         }
      }
      
      return transactions;
   }
   
   
   
   private Collection< RtmTransaction > sendTaskUpdates( IRtmContentEditService contentEditService,
                                                         String timelineId,
                                                         long lastOutSyncMillis,
                                                         RtmTask syncPartnerTask ) throws RtmServiceException
   {
      final Map< String, RtmTask > rtmTasksFromResponse = new LinkedHashMap< String, RtmTask >();
      final List< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      final Collection< RtmTransaction > noteTransactions = sendNoteModifications( contentEditService,
                                                                                   timelineId,
                                                                                   syncPartnerTask,
                                                                                   lastOutSyncMillis );
      transactions.addAll( noteTransactions );
      
      try
      {
         final SendTaskModificationsReturn modificationsResult = sendTaskModifications( contentEditService,
                                                                                        timelineId,
                                                                                        new TaskId( syncPartnerTask.getListId(),
                                                                                                    syncPartnerTask.getTaskSeriesId(),
                                                                                                    syncPartnerTask.getId() ),
                                                                                        syncPartnerTask );
         transactions.addAll( modificationsResult.transactions );
         rtmTasksFromResponse.putAll( modificationsResult.rtmTasks );
         
         updateSyncPartner( syncPartnerTask, rtmTasksFromResponse );
      }
      catch ( RtmServiceException e )
      {
         // We may get an invalid task ID error from RTM if the task has been deleted
         // on RTM side by another partner.
         if ( e.getResponseCode() != RtmErrorCodes.TASK_INVALID_ID )
         {
            throw e;
         }
      }
      
      return transactions;
   }
   
   
   
   private Collection< RtmTransaction > deleteTask( IRtmContentEditService contentEditService,
                                                    String timelineId,
                                                    RtmTask syncPartnerTask ) throws RtmServiceException
   {
      final Map< String, RtmTask > rtmTasksFromResponse = new LinkedHashMap< String, RtmTask >();
      final List< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      try
      {
         storeRtmTasksAndTransaction( contentEditService.tasks_delete( timelineId,
                                                                       syncPartnerTask.getListId(),
                                                                       syncPartnerTask.getTaskSeriesId(),
                                                                       syncPartnerTask.getId() ),
                                      rtmTasksFromResponse,
                                      transactions );
         
         updateSyncPartner( syncPartnerTask, rtmTasksFromResponse );
      }
      catch ( RtmServiceException e )
      {
         // We may get an invalid task ID error from RTM if the task has been deleted
         // on RTM side by another partner.
         if ( e.getResponseCode() != RtmErrorCodes.TASK_INVALID_ID )
         {
            throw e;
         }
      }
      
      return transactions;
   }
   
   
   
   private SendTaskModificationsReturn sendTaskModifications( IRtmContentEditService contentEditService,
                                                              String timelineId,
                                                              TaskId taskIdentifier,
                                                              RtmTask syncPartnerTask ) throws RtmServiceException
   {
      final Map< String, RtmTask > rtmTasks = new HashMap< String, RtmTask >();
      final Collection< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      final Iterable< IModification > modifications = syncPartner.getModificationsOfTask( syncPartnerTask );
      
      for ( IModification modification : modifications )
      {
         final String property = modification.getPropertyName();
         final String listId = taskIdentifier.listId;
         final String taskSeriesId = taskIdentifier.taskSeriesId;
         final String taskId = taskIdentifier.id;
         
         if ( NAME.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setName( timelineId,
                                                                           listId,
                                                                           taskSeriesId,
                                                                           taskId,
                                                                           syncPartnerTask.getName() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( RECURRENCE.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setRecurrence( timelineId,
                                                                                 listId,
                                                                                 taskSeriesId,
                                                                                 taskId,
                                                                                 syncPartnerTask.getRecurrenceSentence() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( TAGS.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setTags( timelineId,
                                                                           listId,
                                                                           taskSeriesId,
                                                                           taskId,
                                                                           syncPartnerTask.getTagsJoined() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( LOCATION_ID.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setLocation( timelineId,
                                                                               listId,
                                                                               taskSeriesId,
                                                                               taskId,
                                                                               syncPartnerTask.getLocationId() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( URL.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setURL( timelineId,
                                                                          listId,
                                                                          taskSeriesId,
                                                                          taskId,
                                                                          syncPartnerTask.getUrl() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( PRIORITY.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setPriority( timelineId,
                                                                               listId,
                                                                               taskSeriesId,
                                                                               taskId,
                                                                               syncPartnerTask.getPriority() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( COMPLETED_DATE.equalsIgnoreCase( property ) )
         {
            if ( syncPartnerTask.getCompletedMillisUtc() != RtmConstants.NO_TIME )
            {
               storeRtmTasksAndTransaction( contentEditService.tasks_complete( timelineId,
                                                                               listId,
                                                                               taskSeriesId,
                                                                               taskId ),
                                            rtmTasks,
                                            transactions );
            }
            else
            {
               storeRtmTasksAndTransaction( contentEditService.tasks_uncomplete( timelineId,
                                                                                 listId,
                                                                                 taskSeriesId,
                                                                                 taskId ),
                                            rtmTasks,
                                            transactions );
            }
         }
         else if ( DUE_DATE.equalsIgnoreCase( property )
            || HAS_DUE_TIME.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setDueDate( timelineId,
                                                                              listId,
                                                                              taskSeriesId,
                                                                              taskId,
                                                                              syncPartnerTask.getDueMillisUtc(),
                                                                              syncPartnerTask.hasDueTime() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( ESTIMATE.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_setEstimate( timelineId,
                                                                               listId,
                                                                               taskSeriesId,
                                                                               taskId,
                                                                               syncPartnerTask.getEstimationSentence() ),
                                         rtmTasks,
                                         transactions );
         }
         else if ( POSTPONED.equalsIgnoreCase( property ) )
         {
            final int lastSyncPostponedCount = Strings.convertTo( modification.getSyncedValue(),
                                                                  int.class );
            int numPostponed = syncPartnerTask.getPostponedCount()
               - lastSyncPostponedCount;
            
            while ( numPostponed-- > 0 )
            {
               storeRtmTasksAndTransaction( contentEditService.tasks_postpone( timelineId,
                                                                               listId,
                                                                               taskSeriesId,
                                                                               taskId ),
                                            rtmTasks,
                                            transactions );
            }
         }
         else if ( LIST_ID.equalsIgnoreCase( property ) )
         {
            storeRtmTasksAndTransaction( contentEditService.tasks_moveTo( timelineId,
                                                                          modification.getSyncedValue(),
                                                                          listId,
                                                                          taskSeriesId,
                                                                          taskId ),
                                         rtmTasks,
                                         transactions );
         }
      }
      
      return new SendTaskModificationsReturn( transactions, rtmTasks );
   }
   
   
   
   private Collection< RtmTransaction > sendNoteModifications( IRtmContentEditService contentEditService,
                                                               String timelineId,
                                                               RtmTask syncPartnerTask,
                                                               long lastSyncOutMs ) throws RtmServiceException
   {
      final String listId = syncPartnerTask.getListId();
      final String taskSeriesId = syncPartnerTask.getTaskSeriesId();
      final String taskId = syncPartnerTask.getId();
      final Collection< RtmTransaction > transactions = new ArrayList< RtmTransaction >();
      
      for ( RtmNote syncPartnerTaskNote : syncPartnerTask.getNotes() )
      {
         // INSERT: The partner element is new.
         if ( syncPartnerTaskNote.getId() == RtmConstants.NO_ID )
         {
            final RtmResponse< RtmNote > rtmResponse = contentEditService.tasks_notes_add( timelineId,
                                                                                           listId,
                                                                                           taskSeriesId,
                                                                                           taskId,
                                                                                           syncPartnerTaskNote.getTitle(),
                                                                                           syncPartnerTaskNote.getText() );
            addIfUndoable( transactions, rtmResponse );
         }
         
         // UPDATE: The partner element is known at RTM side.
         else
         {
            try
            {
               final RtmResponse< RtmNote > rtmResponse = contentEditService.tasks_notes_edit( timelineId,
                                                                                               syncPartnerTaskNote.getId(),
                                                                                               syncPartnerTaskNote.getTitle(),
                                                                                               syncPartnerTaskNote.getText() );
               addIfUndoable( transactions, rtmResponse );
            }
            catch ( RtmServiceException e )
            {
               // We may get an invalid note ID error from RTM if the note has been deleted
               // on RTM side by another partner.
               if ( e.getResponseCode() != RtmErrorCodes.NOTE_INVALID_ID )
               {
                  throw e;
               }
            }
         }
      }
      
      // DELETE: If the partner task is not new
      try
      {
         for ( RtmNote deletedNote : syncPartner.getDeletedNotes( syncPartnerTask,
                                                                  lastSyncOutMs ) )
         {
            final RtmResponse< Void > rtmResponse = contentEditService.tasks_notes_delete( timelineId,
                                                                                           deletedNote.getId() );
            addIfUndoable( transactions, rtmResponse );
         }
      }
      catch ( RtmServiceException e )
      {
         // We may get an invalid note ID error from RTM if the note has been deleted
         // on RTM side by another partner.
         if ( e.getResponseCode() != RtmErrorCodes.NOTE_INVALID_ID )
         {
            throw e;
         }
      }
      
      return transactions;
   }
   
   
   
   private void storeRtmTasksAndTransaction( RtmResponse< List< RtmTask > > response,
                                             Map< String, RtmTask > rtmTasks,
                                             Collection< RtmTransaction > transactions )
   {
      storeRtmTasks( rtmTasks, response.getElement() );
      addIfUndoable( transactions, response );
   }
   
   
   
   private void storeRtmTasks( Map< String, RtmTask > rtmTasksMap,
                               Iterable< RtmTask > rtmTasksToStore )
   {
      for ( RtmTask rtmTaskToStore : rtmTasksToStore )
      {
         rtmTasksMap.put( rtmTaskToStore.getId(), rtmTaskToStore );
      }
   }
   
   
   
   private void updateSyncPartner( RtmTask syncPartnerTask,
                                   final Map< String, RtmTask > rtmTasksFromResponse )
   {
      for ( RtmTask rtmTask : rtmTasksFromResponse.values() )
      {
         if ( rtmTask.getId().equals( syncPartnerTask.getId() ) )
         {
            syncPartner.updateTask( syncPartnerTask, rtmTask );
         }
         else
         {
            syncPartner.insertTask( rtmTask );
         }
      }
   }
   
   
   
   private void addIfUndoable( Collection< RtmTransaction > transactions,
                               RtmResponse< ? > rtmResponse )
   {
      if ( rtmResponse.isUndoable() )
      {
         transactions.add( rtmResponse.getTransaction() );
      }
   }
   
   
   
   private void sortLists( List< RtmTask > syncPartnerElements,
                           List< RtmTask > rtmElements )
   {
      Collections.sort( syncPartnerElements, comparator );
      Collections.sort( rtmElements, comparator );
   }
   
   
   private final static class TaskId
   {
      public final String taskSeriesId;
      
      public final String id;
      
      public final String listId;
      
      
      
      public TaskId( String listId, String taskSeriesId, String id )
      {
         this.taskSeriesId = taskSeriesId;
         this.id = id;
         this.listId = listId;
      }
   }
   
   
   private final static class SendTaskModificationsReturn
   {
      public final Collection< RtmTransaction > transactions;
      
      public final Map< String, RtmTask > rtmTasks;
      
      
      
      public SendTaskModificationsReturn(
         Collection< RtmTransaction > transactions,
         Map< String, RtmTask > rtmTasks )
      {
         this.transactions = transactions;
         this.rtmTasks = rtmTasks;
      }
   }
}
