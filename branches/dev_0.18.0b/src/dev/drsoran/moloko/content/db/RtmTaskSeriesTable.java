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

import java.util.HashMap;
import java.util.Map;

import android.database.SQLException;
import dev.drsoran.moloko.content.db.Columns.RtmLocationsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


class RtmTaskSeriesTable extends Table
{
   public final static String TABLE_NAME = "taskseries";
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { RtmTaskSeriesColumns._ID, RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE,
    RtmTaskSeriesColumns.MODIFIED_DATE, RtmTaskSeriesColumns.TASKSERIES_NAME,
    RtmTaskSeriesColumns.SOURCE, RtmTaskSeriesColumns.URL,
    RtmTaskSeriesColumns.RECURRENCE, RtmTaskSeriesColumns.RECURRENCE_EVERY,
    RtmTaskSeriesColumns.LOCATION_ID, RtmTaskSeriesColumns.LIST_ID,
    RtmTaskSeriesColumns.TAGS };
   
   private final static Map< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public RtmTaskSeriesTable( RtmDatabase database )
   {
      super( database, TABLE_NAME );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( " ( " );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.TASKSERIES_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.MODIFIED_DATE );
      builder.append( " INTEGER, " );
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
      builder.append( " TEXT, " );
      builder.append( RtmTaskSeriesColumns.LIST_ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmTaskSeriesColumns.TAGS );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( "\" ), " );
      builder.append( "CONSTRAINT list FOREIGN KEY ( " );
      builder.append( RtmTaskSeriesColumns.LIST_ID );
      builder.append( " ) REFERENCES lists ( \"" );
      builder.append( RtmListsColumns._ID );
      builder.append( "\" ), " );
      builder.append( "CONSTRAINT location FOREIGN KEY ( " );
      builder.append( RtmTaskSeriesColumns.LOCATION_ID );
      builder.append( " ) REFERENCES locations ( \"" );
      builder.append( RtmLocationsColumns._ID );
      builder.append( "\" )" );
      builder.append( ");" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmTaskSeriesColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public Map< String, String > getProjectionMap()
   {
      return PROJECTION_MAP;
   }
   
   
   
   @Override
   public Map< String, Integer > getColumnIndices()
   {
      return COL_INDICES;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return PROJECTION;
   }
}
