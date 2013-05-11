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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Constants;


abstract class AbstractTable implements ITable
{
   private final static String ITEM_ID_EQUALS = BaseColumns._ID + "=";
   
   private final ILog log;
   
   private final RtmDatabase database;
   
   private final String tableName;
   
   
   
   protected AbstractTable( RtmDatabase database, String tableName )
   {
      this.log = database.Log();
      this.database = database;
      this.tableName = tableName;
   }
   
   
   
   @Override
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
      Log().w( AbstractTable.class,
               "Upgrading database '" + getTableName() + "' from version "
                  + oldVersion + " to " + newVersion
                  + ", which will destroy all old data" );
      
      dropIndices();
      drop();
      create();
      createIndices();
   }
   
   
   
   public void drop()
   {
      getDatabase().getWritable().execSQL( "DROP TABLE IF EXISTS "
         + getTableName() );
   }
   
   
   
   public void createIndices()
   {
   }
   
   
   
   public void dropIndices()
   {
   }
   
   
   
   @Override
   public Cursor query( String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder ) throws SQLException
   {
      final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
      builder.setTables( getTableName() );
      
      try
      {
         return builder.query( getDatabase().getReadable(),
                               projection,
                               selection,
                               selectionArgs,
                               null,
                               null,
                               sortOrder );
      }
      catch ( Exception e )
      {
         throw new SQLiteException( "Failed to perform a query for table '"
            + getTableName() + "'", e );
      }
   }
   
   
   
   @Override
   public long insert( ContentValues initialValues ) throws SQLException
   {
      if ( initialValues == null )
      {
         throw new IllegalArgumentException( "initialValues" );
      }
      
      throwIfContentValuesIdDiffers( Constants.NO_ID, initialValues );
      
      final SQLiteDatabase db = getDatabase().getWritable();
      final long rowId = db.insertWithOnConflict( getTableName(),
                                                  BaseColumns._ID,
                                                  initialValues,
                                                  getInsertConflictHandler() );
      
      if ( rowId == Constants.NO_ID )
      {
         throw new SQLException( "Failed to insert content values in table '"
            + getTableName() + "'" );
      }
      
      return rowId;
   }
   
   
   
   @Override
   public int update( long id,
                      ContentValues values,
                      String where,
                      String[] whereArgs )
   {
      if ( id == Constants.NO_ID )
      {
         throw new IllegalArgumentException( "id" );
      }
      
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      throwIfContentValuesIdDiffers( id, values );
      
      int numUpdated = 0;
      
      if ( values.size() > 0 )
      {
         final SQLiteDatabase db = getDatabase().getWritable();
         numUpdated = db.updateWithOnConflict( getTableName(),
                                               values,
                                               where,
                                               whereArgs,
                                               getUpdateConflictHandler() );
      }
      
      return numUpdated;
   }
   
   
   
   @Override
   public int delete( long id, String where, String[] whereArgs )
   {
      final SQLiteDatabase db = getDatabase().getWritable();
      
      int numDeleted = 0;
      
      if ( id == Constants.NO_ID )
      {
         numDeleted = db.delete( getTableName(), where, whereArgs );
      }
      else
      {
         final StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
         
         if ( !TextUtils.isEmpty( where ) )
         {
            sb.append( " AND (" ).append( where ).append( ")" );
         }
         
         numDeleted = db.delete( getTableName(), sb.toString(), whereArgs );
      }
      
      return numDeleted;
   }
   
   
   
   private static void throwIfContentValuesIdDiffers( long existingId,
                                                      ContentValues contentValues ) throws IllegalArgumentException
   {
      final Long contentValueId = contentValues.getAsLong( BaseColumns._ID );
      
      if ( contentValueId != null && contentValueId.longValue() != existingId )
      {
         throw new IllegalArgumentException( "Trying to change the element ID from '"
            + existingId + "' to '" + contentValueId.longValue() + "'" );
      }
   }
   
   
   
   public int getInsertConflictHandler()
   {
      return SQLiteDatabase.CONFLICT_REPLACE;
   }
   
   
   
   public int getUpdateConflictHandler()
   {
      return SQLiteDatabase.CONFLICT_REPLACE;
   }
   
   
   
   public abstract void create() throws SQLException;
}
