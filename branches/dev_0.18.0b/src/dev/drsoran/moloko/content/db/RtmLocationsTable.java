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


class RtmLocationsTable extends AbstractTable
{
   public final static String TABLE_NAME = "locations";
   
   
   
   public RtmLocationsTable()
   {
      super( TABLE_NAME );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( " ( " );
      builder.append( RtmLocationColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_LOCATIONS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmLocationColumns.LOCATION_NAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmLocationColumns.LONGITUDE );
      builder.append( " REAL NOT NULL, " );
      builder.append( RtmLocationColumns.LATITUDE );
      builder.append( " REAL NOT NULL, " );
      builder.append( RtmLocationColumns.ADDRESS );
      builder.append( " TEXT, " );
      builder.append( RtmLocationColumns.VIEWABLE );
      builder.append( " INTEGER NOT NULL DEFAULT 1, " );
      builder.append( RtmLocationColumns.ZOOM );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmLocationColumns.RTM_LOCATION_ID );
      builder.append( " TEXT NOT NULL" );
      builder.append( " );" );
      
      database.execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmLocationColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmLocationColumns.TABLE_PROJECTION;
   }
}
