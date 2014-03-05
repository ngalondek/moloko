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
import dev.drsoran.db.AbstractTrigger;
import dev.drsoran.moloko.content.db.TableColumns.RtmRawTaskColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;


/**
 * @brief If a RawTask gets deleted, also delete the associated taskseries if it contains no RawTasks anymore
 */
class DeleteRawTaskTrigger extends AbstractTrigger
{
   public DeleteRawTaskTrigger()
   {
      super( RtmRawTasksTable.TABLE_NAME + "_delete_rawtask" );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final String rawTasks = RtmRawTasksTable.TABLE_NAME;
      final String taskSeries = RtmTaskSeriesTable.TABLE_NAME;
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER DELETE ON " );
      builder.append( rawTasks );
      builder.append( " FOR EACH ROW BEGIN DELETE FROM " );
      builder.append( taskSeries );
      builder.append( " WHERE " );
      builder.append( taskSeries );
      builder.append( "." );
      builder.append( RtmTaskSeriesColumns._ID );
      builder.append( " = old." );
      builder.append( RtmRawTaskColumns.TASKSERIES_ID );
      builder.append( " AND NOT EXISTS (SELECT " );
      builder.append( RtmRawTaskColumns._ID );
      builder.append( " FROM " );
      builder.append( rawTasks );
      builder.append( " WHERE old." );
      builder.append( RtmRawTaskColumns.TASKSERIES_ID );
      builder.append( " = " );
      builder.append( RtmRawTaskColumns.TASKSERIES_ID );
      builder.append( "); END;" );
      
      database.execSQL( builder.toString() );
   }
}
