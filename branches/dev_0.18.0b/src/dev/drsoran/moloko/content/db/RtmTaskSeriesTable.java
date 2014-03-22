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
import dev.drsoran.moloko.content.db.TableColumns.RtmLocationColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


class RtmTaskSeriesTable extends AbstractTable
{
   public final static String TABLE_NAME = "taskseries";
   
   
   
   public RtmTaskSeriesTable()
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
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_TASKSERIES PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.TASKSERIES_MODIFIED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.TASKSERIES_NAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.SOURCE );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.URL );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.RECURRENCE );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.RECURRENCE_EVERY );
      builder.append( " INTEGER, " );
      builder.append( RtmTaskSeriesColumns.LOCATION_ID );
      builder.append( " INTEGER, " );
      builder.append( RtmTaskSeriesColumns.LIST_ID );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.TAGS );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.RTM_TASKSERIES_ID );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.RTM_LIST_ID );
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.RTM_LOCATION_ID );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT list FOREIGN KEY ( " );
      builder.append( RtmTaskSeriesColumns.LIST_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmTasksListsTable.TABLE_NAME );
      builder.append( " ( \"" );
      builder.append( RtmTasksListColumns._ID );
      builder.append( "\" ), " );
      builder.append( "CONSTRAINT location FOREIGN KEY ( " );
      builder.append( RtmTaskSeriesColumns.LOCATION_ID );
      builder.append( " ) REFERENCES " );
      builder.append( RtmLocationsTable.TABLE_NAME );
      builder.append( " ( \"" );
      builder.append( RtmLocationColumns._ID );
      builder.append( "\" )" );
      builder.append( ");" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmTaskSeriesColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmTaskSeriesColumns.TABLE_PROJECTION;
   }
}
