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

import android.database.Cursor;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;


class RawTasksQuery
{
   private final RtmDatabase database;
   
   private final RawTasksTable rawTasksTable;
   
   private final static String SEL_QUERY_NON_DELETED;
   
   private final static String SEL_QUERY_BY_TASKSERIES;
   
   private final static String SEL_QUERY_NON_DELETED_BY_TASKSERIES;
   
   static
   {
      SEL_QUERY_NON_DELETED = new StringBuilder( RawTasksColumns._ID ).append( "=? AND " )
                                                                      .append( RawTasksColumns.DELETED_DATE )
                                                                      .append( " IS NULL" )
                                                                      .toString();
      
      SEL_QUERY_BY_TASKSERIES = RawTasksColumns.TASKSERIES_ID + "=?";
      
      SEL_QUERY_NON_DELETED_BY_TASKSERIES = new StringBuilder( SEL_QUERY_BY_TASKSERIES ).append( " AND " )
                                                                                        .append( RawTasksColumns.DELETED_DATE )
                                                                                        .append( " IS NULL" )
                                                                                        .toString();
   }
   
   
   
   public RawTasksQuery( RtmDatabase database, RawTasksTable rawTasksTable )
   {
      this.database = database;
      this.rawTasksTable = rawTasksTable;
   }
   
   
   
   public Cursor getRawTask( String rawTaskId )
   {
      final Cursor c = database.getReadable()
                               .query( rawTasksTable.getTableName(),
                                       rawTasksTable.getProjection(),
                                       SEL_QUERY_NON_DELETED,
                                       new String[]
                                       { rawTaskId },
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getRawTasksOfTaskseries( String taskSeriesId )
   {
      return getAllRawTasksOfTaskseries( taskSeriesId, false );
   }
   
   
   
   public Cursor getAllRawTasksOfTaskseries( String taskSeriesId,
                                             boolean includeDeleted )
   {
      final String selection = includeDeleted
                                             ? SEL_QUERY_BY_TASKSERIES
                                             : SEL_QUERY_NON_DELETED_BY_TASKSERIES;
      
      final Cursor c = database.getReadable()
                               .query( rawTasksTable.getTableName(),
                                       rawTasksTable.getProjection(),
                                       selection,
                                       new String[]
                                       { taskSeriesId },
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public int getDeletedRawTasksCount()
   {
      int cnt = -1;
      Cursor c = null;
      
      try
      {
         c = database.getReadable().query( rawTasksTable.getTableName(),
                                           new String[]
                                           { RawTasksColumns._ID },
                                           RawTasksColumns.DELETED_DATE
                                              + " IS NOT NULL",
                                           null,
                                           null,
                                           null,
                                           null );
         cnt = c.getCount();
      }
      finally
      {
         if ( c != null )
         {
            c.close();
         }
      }
      
      return cnt;
   }
}
