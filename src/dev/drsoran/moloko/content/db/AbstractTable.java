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
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.rtm.Strings;


public abstract class AbstractTable implements ITable
{
   private final static String ITEM_ID_EQUALS = BaseColumns._ID + "=";
   
   private final String tableName;
   
   private SQLiteDatabase database;
   
   
   
   protected AbstractTable( String tableName )
   {
      this.tableName = tableName;
   }
   
   
   
   public void init( SQLiteDatabase database )
   {
      if ( database == null )
      {
         throw new IllegalArgumentException( "database" );
      }
      
      this.database = database;
   }
   
   
   
   @Override
   public final String getTableName()
   {
      return tableName;
   }
   
   
   
   public void upgrade( SQLiteDatabase database, int oldVersion, int newVersion ) throws SQLException
   {
      wipe( database );
   }
   
   
   
   public void wipe( SQLiteDatabase database )
   {
      dropIndices( database );
      drop( database );
      create( database );
      createIndices( database );
      insertInitialRows( database );
   }
   
   
   
   public void drop( SQLiteDatabase database )
   {
      database.execSQL( MessageFormat.format( "DROP TABLE IF EXISTS {0}",
                                              getTableName() ) );
   }
   
   
   
   public void createIndices( SQLiteDatabase database )
   {
   }
   
   
   
   public void dropIndices( SQLiteDatabase database )
   {
   }
   
   
   
   public void insertInitialRows( SQLiteDatabase database )
   {
   }
   
   
   
   @Override
   public void clear( SQLiteDatabase database )
   {
      database.delete( tableName, null, null );
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
         
         return database.rawQuery( rawQueryString, selectionArgs );
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
      
      final long rowId = database.insertWithOnConflict( getTableName(),
                                                        BaseColumns._ID,
                                                        initialValues,
                                                        getInsertConflictHandler() );
      
      if ( rowId == Constants.NO_ID )
      {
         throw new SQLiteException( MessageFormat.format( "Failed to insert content values in table ''{0}''",
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
      if ( values == null )
      {
         throw new IllegalArgumentException( "values" );
      }
      
      if ( id != Constants.NO_ID )
      {
         throwIfContentValuesIdDiffers( id, values );
         
         if ( values.size() > 0 )
         {
            where = itemIdAndWhereClause( id, where );
         }
      }
      
      if ( values.size() > 0 )
      {
         return database.update( getTableName(), values, where, whereArgs );
      }
      
      return 0;
   }
   
   
   
   @Override
   public int delete( long id, String where, String[] whereArgs )
   {
      int numDeleted = 0;
      
      if ( id == Constants.NO_ID )
      {
         numDeleted = database.delete( getTableName(), where, whereArgs );
      }
      else
      {
         where = itemIdAndWhereClause( id, where );
         numDeleted = database.delete( getTableName(), where, whereArgs );
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
   
   
   
   public abstract void create( SQLiteDatabase database ) throws SQLException;
}
