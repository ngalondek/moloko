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
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;


class RtmListsTable extends AbstractTable
{
   public final static String TABLE_NAME = "lists";
   
   
   @Deprecated
   public final static class NewRtmListId
   {
      public String rtmListId;
   }
   
   public final static String SELECTION_EXCLUDE_DELETED_AND_ARCHIVED = RtmListsColumns.LIST_DELETED_DATE
      + " IS NULL AND " + RtmListsColumns.ARCHIVED + "=0";
   
   
   
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
      builder.append( RtmListsColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_LISTS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmListsColumns.RTM_LIST_ID );
      builder.append( " TEXT, " );
      builder.append( RtmListsColumns.LIST_NAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmListsColumns.LIST_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmListsColumns.LIST_MODIFIED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmListsColumns.LIST_DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmListsColumns.LOCKED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmListsColumns.ARCHIVED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmListsColumns.POSITION );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmListsColumns.IS_SMART_LIST );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmListsColumns.FILTER );
      builder.append( " TEXT" );
      builder.append( " );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmListsColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmListsColumns.PROJECTION;
   }
}
