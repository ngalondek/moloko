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


/**
 * If a list gets deleted, check the default list setting
 */
class DefaultListSettingConsistencyTrigger extends Trigger
{
   
   public DefaultListSettingConsistencyTrigger( RtmDatabase database )
   {
      super( database, RtmSettingsTable.TABLE_NAME + "_default_list" );
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " AFTER DELETE ON " );
      builder.append( RtmListsTable.TABLE_NAME );
      builder.append( " BEGIN UPDATE " );
      builder.append( RtmSettingsTable.TABLE_NAME );
      builder.append( " SET " );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( " = NULL WHERE old." );
      builder.append( RtmListsColumns._ID );
      builder.append( " = " );
      builder.append( RtmSettingsColumns.DEFAULTLIST_ID );
      builder.append( "; END;" );
      
      getDatabase().getWritable().execSQL( builder.toString() );
   }
}
