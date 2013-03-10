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

import java.util.Map;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.moloko.ILog;


abstract class Table
{
   private final static String ITEM_ID_EQUALS = BaseColumns._ID + "=";
   
   private final ILog log;
   
   private final RtmDatabase database;
   
   private final String tableName;
   
   
   
   protected Table( RtmDatabase database, String tableName )
   {
      this.log = database.Log();
      this.database = database;
      this.tableName = tableName;
   }
   
   
   
   public final String getTableName()
   {
      return tableName;
   }
   
   
   
   public final RtmDatabase getDatabase()
   {
      return database;
   }
   
   
   
   public final ILog Log()
   {
      return log;
   }
   
   
   
   public void upgrade( int oldVersion, int newVersion ) throws SQLException
   {
      log.w( getClass(), "Upgrading database '" + tableName + "' from version "
         + oldVersion + " to " + newVersion
         + ", which will destroy all old data" );
      
      drop();
      create();
   }
   
   
   
   public void drop()
   {
      database.getWritable().execSQL( "DROP TABLE IF EXISTS " + tableName );
   }
   
   
   
   public long insert( ContentValues initialValues )
   {
      long newRowId = -1;
      
      if ( initialValues != null && initialValues.containsKey( BaseColumns._ID ) )
      {
         initialValues = getInitialValues( initialValues );
         final SQLiteDatabase db = database.getWritable();
         
         newRowId = db.insertWithOnConflict( tableName,
                                             BaseColumns._ID,
                                             initialValues,
                                             getInsertConflictHandler() );
         
         // TODO: This is content provider logic
         
         // final String id = initialValues.getAsString( BaseColumns._ID );
         
         // if ( rowId > 0 )
         // {
         // if ( TextUtils.isEmpty( id ) )
         // {
         // uri = ContentUris.withAppendedId( getContentUri(), rowId );
         // }
         // else
         // {
         // uri = Queries.contentUriWithId( getContentUri(), id );
         // }
         // }
      }
      
      return newRowId;
   }
   
   
   
   public int update( String id,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      final SQLiteDatabase db = database.getWritable();
      
      int numUpdated = 0;
      
      if ( id == null )
      {
         numUpdated = db.updateWithOnConflict( tableName,
                                               values,
                                               where,
                                               whereArgs,
                                               getUpdateConflictHandler() );
      }
      else
      {
         final StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         numUpdated = db.updateWithOnConflict( tableName,
                                               values,
                                               sb.toString(),
                                               whereArgs,
                                               getUpdateConflictHandler() );
      }
      
      return numUpdated;
   }
   
   
   
   public int delete( String id, String where, String[] whereArgs )
   {
      final SQLiteDatabase db = database.getWritable();
      
      int numDeleted = 0;
      
      if ( id == null )
      {
         numDeleted = db.delete( tableName, where, whereArgs );
      }
      else
      {
         final StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ')' );
         }
         
         numDeleted = db.delete( tableName, sb.toString(), whereArgs );
      }
      
      return numDeleted;
   }
   
   
   
   public int getInsertConflictHandler()
   {
      return SQLiteDatabase.CONFLICT_REPLACE;
   }
   
   
   
   public int getUpdateConflictHandler()
   {
      return SQLiteDatabase.CONFLICT_REPLACE;
   }
   
   
   
   protected static void initProjectionDependent( String[] projection,
                                                  Map< String, String > projectionMap,
                                                  Map< String, Integer > columnIndices )
   {
      for ( int i = 0; i < projection.length; i++ )
      {
         final String column = projection[ i ];
         projectionMap.put( column, column );
         columnIndices.put( column, i );
      }
   }
   
   
   
   public abstract void create() throws SQLException;
   
   
   
   public abstract String getDefaultSortOrder();
   
   
   
   public abstract Map< String, String > getProjectionMap();
   
   
   
   public abstract Map< String, Integer > getColumnIndices();
   
   
   
   public abstract String[] getProjection();
}
