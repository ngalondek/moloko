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
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


class RtmTasksListsTable extends AbstractTable
{
   public final static String TABLE_NAME = "lists";
   
   
   
   public RtmTasksListsTable( RtmDatabase database )
   {
      super( database, TABLE_NAME );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TABLE " );
      builder.append( TABLE_NAME );
      builder.append( "( " );
      builder.append( RtmTasksListColumns._ID );
      builder.append( " INTEGER NOT NULL CONSTRAINT PK_LISTS PRIMARY KEY AUTOINCREMENT, " );
      builder.append( RtmTasksListColumns.LIST_NAME );
      builder.append( " TEXT NOT NULL, " );
      builder.append( RtmTasksListColumns.LIST_CREATED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTasksListColumns.LIST_MODIFIED_DATE );
      builder.append( " INTEGER NOT NULL, " );
      builder.append( RtmTasksListColumns.LIST_DELETED_DATE );
      builder.append( " INTEGER, " );
      builder.append( RtmTasksListColumns.LOCKED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmTasksListColumns.ARCHIVED );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmTasksListColumns.POSITION );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmTasksListColumns.IS_SMART_LIST );
      builder.append( " INTEGER NOT NULL DEFAULT 0, " );
      builder.append( RtmTasksListColumns.FILTER );
      builder.append( " TEXT, " );
      builder.append( RtmTasksListColumns.RTM_LIST_ID );
      builder.append( " TEXT" );
      builder.append( " );" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
   
   
   
   @Override
   public String getDefaultSortOrder()
   {
      return RtmTasksListColumns.DEFAULT_SORT_ORDER;
   }
   
   
   
   @Override
   public String[] getProjection()
   {
      return RtmTasksListColumns.TABLE_PROJECTION;
   }
}
