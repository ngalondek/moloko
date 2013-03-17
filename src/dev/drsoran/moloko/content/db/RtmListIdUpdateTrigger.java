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
import dev.drsoran.moloko.content.db.Columns.RtmListsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.Columns.RtmTaskSeriesColumns;


/**
 * If a list ID gets updated (e.g. after inserting on RTM side), we also update all referenced taskseries and default
 * list in settings
 */
class RtmListIdUpdateTrigger extends Trigger
{
   
   public RtmListIdUpdateTrigger( RtmDatabase database )
   {
      super( database, RtmListsTable.TABLE_NAME + "_update_list" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder stringBuilder = new StringBuilder();
      
      stringBuilder.append( "CREATE TRIGGER " );
      stringBuilder.append( RtmListsTable.TABLE_NAME );
      stringBuilder.append( "_update_list AFTER UPDATE OF " );
      stringBuilder.append( RtmListsColumns._ID );
      stringBuilder.append( " ON " );
      stringBuilder.append( RtmListsTable.TABLE_NAME );
      stringBuilder.append( " FOR EACH ROW BEGIN UPDATE " );
      stringBuilder.append( RtmTaskSeriesTable.TABLE_NAME );
      stringBuilder.append( " SET " );
      stringBuilder.append( RtmTaskSeriesColumns.LIST_ID );
      stringBuilder.append( " = new." );
      stringBuilder.append( RtmListsColumns._ID );
      stringBuilder.append( " WHERE " );
      stringBuilder.append( RtmTaskSeriesColumns.LIST_ID );
      stringBuilder.append( " = old." );
      stringBuilder.append( RtmListsColumns._ID );
      stringBuilder.append( "; UPDATE " );
      stringBuilder.append( RtmSettingsTable.TABLE_NAME );
      stringBuilder.append( " SET " );
      stringBuilder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      stringBuilder.append( " = new." );
      stringBuilder.append( RtmListsColumns._ID );
      stringBuilder.append( " WHERE " );
      stringBuilder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      stringBuilder.append( " = old." );
      stringBuilder.append( RtmListsColumns._ID );
      stringBuilder.append( "; END;" );
      
      getDatabase().getWritable().execSQL( stringBuilder.toString() );
   }
}
