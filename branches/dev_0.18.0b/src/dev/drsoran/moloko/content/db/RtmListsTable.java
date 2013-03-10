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
import dev.drsoran.moloko.content.db.Columns.RtmLists;


class RtmListsTable extends Table
{
   public final static String TABLE_NAME = "lists";
   
   
   @Deprecated
   public final static class NewRtmListId
   {
      public String rtmListId;
   }
   
   private final static Map< String, String > PROJECTION_MAP = new HashMap< String, String >();
   
   private final static String[] PROJECTION =
   { RtmLists._ID, RtmLists.LIST_NAME, RtmLists.CREATED_DATE,
    RtmLists.MODIFIED_DATE, RtmLists.LIST_DELETED, RtmLists.LOCKED,
    RtmLists.ARCHIVED, RtmLists.POSITION, RtmLists.IS_SMART_LIST,
    RtmLists.FILTER };
   
   private final static HashMap< String, Integer > COL_INDICES = new HashMap< String, Integer >();
   
   public final static String SELECTION_EXCLUDE_DELETED_AND_ARCHIVED = RtmLists.LIST_DELETED
      + " IS NULL AND " + RtmLists.ARCHIVED + "=0";
   
   static
   {
      initProjectionDependent( PROJECTION, PROJECTION_MAP, COL_INDICES );
   }
   
   
   
   public RtmListsTable( RtmDatabase database )
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
      builder.append( RtmLists._ID );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmLists.LIST_NAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmLists.CREATED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmLists.MODIFIED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmLists.LIST_DELETED );
      builder.append( " INTEGER, " );
      builder.append( RtmLists.LOCKED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmLists.ARCHIVED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmLists.POSITION );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmLists.IS_SMART_LIST );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmLists.FILTER );
      builder.append( " TEXT, " );
      builder.append( "CONSTRAINT PK_LISTS PRIMARY KEY ( \"" );
      builder.append( RtmLists._ID );
      builder.append( "\" ) );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmLists.DEFAULT_SORT_ORDER;
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
