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


class RtmLocationsTable extends Table
{
   public final static String TABLE_NAME = "locations";
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { RtmLocationsColumns._ID, RtmLocationsColumns.LOCATION_NAME,
    RtmLocationsColumns.LONGITUDE, RtmLocationsColumns.LATITUDE,
    RtmLocationsColumns.ADDRESS, RtmLocationsColumns.VIEWABLE,
    RtmLocationsColumns.ZOOM };
   
   private final static Map< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public RtmLocationsTable( RtmDatabase database )
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
      builder.append( RtmLocationsColumns._ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmLocationsColumns.LOCATION_NAME );
      builder.append( " NOTE_TEXT, " );
      builder.append( RtmLocationsColumns.LONGITUDE );
      builder.append( " REAL NOT NULL, " );
      builder.append( RtmLocationsColumns.LATITUDE );
      builder.append( " REAL NOT NULL, " );
      builder.append( RtmLocationsColumns.ADDRESS );
      builder.append( " NOTE_TEXT, " );
      builder.append( RtmLocationsColumns.VIEWABLE );
      builder.append( " INTEGER NOT NULL DEFAULT 1, " );
      builder.append( RtmLocationsColumns.ZOOM );
      builder.append( " INTEGER, " );
      builder.append( "CONSTRAINT PK_TASKSERIES PRIMARY KEY ( \"" );
      builder.append( RtmLocationsColumns._ID );
      builder.append( "\" )" );
      builder.append( " );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmLocationsColumns.DEFAULT_SORT_ORDER;
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
