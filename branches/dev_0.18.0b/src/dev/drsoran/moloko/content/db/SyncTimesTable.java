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

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.db.AbstractTable;
import dev.drsoran.moloko.content.Columns.SyncTimesColumns;


class SyncTimesTable extends AbstractTable
{
   public final static String TABLE_NAME = "synctimes";
   
   
   
   public SyncTimesTable()
   {
      super( TABLE_NAME );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( "( " );
      builder.append( SyncTimesColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_SYNC PRIMARY KEY DEFAULT " );
      builder.append( SyncTimesColumns.SINGLETON_ID );
      builder.append( ", " );
      builder.append( SyncTimesColumns.LAST_IN );
      builder.append( " INTEGER DEFAULT -1, " );
      builder.append( SyncTimesColumns.LAST_OUT );
      builder.append( " INTEGER DEFAULT -1);" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void insertInitialRows( SQLiteDatabase database )
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "INSERT INTO " );
      builder.append( TABLE_NAME );
      builder.append( " DEFAULT VALUES;" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public void clear( SQLiteDatabase database )
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "UPDATE " );
      builder.append( TABLE_NAME );
      builder.append( " SET " );
      builder.append( SyncTimesColumns.LAST_IN );
      builder.append( " = -1, " );
      builder.append( SyncTimesColumns.LAST_OUT );
      builder.append( " = -1" );
      builder.append( ";" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return null;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return SyncTimesColumns.PROJECTION;
   }
}
