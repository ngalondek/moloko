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


/**
 * If a list gets deleted, check the default list setting
 */
class DefaultListSettingConsistencyTrigger extends Trigger
{
   
   public DefaultListSettingConsistencyTrigger( RtmDatabase database )
   {
      super( database, Settings.PATH + "_default_list" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      getDatabase().getWritable().execSQL( "CREATE TRIGGER " + getTriggerName()
         + " AFTER DELETE ON " + RtmListsTable.TABLE_NAME + " BEGIN UPDATE "
         + Settings.PATH + " SET " + Settings.DEFAULTLIST_ID
         + " = NULL WHERE old." + RtmLists._ID + " = "
         + Settings.DEFAULTLIST_ID + "; END;" );
   }
}
