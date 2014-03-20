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
   
   private final AbstractTable[] tables;
   
   
   
   protected AbstractDatabase( Context context, String databaseName,
      int databaseVersion )
   {
      this.databaseName = databaseName;
      this.databaseVersion = databaseVersion;
      this.dbAccess = new DatabaseOpenHelper( context );
      this.tables = createTables();
   }
   
   
   
   public String getDatabaseName()
   {
      return databaseName;
   }
   
   
   
   public int getDatabaseVersion()
   {
      return databaseVersion;
   }
   
   
   
   public void open()
   {
      dbAccess.getReadableDatabase();
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
         table.clear( getWritable() );
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
      
      table.clear( null );
   }
   
   
   
   public void beginTransaction()
   {
      getWritable().beginTransaction();
   }
   
   
   
   public void setTransactionSuccessful()
   {
      getWritable().setTransactionSuccessful();
   }
   
   
   
   public void endTransaction()
   {
      getWritable().endTransaction();
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
      public void onCreate( SQLiteDatabase database )
      {
         createTables( database );
         createTriggers( database );
      }
      
      
      
      @Override
      public void onOpen( SQLiteDatabase database )
      {
         for ( AbstractTable table : tables )
         {
            table.init( database );
         }
      }
      
      
      
      @Override
      public void onUpgrade( SQLiteDatabase database,
                             int oldVersion,
                             int newVersion )
      {
         for ( AbstractTable table : tables )
         {
            table.upgrade( database, oldVersion, newVersion );
         }
      }
      
      
      
      private void createTables( SQLiteDatabase database )
      {
         for ( AbstractTable table : tables )
         {
            table.create( database );
            table.createIndices( database );
            table.insertInitialRows( database );
         }
      }
      
      
      
      private void createTriggers( SQLiteDatabase database )
      {
         for ( AbstractTrigger trigger : AbstractDatabase.this.createTriggers() )
         {
            trigger.create( database );
         }
      }
   }
   
   
   
   protected abstract AbstractTable[] createTables();
   
   
   
   protected abstract AbstractTrigger[] createTriggers();
}
