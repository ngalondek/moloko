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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.domain.model.ITasksList;
import dev.drsoran.moloko.domain.model.PhysicalList;
import dev.drsoran.moloko.domain.model.RtmSmartFilter;
import dev.drsoran.moloko.domain.model.SmartList;
import dev.drsoran.moloko.domain.model.TasksList;
import dev.drsoran.moloko.domain.services.ContentException;


class DbTasksListsContentRepositoryPart
{
   private final RtmListsQuery listsQuery;
   
   
   
   public DbTasksListsContentRepositoryPart( RtmDatabase database )
   {
      this.listsQuery = database.getQuery( RtmListsQuery.class );
   }
   
   
   
   public ITasksList getById( long id ) throws NoSuchElementException,
                                       ContentException
   {
      final Iterator< ITasksList > listsIterator = getListsFromDb( RtmListsColumns._ID
         + "=" + id ).iterator();
      
      if ( listsIterator.hasNext() )
      {
         return listsIterator.next();
      }
      else
      {
         throw new NoSuchElementException( "No TasksList with ID '" + id + "'" );
      }
   }
   
   
   
   public Iterable< ITasksList > getAll( String selection ) throws ContentException
   {
      return getListsFromDb( selection );
   }
   
   
   
   private Iterable< ITasksList > getListsFromDb( String selection ) throws ContentException
   {
      final Collection< ITasksList > tasksLists = new ArrayList< ITasksList >();
      
      Cursor c = null;
      
      try
      {
         c = listsQuery.getAllLists( selection );
         
         while ( c.moveToNext() )
         {
            final TasksList tasksList = createTasksListFromCursor( c );
            tasksLists.add( tasksList );
         }
      }
      catch ( Throwable e )
      {
         throw new ContentException( e );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return tasksLists;
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
}
