/* 
 *	Copyright (c) 2013 Ronny R�hricht
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
 * Ronny R�hricht - implementation
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
   
   private final DatabaseAccess dbAccess;
   
   private final Table[] tables;
   
   private final Trigger[] triggers;
   
   private final Map< Class< ? >, Object > queries;
   
   
   
   public RtmDatabase( SystemContext context )
   {
      log = context.Log();
      dbAccess = new DatabaseAccess( context );
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
   
   
   
   private Table[] getTables()
   {
      return new Table[]
      { new RtmListsTable( this ), new CreationsTable( this ),
       new ModificationsTable( this ) };
   }
   
   
   
   private Trigger[] getTriggers()
   {
      return new Trigger[]
      { new RtmListIdUpdateTrigger( this ),
       new DeleteModificationsTrigger( this, RtmListsTable.TABLE_NAME ),
       new DefaultListSettingConsistencyTrigger( this ) };
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
      
      return queries;
   }
   
   
   
   private Table getTable( String tableName )
   {
      for ( Table table : tables )
      {
         if ( table.getTableName().equalsIgnoreCase( tableName ) )
         {
            return table;
         }
      }
      
      return null;
   }
   
   
   private class DatabaseAccess extends SQLiteOpenHelper
   {
      public DatabaseAccess( Context context )
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
         for ( Table table : tables )
         {
            table.upgrade( oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables()
      {
         for ( Table table : tables )
         {
            table.create();
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
