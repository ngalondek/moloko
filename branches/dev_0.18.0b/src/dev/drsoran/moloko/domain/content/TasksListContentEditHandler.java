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
import java.util.Collection;
import java.util.Collections;

import android.content.ContentResolver;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.sync.Modification;


public class TasksListContentEditHandler extends
         AbstractContentEditHandler< TasksList >
{
   public TasksListContentEditHandler( ContentResolver contentResolver,
      IContentValuesFactory contentValuesFactory,
      IModificationsApplier modificationsApplier )
   {
      super( contentResolver, contentValuesFactory, modificationsApplier );
   }
   
   
   
   @Override
   protected Collection< Modification > collectUpdateModifications( TasksList existingTasksList,
                                                                    TasksList updatedTasksList )
   {
      final Collection< Modification > modifications = new ArrayList< Modification >();
      
      final String entityUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                          existingTasksList.getId() )
                                          .toString();
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.LIST_CREATED_DATE,
                                                existingTasksList.getCreatedMillisUtc(),
                                                updatedTasksList.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.LIST_MODIFIED_DATE,
                                                existingTasksList.getModifiedMillisUtc(),
                                                updatedTasksList.getModifiedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.LIST_DELETED_DATE,
                                                existingTasksList.getDeletedMillisUtc(),
                                                updatedTasksList.getDeletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TasksListColumns.LIST_NAME,
                                   existingTasksList.getName(),
                                   updatedTasksList.getName() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.POSITION,
                                                existingTasksList.getPosition(),
                                                updatedTasksList.getPosition() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.ARCHIVED,
                                                existingTasksList.isArchived()
                                                                              ? 1
                                                                              : 0,
                                                updatedTasksList.isArchived()
                                                                             ? 1
                                                                             : 0 );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.LOCKED,
                                                existingTasksList.isLocked()
                                                                            ? 1
                                                                            : 0,
                                                updatedTasksList.isLocked() ? 1
                                                                           : 0 );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                TasksListColumns.IS_SMART_LIST,
                                                existingTasksList.isSmartList()
                                                                               ? 1
                                                                               : 0,
                                                updatedTasksList.isSmartList()
                                                                              ? 1
                                                                              : 0 );
      
      String existingFilterString = null;
      if ( existingTasksList.getSmartFilter() != null )
      {
         existingFilterString = existingTasksList.getSmartFilter()
                                                 .getFilterString();
      }
      
      String updateFilterString = null;
      if ( updatedTasksList.getSmartFilter() != null )
      {
         updateFilterString = updatedTasksList.getSmartFilter()
                                              .getFilterString();
      }
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   TasksListColumns.FILTER,
                                   existingFilterString,
                                   updateFilterString );
      
      return modifications;
   }
   
   
   
   @Override
   protected Collection< Modification > collectDeleteModifications( long elementId )
   {
      final String entityUri = ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                          elementId )
                                          .toString();
      
      final Modification modification = Modification.newNonPersistentModification( entityUri,
                                                                                   TasksListColumns.LIST_DELETED_DATE,
                                                                                   System.currentTimeMillis() );
      
      return Collections.singletonList( modification );
   }
}
