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
import android.provider.BaseColumns;
import dev.drsoran.provider.Rtm.Modifications;


/**
 * If a RTM element gets deleted we also delete all possible open modifications for this element.
 */
class DeleteModificationsTrigger extends Trigger
{
   private final String tableName;
   
   
   
   public DeleteModificationsTrigger( RtmDatabase database, String tableName )
   {
      super( database, tableName + "_delete_modifications" );
      this.tableName = tableName;
   }
   
   
   
   @Override
   public void create() throws SQLException
   {
      getDatabase().getWritable().execSQL( "CREATE TRIGGER " + getTriggerName()
         + " AFTER DELETE ON " + tableName + " FOR EACH ROW BEGIN DELETE FROM "
         + Modifications.PATH + " WHERE " + Modifications.ENTITY_URI + " = '"
         + tableName + "' || '/' || old." + BaseColumns._ID + ";" + "END;" );
   }
}
