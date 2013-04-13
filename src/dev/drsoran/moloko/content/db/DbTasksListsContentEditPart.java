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

package dev.drsoran.moloko.content.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import android.content.ContentValues;
import android.net.Uri;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.domain.model.Constants;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;


class DbTasksListsContentEditPart
{
   private final RtmDatabase database;
   
   private final IContentRepository contentRepository;
   
   private final ITable tasksListTable;
   
   private final DbModificationsEditPart modificationsEditPart;
   
   
   
   public DbTasksListsContentEditPart( RtmDatabase database,
      IContentRepository contentRepository,
      DbModificationsEditPart modificationsEditPart )
   {
      this.database = database;
      this.contentRepository = contentRepository;
      this.tasksListTable = database.getTable( RtmListsTable.TABLE_NAME );
      this.modificationsEditPart = modificationsEditPart;
   }
   
   
   
   public void insertTasksList( final ITasksList tasksList ) throws ContentException
   {
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            try
            {
               final ContentValues tasksListContentValues = createTasksListContentValues( tasksList );
               tasksListTable.insert( tasksListContentValues );
            }
            catch ( Throwable e )
            {
               throw new ContentException( "Failed to insert new TasksList", e );
            }
         }
      } );
   }
   
   
   
   public void updateTasksList( final long tasksListId,
                                final ITasksList updatedTasksList ) throws NoSuchElementException,
                                                                   ContentException
   {
      final ITasksList existingTasksList = contentRepository.getTasksList( tasksListId );
      
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            final Collection< Modification > modifications = new ArrayList< Modification >();
            
            collectTasksListModifications( modifications,
                                           existingTasksList,
                                           updatedTasksList );
            
            if ( modifications.size() > 0 )
            {
               modificationsEditPart.applyModificationsInTransaction( modifications );
            }
         }
      } );
   }
   
   
   
   public void deleteTasksList( final long tasksListId ) throws NoSuchElementException,
                                                        ContentException
   {
      DbUtils.doTransactional( database.getWritable(), new Runnable()
      {
         @Override
         public void run()
         {
            int numDeleted = 0;
            try
            {
               numDeleted = tasksListTable.delete( tasksListId, null, null );
            }
            catch ( Throwable e )
            {
               throw new ContentException( "Failed to delete TasksList "
                  + tasksListId, e );
            }
            
            if ( numDeleted < 1 )
            {
               throw new NoSuchElementException( String.valueOf( tasksListId ) );
            }
         }
      } );
   }
   
   
   
   private static ContentValues createTasksListContentValues( ITasksList tasksList )
   {
      final ContentValues values = new ContentValues();
      
      values.put( RtmListsColumns.LIST_CREATED_DATE,
                  tasksList.getCreatedMillisUtc() );
      values.put( RtmListsColumns.LIST_MODIFIED_DATE,
                  tasksList.getModifiedMillisUtc() );
      values.put( RtmListsColumns.LIST_NAME, tasksList.getName() );
      values.put( RtmListsColumns.LOCKED, tasksList.isLocked() ? 1 : 0 );
      values.put( RtmListsColumns.ARCHIVED, tasksList.isArchived() ? 1 : 0 );
      values.put( RtmListsColumns.POSITION, tasksList.getPosition() );
      
      if ( tasksList.getDeletedMillisUtc() != Constants.NO_TIME )
      {
         values.put( RtmListsColumns.LIST_DELETED_DATE,
                     tasksList.getDeletedMillisUtc() );
      }
      else
      {
         values.putNull( RtmListsColumns.LIST_DELETED_DATE );
      }
      
      if ( tasksList.isSmartList() )
      {
         final RtmSmartFilter filter = tasksList.getSmartFilter();
         values.put( RtmListsColumns.IS_SMART_LIST, 1 );
         values.put( RtmListsColumns.FILTER, filter.getFilterString() );
      }
      else
      {
         values.put( RtmListsColumns.IS_SMART_LIST, 0 );
         values.putNull( RtmListsColumns.FILTER );
      }
      
      return values;
   }
   
   
   
   private static void collectTasksListModifications( Collection< Modification > modifications,
                                                      ITasksList existingTasksList,
                                                      ITasksList updatedTasksList )
   {
      final Uri entityUri = DbUtils.entityUriWithId( RtmListsTable.TABLE_NAME,
                                                     existingTasksList.getId() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.LIST_CREATED_DATE,
                                                existingTasksList.getCreatedMillisUtc(),
                                                updatedTasksList.getCreatedMillisUtc() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.LIST_MODIFIED_DATE,
                                                existingTasksList.getModifiedMillisUtc(),
                                                updatedTasksList.getModifiedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmListsColumns.LIST_DELETED_DATE,
                                   existingTasksList.getDeletedMillisUtc(),
                                   updatedTasksList.getDeletedMillisUtc() );
      
      Modification.addIfDifferent( modifications,
                                   entityUri,
                                   RtmListsColumns.LIST_NAME,
                                   existingTasksList.getName(),
                                   updatedTasksList.getName() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.POSITION,
                                                existingTasksList.getPosition(),
                                                updatedTasksList.getPosition() );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.ARCHIVED,
                                                existingTasksList.isArchived()
                                                                              ? 1
                                                                              : 0,
                                                updatedTasksList.isArchived()
                                                                             ? 1
                                                                             : 0 );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.LOCKED,
                                                existingTasksList.isLocked()
                                                                            ? 1
                                                                            : 0,
                                                updatedTasksList.isLocked() ? 1
                                                                           : 0 );
      
      Modification.addIfDifferentNonPersistent( modifications,
                                                entityUri,
                                                RtmListsColumns.IS_SMART_LIST,
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
                                   RtmListsColumns.FILTER,
                                   existingFilterString,
                                   updateFilterString );
   }
}
