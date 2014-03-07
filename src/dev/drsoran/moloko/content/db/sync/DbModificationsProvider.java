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

package dev.drsoran.moloko.content.db.sync;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import dev.drsoran.db.ITable;
import dev.drsoran.moloko.content.Columns.TaskColumns;
import dev.drsoran.moloko.content.Columns.TasksListColumns;
import dev.drsoran.moloko.content.ContentUris;
import dev.drsoran.moloko.content.db.RtmDatabase;
import dev.drsoran.moloko.content.db.TableColumns.ModificationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;
import dev.drsoran.moloko.content.db.TableNames;
import dev.drsoran.rtm.content.ContentProperties;
import dev.drsoran.rtm.sync.IModification;


public class DbModificationsProvider implements IModificationsProvider
{
   private final Map< String, String > propertiesMap = new HashMap< String, String >();
   
   private final RtmDatabase database;
   
   private final ITable modificationsTable;
   
   private final ITable tasksTable;
   
   private final ITable tasksListsTable;
   
   
   
   public DbModificationsProvider( RtmDatabase database )
   {
      this.database = database;
      this.modificationsTable = database.getTable( TableNames.MODIFICATIONS_TABLE );
      this.tasksTable = database.getTable( TableNames.RTM_RAW_TASKS_TABLE );
      this.tasksListsTable = database.getTable( TableNames.RTM_TASKS_LIST_TABLE );
      
      fillPropertiesMap();
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfRtmTask( String rtmTaskId )
   {
      long taskId = getTaskIdFromRtmTaskId( rtmTaskId );
      return getModifications( ContentUris.bindElementId( ContentUris.TASKS_CONTENT_URI_ID,
                                                          taskId )
                                          .toString() );
   }
   
   
   
   @Override
   public List< IModification > getModificationsOfRtmTasksList( String rtmTasksListId )
   {
      long tasksListId = getTasksListIdFromRtmTasksListId( rtmTasksListId );
      return getModifications( ContentUris.bindElementId( ContentUris.TASKS_LISTS_CONTENT_URI_ID,
                                                          tasksListId )
                                          .toString() );
   }
   
   
   
   @Override
   public void clearModifications()
   {
      modificationsTable.clear( database.getWritable() );
   }
   
   
   
   private List< IModification > getModifications( String entityUri )
   {
      Cursor c = null;
      try
      {
         c = modificationsTable.query( ModificationColumns.PROJECTION,
                                       ModificationColumns.ENTITY_URI + "=?",
                                       new String[]
                                       { entityUri },
                                       null );
         
         final List< IModification > modifications = new ArrayList< IModification >( c.getCount() );
         while ( c.moveToNext() )
         {
            final String rtmProperty = mapToRtmProperty( c.getString( ModificationColumns.PROPERTY_IDX ) );
            modifications.add( new SyncModification( rtmProperty,
                                                     c.getString( ModificationColumns.NEW_VALUE_IDX ),
                                                     c.getString( ModificationColumns.SYNCED_VALUE_IDX ) ) );
         }
         
         return modifications;
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private long getTaskIdFromRtmTaskId( String rtmTaskId )
   {
      Cursor c = null;
      try
      {
         c = tasksTable.query( new String[]
                               { RtmRawTaskColumns._ID },
                               RtmRawTaskColumns.RTM_RAWTASK_ID + "=?",
                               new String[]
                               { rtmTaskId },
                               null );
         
         if ( !c.moveToNext() )
         {
            throw new SQLiteException( MessageFormat.format( "Unable to query task ID for RTM task ID {0}",
                                                             rtmTaskId ) );
         }
         
         return c.getLong( 0 );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private long getTasksListIdFromRtmTasksListId( String rtmTasksListId )
   {
      Cursor c = null;
      try
      {
         c = tasksListsTable.query( new String[]
                                    { RtmTasksListColumns._ID },
                                    RtmTasksListColumns.RTM_LIST_ID + "=?",
                                    new String[]
                                    { rtmTasksListId },
                                    null );
         
         if ( !c.moveToNext() )
         {
            throw new SQLiteException( MessageFormat.format( "Unable to query taskslist ID for RTM taskslist ID {0}",
                                                             rtmTasksListId ) );
         }
         
         return c.getLong( 0 );
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
   }
   
   
   
   private String mapToRtmProperty( String key )
   {
      String value = propertiesMap.get( key );
      return value != null ? value : key;
   }
   
   
   
   private void fillPropertiesMap()
   {
      propertiesMap.put( TasksListColumns.LIST_NAME,
                         ContentProperties.RtmTasksListProperties.NAME );
      propertiesMap.put( TasksListColumns.LIST_DELETED_DATE,
                         ContentProperties.RtmTasksListProperties.DELETED );
      
      propertiesMap.put( TaskColumns.TASK_CREATED_DATE,
                         ContentProperties.RtmTaskProperties.CREATED_DATE );
      propertiesMap.put( TaskColumns.TASK_MODIFIED_DATE,
                         ContentProperties.RtmTaskProperties.MODIFIED_DATE );
      propertiesMap.put( TaskColumns.TASK_NAME,
                         ContentProperties.RtmTaskProperties.NAME );
      propertiesMap.put( TaskColumns.RECURRENCE_EVERY,
                         ContentProperties.RtmTaskProperties.RECURRENCE_EVERY );
   }
}
