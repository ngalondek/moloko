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

package dev.drsoran.moloko.content.db;

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.PhysicalList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.SmartList;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;
import dev.drsoran.moloko.domain.services.IContentRepository;
import dev.drsoran.moloko.domain.services.TasksListContentOptions;


class DbTasksListsContentRepositoryPart
{
   private final IContentRepository contentRepository;
   
   private final RtmListsQuery listsQuery;
   
   
   
   public DbTasksListsContentRepositoryPart( RtmDatabase database,
      IContentRepository contentRepository )
   {
      this.contentRepository = contentRepository;
      this.listsQuery = database.getQuery( RtmListsQuery.class );
   }
   
   
   
   public ITasksList getById( long id, int options ) throws ContentException
   {
      TasksList tasksList;
      Cursor c = null;
      
      try
      {
         try
         {
            c = listsQuery.getList( id );
         }
         catch ( Throwable e )
         {
            throw new ContentException( e );
         }
         
         if ( c.moveToNext() )
         {
            tasksList = createTasksListFromCursor( c );
            
            if ( ( options & TasksListContentOptions.WITH_TASKSCOUNT ) != 0 )
            {
               addTasksCountToTasksList( tasksList );
            }
         }
         else
         {
            throw new ContentException( "No TasksList with ID '" + id + "'" );
         }
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return tasksList;
   }
   
   
   
   public Iterable< ITasksList > getAll( int options ) throws ContentException
   {
      return getTasksFromDb( selection, options );
   }
   
   
   
   private static TasksList createTasksListFromCursor( Cursor c )
   {
      final long id = c.getLong( Columns.ID_IDX );
      final long createdMillis = c.getLong( RtmListsColumns.LIST_CREATED_DATE_IDX );
      final boolean isLocked = c.getInt( RtmListsColumns.LOCKED_IDX ) != 0;
      final boolean isArchved = c.getInt( RtmListsColumns.ARCHIVED_IDX ) != 0;
      final boolean isSmartList = c.getInt( RtmListsColumns.IS_SMART_LIST_IDX ) != 0;
      
      final TasksList tasksList;
      
      if ( isSmartList )
      {
         final RtmSmartFilter smartFilter = new RtmSmartFilter( c.getString( RtmListsColumns.FILTER_IDX ) );
         
         tasksList = new SmartList( id,
                                    createdMillis,
                                    isLocked,
                                    isArchved,
                                    smartFilter );
      }
      else
      {
         tasksList = new PhysicalList( id, createdMillis, isLocked, isArchved );
      }
      
      return tasksList;
   }
   
   
   
   private void addTasksCountToTasksList( TasksList tasksList ) throws ContentException
   {
      final int tasksCount = contentRepository.getNumberOfTasksInTasksList( tasksList );
      tasksList.setTasksCount( tasksCount );
   }
}
