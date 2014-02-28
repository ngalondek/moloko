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

package dev.drsoran.db;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public abstract class AbstractDatabase
{
   private final DatabaseOpenHelper dbAccess;
   
   private final String databaseName;
   
   private final int databaseVersion;
   
   private AbstractTable[] tables;
   
   
   
   protected AbstractDatabase( Context context, String databaseName,
      int databaseVersion )
   {
      this.databaseName = databaseName;
      this.databaseVersion = databaseVersion;
      this.dbAccess = new DatabaseOpenHelper( context );
   }
   
   
   
   public String getDatabaseName()
   {
      return databaseName;
   }
   
   
   
   public int getDatabaseVersion()
   {
      return databaseVersion;
   }
   
   
   
   public SQLiteDatabase getWritable()
   {
      return dbAccess.getWritableDatabase();
   }
   
   
   
   public SQLiteDatabase getReadable()
   {
      return dbAccess.getReadableDatabase();
   }
   
   
   
   public void clearAllTables()
   {
      for ( ITable table : tables )
      {
         table.clear();
      }
   }
   
   
   
   public void clearTable( String tableName ) throws NoSuchElementException
   {
      final ITable table = getTable( tableName );
      if ( table == null )
      {
         throw new NoSuchElementException( MessageFormat.format( "No table with name ''{0}'' in database ''{1}''",
                                                                 tableName,
                                                                 databaseName ) );
      }
      
      table.clear();
   }
   
   
   
   public void close()
   {
      dbAccess.close();
   }
   
   
   
   public List< ? extends ITable > getAllTables()
   {
      return Arrays.asList( tables );
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
   
   
   private class DatabaseOpenHelper extends SQLiteOpenHelper
   {
      public DatabaseOpenHelper( Context context )
      {
         super( context, databaseName, null, databaseVersion );
      }
      
      
      
      @Override
      public void onCreate( SQLiteDatabase db )
      {
         createTables( db );
         createTriggers( db );
      }
      
      
      
      @Override
      public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
      {
         for ( AbstractTable table : tables )
         {
            table.upgrade( oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables( SQLiteDatabase database )
      {
         tables = AbstractDatabase.this.createTables( database );
         
         for ( AbstractTable table : tables )
         {
            table.create();
            table.createIndices();
            table.insertInitialRows();
         }
      }
      
      
      
      private void createTriggers( SQLiteDatabase database )
      {
         final AbstractTrigger[] triggers = AbstractDatabase.this.createTriggers( database );
         
         for ( AbstractTrigger trigger : triggers )
         {
            trigger.create();
         }
      }
   }
   
   
   
   protected abstract AbstractTable[] createTables( SQLiteDatabase database );
   
   
   
   protected abstract AbstractTrigger[] createTriggers( SQLiteDatabase database );
}
