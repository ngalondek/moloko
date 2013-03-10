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
import dev.drsoran.moloko.content.db.Columns.RtmLists;
import dev.drsoran.provider.Rtm.Settings;
import dev.drsoran.provider.Rtm.TaskSeries;


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
      getDatabase().getWritable().execSQL( "CREATE TRIGGER "
         + RtmListsTable.TABLE_NAME + "_update_list AFTER UPDATE OF "
         + RtmLists._ID + " ON " + RtmListsTable.TABLE_NAME
         + " FOR EACH ROW BEGIN UPDATE " + TaskSeries.PATH + " SET "
         + TaskSeries.LIST_ID + " = new." + RtmLists._ID + " WHERE "
         + TaskSeries.LIST_ID + " = old." + RtmLists._ID + "; UPDATE "
         + Settings.PATH + " SET " + Settings.DEFAULTLIST_ID + " = new."
         + RtmLists._ID + " WHERE " + Settings.DEFAULTLIST_ID + " = old."
         + RtmLists._ID + "; END;" );
   }
}
