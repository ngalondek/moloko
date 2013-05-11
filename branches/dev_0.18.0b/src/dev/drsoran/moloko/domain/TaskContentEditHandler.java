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

package dev.drsoran.moloko.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.content.ContentResolver;
import android.net.Uri;
import android.text.TextUtils;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.Modification;
import dev.drsoran.moloko.domain.model.Task;


class TaskContentEditHandler extends AbstractContentEditHandler< Task >
{
   public TaskContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
   }
   
   
   
   @Override
   protected Collection< Modification > collectUpdateModifications( Task existingTask,
                                                                    Task updatedTask )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final Uri entityUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                       existingTask.getId() );
      
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
      Boolean existingTaskIsEveryRecurrence = null;
      if ( existingTask.getRecurrence() != null )
      {
         existingTaskRecurrence = existingTask.getRecurrence().getPattern();
         existingTaskIsEveryRecurrence = existingTask.getRecurrence()
                                                     .isEveryRecurrence();
      }
      
      String updateTaskRecurrence = null;
      Boolean updateTaskIsEveryRecurrence = null;
      if ( updatedTask.getRecurrence() != null )
      {
         updateTaskRecurrence = updatedTask.getRecurrence().getPattern();
         updateTaskIsEveryRecurrence = updatedTask.getRecurrence()
                                                  .isEveryRecurrence();
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
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TaskColumns.POSTPONED,
                                   existingTask.getPostponedCount(),
                                   updatedTask.getPostponedCount() );
      
      Long existingTaskDue = null;
      Boolean existingTaskHasDueTime = null;
      if ( existingTask.getDue() != null )
      {
         existingTaskDue = existingTask.getDue().getMillisUtc();
         existingTaskHasDueTime = existingTask.getDue().hasDueTime();
      }
      
      Long updateTaskDue = null;
      Boolean updateTaskHasDueTime = null;
      if ( updatedTask.getDue() != null )
      {
         updateTaskDue = updatedTask.getDue().getMillisUtc();
         updateTaskHasDueTime = updatedTask.getDue().hasDueTime();
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
      
      String existingTaskEstimation = null;
      Long existingTaskEstimationMillis = null;
      if ( existingTask.getEstimation() != null )
      {
         existingTaskEstimation = existingTask.getEstimation().getSentence();
         existingTaskEstimationMillis = existingTask.getEstimation()
                                                    .getMillisUtc();
      }
      
      String updateTaskEstimation = null;
      Long updateTaskEstimationMillis = null;
      if ( updatedTask.getEstimation() != null )
      {
         updateTaskEstimation = updatedTask.getEstimation().getSentence();
         updateTaskEstimationMillis = updatedTask.getEstimation()
                                                 .getMillisUtc();
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
      final Uri entityUri = ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                       elementId );
      
      final Modification modification = Modification.newModification( entityUri,
                                                                      TaskColumns.DELETED_DATE,
                                                                      System.currentTimeMillis() );
      
      return Collections.singletonList( modification );
   }
}
