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
import dev.drsoran.moloko.content.db.TableColumns.RtmTaskSeriesColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


/**
 * If a taskseries RTM list ID gets updated, set the corresponding list ID.
 */
class UpdateTaskSeriesRtmListIdTrigger extends AbstractTrigger
{
   public UpdateTaskSeriesRtmListIdTrigger( SQLiteDatabase database )
   {
      super( database, RtmTaskSeriesTable.TABLE_NAME + "_update_rtm_list_id" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final SQLiteDatabase db = getDatabase();
      
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER UPDATE OF " );
      builder.append( RtmTaskSeriesColumns.RTM_LIST_ID );
      builder.append( " ON " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " FOR EACH ROW BEGIN UPDATE " );
      builder.append( RtmTaskSeriesTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( RtmTaskSeriesColumns.LIST_ID );
      builder.append( " = (SELECT " );
      builder.append( RtmTasksListColumns._ID );
      builder.append( " FROM " );
      builder.append( RtmTasksListsTable.TABLE_NAME );
      builder.append( " WHERE " );
      builder.append( RtmTasksListColumns.RTM_LIST_ID );
      builder.append( " = new." );
      builder.append( RtmTaskSeriesColumns.RTM_LIST_ID );
      builder.append( ")" );
      builder.append( "; END;" );
      
      db.execSQL( builder.toString() );
   }
}
