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
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


class RawTasksTable extends AbstractTable
{
   public final static String TABLE_NAME = "rawtasks";
   
   
   
   public RawTasksTable( RtmDatabase database )
   {
      super( database, TABLE_NAME );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final SQLiteDatabase db = getDatabase().getWritable();
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( " ( " );
      builder.append( RawTasksColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_RAWTASKS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RawTasksColumns.RTM_RAWTASK_ID );
      builder.append( " TEXT, " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RawTasksColumns.DUE_DATE );
      builder.append( " INTEGER, " );
      builder.append( RawTasksColumns.HAS_DUE_TIME );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RawTasksColumns.ADDED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RawTasksColumns.COMPLETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RawTasksColumns.DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RawTasksColumns.PRIORITY );
      builder.append( " CHAR(1) NOT NULL DEFAULT 'n', " );
      builder.append( RawTasksColumns.POSTPONED );
      builder.append( " INTEGER DEFAULT 0, " );
      builder.append( RawTasksColumns.ESTIMATE );
      builder.append( " TEXT, " );
      builder.append( RawTasksColumns.ESTIMATE_MILLIS );
      builder.append( " INTEGER DEFAULT -1, " );
      builder.append( "CONSTRAINT rawtasks_taskseries_ref FOREIGN KEY ( " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( "( \"" );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "\" )" );
      builder.append( " );" );
      
      db.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RawTasksColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RawTasksColumns.PROJECTION;
   }
}
