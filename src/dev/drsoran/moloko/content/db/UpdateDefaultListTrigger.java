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
import dev.drsoran.moloko.content.db.TableColumns.RtmSettingsColumns;
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


/**
 * @brief If the default list setting gets updated, also update the RTM default list ID.
 * 
 *        The other direction, if the RTM default list ID gets updated by a sync, is handled by the sync handler to
 *        avoid cyclic updates.
 */
class UpdateDefaultListTrigger extends AbstractTrigger
{
   
   public UpdateDefaultListTrigger()
   {
      super( RtmSettingsTable.TABLE_NAME + "_update_default_list" );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER UPDATE OF " );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( " ON " );
      builder.append( RtmSettingsTable.TABLE_NAME );
      builder.append( " FOR EACH ROW BEGIN UPDATE " );
      builder.append( RtmSettingsTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( RtmSettingsColumns.RTM_DEFAULTLIST_ID );
      builder.append( " = (SELECT " );
      builder.append( RtmTasksListColumns.RTM_LIST_ID );
      builder.append( " FROM " );
      builder.append( RtmTasksListsTable.TABLE_NAME );
      builder.append( " WHERE new." );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( " = " );
      builder.append( RtmTasksListColumns._ID );
      builder.append( "); END;" );
      
      database.execSQL( builder.toString() );
   }
}
