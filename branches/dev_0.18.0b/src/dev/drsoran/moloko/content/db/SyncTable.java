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
import dev.drsoran.moloko.content.db.Columns.SyncColumns;


class SyncTable extends Table
{
   public final static String TABLE_NAME = "sync";
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { SyncColumns._ID, SyncColumns.LAST_IN, SyncColumns.LAST_OUT };
   
   private final static Map< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public SyncTable( RtmDatabase database )
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
      builder.append( SyncColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_SYNC PRIMARY KEY AUTOINCREMENT, " );
      builder.append( SyncColumns.LAST_IN );
      builder.append( " INTEGER, " );
      builder.append( SyncColumns.LAST_OUT );
      builder.append( " INTEGER );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return null;
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
