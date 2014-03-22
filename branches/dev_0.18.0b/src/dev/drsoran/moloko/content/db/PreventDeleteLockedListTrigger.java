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
import dev.drsoran.moloko.content.db.TableColumns.RtmTasksListColumns;


/**
 * @brief A locked tasks list must not be deleted.
 */
class PreventDeleteLockedListTrigger extends AbstractTrigger
{
   public PreventDeleteLockedListTrigger()
   {
      super( RtmTasksListsTable.TABLE_NAME + "_prevent_delete_locked_list" );
   }
   
   
   
   @Override
   public void create( SQLiteDatabase database ) throws SQLException
   {
      final StringBuilder builder = new StringBuilder();
      
      builder.append( "CREATE TRIGGER " );
      builder.append( getTriggerName() );
      builder.append( " BEFORE DELETE ON " );
      builder.append( RtmTasksListsTable.TABLE_NAME );
      builder.append( " WHEN old." );
      builder.append( RtmTasksListColumns.LOCKED );
      builder.append( " != 0 BEGIN SELECT RAISE( FAIL, 'Could not delete locked list" );
      builder.append( "'); END;" );
      
      database.execSQL( builder.toString() );
   }
}
