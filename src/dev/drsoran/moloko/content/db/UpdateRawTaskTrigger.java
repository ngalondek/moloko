/* 
 *	Copyright (c) 2013 Ronny Röhricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny Röhricht - implementation
 */

package dev.drsoran.moloko.content.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dev.drsoran.moloko.content.db.Columns.RawTasksColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


/**
 * If a RawTask's taskseries_id gets updated, delete the former taskseries if it contains no RawTasks anymore.
 */
class UpdateRawTaskTrigger extends Trigger
{
   public UpdateRawTaskTrigger( RtmDatabase database )
   {
      super( database, RawTasksTable.TABLE_NAME + "_update_taskseries_id" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final SQLiteDatabase db = getDatabase().getWritable();
      final String rawTasks = RawTasksTable.TABLE_NAME;
      final String taskSeries = RtmTaskSeriesTable.TABLE_NAME;
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER UPDATE OF " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " ON " );
      builder.append( rawTasks );
      builder.append( " FOR EACH ROW BEGIN DELETE FROM " );
      builder.append( taskSeries );
      builder.append( " WHERE " );
      builder.append( taskSeries );
      builder.append( "." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " = old." );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " AND NOT EXISTS (SELECT " );
      builder.append( RawTasksColumns._ID );
      builder.append( " FROM " );
      builder.append( rawTasks );
      builder.append( " WHERE old." );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( " = " );
      builder.append( RawTasksColumns.TASKSERIES_ID );
      builder.append( "); END;" );
      
      db.execSQL( builder.toString() );
   }
}
