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
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


public class RtmTaskSeriesQuery
{
   private final RtmDatabase database;
   
   private final RtmTaskSeriesTable taskSeriesTable;
   
   
   
   public RtmTaskSeriesQuery( RtmDatabase database,
      RtmTaskSeriesTable taskSeriesTable )
   {
      this.database = database;
      this.taskSeriesTable = taskSeriesTable;
   }
   
   
   
   public Cursor getTaskSeries( String taskSeriesId )
   {
      final Cursor c = database.getReadable()
                               .query( taskSeriesTable.getTableName(),
                                       taskSeriesTable.getProjection(),
                                       RtmTaskSeriesColumns._ID + "="
                                          + taskSeriesId,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getLocalCreatedTaskSerieses()
   {
      final Cursor c = database.getQuery( CreationsQuery.class )
                               .getCreationsForTable( taskSeriesTable.getTableName() );
      return c;
   }
   
   
   
   public Cursor getAllTaskSeries()
   {
      final Cursor c = database.getReadable()
                               .query( taskSeriesTable.getTableName(),
                                       taskSeriesTable.getProjection(),
                                       null,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
   
   
   
   public Cursor getAllTaskSeriesesForList( String rtmListId )
   {
      final Cursor c = database.getReadable()
                               .query( taskSeriesTable.getTableName(),
                                       taskSeriesTable.getProjection(),
                                       RtmTaskSeriesColumns.LIST_ID + "="
                                          + rtmListId,
                                       null,
                                       null,
                                       null,
                                       null );
      return c;
   }
}
