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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import dev.drsoran.moloko.ILog;


public class RtmDatabase
{
   private final static String DATABASE_NAME = "rtm.db";
   
   private final static int DATABASE_VERSION = 1;
   
   private final ILog log;
   
   private final DatabaseOpenHelper dbAccess;
   
   private AbstractTable[] tables;
   
   private AbstractTrigger[] triggers;
   
   
   
   public RtmDatabase( Context context, ILog log )
   {
      this( context, log, DATABASE_NAME );
   }
   
   
   
   public RtmDatabase( Context context, ILog log, String databaseName )
   {
      this.log = log;
      this.dbAccess = new DatabaseOpenHelper( context,
                                              databaseName,
                                              RtmDatabase.DATABASE_VERSION );
   }
   
   
   
   public SQLiteDatabase getWritable()
   {
      return dbAccess.getWritableDatabase();
   }
   
   
   
   public SQLiteDatabase getReadable()
   {
      return dbAccess.getReadableDatabase();
   }
   
   
   
   public void close()
   {
      dbAccess.close();
   }
   
   
   
   public ILog Log()
   {
      return log;
   }
   
   
   
   public ITable getTable( String tableName )
   {
      for ( ITable table : getTables() )
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
      if ( tables == null )
      {
         tables = new AbstractTable[]
         { new RtmTasksListsTable( this ), new CreationsTable( this ),
          new ModificationsTable( this ), new RtmRawTasksTable( this ),
          new RtmTaskSeriesTable( this ), new RtmNotesTable( this ),
          new RtmContactsTable( this ), new RtmParticipantsTable( this ),
          new RtmLocationsTable( this ), new RtmSettingsTable( this ),
          new SyncTable( this ) };
      }
      
      return tables;
   }
   
   
   
   private AbstractTrigger[] getTriggers()
   {
      if ( triggers == null )
      {
         triggers = new AbstractTrigger[]
         {
          new DefaultListSettingConsistencyTrigger( this ),
          new DeleteRawTaskTrigger( this ),
          new DeleteTaskSeriesTrigger( this ),
          new DeleteContactTrigger( this ),
          new DeleteModificationsTrigger( this, RtmTasksListsTable.TABLE_NAME ),
          new DeleteModificationsTrigger( this, RtmRawTasksTable.TABLE_NAME ),
          new DeleteModificationsTrigger( this, RtmTaskSeriesTable.TABLE_NAME ),
          new DeleteModificationsTrigger( this, RtmNotesTable.TABLE_NAME ) };
      }
      
      return triggers;
   }
   
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper
   {
      public DatabaseOpenHelper( Context context, String databaseName,
         int version )
      {
         super( context, databaseName, null, version );
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
         for ( AbstractTable table : getTables() )
         {
            table.upgrade( oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables()
      {
         for ( AbstractTable table : getTables() )
         {
            table.create();
            table.createIndices();
         }
      }
      
      
      
      private void createTriggers()
      {
         for ( AbstractTrigger trigger : getTriggers() )
         {
            trigger.create();
         }
      }
   }
}
