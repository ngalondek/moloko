/* 
 *	Copyright (c) 2013 Ronny Röhricht
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

package dev.drsoran.moloko.app.taskedit;

import java.util.List;

import android.text.TextUtils;


import dev.drsoran.Strings;
import dev.drsoran.db.DbUtils;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.sync.model.Modification;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.model.old.RtmTask;


class TaskChangesCollector
{
   private ModificationSet createModificationSet( List< Task > tasks )
   {
      final ModificationSet modifications = new ModificationSet();
      
      for ( Task task : tasks )
      {
         boolean anyChanges = false;
         
         // Task name
         if ( hasChange( TaskColumns.TASKSERIES_NAME ) )
         {
            final String taskName = getCurrentValue( TaskColumns.TASKSERIES_NAME,
                                                     String.class );
            
            if ( SyncUtils.isDifferent( task.getName(), taskName ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TASKSERIES_NAME,
                                                                taskName ) );
               anyChanges = true;
            }
         }
         
         // List
         if ( hasChange( TaskColumns.LIST_ID ) )
         {
            final String selectedListId = getCurrentValue( TaskColumns.LIST_ID,
                                                           String.class );
            
            if ( SyncUtils.isDifferent( task.getListId(), selectedListId ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LIST_ID,
                                                                selectedListId ) );
               anyChanges = true;
            }
         }
         
         // Priority
         if ( hasChange( TaskColumns.PRIORITY ) )
         {
            final String selectedPriority = getCurrentValue( TaskColumns.PRIORITY,
                                                             String.class );
            
            if ( SyncUtils.isDifferent( RtmTask.convertPriority( task.getPriority() ),
                                        selectedPriority ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTaskColumns.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTaskColumns.PRIORITY,
                                                                selectedPriority ) );
               anyChanges = true;
            }
         }
         
         // Tags
         if ( hasChange( TaskColumns.TAGS ) )
         {
            final String tags = getCurrentValue( TaskColumns.TAGS, String.class );
            
            if ( SyncUtils.isDifferent( tags,
                                        TextUtils.join( Tags.TAGS_SEPARATOR,
                                                        task.getTags() ) ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.TAGS,
                                                                tags ) );
               anyChanges = true;
            }
         }
         
         // Due
         if ( hasChange( TaskColumns.DUE_DATE ) )
         {
            Long newDue = getCurrentValue( TaskColumns.DUE_DATE, Long.class );
            
            if ( newDue == -1 )
               newDue = null;
            
            if ( SyncUtils.isDifferent( MolokoDateUtils.getTime( task.getDue() ),
                                        newDue ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.DUE_DATE,
                                                                TaskColumns.newDue ) );
               anyChanges = true;
            }
         }
         
         if ( hasChange( TaskColumns.HAS_DUE_TIME ) )
         {
            final boolean newHasDueTime = getCurrentValue( TaskColumns.HAS_DUE_TIME,
                                                           Boolean.class );
            
            if ( SyncUtils.isDifferent( task.hasDueTime(), newHasDueTime ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.HAS_DUE_TIME,
                                                                newHasDueTime
                                                                             ? 1
                                                                             : 0 ) );
               anyChanges = true;
            }
         }
         
         // Recurrence
         if ( hasChange( TaskColumns.RECURRENCE )
            || hasChange( TaskColumns.RECURRENCE_EVERY ) )
         {
            final String recurrence = getCurrentValue( TaskColumns.RECURRENCE,
                                                       String.class );
            
            if ( SyncUtils.isDifferent( task.getRecurrence(), recurrence ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.RECURRENCE,
                                                                recurrence ) );
               anyChanges = true;
            }
            
            final boolean isEveryRecurrence = getCurrentValue( TaskColumns.RECURRENCE_EVERY,
                                                               Boolean.class );
            
            if ( SyncUtils.isDifferent( task.isEveryRecurrence(),
                                        isEveryRecurrence ) )
            {
               // The flag RECURRENCE_EVERY will not be synced out. RTM parses only the recurrence sentence.
               modifications.add( Modification.newNonPersistentModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                                       task.getTaskSeriesId() ),
                                                                             TaskSeries.RECURRENCE_EVERY,
                                                                             isEveryRecurrence ) );
               anyChanges = true;
            }
         }
         
         // Estimate
         if ( hasChange( TaskColumns.ESTIMATE_MILLIS ) )
         {
            final long estimateMillis = getCurrentValue( TaskColumns.ESTIMATE_MILLIS,
                                                         Long.class );
            
            if ( SyncUtils.isDifferent( task.getEstimateMillis(),
                                        estimateMillis ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE,
                                                                getCurrentValue( RawTasks.ESTIMATE,
                                                                                 String.class ) ) );
               
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( RawTasks.CONTENT_URI,
                                                                                          task.getId() ),
                                                                RawTasks.ESTIMATE_MILLIS,
                                                                estimateMillis ) );
               anyChanges = true;
            }
         }
         
         // Location
         if ( hasChange( TaskColumns.LOCATION_ID ) )
         {
            final String selectedLocation = getCurrentValue( TaskColumns.LOCATION_ID,
                                                             String.class );
            
            if ( SyncUtils.isDifferent( task.getLocationId(), selectedLocation ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.LOCATION_ID,
                                                                selectedLocation ) );
               anyChanges = true;
            }
         }
         
         // URL
         if ( hasChange( TaskColumns.URL ) )
         {
            final String newUrl = Strings.nullIfEmpty( getCurrentValue( TaskColumns.URL,
                                                                        String.class ) );
            
            if ( SyncUtils.isDifferent( task.getUrl(), newUrl ) )
            {
               modifications.add( Modification.newModification( DbUtils.contentUriWithId( TaskSeries.CONTENT_URI,
                                                                                          task.getTaskSeriesId() ),
                                                                TaskSeries.URL,
                                                                newUrl ) );
               anyChanges = true;
            }
         }
         
         // set the taskseries modification time to now
         if ( anyChanges )
            modifications.add( Modification.newTaskModified( task.getTaskSeriesId() ) );
      }
      
      return modifications;
   }
}
