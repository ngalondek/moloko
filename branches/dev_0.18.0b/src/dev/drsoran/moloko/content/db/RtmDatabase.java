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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.SystemContext;


public class RtmDatabase
{
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private final ILog log;
   
   private final DatabaseOpenHelper dbAccess;
   
   private final AbstractTable[] tables;
   
   private final Trigger[] triggers;
   
   private final Map< Class< ? >, Object > queries;
   
   
   
   public RtmDatabase( SystemContext context )
   {
      log = context.Log();
      dbAccess = new DatabaseOpenHelper( context );
      tables = getTables();
      triggers = getTriggers();
      queries = getQueries();
   }
   
   
   
   public SQLiteDatabase getWritable()
   {
      return dbAccess.getWritableDatabase();
   }
   
   
   
   public SQLiteDatabase getReadable()
   {
      return dbAccess.getReadableDatabase();
   }
   
   
   
   public ILog Log()
   {
      return log;
   }
   
   
   
   public < T > T getQuery( Class< T > queryType )
   {
      @SuppressWarnings( "unchecked" )
      final T query = (T) queries.get( queryType );
      return query;
   }
   
   
   
   public ITable getTable( String tableName )
   {
      for ( ITable table : tables )
      {
         if ( table.getTableName().equals( tableName ) )
         {
            return table;
         }
      }
      
      return null;
   }
   
   
   
   private AbstractTable[] getTables()
   {
      return new AbstractTable[]
      { new RtmListsTable( this ), new CreationsTable( this ),
       new ModificationsTable( this ), new RawTasksTable( this ),
       new RtmTaskSeriesTable( this ), new RtmNotesTable( this ),
       new RtmContactsTable( this ), new ParticipantsTable( this ),
       new RtmLocationsTable( this ), new RtmSettingsTable( this ),
       new SyncTable( this ) };
   }
   
   
   
   private Trigger[] getTriggers()
   {
      return new Trigger[]
      { new RtmListIdUpdateTrigger( this ),
       new DefaultListSettingConsistencyTrigger( this ),
       new DeleteRawTaskTrigger( this ), new UpdateRawTaskTrigger( this ),
       new DeleteTaskSeriesTrigger( this ),
       new UpdateTaskSeriesTrigger( this ), new DeleteContactTrigger( this ),
       new DeleteModificationsTrigger( this, RtmListsTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RawTasksTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RtmTaskSeriesTable.TABLE_NAME ),
       new DeleteModificationsTrigger( this, RtmNotesTable.TABLE_NAME ) };
   }
   
   
   
   private Map< Class< ? >, Object > getQueries()
   {
      final Map< Class< ? >, Object > queries = new HashMap< Class< ? >, Object >();
      
      queries.put( RtmListsQuery.class,
                   new RtmListsQuery( this,
                                      (RtmListsTable) getTable( RtmListsTable.TABLE_NAME ) ) );
      queries.put( CreationsQuery.class,
                   new CreationsQuery( this,
                                       (CreationsTable) getTable( CreationsTable.TABLE_NAME ) ) );
      queries.put( ModificationsQuery.class,
                   new ModificationsQuery( this,
                                           (ModificationsTable) getTable( ModificationsTable.TABLE_NAME ) ) );
      queries.put( RawTasksQuery.class,
                   new RawTasksQuery( this,
                                      (RawTasksTable) getTable( RawTasksTable.TABLE_NAME ) ) );
      queries.put( RtmTaskSeriesQuery.class,
                   new RtmTaskSeriesQuery( this,
                                           (RtmTaskSeriesTable) getTable( RtmTaskSeriesTable.TABLE_NAME ) ) );
      queries.put( RtmNotesQuery.class,
                   new RtmNotesQuery( this,
                                      (RtmNotesTable) getTable( RtmNotesTable.TABLE_NAME ) ) );
      queries.put( RtmSettingsQuery.class,
                   new RtmSettingsQuery( this,
                                         (RtmSettingsTable) getTable( RtmSettingsTable.TABLE_NAME ) ) );
      queries.put( RtmContactsQuery.class,
                   new RtmContactsQuery( this,
                                         (RtmContactsTable) getTable( RtmContactsTable.TABLE_NAME ) ) );
      queries.put( ParticipantsQuery.class,
                   new ParticipantsQuery( this,
                                          (ParticipantsTable) getTable( ParticipantsTable.TABLE_NAME ) ) );
      queries.put( RtmLocationsQuery.class,
                   new RtmLocationsQuery( this,
                                          (RtmLocationsTable) getTable( RtmLocationsTable.TABLE_NAME ) ) );
      queries.put( SyncQuery.class,
                   new SyncQuery( this,
                                  (SyncTable) getTable( SyncTable.TABLE_NAME ) ) );
      
      return queries;
   }
   
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper
   {
      public DatabaseOpenHelper( Context context )
      {
         super( context,
                RtmDatabase.DATABASE_NAME,
                null,
                RtmDatabase.DATABASE_VERSION );
      }
      
      
      
      @Override
      public void onCreate( SQLiteDatabase db )
      {
         createTables();
         createTriggers();
      }
      
      
      
      @Override
      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         for ( AbstractTable table : tables )
         {
            table.upgrade( oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables()
      {
         for ( AbstractTable table : tables )
         {
            table.create();
            table.createIndices();
         }
      }
      
      
      
      private void createTriggers()
      {
         for ( Trigger trigger : triggers )
         {
            trigger.create();
         }
      }
   }
}
