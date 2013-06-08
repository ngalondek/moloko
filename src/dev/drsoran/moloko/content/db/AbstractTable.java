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

import java.text.MessageFormat;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;
import dev.drsoran.moloko.ILog;
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.util.Strings;


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
               MessageFormat.format( "Upgrading database ''{0}'' from version {1} to {2}, which will destroy all old data",
                                     getTableName(),
                                     oldVersion,
                                     newVersion ) );
      
      dropIndices();
      drop();
      create();
      createIndices();
   }
   
   
   
   public void drop()
   {
      getDatabase().getWritable()
                   .execSQL( MessageFormat.format( "DROP TABLE IF EXISTS {0}",
                                                   getTableName() ) );
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
      try
      {
         final SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
         builder.setTables( getTableName() );
         
         final String rawQueryString = builder.buildQuery( projection,
                                                           selection,
                                                           null,
                                                           null,
                                                           sortOrder,
                                                           null );
         
         return getDatabase().getReadable().rawQuery( rawQueryString,
                                                      selectionArgs );
      }
      catch ( Exception e )
      {
         throw new SQLiteException( MessageFormat.format( "Failed to perform a query for table ''{0}''",
                                                          getTableName() ),
                                    e );
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
         throw new SQLException( MessageFormat.format( "Failed to insert content values in table ''{0}''",
                                                       getTableName() ) );
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
         where = itemIdAndWhereClause( id, where );
         
         final SQLiteDatabase db = getDatabase().getWritable();
         numUpdated = db.update( getTableName(), values, where, whereArgs );
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
         where = itemIdAndWhereClause( id, where );
         numDeleted = db.delete( getTableName(), where, whereArgs );
      }
      
      return numDeleted;
   }
   
   
   
   private String itemIdAndWhereClause( long id, String where )
   {
      final StringBuilder sb = new StringBuilder( ITEM_ID_EQUALS ).append( id );
      
      if ( !Strings.isNullOrEmpty( where ) )
      {
         sb.append( " AND (" ).append( where ).append( ")" );
      }
      
      return sb.toString();
   }
   
   
   
   private static void throwIfContentValuesIdDiffers( long existingId,
                                                      ContentValues contentValues ) throws IllegalArgumentException
   {
      final Long contentValueId = contentValues.getAsLong( BaseColumns._ID );
      
      if ( contentValueId != null && contentValueId.longValue() != existingId )
      {
         throw new IllegalArgumentException( MessageFormat.format( "Trying to change the element ID from ''{0}'' to ''{1}''",
                                                                   existingId,
                                                                   contentValueId.longValue() ) );
      }
   }
   
   
   
   public int getInsertConflictHandler()
   {
      return SQLiteDatabase.CONFLICT_REPLACE;
   }
   
   
   
   public abstract void create() throws SQLException;
}
