/*
 * Copyright (c) 2010 Ronny R�hricht
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
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.text.TextUtils;
import dev.drsoran.moloko.ILog;


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
   
   
   
   public void createIndices()
   {
   }
   
   
   
   public void upgrade( int oldVersion, int newVersion ) throws SQLException
   {
      log.w( getClass(), "Upgrading database '" + tableName + "' from version "
         + oldVersion + " to " + newVersion
         + ", which will destroy all old data" );
      
      dropIndices();
      drop();
      create();
      createIndices();
   }
   
   
   
   public void drop()
   {
      database.getWritable().execSQL( "DROP TABLE IF EXISTS " + tableName );
   }
   
   
   
   public void dropIndices()
   {
   }
   
   
   
   @Override
   public long insert( ContentValues initialValues )
   {
      if ( initialValues == null )
      {
         throw new IllegalArgumentException( "initialValues" );
      }
      
      final SQLiteDatabase db = database.getWritable();
      final long rowId = db.insertWithOnConflict( tableName,
                                                  BaseColumns._ID,
                                                  initialValues,
                                                  getInsertConflictHandler() );
      
      if ( rowId == -1L )
      {
         throw new SQLException( "Failed to insert content values" );
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
      
      int numUpdated = 0;
      
      if ( values.size() > 0 )
      {
         final SQLiteDatabase db = database.getWritable();
         if ( id == -1L )
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
      }
      
      return numUpdated;
   }
   
   
   
   @Override
   public int delete( long id, String where, String[] whereArgs )
   {
      final SQLiteDatabase db = database.getWritable();
      
      int numDeleted = 0;
      
      if ( id == -1L )
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
   
   
   
   public abstract void create() throws SQLException;
   
   
   
   @Override
   public abstract String getDefaultSortOrder();
}