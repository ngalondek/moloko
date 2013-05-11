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
import dev.drsoran.moloko.content.Constants;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;


class RtmRawTasksTable extends AbstractTable
{
   public final static String TABLE_NAME = "rawtasks";
   
   
   
   public RtmRawTasksTable( RtmDatabase database )
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
      builder.append( "( " );
      builder.append( RtmRawTaskColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_RAWTASKS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmRawTaskColumns.DUE_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmRawTaskColumns.HAS_DUE_TIME );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmRawTaskColumns.ADDED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmRawTaskColumns.COMPLETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmRawTaskColumns.DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmRawTaskColumns.PRIORITY );
      builder.append( " CHAR(1) NOT NULL DEFAULT 'n', " );
      builder.append( RtmRawTaskColumns.POSTPONED );
      builder.append( " INTEGER DEFAULT 0, " );
      builder.append( RtmRawTaskColumns.ESTIMATE );
      builder.append( " TEXT, " );
      builder.append( RtmRawTaskColumns.ESTIMATE_MILLIS );
      builder.append( " INTEGER DEFAULT " );
      builder.append( Constants.NO_TIME );
      builder.append( ", " );
      builder.append( RtmRawTaskColumns.RTM_RAWTASK_ID );
      builder.append( " TEXT, " );
      builder.append( RtmRawTaskColumns.TASKSERIES_ID );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( "CONSTRAINT rawtasks_taskseries_ref FOREIGN KEY ( " );
      builder.append( RtmRawTaskColumns.TASKSERIES_ID );
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
      return RtmRawTaskColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmRawTaskColumns.TABLE_PROJECTION;
   }
}
