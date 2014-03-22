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

package dev.drsoran.moloko.domain.content;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

import android.content.ContentResolver;
import android.text.TextUtils;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Due;
import dev.drsoran.moloko.domain.model.Task;
import dev.drsoran.moloko.util.MolokoDateUtils;
import dev.drsoran.rtm.RtmCalendar;
import dev.drsoran.rtm.content.Compare;
import dev.drsoran.rtm.parsing.IRtmCalendarProvider;


public class TaskContentEditHandler extends AbstractContentEditHandler< Task >
{
   private final IRtmCalendarProvider calendarProvider;
   
   
   
   public TaskContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier,
      IRtmCalendarProvider calendarProvider )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
      this.calendarProvider = calendarProvider;
   }
   
   
   
   @Override
   protected Collection< Modification > collectUpdateModifications( Task existingTask,
                                                                    Task updatedTask )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String entityUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                          existingTask.getId() )
                                          .toString();
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.LIST_ID,
                                   existingTask.getListId(),
                                   updatedTask.getListId() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TaskColumns.TASK_CREATED_DATE,
                                                existingTask.getCreatedMillisUtc(),
                                                updatedTask.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TaskColumns.TASK_MODIFIED_DATE,
                                                existingTask.getModifiedMillisUtc(),
                                                updatedTask.getModifiedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TaskColumns.DELETED_DATE,
                                                existingTask.getDeletedMillisUtc(),
                                                updatedTask.getDeletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.TASK_NAME,
                                   existingTask.getName(),
                                   updatedTask.getName() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.SOURCE,
                                   existingTask.getSource(),
                                   updatedTask.getSource() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.URL,
                                   existingTask.getUrl(),
                                   updatedTask.getUrl() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.TAGS,
                                   TextUtils.join( TaskColumns.TAGS_SEPARATOR,
                                                   existingTask.getTags() ),
                                   TextUtils.join( TaskColumns.TAGS_SEPARATOR,
                                                   updatedTask.getTags() ) );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.LOCATION_ID,
                                   existingTask.getLocationId(),
                                   updatedTask.getLocationId() );
      
      String existingTaskRecurrence = null;
      int existingTaskIsEveryRecurrence = 0;
      if ( existingTask.getRecurrence() != null )
      {
         existingTaskRecurrence = existingTask.getRecurrence().getPattern();
         existingTaskIsEveryRecurrence = existingTask.getRecurrence()
                                                     .isEveryRecurrence() ? 1
                                                                         : 0;
      }
      
      String updateTaskRecurrence = null;
      int updateTaskIsEveryRecurrence = 0;
      if ( updatedTask.getRecurrence() != null )
      {
         updateTaskRecurrence = updatedTask.getRecurrence().getPattern();
         updateTaskIsEveryRecurrence = updatedTask.getRecurrence()
                                                  .isEveryRecurrence() ? 1 : 0;
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.RECURRENCE,
                                   existingTaskRecurrence,
                                   updateTaskRecurrence );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.RECURRENCE_EVERY,
                                   existingTaskIsEveryRecurrence,
                                   updateTaskIsEveryRecurrence );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TaskColumns.ADDED_DATE,
                                                existingTask.getAddedMillisUtc(),
                                                updatedTask.getAddedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.COMPLETED_DATE,
                                   existingTask.getCompletedMillisUtc(),
                                   updatedTask.getCompletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.PRIORITY,
                                   existingTask.getPriority().toString(),
                                   updatedTask.getPriority().toString() );
      
      // If we have a postponed change, this will also change the due date. So
      // the due date will not be tested on change then.
      if ( Compare.isDifferent( existingTask.getPostponedCount(),
                                updatedTask.getPostponedCount() ) )
      {
         addPostponedChangedModifications( modifications,
                                           entityUri,
                                           existingTask,
                                           updatedTask );
      }
      else
      {
         Long existingTaskDue = null;
         int existingTaskHasDueTime = 0;
         if ( existingTask.getDue() != null )
         {
            existingTaskDue = existingTask.getDue().getMillisUtc();
            existingTaskHasDueTime = existingTask.getDue().hasDueTime() ? 1 : 0;
         }
         
         Long updateTaskDue = null;
         int updateTaskHasDueTime = 0;
         if ( updatedTask.getDue() != null )
         {
            updateTaskDue = updatedTask.getDue().getMillisUtc();
            updateTaskHasDueTime = updatedTask.getDue().hasDueTime() ? 1 : 0;
         }
         
         Modification.addIfDifferent( modifications,
                                      entityUri,
                                      TaskColumns.DUE_DATE,
                                      existingTaskDue,
                                      updateTaskDue );
         
         Modification.addIfDifferent( modifications,
                                      entityUri,
                                      TaskColumns.HAS_DUE_TIME,
                                      existingTaskHasDueTime,
                                      updateTaskHasDueTime );
      }
      
      String existingTaskEstimation = null;
      long existingTaskEstimationMillis = -1L;
      if ( existingTask.getEstimation() != null )
      {
         existingTaskEstimation = existingTask.getEstimation().getSentence();
         existingTaskEstimationMillis = existingTask.getEstimation()
                                                    .getMillis();
      }
      
      String updateTaskEstimation = null;
      long updateTaskEstimationMillis = -1L;
      if ( updatedTask.getEstimation() != null )
      {
         updateTaskEstimation = updatedTask.getEstimation().getSentence();
         updateTaskEstimationMillis = updatedTask.getEstimation().getMillis();
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.ESTIMATE,
                                   existingTaskEstimation,
                                   updateTaskEstimation );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.ESTIMATE_MILLIS,
                                   existingTaskEstimationMillis,
                                   updateTaskEstimationMillis );
      
      return modifications;
   }
   
   
   
   @Override
   protected Collection< Modification > collectDeleteModifications( long elementId )
   {
      final String entityUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                          elementId )
                                          .toString();
      
      final Modification modification = Modification.newNonPersistentModification( entityUri,
                                                                                   TaskColumns.DELETED_DATE,
                                                                                   calendarProvider.getNowMillisUtc() );
      
      return Collections.singletonList( modification );
   }
   
   
   
   private void addPostponedChangedModifications( Collection< Modification > modifications,
                                                  String entityUri,
                                                  Task existingTask,
                                                  Task updatedTask )
   {
      final RtmCalendar cal = calendarProvider.getToday();
      final Due due = updatedTask.getDue();
      
      // If the task has a due date...
      if ( due != null )
      {
         // ...and is overdue, its due date is set to today.
         if ( MolokoDateUtils.isDaysBefore( due.getMillisUtc(),
                                            cal.getTimeInMillis() ) )
         {
            final RtmCalendar calDue = MolokoDateUtils.newCalendar( due.getMillisUtc() );
            
            // Preserve the original time when setting to today
            cal.set( Calendar.HOUR_OF_DAY, calDue.get( Calendar.HOUR_OF_DAY ) );
            cal.set( Calendar.MINUTE, calDue.get( Calendar.MINUTE ) );
            cal.set( Calendar.SECOND, calDue.get( Calendar.SECOND ) );
            cal.set( Calendar.MILLISECOND, 0 );
         }
         
         // Otherwise, the task due date is advanced a day.
         else
         {
            cal.setTimeInMillis( due.getMillisUtc() );
            cal.add( Calendar.DAY_OF_YEAR, 1 );
            cal.setHasTime( due.hasDueTime() );
         }
      }
      
      modifications.add( Modification.newModification( entityUri,
                                                       TaskColumns.POSTPONED,
                                                       updatedTask.getPostponedCount(),
                                                       existingTask.getPostponedCount() ) );
      
      // We add the due date modification non-persistent because the sync to RTM will
      // set this date again due to the postponing.
      modifications.add( Modification.newNonPersistentModification( entityUri,
                                                                    TaskColumns.DUE_DATE,
                                                                    cal.getTimeInMillis() ) );
   }
}
